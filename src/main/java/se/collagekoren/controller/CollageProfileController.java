package se.collagekoren.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.collagekoren.domain.Profile;
import se.collagekoren.domain.Voice;
import se.collagekoren.repository.ProfileRepository;
import se.collagekoren.request.ProfileRequest;

import java.security.Principal;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Jonatan on 2015-11-26.
 */
@Controller
public class CollageProfileController {

    private ProfileRepository profileRepository;

    @Autowired
    public CollageProfileController(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @RequestMapping("/profile/new")
    @ResponseBody
    public Integer newProfile(@RequestBody ProfileRequest request){
        Profile profile = new Profile(request.getName(), request.getBio(), request.getFbLink(), request.getLinkedInProfile(), request.getLastFmProfile(), request.getVoice(), request.getImage());
        Profile result = profileRepository.save(profile);
        return result.getId();
    }

    @RequestMapping("/profile/all")
    @ResponseBody
    public Iterable<Profile> listAll(){
        return profileRepository.findAll();
    }

    @RequestMapping("/profile/voice/{voice}")
    @ResponseBody
    public Collection<Profile> allInVoice(@PathVariable Voice voice){
        return profileRepository.findByVoice(voice).collect(Collectors.toList());
    }

    @RequestMapping({"/", "/start"})
    public String start(Principal principal, Model model){
        model.addAttribute("principal", principal);
        return "main";
    }

    @RequestMapping("/profile/update")
    @ResponseBody
    public Profile update(@RequestBody ProfileRequest request){
        Profile one = profileRepository.findOne(request.getId());
        if(one == null){
            throw new IllegalArgumentException("No such profile");
        }
        one.setBio(request.getBio());
        one.setFbLink(request.getFbLink());
        one.setImage(request.getImage());
        one.setLastFmProfile(request.getLastFmProfile());
        one.setLinkedInProfile(request.getLinkedInProfile());
        one.setName(request.getName());
        one.setVoice(request.getVoice());
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
