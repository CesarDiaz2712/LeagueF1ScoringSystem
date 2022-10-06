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
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
@Table(name = "driver_racer")
public class DriverRacer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "id")
    private Integer id;
    
    @Column(name= "name", columnDefinition="varchar(20)")
    private String name;
    
    @Column(name= "gamertag", columnDefinition="varchar(20)", unique = true)
    private String gamertag;
    
    @Column(name= "number_driver", columnDefinition="varchar(2)")
    private String number_driver;
    
    @Column(name= "date_creared")
    private Date date_creared;
    
    @Column(name= "date_updated")
    private Date date_updated;
    
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserApp user;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;

}
