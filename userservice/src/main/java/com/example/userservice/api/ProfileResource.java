/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.userservice.api;

import com.example.userservice.domain.Profile;
import com.example.userservice.service.ProfileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author cesaralejodiaz
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProfileResource {

    private final ProfileService profileService;

    @PostMapping("/profile/save")
    public ResponseEntity<Profile> saveProfile(HttpServletResponse response, @RequestBody FormProfile formProfile) throws IOException {
        Date date = new Date();

        Profile profile = new Profile(null, formProfile.getRoute(), formProfile.getFile_name(), date, date, null);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/profile/save").toString());

        try {
            Profile profileAux = profileService.saveProfile(profile, formProfile.getUser_id());

            return ResponseEntity.created(uri).body(profileAux);
        } catch (Exception e) {
            Map<String, String> err = new HashMap<>();
            err.put("Exception: ", e.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), err);
        }
        return ResponseEntity.created(uri).body(profile);
    }

    @PutMapping("/profile/update")
    public void updateProfile(HttpServletResponse response, @RequestBody Profile profile) throws IOException {
        Date date = new Date();
        profile.setDate_updated(date);

        Map<String, String> message = new HashMap<>();
        try {
            boolean flag = profileService.updateProfile(profile);
            System.out.println(flag);
            if (flag == true) {
                message.put("Message", "Updated Profile");
            } else {
                message.put("Message", "Profile not founded");
            }
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), message);
        } catch (IOException e) {
            message.put("Exception", e.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), message);
        }
    }

    @GetMapping("/getprofile")
    public ResponseEntity<Profile> getProfile(HttpServletResponse response, @RequestParam(name = "id") Integer id) throws IOException {

        Map<String, String> message = new HashMap<>();
        try {
            Optional<Profile> profile = profileService.getProfile(id);
            if (profile.isEmpty()) {
                message.put("Message", "Driver not found");
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), message);
            } else {
                return ResponseEntity.ok().body(profile.get());
            }
        } catch (IOException e) {
            message.put("Exception", e.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), message);
        }
        return ResponseEntity.ok().body(null);
    }
}

@Data
class FormProfile {

    private String route;
    private String file_name;
    private Integer user_id;
}
