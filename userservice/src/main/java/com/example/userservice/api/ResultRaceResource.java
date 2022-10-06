/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.userservice.api;

import com.example.userservice.domain.DriverRacer;
import com.example.userservice.domain.ResultRace;
import com.example.userservice.service.ResultRaceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author cesaralejodiaz
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ResultRaceResource {

    private final ResultRaceService resultRaceService;

    public LocalTime convertTime(String time) {
        String min = "", sec = "", miles = "";
        boolean flagSec = false;
        boolean flagMiles = false;

        for (int i = 0; i < time.length(); i++) {
            char chr = time.charAt(i);
            boolean isSignal = false;

            if (chr == ':') {
                flagSec = true;
                isSignal = true;
            } else if (chr == '.') {
                flagMiles = true;
                isSignal = true;
            }

            if (flagSec == false) {
                min += Character.toString(chr);
            } else {
                if (flagMiles == false && isSignal == false) {
                    sec += String.valueOf(chr);
                } else {
                    if (isSignal == false) {
                        miles += String.valueOf(chr);
                    }
                }
            }

        }

        Integer minConvert = Integer.parseInt(min);
        Integer secConvert = Integer.parseInt(sec);
        Integer milesConvert = Integer.parseInt(miles);

        return LocalTime.of(0, minConvert, secConvert, milesConvert*1000000);
    }

    @PostMapping("/resultrace/save")
    public ResponseEntity<ResultRace> saveResultRace(HttpServletResponse response, @RequestBody FormResultRace formResultRace) throws IOException {

        LocalTime timeConvert = convertTime(formResultRace.getVr_time());
        System.out.println(timeConvert);
        Date date = new Date();
        ResultRace result_race = new ResultRace(null, formResultRace.getPoints(), formResultRace.isDnf(), formResultRace.isDns(), formResultRace.isDnq(), formResultRace.isVr(), timeConvert, date, date, null);

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/resultrace/save").toUriString());
        
        try {
            ResultRace result_raceAux = resultRaceService.saveResultRace(result_race, formResultRace.getDriver_id());

            return ResponseEntity.created(uri).body(result_raceAux);
        } catch (Exception e) {
            Map<String, String> err = new HashMap<>();
            err.put("Exception: ", e.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), err);
        }
        return ResponseEntity.created(uri).body(result_race);
    }

}

@Data
class FormResultRace {

    private Integer id;
    private Integer points;
    private boolean dnf;
    private boolean dns;
    private boolean dnq;
    private boolean vr;
    private String vr_time;
    private Integer driver_id;
}
