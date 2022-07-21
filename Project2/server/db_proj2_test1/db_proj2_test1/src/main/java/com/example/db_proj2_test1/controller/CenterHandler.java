package com.example.db_proj2_test1.controller;
import APIs.task1;
import com.example.db_proj2_test1.entity.Center;
import com.example.db_proj2_test1.entity.Center1;
import com.example.db_proj2_test1.repository.Center1Repository;
import com.example.db_proj2_test1.repository.CenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//import java.awt.print.Pageable;


@RestController
@RequestMapping("/center")
public class CenterHandler {
    @Autowired
    CenterRepository CenterRepository;
    Center1Repository CenterRepository1;

    @GetMapping("/findAll/{page}/{size}")
    public Page<Center> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return CenterRepository.findAll(pageable);
    }
    @PostMapping("/find/{page}/{size}")
    public Page<Center> find(@PathVariable("page") Integer page, @PathVariable("size") Integer size) throws SQLException {
        task1.start();
        task1.execute("TRUNCATE center1");
        task1.end();
        Pageable pageable = PageRequest.of(page - 1, size);
        return CenterRepository.findAll(pageable);
    }

    @PostMapping("/save")
    public String save(@RequestBody Center center) {
        Center result = CenterRepository.save(center);
        if (result != null) {
            return "success";
        } else {
            return "error";
        }
    }


    @GetMapping("/findById/{id}")
    public Center findById(@PathVariable("id") Integer id){
        return CenterRepository.findById(id).get();

    }

    @PostMapping("/findId/{id}")
    public Center findId(@PathVariable("id") Integer id) throws SQLException {
        Center fin= CenterRepository.findById(id).get();

        task1.start();

//        task1.execute("TRUNCATE center1");
        String statement="insert into center1 values("+fin.getId()+", '"+ fin.getName()+"')";
        task1.load_insert(statement);

        task1.end();
        return fin;

    }
    @PutMapping("/update")
    public String update(@RequestBody Center center) throws SQLException {
        task1.start();
        ArrayList<String> Columns =new ArrayList<>();

        ArrayList<String> Data =new ArrayList<>();
        ArrayList<String>content=new ArrayList<>();
        ArrayList<String>parts = new ArrayList<>();
        Columns.add("name");
        content.add(center.getName());
        Data.add("id");
        parts.add(String.valueOf(center.getId()));
        content.add(String.valueOf(center.getId()));
        boolean result=task1.Update("update center set ","center",Columns,Data,content,parts);
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
        task1.Delete("delete from ","center",new ArrayList<>(),Data,content);
        task1.end();
//        CenterRepository.deleteById(id);

    }

    @GetMapping("/get")
    public Integer getCount() throws SQLException {
//        task1.start();
//        int fin= task1.Count("staff",null);
//        task1.end();
//        return fin;
        return CenterRepository.getCount();
    }

    @GetMapping  ("/select")
    public boolean select(String name) throws SQLException {
boolean isValid=false;
        task1.start();
        ArrayList<String>content=new ArrayList<>();
        content.add(name);
        ArrayList<String>columns = new ArrayList<>();

        ArrayList<String>Data = new ArrayList<>();
        Data.add("name");

        ArrayList<String[]> strings=task1.Select("center",content,columns,Data,"select ");

//        String state="TRUNCATE center1";
//        task1.execute(state);
        for (int i = 0; i <strings.size() ; i++) {
            String statement="insert into center1 values("+strings.get(i)[0]+", '"+ strings.get(i)[1]+"')";
            task1.load_insert(statement);

        }
        task1.end();
        return true;
    }

    @PostMapping  ("/findbyName/{name}")
    public boolean findbyName(@PathVariable("name") String name) throws SQLException {
        List<Center> list1= CenterRepository.searchByName(name);
        task1.start();
//task1.execute("TRUNCATE center1");
        for (int i = 0; i < list1.size(); i++) {
            String statement="insert into center1 values("+list1.get(i).getId()+", '"+ list1.get(i).getName()+"')";
            task1.load_insert(statement);
        }
        task1.end();
        return list1.size() > 0;
    }


}
