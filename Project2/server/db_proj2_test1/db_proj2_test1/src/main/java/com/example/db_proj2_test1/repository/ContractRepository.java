package com.example.db_proj2_test1.repository;

import com.example.db_proj2_test1.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract,Integer> {
}
