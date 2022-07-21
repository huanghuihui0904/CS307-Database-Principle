package com.example.db_proj2_test1.entity;

import jdk.jfr.Name;
import lombok.Data;
import org.hibernate.annotations.Table;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Entity

@Data
public class Contract {
    @Id
    private String contract_num;
    private String enterprise;
    private String contract_manager;
    private Date contract_date;
    private String contract_type;
    private String supply_center;
}

