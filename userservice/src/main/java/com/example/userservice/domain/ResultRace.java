/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.userservice.domain;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author cesaralejodiaz
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "result_race")
public class ResultRace{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "id")
    private Integer id;
    
    @Column(name= "points")
    private Integer points;
    
    @Column(name= "dnf")
    private boolean dnf;
    
    @Column(name= "dns")
    private boolean dns;
    
    @Column(name= "dnq")
    private boolean dnq;
    
    @Column(name= "vr")
    private boolean vr;
    
    @Column(name= "vr_time")
    private LocalTime vr_time;
    
    @Column(name= "date_creared")
    @Temporal(TemporalType.DATE)
    private Date date_creared;
    
    @Column(name= "date_updated")
    @Temporal(TemporalType.DATE)
    private Date date_updated;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    private DriverRacer driver;
    
}
