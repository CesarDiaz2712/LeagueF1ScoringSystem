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
@Table(name = "seasson")
public class Seasson {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "id")
    private Integer id;
    
    @Column(name= "year", columnDefinition = "varchar(4)")
    private String year;
    
    @Column(name= "date_creared")
    @Temporal(TemporalType.DATE)
    private Date date_creared;
    
    @Column(name= "date_updated")
    @Temporal(TemporalType.DATE)
    private Date date_updated;
    
}
