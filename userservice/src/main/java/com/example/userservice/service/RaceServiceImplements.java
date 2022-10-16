/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.userservice.service;

import com.example.userservice.domain.Race;
import com.example.userservice.repo.RaceRepo;
import java.util.Collection;
import java.util.Date;
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
public class RaceServiceImplements implements RaceService {

    private final RaceRepo raceRepo;

    @Override
    public Race saveRace(Race race, Integer seasson_id) {
        log.info("Save race");
        return raceRepo.save(race);
    }

    @Override
    public boolean updateRace(Race race) {

        log.info("Update race");
        Integer response = raceRepo.updateRace(race.getId(), race.getCircuit_name(), race.getCountry(), race.getDate_race(), race.getDate_updated());

        if (response > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<Race> getRaceById(Integer id) {
        log.info("Get race by id");
        return raceRepo.findRaceById(id);
    }

    @Override
    public Optional<Race> getRaceByDate(Date date) {
        log.info("Get race by date");
        return raceRepo.findRaceByDate(date);
    }

    @Override
    public Optional<Race> getRaceBySeasson(String name, Integer seasson_id) {
        log.info("Get race by Seasson");
        return raceRepo.findRaceBySeassonId(seasson_id, name);
    }

    @Override
    public Collection<Race> getRacesBySeasson(Integer seasson_id) {
        log.info("Get All races by seasson");
        return raceRepo.findRacesBySeassonId(seasson_id);
    }

    @Override
    public Optional<Race> existRace(String circuit_name) {
        log.info("Verifying race existing");
        return raceRepo.existRace(circuit_name);
    }
}
