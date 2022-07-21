package com.example.db_proj2_test1.controller;
import APIs.task1;

import com.example.db_proj2_test1.entity.Contract;
import com.example.db_proj2_test1.entity.Searched;


import com.example.db_proj2_test1.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//import java.awt.print.Pageable;


@RestController
@RequestMapping("/contract")
public class ContractHandler {

    @Autowired
    private ContractRepository contractRepository;

    @GetMapping("/findAll/{page}/{size}")
    public Page<Contract> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        return contractRepository.findAll(pageable);
    }

    @PostMapping("/save")
    public String save(@RequestBody Contract model) {
        Contract result = contractRepository.save(model);
        if (result != null) {
            return "success";
        } else {
            return "error";
        }

    }





    @GetMapping("/findById/{id}")
    public Contract findById(@PathVariable("id") Integer id){
        return contractRepository.findById(id).get();

    }






}
