/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.userservice.service;

import com.example.userservice.domain.Role;
import com.example.userservice.domain.UserApp;
import com.example.userservice.repo.RoleRepo;
import com.example.userservice.repo.UserRepo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class UserServiceImplements implements UserService, UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserApp user = userRepo.findByUsername(username);
        if (user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.error("User found in the database", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public UserApp saveUser(UserApp user) {
        log.info("Saving new user {} to the database", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        UserApp user = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        /*you have to put validations for de name things like that*/
        user.getRoles().add(role);
    }

    @Override
    public UserApp getUser(String username) {
        log.info("Fetching user {}", username);

        return userRepo.findByUsername(username);
    }

    @Override
    public Collection<UserApp> getUsers() {
        log.info("Fetching all users");
        return userRepo.findAll();
    }

    @Override
    public boolean existUsername(String username) {
        log.info("Verify username: ", username);
        int result = userRepo.verifyUsername(username);
        if (result >= 0) {
            return true;
        } else {
            return false;
        }
    }

}
