package com.example.db_proj2_test1.entity;

import jdk.jfr.Name;
import lombok.Data;
import org.hibernate.annotations.Table;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity

@Data
public class Staff {
    @Id
    private Integer id;
    private String name;
    private Integer age;
    private String gender;

    private String number;
    private String supply_center;
    private String mobile_number;
    private String type;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSupply_center() {
        return supply_center;
    }

    public void setSupply_center(String supply_center) {
        this.supply_center = supply_center;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Staff(){

    }
    public Staff( Integer id,
     String name,
     Integer age,
     String gender,
     String number,
     String supply_center,
     String mobile_number,
     String type){
        this.id=id;
        this.name=name;
        this.age=age;
        this.gender=gender;
        this.number=number;
        this.supply_center=supply_center;
        this.mobile_number=mobile_number;
        this.type=type;
    }



}
