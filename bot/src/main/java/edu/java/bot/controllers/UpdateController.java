package edu.java.bot.controllers;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.dto.responseDto.LinkUpdate;
import edu.java.bot.servicebot.ServiceBot;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateController {
    private final ServiceBot serviceBot;

    public UpdateController(ServiceBot serviceBot) {
        this.serviceBot = serviceBot;
    }

    @PostMapping("/updates")
    public ResponseEntity<?> handleUpdate(@RequestBody LinkUpdate linkUpdate) {
        if (linkUpdate.getTgChatIds() == null || linkUpdate.getTgChatIds().isEmpty()) {
            return ResponseEntity.ok().build();
        }

        for (Long chatId : linkUpdate.getTgChatIds()) {
            serviceBot.execute(new SendMessage(chatId, linkUpdate.getDescription()));
        }

        return ResponseEntity.ok().build();
    }
}
