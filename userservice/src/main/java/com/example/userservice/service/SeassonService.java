/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.userservice.service;

import com.example.userservice.domain.Seasson;
import java.util.Collection;
import java.util.Optional;

/**
 *
 * @author cesaralejodiaz
 */
public interface SeassonService {
    Seasson saveNewSeasson(Seasson seasson);
    Optional<Seasson> getSeassonById(Integer id);
    Optional<Seasson> existSeasson(String year);
    Collection<Seasson> getAllSeassons();
}
