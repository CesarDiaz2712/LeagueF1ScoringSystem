/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.userservice.api;

import com.example.userservice.domain.Seasson;
import com.example.userservice.service.SeassonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
public class SeassonResource {

    public final SeassonService seassonService;

    @PostMapping("/seasson/save")
    public ResponseEntity<Seasson> saveNewSeasson(HttpServletResponse response, @RequestBody Seasson seasson) throws IOException {

        Map<String, String> message = new HashMap<>();
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/seasson/save").toString());

        Optional<Seasson> existSeasson = seassonService.existSeasson(seasson.getYear());

        if (existSeasson.isEmpty()) {

            Date date = new Date();

            seasson.setDate_creared(date);
            seasson.setDate_updated(date);
            try {
                Seasson seassonAux = seassonService.saveNewSeasson(seasson);

                return ResponseEntity.created(uri).body(seassonAux);
            } catch (Exception e) {
                message.put("Exception", e.getMessage());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), message);
            }
        } else {
            message.put("Message", "Seasson created");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), message);
        }
        return ResponseEntity.created(uri).body(null);
    }

    @GetMapping("/getseasson-byid")
    public ResponseEntity<Seasson> getSeassonById(HttpServletResponse response, @RequestBody Seasson seasson) throws IOException {

        Map<String, String> message = new HashMap<>();

        try {
            Optional<Seasson> seassonAux = seassonService.getSeassonById(seasson.getId());
            if (!seassonAux.isEmpty()) {
                return ResponseEntity.ok().body(seassonAux.get());
            } else {
                message.put("Message", "Seasson not founded");
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), message);
            }
        } catch (Exception e) {
            message.put("Exception", e.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), message);
        }
        return ResponseEntity.ok().body(null);
    }
    
    @GetMapping("/getallseassons")
    public ResponseEntity<Collection<Seasson>> getAllSeassons(HttpServletResponse response) throws IOException {

        Map<String, String> message = new HashMap<>();

        try {
            Collection<Seasson> seassonAux = seassonService.getAllSeassons();
            if (!seassonAux.isEmpty()) {
                return ResponseEntity.ok().body(seassonAux);
            } else {
                message.put("Message", "Seassons not founded");
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), message);
            }
        } catch (Exception e) {
            message.put("Exception", e.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), message);
        }
        return ResponseEntity.ok().body(null);
    }
}
