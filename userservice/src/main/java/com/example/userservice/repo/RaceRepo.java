/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.userservice.repo;

import com.example.userservice.domain.Race;
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
public interface RaceRepo extends JpaRepository<Race, Integer> {

    @Query(value = "select * from race where id= :id", nativeQuery = true)
    Optional<Race> findRaceById(@Param("id") Integer id);

    @Query(value = "select * from race where date_race = :date_race", nativeQuery = true)
    Optional<Race> findRaceByDate(@Param("date_race") Date date_race);

    @Query(value = "select * from race where seasson_id = :seasson_id and circuit_name = :circuit_name", nativeQuery = true)
    Optional<Race> findRaceBySeassonId(@Param("seasson_id") Integer seasson_id, @Param("circuit_name") String circuit_name);

    @Query(value = "select * from race where seasson_id = :seasson_id", nativeQuery = true)
    Collection<Race> findRacesBySeassonId(@Param("seasson_id") Integer seasson_id);

    @Modifying
    @Query(value = "UPDATE race SET circuit_name = :circuit_name, country = :country, date_race = :date_race, date_updated = :date_updated WHERE id = :id", nativeQuery = true)
    Integer updateRace(@Param("id") Integer id, @Param("circuit_name") String circuit_name, @Param("country") String country, @Param("date_race") Date date_race, @Param("date_updated") Date date_updated);
    
    @Query(value = "select * from race where circuit_name = :circuit_name", nativeQuery = true)
    Optional<Race> existRace(@Param("circuit_name") String circuit_name);
}
