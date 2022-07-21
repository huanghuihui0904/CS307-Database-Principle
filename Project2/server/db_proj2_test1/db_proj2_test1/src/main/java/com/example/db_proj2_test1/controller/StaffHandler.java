package com.example.db_proj2_test1.controller;
import APIs.task1;
import cn.yueshutong.springbootstartercurrentlimiting.annotation.CurrentLimiter;
import com.example.db_proj2_test1.entity.Searched;
import com.example.db_proj2_test1.entity.Staff;
import com.example.db_proj2_test1.repository.StaffRepository;
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
@RequestMapping("/staff")
public class StaffHandler {

    @Autowired
    private StaffRepository staffRepository;

    @GetMapping("/findAll/{page}/{size}")
    @CurrentLimiter(QPS=5)
    public Page<Staff> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        return staffRepository.findAll(pageable);
    }

    @PostMapping("/save")
    public String save(@RequestBody Staff staff) {
        Staff result = staffRepository.save(staff);
        if (result != null) {
            return "success";
        } else {
            return "error";
        }

    }



    @PutMapping("/update")
    public static String update(@RequestBody Staff staff) throws SQLException {
        task1.start();
        ArrayList<String>Columns =new ArrayList<>();

        ArrayList<String> Data =new ArrayList<>();
        ArrayList<String>content=new ArrayList<>();
        ArrayList<String>parts = new ArrayList<>();
        Columns.add("name");
        Columns.add("age");
        Columns.add("gender");
//        Columns.add("number");
        Columns.add("supply_center");
        Columns.add("mobile_number");
        Columns.add("type");
        content.add(staff.getName());
        content.add(String.valueOf(staff.getAge()));
        content.add(staff.getGender());
//        content.add(staff.getNumber());
        content.add(staff.getSupply_center());
        content.add(staff.getMobile_number());
        content.add(staff.getType());
        Data.add("id");
        Data.add("number");
        parts.add(String.valueOf(staff.getId()));
        content.add(String.valueOf(staff.getId()));
        parts.add(String.valueOf(staff.getNumber()));
        content.add(String.valueOf(staff.getNumber()));
        boolean result=task1.Update("update staff set ","staff",Columns,Data,content,parts);
        task1.end();
        if (result){
            return "success";
        }else {
            return "error";
        }
    }
    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable("id") Integer id) throws SQLException {
        task1.start();
        ArrayList<String> Data = new ArrayList<>();
        Data.add("id");
        ArrayList<String>content=new ArrayList<>();
        content.add(String.valueOf(id));
        task1.Delete("delete from ","staff",new ArrayList<>(),Data,content);
        task1.end();
    }

    @GetMapping("/get")
   public  Integer getCount() throws SQLException {
        task1.start();
        int fin= task1.Count("staff",null);
        task1.end();
        return fin;
    }

    @GetMapping("/findById/{id}")
    public Staff findById(@PathVariable("id") Integer id){
        return staffRepository.findById(id).get();

    }

    @GetMapping("/select")
    public  ArrayList<Staff> Select(@RequestParam(defaultValue = "id") String column1, @RequestParam(defaultValue = "0") String content1) throws SQLException {
        task1.start();
        ArrayList<String>contents=new ArrayList<>();
        contents.add(content1);
        ArrayList<String>Data=new ArrayList<>();;
        Data.add(column1);
        ArrayList<String[]>fin=task1.Select("staff",contents,new ArrayList<>(),Data,"select ");
        ArrayList<Staff> staffs=new ArrayList<>();
        for (int i = 0; i <staffs.size() ; i++) {
            staffs.add(staffRepository.findById(Integer.valueOf(fin.get(i)[0])).get());
        }
        task1.end();
        return staffs;




    }





}
