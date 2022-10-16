/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.userservice.service;

import com.example.userservice.domain.Seasson;
import com.example.userservice.repo.SeassonRepo;
import java.util.Collection;
import java.util.Optional;
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
public class SeassonServiceImplements implements SeassonService{
    
    private final SeassonRepo seassonRepo;

    @Override
    public Seasson saveNewSeasson(Seasson seasson) {
        log.info("Save new seasson");
        return seassonRepo.save(seasson);
    }

    @Override
    public Optional<Seasson> getSeassonById(Integer id) {
        log.info("Get seasson by id");
        return seassonRepo.findSeassonById(id);
    }

    @Override
    public Optional<Seasson> existSeasson(String year) {
        log.info("Exist seasson");
        return seassonRepo.existSeasson(year);
    }

    @Override
    public Collection<Seasson> getAllSeassons() {
        log.info("Get All seassons");
        return seassonRepo.findAll();
    }
    
}
