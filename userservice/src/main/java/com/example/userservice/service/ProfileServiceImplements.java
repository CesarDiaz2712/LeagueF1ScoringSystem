/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.userservice.service;

import com.example.userservice.domain.Profile;
import com.example.userservice.domain.UserApp;
import com.example.userservice.repo.ProfileRepo;
import com.example.userservice.repo.UserRepo;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author cesaralejodiaz
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProfileServiceImplements implements ProfileService {

    private final ProfileRepo profileRepo;
    private final UserRepo userRepo;

    @Override
    public Profile saveProfile(Profile profile, Integer user_id) {
        log.info("Saving new profile");
        UserApp user = userRepo.findUserById(user_id);
        profile.setUser(user);
        return profileRepo.save(profile);
    }

    @Override
    public boolean updateProfile(Profile profile) {
        log.info("Updating profile user");
        Integer response = profileRepo.updateProfile(profile.getId(), profile.getRoute(), profile.getFile_name(), profile.getDate_updated());
        System.out.println(response.toString());
        if (response==0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Optional<Profile> getProfile(Integer id) {
        log.info("Finding profile user");
        return profileRepo.findProfileByUserId(id);
    }

}
