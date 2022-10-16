/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.userservice.repo;

import com.example.userservice.domain.Profile;
import java.util.Date;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author cesaralejodiaz
 */
public interface ProfileRepo extends JpaRepository<Profile, Integer> {
    
    @Query(value = "select * from profile where id = :id", nativeQuery = true)
    Optional<Profile> findProfileByUserId(@Param("id") Integer id);
    
    @Modifying
    @Query(value = "UPDATE profile SET route = :route, file_name = :file_name, date_updated = :date_updated WHERE id = :id", nativeQuery = true)
    Integer updateProfile(@Param("id") Integer id, @Param("route") String route, @Param("file_name") String file_name, @Param("date_updated") Date date_updated);
    
    
}
