//package edu.java.bot.controllers;
//
//import edu.java.bot.dto.response.ListLinksResponse;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/links")
//public class LinkController {
//    @GetMapping
//    public ResponseEntity<ListLinksResponse> getAllLinks(@RequestHeader("Tg-Chat-Id") Long thChatId){
//        return ResponseEntity.ok("Получить все отслеживаемые ссылки");
//    }
//}
