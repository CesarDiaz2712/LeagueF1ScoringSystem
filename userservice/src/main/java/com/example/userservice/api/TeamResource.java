/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.userservice.api;

import com.example.userservice.domain.Team;
import com.example.userservice.domain.UserApp;
import com.example.userservice.service.TeamService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
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
public class TeamResource {
    private final TeamService teamService;
    
    @GetMapping("/teams")
    public ResponseEntity<Collection<Team>> getAllTeams() {
        return ResponseEntity.ok().body(teamService.getAllTeams());
    }
    
    @PostMapping("/team/save")
    public ResponseEntity<Team> saveTeam(HttpServletResponse response, @RequestBody Team team) throws IOException {

        try {
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/team/save").toUriString());
            boolean flag = teamService.existTeam(team.getName());
            System.out.println(flag);
            if (flag) {
                Map<String, String> message = new HashMap<>();
                message.put("Team exist, use other name team: ", team.getName());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), message);
            } else {
                Date date= new Date();
                team.setDate_creared(date);
                team.setDate_updated(date);
                Team teamAux = teamService.saveTeam(team);
                if (teamAux != null) {
                    return ResponseEntity.created(uri).body(teamAux);
                } else {
                    Map<String, String> message = new HashMap<>();
                    message.put("Error to save the team: ", team.getName());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), message);
                }
            }
        } catch (Exception e) {
            Map<String, String> err = new HashMap<>();
            err.put("Exception: ", e.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), err);
        }
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/team/update")
    public void updateTeam(HttpServletResponse response, @RequestBody Team team) throws IOException {
        Date date = new Date();
        team.setDate_updated(date);
        try {

            boolean flag = teamService.updateTeam(team);
            if (flag) {
                String update="Update Team";
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), update);
            } else {
                String message="Team not founded";
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), message);
            }

        } catch (IOException e) {
            Map<String, String> err = new HashMap<>();
            err.put("Exception: ", e.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), err);
        }
    }

    @GetMapping("/getteam")
    public ResponseEntity<Team> getTeam(@RequestBody Team team){
        
        return ResponseEntity.ok().body(teamService.getTeam(team.getId()));
    }
}
