/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.userservice.service;

import com.example.userservice.domain.ResultRace;
import java.util.Collection;
import java.util.Optional;

/**
 *
 * @author cesaralejodiaz
 */
public interface ResultRaceService {
    ResultRace saveResultRace(ResultRace result_race, Integer driver_id, Integer race_id);
    boolean updateResultRace(ResultRace resultrace);
    Optional<ResultRace> getResultRace(Integer id);
    Optional<ResultRace> getResultRaceByIdDriverAndIdRace(Integer id_driver, Integer id_Race);
    Collection<ResultRace> getAllResultsRaceByIdRace(Integer race_id);
    boolean existResultRace(Integer driver_id, Integer race_id);
}
