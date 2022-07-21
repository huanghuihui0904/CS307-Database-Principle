package com.example.db_proj2_test1.controller;

import com.example.db_proj2_test1.entity.Login;
import com.example.db_proj2_test1.entity.Role;
import com.example.db_proj2_test1.entity.Staff;
import com.example.db_proj2_test1.repository.LoginRepository;
import com.example.db_proj2_test1.repository.RoleRepository;
import com.example.db_proj2_test1.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
//import java.awt.print.Pageable;


@RestController
@RequestMapping("/login")
public class LoginHandler {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private RoleRepository roleRepository;


    @PostMapping("/check")
    public String check(@RequestBody Login login) {
        Login result = loginRepository.check(login.getUser_name(), login.getPassword());
        if (result != null) {
           return "success";

        } else {
            return "error";
        }

    }
    @PostMapping("/getRole")
    public String getRole(@RequestBody Login login) {

            Role result= roleRepository.getRole(login.getUser_name());
            return result.getRole_name();



    }




}
