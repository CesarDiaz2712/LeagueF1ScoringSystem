/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.userservice.repo;

import com.example.userservice.domain.Team;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author cesaralejodiaz
 */
public interface TeamRepo extends JpaRepository<Team, Integer>{
    
    @Query(value = "select * from team where id = :id", nativeQuery = true)
    Team findTeamById(@Param("id") Integer id);
    
    @Query(value = "select id from team where name = :name", nativeQuery = true)
    Integer verifyTeamByName(@Param("name") String name);
    
    @Modifying
    @Query(value = "update team set name = :name, alias = :alias, anio_register = :anio_register, team_chief = :team_chief, technical_chief = :technical_chief, date_updated = :date_updated where id = :id", nativeQuery = true)
    int updateTeam(@Param("id") Integer id, @Param("name") String name, @Param("alias") String alias, @Param("anio_register") String anio_register, @Param("team_chief") String team_chief, @Param("technical_chief") String technical_chief, @Param("date_updated") Date date_updated);
    
    
    
}
