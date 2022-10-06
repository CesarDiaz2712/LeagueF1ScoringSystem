/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.userservice.service;

import com.example.userservice.domain.Role;
import com.example.userservice.domain.UserApp;
import java.util.Collection;
import java.util.List;
/**
 *
 * @author cesaralejodiaz
 */
public interface UserService {
    UserApp saveUser(UserApp user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    UserApp getUser(String username);
    Collection<UserApp> getUsers();
    boolean existUsername(String username);
    
}
