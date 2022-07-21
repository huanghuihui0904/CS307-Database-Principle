package com.example.db_proj2_test1.entity;

import jdk.jfr.Name;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Role {
    @Id
    private String user_name;
    private String role_name;


}
