package com.gmail.kijevigombooc.jottinshoppin.server.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.gmail.kijevigombooc.jottinshoppin.server.service.ItemService;
import com.gmail.kijevigombooc.jottinshoppin.server.service.SseService;

@Controller
@RequestMapping("/sse")
public class SseController {

    @Autowired
    SseService sseService;
    @Autowired
    ItemService itemService;

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public ResponseEntity<String> send() throws IOException {
        sseService.sendToAll(itemService.getAll().toString());
        return new ResponseEntity<String>(itemService.getAll().toString(), HttpStatus.OK);
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ResponseEntity<SseEmitter> register() throws IOException {
        return new ResponseEntity<SseEmitter>(sseService.getNewEmitter(itemService.getAll().toString()), HttpStatus.OK);
    }
}
