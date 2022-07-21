package com.example.db_proj2_test1.entity;

import jdk.jfr.Name;
import lombok.Data;
import org.hibernate.annotations.Table;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity

@Data
public class Model {
    @Id
    private Integer id;
    private String number;
    private String model;
    private String name;
    private Integer unit_price;
}
