package com.example.db_proj2_test1.repository;

import com.example.db_proj2_test1.entity.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EnterpriseRepository extends JpaRepository<Enterprise,Integer> {

    @Query(value = "select count(*) from enterprise",nativeQuery = true)
    Integer getCount();
}
