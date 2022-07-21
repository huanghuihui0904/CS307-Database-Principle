package com.example.db_proj2_test1.controller;
import APIs.task1;

import com.example.db_proj2_test1.entity.Orders;
import com.example.db_proj2_test1.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import javax.persistence.criteria.Order;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//import java.awt.print.Pageable;


@RestController
@RequestMapping("/orders")
public class OrdersHandler {

    @Autowired
    private OrdersRepository ordersRepository;

    @GetMapping("/findAll/{page}/{size}")
    public Page<Orders> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        return ordersRepository.findAll(pageable);
    }

    @PostMapping("/save")
    public String save(@RequestBody Orders orders) {
        Orders result = ordersRepository.save(orders);
        if (result != null) {
            return "success";
        } else {
            return "error";
        }

    }





    @GetMapping("/findById/{id}")
    public Orders findById(@PathVariable("id") Integer id){
        return ordersRepository.findById(id).get();

    }






}
