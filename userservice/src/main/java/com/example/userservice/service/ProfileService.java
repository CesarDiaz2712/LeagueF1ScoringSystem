/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.userservice.service;

import com.example.userservice.domain.Profile;
import java.util.Optional;

/**
 *
 * @author cesaralejodiaz
 */
public interface ProfileService {
    Profile saveProfile(Profile profile, Integer user_id);
    boolean updateProfile(Profile profile);
    Optional<Profile> getProfile(Integer id);
}
