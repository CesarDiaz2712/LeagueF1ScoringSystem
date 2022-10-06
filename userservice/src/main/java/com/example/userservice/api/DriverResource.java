/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.userservice.api;

import com.example.userservice.domain.DriverRacer;
import com.example.userservice.domain.UserApp;
import com.example.userservice.service.DriverService;
import com.example.userservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author cesaralejodiaz
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class DriverResource {

    private final DriverService driverService;

    @PostMapping("/driver/save")
    public ResponseEntity<DriverRacer> saveDriver(HttpServletResponse response, @RequestBody FormNewDriver form) throws IOException {

        Date date = new Date();
        DriverRacer driver = new DriverRacer(null, form.getName(), form.getGamertag(), form.getNumber_driver(), date, date, null, null);

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/driver/save").toUriString());
        DriverRacer driverAux;
        try {
            driverAux = driverService.saveNewDriver(driver, form.getUsername(), form.getTeam_id());

            return ResponseEntity.created(uri).body(driverAux);
        } catch (Exception e) {
            Map<String, String> err = new HashMap<>();
            err.put("Exception: ", e.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), err);
        }
        return ResponseEntity.created(uri).body(driver);
    }

    @PutMapping("/driver/update")
    public void updateDriver(HttpServletResponse response, @RequestBody DriverRacer driver) throws IOException {
        Date date = new Date();
        driver.setDate_updated(date);
        try {

            int flag = driverService.updateDriver(driver);
            if (flag == 1) {
                Map<String, String> update = new HashMap<>();
                update.put("Updated driver: ", driver.getGamertag());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), update);
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("Driver not found: ", driver.getGamertag());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }

        } catch (IOException e) {
            Map<String, String> err = new HashMap<>();
            err.put("Exception: ", e.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), err);
        }
    }

    @GetMapping("/getdriver")
    @ResponseBody
    public ResponseEntity<DriverRacer> getUserSpecific(HttpServletResponse response, @RequestParam(name = "gamertag") String gamertag) throws IOException {
        log.info("El gamertag ", gamertag);

        try {
            DriverRacer driver = driverService.getDriver(gamertag);
            if (driver == null) {
                Map<String, String> message = new HashMap<>();
                message.put("Driver not found: ", gamertag);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), message);
            } else {
                return ResponseEntity.ok().body(driver);
            }
        } catch (Exception e) {
            Map<String, String> err = new HashMap<>();
            err.put("Exception: ", e.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), err);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getalldrivers")
    public ResponseEntity<Collection<DriverRacer>> getDrives() {
        return ResponseEntity.ok().body(driverService.getDrivers());
    }
}

@Data
class FormNewDriver {

    private Integer id;
    private String name;
    private String gamertag;
    private String number_driver;
    private String username;
    private Integer team_id;
}
