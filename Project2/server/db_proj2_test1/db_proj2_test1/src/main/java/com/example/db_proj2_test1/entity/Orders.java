package com.example.db_proj2_test1.entity;

import jdk.jfr.Name;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Entity
@Data
public class Orders {
    @Id
    private String contract_num;
    private String product_model;
    private Integer quantity;
    private String salesman_num;
    private Date estimated_delivery;
    private Date lodgement_date;




}
