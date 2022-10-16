/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.userservice.api;

import com.example.userservice.domain.ResultRace;
import com.example.userservice.service.ResultRaceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

        LocalTime timeResult = LocalTime.of(0, minConvert, secConvert, milesConvert * 1000000);
        return timeResult;
    }

    @PostMapping("/resultrace/save")
    public ResponseEntity<ResultRace> saveResultRace(HttpServletResponse response, @RequestBody FormResultRace formResultRace) throws IOException {

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/resultrace/save").toUriString());

        Map<String, String> message = new HashMap<>();
        //verificar si existe ya un resultado actualmente
        boolean existResultRace = resultRaceService.existResultRace(formResultRace.getDriver_id(), formResultRace.getRace_id());
        if (!existResultRace) {
            LocalTime timeConvert = convertTime(formResultRace.getVr_time());

            Date date = new Date();
            ResultRace result_race = new ResultRace(null, formResultRace.getPoints(), formResultRace.isDnf(), formResultRace.isDns(), formResultRace.isDnq(), formResultRace.isVr(), timeConvert, date, date, null, null);

            try {
                ResultRace result_raceAux = resultRaceService.saveResultRace(result_race, formResultRace.getDriver_id(), formResultRace.getRace_id());
                return ResponseEntity.created(uri).body(result_raceAux);
            } catch (Exception e) {
                message.put("Exception: ", e.getMessage());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), message);
            }
            return ResponseEntity.created(uri).body(null);
        } else {
            message.put("Message: ", "ResultRace was creadred , Try with other driver or race");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), message);
            return ResponseEntity.created(uri).body(null);
        }
    }

    @PutMapping("/resultrace/update")
    public void updateProfile(HttpServletResponse response, @RequestBody ResultRace resultRace) throws IOException {
        Date date = new Date();
        resultRace.setDate_updated(date);
        Map<String, String> message = new HashMap<>();

        try {
            boolean flag = resultRaceService.updateResultRace(resultRace);
            System.out.println(flag);
            if (flag == true) {
                message.put("Message", "Updated ResultRace");
            } else {
                message.put("Message", "ResultRace not founded");
            }
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), message);
        } catch (IOException e) {
            message.put("Exception", e.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), message);
        }
    }

    @GetMapping("/getresult-byid")
    public ResponseEntity<ResultRace> getResultRaceById(HttpServletResponse response, @RequestBody ResultRace resultRace) throws IOException {

        Map<String, String> message = new HashMap<>();

        try {
            Optional<ResultRace> resultRaceAux = resultRaceService.getResultRace(resultRace.getId());
            if (!resultRaceAux.isEmpty()) {
                return ResponseEntity.ok().body(resultRaceAux.get());
            } else {
                message.put("Message", "ResultRace not founded");
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), message);
                return ResponseEntity.ok().body(null);
            }

        } catch (Exception e) {
            message.put("Exception", e.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), message);
        }
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/getallresult-byraceid")
    public ResponseEntity<Collection<ResultRace>> getResultRaceByRaceId(HttpServletResponse response, @RequestParam(name = "race_id") Integer race_id) throws IOException {

        Map<String, String> message = new HashMap<>();

        try {

            Collection<ResultRace> listResultRace = resultRaceService.getAllResultsRaceByIdRace(race_id);
            if (!listResultRace.isEmpty()) {
                return ResponseEntity.ok().body(listResultRace);
            } else {
                message.put("Message", "There are not result races");
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), message);
                return ResponseEntity.ok().body(null);
            }
        } catch (Exception e) {
            message.put("Exception", e.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), message);
        }
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/getresult-bydriveridandraceid")
    public ResponseEntity<ResultRace> getResultRaceByDriverIdAndRaceId(HttpServletResponse response, @RequestParam(name = "driver_id") Integer driver_id, @RequestParam(name = "race_id") Integer race_id) throws IOException {

        Map<String, String> message = new HashMap<>();

        try {

            Optional<ResultRace> resultRaceAux = resultRaceService.getResultRaceByIdDriverAndIdRace(driver_id, race_id);
            if (!resultRaceAux.isEmpty()) {
                return ResponseEntity.ok().body(resultRaceAux.get());
            } else {
                message.put("Message", "ResultRace not exist");
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), message);
                return ResponseEntity.ok().body(null);
            }
        } catch (Exception e) {
            message.put("Exception", e.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), message);
        }
        return ResponseEntity.ok().body(null);
    }

    public byte[] createScoringTable(@RequestBody ArrayList list) {
        ArrayList listResultRace = list;

        byte[] image = null;

        return image;
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
    private Integer race_id;
}
