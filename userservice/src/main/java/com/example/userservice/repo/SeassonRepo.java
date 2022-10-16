/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.userservice.repo;

import com.example.userservice.domain.Seasson;
import java.util.Collection;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author cesaralejodiaz
 */
public interface SeassonRepo extends JpaRepository<Seasson, Integer>{
    
    @Query(value = "select * from seasson where id = :id", nativeQuery = true)
    Optional<Seasson> findSeassonById(@Param("id") Integer id);
    
    @Query(value = "select * from seasson where year = :year", nativeQuery = true)
    Optional<Seasson> existSeasson(@Param("year") String year);
    
}
