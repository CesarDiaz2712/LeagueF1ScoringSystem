/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.userservice.service;

import com.example.userservice.domain.DriverRacer;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author cesaralejodiaz
 */
public interface DriverService {
    DriverRacer saveNewDriver(DriverRacer driver, String username, Integer id_team);
    boolean updateDriver(DriverRacer driver);
    Optional<DriverRacer> getDriver(String gamertag);
    Collection<DriverRacer> getDrivers();
}
