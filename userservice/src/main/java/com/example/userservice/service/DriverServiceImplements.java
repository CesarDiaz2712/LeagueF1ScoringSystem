/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.userservice.service;

import com.example.userservice.domain.DriverRacer;
import com.example.userservice.domain.Team;
import com.example.userservice.domain.UserApp;
import com.example.userservice.repo.TeamRepo;
import com.example.userservice.repo.UserRepo;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.userservice.repo.DriverRacerRepo;
import java.util.Optional;

/**
 *
 * @author cesaralejodiaz
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DriverServiceImplements implements DriverService{

    private final UserRepo userRepo;
    private final DriverRacerRepo driverRepo;
    private final TeamRepo teamRepo;
    
    @Override
    public DriverRacer saveNewDriver(DriverRacer driver, String username, Integer id_team) {
        
        UserApp user = userRepo.findByUsername(username);
        Team team= teamRepo.findTeamById(id_team);
        driver.setUser(user);
        driver.setTeam(team);
        log.info("Saving new driver {} to the database", driver.getName(), team.getName());
        return driverRepo.save(driver);
    }

    @Override
    public Optional<DriverRacer> getDriver(String gamertag) {
        log.info("Get info of gamertag", gamertag);
        return driverRepo.findDriverRacerByGamertag(gamertag);
    }
    
    
    @Override
    public Collection<DriverRacer> getDrivers() {
        log.info("Fetching all users");
        return driverRepo.findAll();
    }

    @Override
    public boolean updateDriver(DriverRacer driver) {
        log.info("Update the driver {} to the database", driver.getName());
        Integer response = driverRepo.updateDriver(driver.getId(), driver.getName(), driver.getGamertag(), driver.getNumber_driver(), driver.getDate_updated());
        if(response > 0){
            return true;
        }else{
            return false;
        }
    }
    
}
