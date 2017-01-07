package se.collagekoren.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import se.collagekoren.domain.*;
import se.collagekoren.repository.ImageRepository;
import se.collagekoren.repository.ProfileRepository;
import se.collagekoren.repository.RequestedProfileRepository;
import se.collagekoren.request.ProfileRequest;
import se.collagekoren.request.UpdateProfileRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.StreamSupport;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

/**
 * Created by Jonatan on 2015-11-26.
 */
@Controller
public class CollageProfileController {

    private ProfileRepository profileRepository;
    private RequestedProfileRepository requestedProfileRepository;
    private CurrentProfileFactory profileFactory;
    private ImageRepository imageRepository;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public CollageProfileController(ProfileRepository profileRepository, RequestedProfileRepository requestedProfileRepository, CurrentProfileFactory profileFactory, ImageRepository imageRepository) {
        this.profileRepository = profileRepository;
        this.requestedProfileRepository = requestedProfileRepository;
        this.profileFactory = profileFactory;
        this.imageRepository = imageRepository;
    }

    @RequestMapping("/profile/new")
    @ResponseBody
    public ProfileView newProfile(@RequestBody ProfileRequest request){
        Profile profile = new Profile(request.getName(), request.getBio(), request.getFbLink(), request.getLinkedInProfile(), request.getLastFmProfile(), request.getVoice(), null, null, request.getEmail());
        Profile result = profileRepository.save(profile);
        return ProfileView.profile(result);
    }

    @PostMapping("/profile/image/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void saveImage(@RequestParam("image") MultipartFile image, @PathVariable Integer id, OAuth2Authentication auth){
        try {
            imageRepository.save(new Image(id, image.getBytes()));
        } catch (IOException e) {
            logger.error("Unable to get store image", e);
        }
    }

    @GetMapping(value = "/profile/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getImage(@PathVariable Integer id, OAuth2Authentication auth2Authentication, HttpServletResponse response){
        if(!auth2Authentication.isAuthenticated() || !profileFactory.getCurrentProfile(auth2Authentication).isPresent()){
            throw noProfileImage(id);
        }
        response.setHeader("Cache-Control", "max-age=86400");
        return Optional.ofNullable(imageRepository.findOne(id)).map(Image::getImageData).orElseThrow(() -> noProfileImage(id));
    }

    private IllegalArgumentException noProfileImage(@PathVariable Integer id) {
        logger.error("No profile!");
        return new IllegalArgumentException("No profile image for " + id);
    }

    @RequestMapping("/profile/all")
    @ResponseBody
    public List<ProfileView> listAll(OAuth2Authentication authentication){
        Optional<Profile> currentProfile = profileFactory.getCurrentProfile(authentication);
        if(!currentProfile.isPresent()){
            return emptyList();
        }
        return StreamSupport.stream(profileRepository.findAll().spliterator(), false).map((profile) -> {
            if(currentProfile.filter(p -> p.getId().equals(profile.getId())).isPresent()){
                return ProfileView.loggedInProfile(profile);
            }
            return ProfileView.profile(profile);
        }).collect(toList());
    }

    @RequestMapping("/profile/voice/{voice}")
    @ResponseBody
    public Collection<Profile> allInVoice(@PathVariable Voice voice){
        return profileRepository.findByVoice(voice).collect(toList());
    }

    @RequestMapping({"/", "/start"})
    public String start(OAuth2Authentication authentication, Model model){
        model.addAttribute("principal", authentication);
        if(authentication != null && authentication.isAuthenticated()) {
            Map<String, Object> details = getUserDetails(authentication);
            Optional<Profile> currentProfile = profileFactory.getCurrentProfile(details);
            currentProfile.ifPresent(profile -> model.addAttribute("currentProfile", profile));
            if(!currentProfile.isPresent()){
                List<Map<String, String>> emails = (List<Map<String, String>>) details.getOrDefault("emails", emptyList());
                emails.stream().map(m -> m.get("value")).findFirst().ifPresent(email -> requestedProfileRepository.save(new RequestedProfile(email, (String)details.get("displayName"), getDataBlob(details))));
                model.addAttribute("profileRequested", true);
            }
            model.addAttribute("userDetails", details);
        }
        return "main";
    }

    private String getDataBlob(Map<String, Object> userDetails){
        try {
            return new ObjectMapper().writeValueAsString(userDetails);
        } catch (JsonProcessingException e) {
            logger.warn("", e);
            return "";
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getUserDetails(OAuth2Authentication authentication) {
        return (Map<String, Object>)authentication.getUserAuthentication().getDetails();
    }

    @RequestMapping("/profile/update")
    @ResponseBody
    public Profile update(@RequestBody UpdateProfileRequest request){
        Profile one = profileRepository.findOne(request.getId());
        if(one == null){
            throw new IllegalArgumentException("No such profile");
        }
        one.setBio(request.getBio());
        one.setFbLink(request.getFbLink());
        one.setLastFmProfile(request.getLastFmProfile());
        one.setStarted(request.getStarted());
        profileRepository.save(one);
        return one;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String notFound(IllegalArgumentException e){
        return e.getMessage();
    }





}
