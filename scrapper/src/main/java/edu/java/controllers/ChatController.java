package edu.java.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
    @PostMapping("/tg-chat/{id}")
    public ResponseEntity<Void> registerChat(@PathVariable("id") Long id) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/tg-chat/{id}")
    public ResponseEntity<Void> deleteChat(@PathVariable("id") Long id) {
        return ResponseEntity.ok().build();
    }
}
