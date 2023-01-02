package com.gmail.kijevigombooc.jottinshoppin.server.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gmail.kijevigombooc.jottinshoppin.server.common.Item;
import com.gmail.kijevigombooc.jottinshoppin.server.repository.ItemRepository;

@Component
public class ItemService {
    
    @Autowired
    ItemRepository itemRepository;
    
    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    public Item addItem(Item item) {
        item.setId(null);
        return itemRepository.save(item);
    }

    public Item updateItem(Item item) throws Exception {
        if(item.getId() == null) {
            throw new Exception("item id is null");
        }
        Optional<Item> foundItem = itemRepository.findById(item.getId());
        if(foundItem.isEmpty()) {
            throw new Exception("item with id '" + item.getId() + "' does not exist");
        }
        return itemRepository.save(item);
    }
}
