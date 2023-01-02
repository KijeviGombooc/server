package com.gmail.kijevigombooc.jottinshoppin.server.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import com.gmail.kijevigombooc.jottinshoppin.server.common.Item;
import com.gmail.kijevigombooc.jottinshoppin.server.repository.ItemRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

@Component
public class ItemService {
    
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    FirebaseMessaging fcm;
    
    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    public Item addItem(Item item) {
        item.setId(null);
        var savedItem = itemRepository.save(item);
        notifyTopic();
        return savedItem;
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

    public void deleteItem(Long id) throws Exception {
        if(id == null) {
            throw new Exception("Item id cannot be null!");
        }
        try {
            itemRepository.deleteById(id);
            
        } catch (EmptyResultDataAccessException e) {
            throw new Exception("Item with id " + id + " does not exist!");
        }
    }

    private void notifyTopic() {
        Message msg = Message.builder()
        .setTopic("JOTTIN_SHOPPIN_TOPIC")
        .putData("body", "some data")
        .build();

        try {
            String id = fcm.send(msg);
            System.out.println(id);
        } catch (FirebaseMessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
