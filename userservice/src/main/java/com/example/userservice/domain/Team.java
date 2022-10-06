/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.userservice.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author cesaralejodiaz
 */
@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", columnDefinition = "varchar(30)", unique = true)
    private String name;

    @Column(name = "alias", columnDefinition = "varchar(5)")
    private String alias;

    @Column(name = "anio_register", columnDefinition = "varchar(4)")
    private String anio_register;

    @Column(name = "team_chief", columnDefinition = "varchar(40)")
    private String team_chief;

    @Column(name = "technical_chief", columnDefinition = "varchar(40)")
    private String technical_chief;

    @Column(name = "date_creared")
    private Date date_creared;

    @Column(name = "date_updated")
    private Date date_updated;

}
