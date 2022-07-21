package com.example.db_proj2_test1.repository;

import com.example.db_proj2_test1.entity.Center;
import com.example.db_proj2_test1.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CenterRepository extends JpaRepository<Center,Integer> {

    @Query(value = "select count(*) from center",nativeQuery = true)
    Integer getCount();


    @Query(value="select * from center where name = ? ",nativeQuery = true)
    List<Center> searchByName(String name);

    @Query(value="select * from center where id = ? ",nativeQuery = true)
    List<Center> searchById(Integer id);
}
