package com.example.db_proj2_test1.repository;

import com.example.db_proj2_test1.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders,Integer> {
}
