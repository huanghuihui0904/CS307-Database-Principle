package com.example.db_proj2_test1.controller;


import com.example.db_proj2_test1.entity.Inventory;
import com.example.db_proj2_test1.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;




@RestController
@RequestMapping("/inventory")
public class InventoryHandler {

    @Autowired
    private InventoryRepository inventoryRepository;

    @GetMapping("/findAll/{page}/{size}")
    public Page<Inventory> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        return inventoryRepository.findAll(pageable);
    }

    @PostMapping("/save")
    public String save(@RequestBody Inventory inventory) {
        Inventory result = inventoryRepository.save(inventory);
        if (result != null) {
            return "success";
        } else {
            return "error";
        }

    }





    @GetMapping("/findById/{id}")
    public Inventory findById(@PathVariable("id") Integer id){
        return inventoryRepository.findById(id).get();

    }






}
