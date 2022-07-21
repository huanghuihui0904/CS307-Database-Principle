package com.example.db_proj2_test1.repository;

import com.example.db_proj2_test1.entity.Login;
import com.example.db_proj2_test1.entity.Role;
import com.example.db_proj2_test1.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role,Integer> {



    @Query(value = "select * from user_role where user_name=?",nativeQuery = true)
    Role getRole(String name);








}
