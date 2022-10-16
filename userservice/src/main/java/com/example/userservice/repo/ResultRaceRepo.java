/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.userservice.repo;

import com.example.userservice.domain.ResultRace;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Collection;
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
public interface ResultRaceRepo extends JpaRepository<ResultRace, Integer> {

    @Query(value = "select * from result_race where id = :id", nativeQuery = true)
    Optional<ResultRace> findResultRaceById(@Param("id") Integer id);
    
    @Query(value = "select * from result_race where race_id = :race_id", nativeQuery = true)
    Collection<ResultRace> findResultRaceByRaceId(@Param("race_id") Integer race_id);

    @Query(value = "select * from result_race where driver_id = :driver_id and race_id = :race_id", nativeQuery = true)
    Optional<ResultRace> findResultRaceByIdDriverAndRaceId(@Param("driver_id") Integer driver_id, @Param("race_id") Integer race_id);
    
    @Query(value = "select * from result_race where driver_id = :driver_id and race_id = :race_id", nativeQuery = true)
    Optional<ResultRace> existResultRace(@Param("driver_id") Integer driver_id, @Param("race_id") Integer race_id);

    @Modifying
    @Query(value = "UPDATE result_racce SET points = :points, dnf = :dnf, dns = :dns, dnq = :dnq, vr = :vr, vr_time = :vr_time, date_updated = :date_updated WHERE id = :id", nativeQuery = true)
    Integer updateDriver(@Param("id") Integer id, @Param("points") Integer points, @Param("dnf") boolean dnf, @Param("dns") boolean dns, @Param("dnq") boolean dnq, @Param("vr_time") LocalTime vr_time, @Param("date_updated") Date date_updated);
}
