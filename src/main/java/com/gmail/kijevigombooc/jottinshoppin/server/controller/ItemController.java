package com.gmail.kijevigombooc.jottinshoppin.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gmail.kijevigombooc.jottinshoppin.server.common.Item;
import com.gmail.kijevigombooc.jottinshoppin.server.service.ItemService;

@RestController
public class ItemController {
    
    @Autowired
    ItemService itemService;

    @GetMapping("/items")
    List<Item> getItems() {
        return itemService.getAll();
    }

    @PostMapping("/items")
    Item addItem(@RequestBody Item item) {
        return itemService.addItem(item);
    }

    @PutMapping("/items")
    Item updateItem(@RequestBody Item item) throws Exception {
        return itemService.updateItem(item);
    }
}
