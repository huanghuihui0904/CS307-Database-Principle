package com.example.db_proj2_test1.entity;

import jdk.jfr.Name;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Center {
    @Id
    private Integer id;
    private String name;

    public Center(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Center() {

    }
}
