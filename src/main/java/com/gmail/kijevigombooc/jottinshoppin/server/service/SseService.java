package com.gmail.kijevigombooc.jottinshoppin.server.service;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
public class SseService {
    private Set<SseEmitter> emitters = ConcurrentHashMap.newKeySet();

    public SseEmitter getNewEmitter(Object initialData) throws IOException {
        SseEmitter emitter = new SseEmitter(-1l);
        emitters.add(emitter);
        emitter.onCompletion(() -> {
            emitters.remove(emitter);
            System.out.println("Removed emitter from set");
        });
        emitter.onTimeout(() -> {
            emitters.remove(emitter);
            System.out.println("Removed emitter from set");
        });
        emitter.onError((e) -> {
            emitters.remove(emitter);
            System.out.println("Removed emitter from set");
        });
        emitter.send(initialData, MediaType.TEXT_EVENT_STREAM);
        System.out.println("Created new emitter");
        return emitter;
    }

    public void sendToAll(Object data) {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(data, MediaType.TEXT_EVENT_STREAM);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                if(e.getMessage().trim().charAt(e.getMessage().trim().length()) == ':') {
                    System.out.println(e.getCause());
                }
            }
        }
        System.out.println("Emitters ramining: " + emitters.size());
    }
}
