package com.example.db_proj2_test1.repository;

import com.example.db_proj2_test1.entity.Get;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface GetRepository extends JpaRepository<Get,Integer> {


    @Query(value = "select count(*) from staff where type='Salesman' ",nativeQuery = true)
     Integer getSalesmenCount() ;

    @Query(value = "select count(*) from staff where type='Director' ",nativeQuery = true)
    Integer getDirectorCount() ;

    @Query(value = "select count(*) from staff where type='Contracts Manager' ",nativeQuery = true)
    Integer getContracts_ManagerCount() ;

    @Query(value = "select count(*) from staff where type='Supply Staff' ",nativeQuery = true)
    Integer getSupply_StaffCount() ;

    @Query(value = "select count(*) from contract",nativeQuery = true)
    Integer getContractCount() ;



}
