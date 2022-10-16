/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.userservice.domain;

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
@Table(name = "race")
public class Race {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "id")
    private Integer id;
    
    @Column(name= "circuit_name", columnDefinition = "varchar(30)")
    private String circuit_name;
    
    @Column(name= "country", columnDefinition = "varchar(20)")
    private String country;
    
    @Column(name= "date_race")
    @Temporal(TemporalType.DATE)
    private Date date_race;
    
    @Column(name= "date_creared")
    @Temporal(TemporalType.DATE)
    private Date date_creared;
    
    @Column(name= "date_updated")
    @Temporal(TemporalType.DATE)
    private Date date_updated;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "seasson_id", referencedColumnName = "id")
    private Seasson seasson;
    
}
