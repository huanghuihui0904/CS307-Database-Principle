package com.example.db_proj2_test1.repository;

import com.example.db_proj2_test1.entity.Center1;
import com.example.db_proj2_test1.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Center1Repository extends JpaRepository<Center1,Integer> {

    @Query(value = "select count(*) from center1",nativeQuery = true)
    Integer getCount();





}
