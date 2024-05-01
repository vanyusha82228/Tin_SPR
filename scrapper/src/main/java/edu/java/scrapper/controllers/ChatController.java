package edu.java.scrapper.controllers;


import edu.java.scrapper.domain.jdbcInterface.TgChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
    private final TgChatService tgChatService;

    @Autowired
    public ChatController(TgChatService tgChatService) {
        this.tgChatService = tgChatService;
    }

    @PostMapping("/tg-chat/{id}")
    public ResponseEntity<Void> registerChat(@PathVariable("id") Long id) {
        tgChatService.register(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/tg-chat/{id}")
    public ResponseEntity<Void> deleteChat(@PathVariable("id") Long id) {
        tgChatService.unregister(id);
        return ResponseEntity.ok().build();
    }
}
