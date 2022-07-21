package com.example.db_proj2_test1.controller;
import APIs.task1;
import com.example.db_proj2_test1.entity.Center;
import com.example.db_proj2_test1.entity.Searched;
import com.example.db_proj2_test1.entity.Enterprise;
import com.example.db_proj2_test1.repository.EnterpriseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
//import java.awt.print.Pageable;


@RestController
@RequestMapping("/enterprise")
public class EnterpriseHandler {

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    @GetMapping("/findAll/{page}/{size}")
    public Page<Enterprise> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        return enterpriseRepository.findAll(pageable);
    }
    @GetMapping("/get")
    public Integer getCount()  {
//        task1.start();
//        int fin= task1.Count("staff",null);
//        task1.end();
//        return fin;
        return enterpriseRepository.getCount();
    }
    @GetMapping("/findById/{id}")
    public Enterprise findById(@PathVariable("id") Integer id){
        return enterpriseRepository.findById(id).get();

    }




}
