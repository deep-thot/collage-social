package se.collagekoren.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    private ImageRepository imageRepository;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public CollageProfileController(ProfileRepository profileRepository, RequestedProfileRepository requestedProfileRepository, ImageRepository imageRepository) {
        this.profileRepository = profileRepository;
        this.requestedProfileRepository = requestedProfileRepository;
        this.imageRepository = imageRepository;
    }

    @RequestMapping("/profile/new")
    @ResponseBody
    public ProfileView newProfile(@RequestBody ProfileRequest request, CurrentUser currentUser){
        if(!currentUser.isAdmin()){
            throw new NotAdminException();
        }
        Profile profile = new Profile(
                request.getName(),
                request.getBio(),
                request.getFbLink(),
                request.getLinkedInProfile(),
                request.getLastFmProfile(),
                request.getVoice(),
                null,
                null,
                request.getEmail(),
                request.getAddress(),
                request.getPhoneNumber());
        Profile result = profileRepository.save(profile);
        return ProfileView.profile(result);
    }

    @PostMapping("/profile/image/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void saveImage(@RequestParam("image") MultipartFile image, @PathVariable Integer id, CurrentUser currentUser){
        if(!allowedToUpdate(currentUser, id)){
            throw new NotAdminException();
        }
        try {
            imageRepository.save(new Image(id, image.getBytes()));
        } catch (IOException e) {
            logger.error("Unable to get store image", e);
        }
    }

    @GetMapping(value = "/profile/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getImage(@PathVariable Integer id, CurrentUser currentUser, HttpServletResponse response){
        if(!currentUser.isVerified()){
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
    public List<ProfileView> listAll(CurrentUser currentUser){
       if(!currentUser.isVerified()){
           return emptyList();
       }
        return StreamSupport.stream(profileRepository.findAll().spliterator(), false).map((profile) -> {
            if(profile.getId().equals(currentUser.getProfile().getId())){
                return ProfileView.loggedInProfile(profile);
            }
            return ProfileView.profile(profile);
        }).collect(toList());
    }

    @RequestMapping("/profile/voice/{voice}")
    @ResponseBody
    public Collection<Profile> allInVoice(@PathVariable Voice voice, CurrentUser currentUser){
        if(!currentUser.isVerified()){
            return emptyList();
        }
        return profileRepository.findByVoice(voice).collect(toList());
    }

    @RequestMapping({"/", "/start"})
    public String start(Model model, CurrentUser currentUser){
        model.addAttribute("currentUser", currentUser);

        if(currentUser.isLoggedIn()){
            Map<String, Object> details = currentUser.getUserDetails();
            if(!currentUser.isVerified()) {
                addRequestedProfile(details);
                model.addAttribute("profileRequested", true);
            }
            model.addAttribute("userDetails", details);
        }

        return "main";
    }

    @SuppressWarnings("unchecked")
    private void addRequestedProfile(Map<String, Object> details) {
        List<Map<String, String>> emails = (List<Map<String, String>>) details.getOrDefault("emails", emptyList());
        emails.stream().map(m -> m.get("value")).findFirst().ifPresent(email -> requestedProfileRepository.save(new RequestedProfile(email, (String) details.get("displayName"), getDataBlob(details))));
    }

    private String getDataBlob(Map<String, Object> userDetails){
        try {
            return new ObjectMapper().writeValueAsString(userDetails);
        } catch (JsonProcessingException e) {
            logger.warn("", e);
            return "";
        }
    }


    @RequestMapping("/profile/update")
    @ResponseBody
    public ProfileView update(@RequestBody UpdateProfileRequest request, CurrentUser currentUser){
        if(!allowedToUpdate(currentUser, request.getId())){
            throw new NotAdminException();
        }
        Profile one = profileRepository.findOne(request.getId());
        if(one == null){
            throw new IllegalArgumentException("No such profile");
        }
        one.setBio(request.getBio());
        one.setFbLink(getFbLink(request));
        one.setLastFmProfile(request.getLastFmProfile());
        one.setStarted(request.getStarted());
        one.setAddress(request.getAddress());
        one.setPhoneNumber(request.getPhoneNumber());
        profileRepository.save(one);
        return one.getId().equals(currentUser.getProfile().getId()) ? ProfileView.loggedInProfile(one) : ProfileView.profile(one);
    }

    private boolean allowedToUpdate(CurrentUser currentUser, Integer id) {
        return currentUser.isVerified() && (id.equals(currentUser.getProfile().getId()) || currentUser.isAdmin());
    }

    private String getFbLink(@RequestBody UpdateProfileRequest request) {
        String fb = request.getFbLink();
        return fb.startsWith("http") || fb.startsWith("www") ? fb.substring(fb.lastIndexOf('/')) : fb;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String notFound(IllegalArgumentException e){
        return e.getMessage();
    }

    @ExceptionHandler(NotAdminException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public String unauthorized(NotAdminException e){
        return "You are not allowed to do that";
    }


}
