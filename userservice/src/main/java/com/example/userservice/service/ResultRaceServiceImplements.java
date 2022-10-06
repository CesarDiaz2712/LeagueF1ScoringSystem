/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.userservice.service;

import com.example.userservice.domain.DriverRacer;
import com.example.userservice.domain.ResultRace;
import com.example.userservice.repo.DriverRacerRepo;
import com.example.userservice.repo.ResultRaceRepo;
import java.util.Collection;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author cesaralejodiaz
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ResultRaceServiceImplements implements ResultRaceService {

    private final DriverRacerRepo driverRaceRepo;
    private final ResultRaceRepo resultRaceRepo;

    @Override
    public ResultRace saveResultRace(ResultRace result_race, Integer driver_id) {
        log.info("Save race result");
        DriverRacer driverRacer = driverRaceRepo.findDriverRacerById(driver_id);
        result_race.setDriver(driverRacer);
        return resultRaceRepo.save(result_race);
    }

    @Override
    public Integer updateResultRace(ResultRace result_race) {

        log.info("Update race result");
        Integer number= resultRaceRepo.updateDriver(result_race.getId(), result_race.getPoints(), result_race.isDnf(), result_race.isDns(), result_race.isDnq(), result_race.getVr_time(), result_race.getDate_updated());
        return number;
    }

    @Override
    public Optional<ResultRace> getResultRace(Integer id) {
        
        log.info("Get result race");
        return resultRaceRepo.findResultRaceById(id);
    }

    @Override
    public Collection<ResultRace> getAllResultRaceByIdDriver(Integer id) {
    
        log.info("Get result race by Driver", id);
        return resultRaceRepo.findResultRaceByIdDriver(id);
    }

    @Override
    public Collection<ResultRace> getAllResultRaceByIdDriverAndIdRace(Integer id_driver, Integer id_Race) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Collection<ResultRace> getAllResultsRaceByIdRace(Integer id_race) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
