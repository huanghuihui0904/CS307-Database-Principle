package com.example.db_proj2_test1.repository;

import com.example.db_proj2_test1.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StaffRepository extends JpaRepository<Staff,Integer> {

    @Query(value = "select count(*) from staff",nativeQuery = true)
    Integer getCount();









}
