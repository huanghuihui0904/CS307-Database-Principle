package com.example.db_proj2_test1.repository;

import com.example.db_proj2_test1.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory,Integer> {

}
