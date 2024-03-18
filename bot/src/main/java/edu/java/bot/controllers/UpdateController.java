package edu.java.bot.controllers;


import edu.java.bot.dto.responseDto.LinkUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateController {

    @PostMapping("/updates")
    public ResponseEntity<?> handleUpdate(@RequestBody LinkUpdate linkUpdater) {
        return ResponseEntity.ok("Обновление успешно обработано");
    }

}
