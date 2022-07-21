package com.example.db_proj2_test1.repository;

import com.example.db_proj2_test1.entity.Login;
import com.example.db_proj2_test1.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LoginRepository extends JpaRepository<Login,Integer> {

    @Query(value = "select user_name,password from t4_user where user_name=? and password=?",nativeQuery = true)
    Login check(String name,String password);

//    @Query(value = "select * from user_role where user_name=?",nativeQuery = true)
//    Login getRole(String name);








}
