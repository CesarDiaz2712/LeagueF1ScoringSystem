/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.userservice.service;

import com.example.userservice.domain.DriverRacer;
import com.example.userservice.domain.Race;
import com.example.userservice.domain.ResultRace;
import com.example.userservice.repo.DriverRacerRepo;
import com.example.userservice.repo.RaceRepo;
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
    private final RaceRepo raceRepo;

    @Override
    public ResultRace saveResultRace(ResultRace result_race, Integer driver_id, Integer race_id) {
        log.info("Save race result");
        DriverRacer driverRacer = driverRaceRepo.findDriverRacerById(driver_id);
        Optional<Race> race = raceRepo.findRaceById(race_id);
        result_race.setDriver(driverRacer);
        result_race.setRace(race.get());
        return resultRaceRepo.save(result_race);
    }

    @Override
    public boolean updateResultRace(ResultRace result_race) {

        log.info("Update race result");
        Integer response = resultRaceRepo.updateDriver(result_race.getId(), result_race.getPoints(), result_race.isDnf(), result_race.isDns(), result_race.isDnq(), result_race.getVr_time(), result_race.getDate_updated());

        if (response > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<ResultRace> getResultRace(Integer id) {

        log.info("Get result race");
        return resultRaceRepo.findResultRaceById(id);
    }

    @Override
    public Optional<ResultRace> getResultRaceByIdDriverAndIdRace(Integer id_driver, Integer id_Race) {

        log.info("Get result race by Driver and Race");
        return resultRaceRepo.findResultRaceByIdDriverAndRaceId(id_driver, id_Race);
    }

    @Override
    public Collection<ResultRace> getAllResultsRaceByIdRace(Integer race_id) {
        log.info("Get result race by Race Id");
        return resultRaceRepo.findResultRaceByRaceId(race_id);
    }

    @Override
    public boolean existResultRace(Integer driver_id, Integer race_id) {
        log.info("Get result race exist");
        Optional<ResultRace> resultRace = resultRaceRepo.existResultRace(driver_id, race_id);
        if (!resultRace.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

}
