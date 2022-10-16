/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.userservice.api;

import com.example.userservice.domain.Race;
import com.example.userservice.service.RaceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author cesaralejodiaz
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RaceResource {

    private final RaceService raceService;

    @PostMapping("/race/save")
    public ResponseEntity<Race> saveRace(HttpServletResponse response, @RequestBody FormRace formRace) throws IOException {

        Map<String, String> message = new HashMap<>();
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/race/save").toString());

        try {

            Optional<Race> existRace = raceService.existRace(formRace.getCircuit_name());

            if (existRace.isEmpty()) {
                Date date = new Date();
                Race race = new Race(null, formRace.getCircuit_name(), formRace.getCountry(), formRace.getDate_race(), date, date, null);
                Race raceAux = raceService.saveRace(race, formRace.getSeasson_id());

                return ResponseEntity.created(uri).body(raceAux);
            } else {
                message.put("Message", "Race exist, try with other name race");
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), message);
            }

        } catch (IOException e) {
            message.put("Exception", e.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), message);
        }
        return ResponseEntity.created(uri).body(null);
    }

    @PutMapping("/race/update")
    public void updateRace(HttpServletResponse response, @RequestBody Race race) throws IOException {
        Date date = new Date();
        race.setDate_updated(date);
        Map<String, String> message = new HashMap<>();

        try {
            boolean flag = raceService.updateRace(race);
            System.out.println(flag);
            if (flag == true) {
                message.put("Message", "Updated Race");
            } else {
                message.put("Message", "Race not founded");
            }
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), message);
        } catch (IOException e) {
            message.put("Exception", e.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), message);
        }
    }

    @GetMapping("/getrace-byid")
    public ResponseEntity<Race> getRaceById(HttpServletResponse response, @RequestBody Race race) throws IOException {

        Map<String, String> message = new HashMap<>();
        try {
            Optional<Race> raceAux = raceService.getRaceById(race.getId());
            if (!raceAux.isEmpty()) {
                return ResponseEntity.ok().body(raceAux.get());
            } else {
                message.put("Message", "Race not founded");
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), message);
            }
        } catch (IOException e) {
            message.put("Exception", e.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), message);
        }
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/getrace-bydate")
    public ResponseEntity<Race> getRaceByDate(HttpServletResponse response, @RequestBody Race race) throws IOException {

        Map<String, String> message = new HashMap<>();
        
        try {

            Optional<Race> raceAux = raceService.getRaceByDate(race.getDate_race());
            
            if (!raceAux.isEmpty()) {
                return ResponseEntity.ok().body(raceAux.get());
            } else {
                message.put("Message", "Race not founded");
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), message);
            }
        } catch (IOException e) {
            message.put("Exception", e.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), message);
        }
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/getrace-byseassonid")
    public ResponseEntity<Race> getRaceBySeassonId(HttpServletResponse response, @RequestBody FormRace formRace) throws IOException {

        Map<String, String> message = new HashMap<>();
        
        try {
            Optional<Race> raceAux = raceService.getRaceBySeasson(formRace.getCircuit_name(), formRace.getSeasson_id());
            
            if (!raceAux.isEmpty()) {
                return ResponseEntity.ok().body(raceAux.get());
            } else {
                message.put("Message", "Race not founded");
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), message);
            }
        } catch (IOException e) {
            message.put("Exception", e.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), message);
        }
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/getallraces-byseassonid")
    public ResponseEntity<Collection<Race>> getRacesBySeassonId(HttpServletResponse response, @RequestBody FormRace formRace) throws IOException {

        Map<String, String> message = new HashMap<>();
        
        try {
            Collection<Race> listRaces = raceService.getRacesBySeasson(formRace.getSeasson_id());
            
            if (!listRaces.isEmpty()) {
                return ResponseEntity.ok().body(listRaces);
            } else {
                message.put("Message", "Races not founded");
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), message);
            }
        } catch (IOException e) {
            message.put("Exception", e.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), message);
        }
        return ResponseEntity.ok().body(null);
    }
}

@Data
class FormRace {

    private String circuit_name;
    private String country;
    private Date date_race;
    private Integer seasson_id;
}
