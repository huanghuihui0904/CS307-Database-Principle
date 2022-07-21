package com.example.db_proj2_test1.entity;

import jdk.jfr.Name;
import lombok.Data;
import org.hibernate.annotations.Table;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity

@Data
public class Inventory {
    @Id
    private String center;
    private String product_model;
    private Integer quantity;
    private Integer total_purchase_money;
    private Integer sales_quantity;
    private Integer profits;
}
