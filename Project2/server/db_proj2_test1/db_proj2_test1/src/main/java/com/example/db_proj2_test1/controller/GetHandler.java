package com.example.db_proj2_test1.controller;
import com.example.db_proj2_test1.entity.Staff;
import com.example.db_proj2_test1.repository.GetRepository;
import com.example.db_proj2_test1.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
//import java.awt.print.Pageable;


@RestController
@RequestMapping("/get")
public class GetHandler {

    @Autowired
    private GetRepository getRepository;


    @GetMapping("/getSalesmenCount")
    public Integer getSalesmenCount(){
        return getRepository.getSalesmenCount();
    }

    @GetMapping("/getDirectorCount")
    public Integer getDirectorCount(){
        return getRepository.getDirectorCount() ;
    }

    @GetMapping("/getContracts_ManagerCount")
    public Integer getContracts_ManagerCount(){
        return getRepository.getContracts_ManagerCount();
    }

    @GetMapping("/getSupply_StaffCount")
    public Integer getSupply_StaffCount(){
        return getRepository.getSupply_StaffCount();
    }

    @GetMapping("/getContractCount")
    public Integer getContractCount(){
        return getRepository.getContractCount();
    }

}
