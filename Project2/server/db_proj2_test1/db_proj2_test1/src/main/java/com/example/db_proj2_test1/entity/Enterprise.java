package com.example.db_proj2_test1.entity;

import jdk.jfr.Name;
import lombok.Data;
import org.hibernate.annotations.Table;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity

@Data
public class Enterprise {
    @Id
    private Integer id;
    private String name;
    private String country;
    private String city;
    private String supply_center;
    private String industry;
}
