/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.userservice.service;

import com.example.userservice.domain.Team;
import java.util.Collection;

/**
 *
 * @author cesaralejodiaz
 */
public interface TeamService {
   Team saveTeam(Team team);
   boolean updateTeam(Team team);
   Team getTeam(Integer id);
   boolean existTeam(String name);
   Collection<Team> getAllTeams();
}
