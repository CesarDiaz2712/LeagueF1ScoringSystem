/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.userservice.service;

import com.example.userservice.domain.Race;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

/**
 *
 * @author cesaralejodiaz
 */
public interface RaceService {
    Race saveRace(Race race, Integer seasson_id);
    boolean updateRace(Race race);
    Optional<Race> getRaceById(Integer id);
    Optional<Race> getRaceByDate(Date date);
    Optional<Race> getRaceBySeasson(String name, Integer seasson_id);
    Collection<Race> getRacesBySeasson(Integer seasson_id);
    Optional<Race> existRace(String circuit_name);
}
