/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.userservice.repo;

import com.example.userservice.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author cesaralejodiaz
 */
public interface RoleRepo extends JpaRepository<Role, Integer>{
    Role findByName(String name);
    
}