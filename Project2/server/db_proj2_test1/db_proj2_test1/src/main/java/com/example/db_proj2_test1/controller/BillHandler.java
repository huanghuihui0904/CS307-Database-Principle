package com.example.db_proj2_test1.controller;
import cn.yueshutong.springbootstartercurrentlimiting.annotation.CurrentLimiter;
import com.example.db_proj2_test1.entity.Bill;

import com.example.db_proj2_test1.entity.Staff;
import com.example.db_proj2_test1.repository.BillRepository;
import com.example.db_proj2_test1.repository.GetRepository;
import com.example.db_proj2_test1.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.util.List;
//import java.awt.print.Pageable;


@RestController
@RequestMapping("/bill")
public class BillHandler {

    @Autowired
    private BillRepository billRepository;

    @GetMapping("/getBill")
    @CurrentLimiter(QPS=5)
    public List<Bill> getBill(){
        return billRepository.getBill();
    }





}
