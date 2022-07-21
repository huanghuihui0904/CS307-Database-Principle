package com.example.db_proj2_test1.controller;
import APIs.task1;
import com.example.db_proj2_test1.entity.Center;
import com.example.db_proj2_test1.entity.Center1;
import com.example.db_proj2_test1.repository.Center1Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//import java.awt.print.Pageable;


@RestController
@RequestMapping("/center1")
public class Center1Handler {
    @Autowired
     Center1Repository CenterRepository1;

    @GetMapping("/findAll/{page}/{size}")
    public Page<Center1> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        return CenterRepository1.findAll(pageable);
    }



    @GetMapping("/findById/{id}")
    public Center1 findById(@PathVariable("id") Integer id){
        return CenterRepository1.findById(id).get();

    }
    @PostMapping("/save")
    public String save(@RequestBody Center1 center1) {
        Center1 result = CenterRepository1.save(center1);
        if (result != null) {
            return "success";
        } else {
            return "error";
        }
    }




}
