/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.userservice.repo;

import com.example.userservice.domain.DriverRacer;
import java.util.Date;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author cesaralejodiaz
 */
public interface DriverRacerRepo extends JpaRepository<DriverRacer, Integer> {

    @Query(value = "select * from driver_racer where id = :id", nativeQuery = true)
    DriverRacer findDriverRacerById(@Param("id") Integer id);
    
    @Query(value = "select * from driver_racer where gamertag = :gamertag", nativeQuery = true)
    Optional<DriverRacer> findDriverRacerByGamertag(@Param("gamertag") String gamertag);

    @Modifying
    @Query(value = "UPDATE driver_racer SET date_updated = :date_updated, gamertag = :gamertag, name = :name, number_driver = :number_driver WHERE id = :id", nativeQuery = true)
    Integer updateDriver(@Param("id") Integer id, @Param("name") String name, @Param("gamertag") String gamertag, @Param("number_driver") String number_driver, @Param("date_updated") Date date_updated);
}
