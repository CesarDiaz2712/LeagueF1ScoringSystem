/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.userservice.service;

import com.example.userservice.domain.Team;
import com.example.userservice.repo.TeamRepo;
import java.util.Collection;
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
public class TeamServiceImplements implements TeamService {

    private final TeamRepo teamRepo;

    @Override
    public Team saveTeam(Team team) {

        log.info("Saving new team {} to the database", team.getName());
        return teamRepo.save(team);
    }

    @Override
    public boolean updateTeam(Team team) {
        log.info("Update the driver {} to the database", team.getName());
        int response = teamRepo.updateTeam(team.getId(), team.getName(), team.getAlias(), team.getAnio_register(), team.getTeam_chief(), team.getTechnical_chief(), team.getDate_updated());
        if (response >= 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Team getTeam(Integer id) {
        log.info("Get team by id");
        return teamRepo.findTeamById(id);
    }

    @Override
    public Collection<Team> getAllTeams() {
        log.info("Fetching all teams");
        return teamRepo.findAll();
    }

    @Override
    public boolean existTeam(String name) {
        
        System.out.println(name);
        Integer result = teamRepo.verifyTeamByName(name);
        log.info("Response: ", result);
        if (result!=null) {
            return true;
        } else {
            return false;
        }
    }

}
