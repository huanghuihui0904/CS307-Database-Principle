package com.example.db_proj2_test1.repository;

import com.example.db_proj2_test1.entity.Bill;
import com.example.db_proj2_test1.entity.Get;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill,Integer> {



    @Query(value = "select * from bill;",nativeQuery = true)
    List<Bill> getBill() ;


}
