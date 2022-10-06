/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.userservice.repo;

import com.example.userservice.domain.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author cesaralejodiaz
 */
public interface UserRepo extends JpaRepository<UserApp, Long>{
    
    @Query(value = "select * from user_app where username = ?1", nativeQuery = true)
    UserApp findByUsername(@Param("username") String username);
    
    @Query(value = "select id from user_app where username = ?1", nativeQuery = true)
    int verifyUsername(@Param("username") String username);
    
}
