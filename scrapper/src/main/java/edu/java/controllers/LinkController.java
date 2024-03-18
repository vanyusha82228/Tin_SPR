    package edu.java.controllers;


    import edu.java.dto.request.AddLinkRequest;
    import edu.java.dto.request.RemoveLinkRequest;
    import edu.java.dto.response.LinkResponse;
    import edu.java.dto.response.ListLinksResponse;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.DeleteMapping;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RequestHeader;
    import org.springframework.web.bind.annotation.RestController;


    @RestController
    public class LinkController {
        @GetMapping("/links")
        public ResponseEntity<ListLinksResponse> getAllLinks(@RequestHeader("Tg-Chat-Id") Long thChatId) {
            ListLinksResponse response = new ListLinksResponse();
            return ResponseEntity.ok(response);
        }

        @PostMapping("/links")
        public ResponseEntity<LinkResponse> addLink(@RequestHeader("Tg-Chat-Id") Long chatId,
            @RequestBody AddLinkRequest request) {
            LinkResponse response = new LinkResponse(1L, request.getLink());
            return ResponseEntity.ok(response);
        }

        @DeleteMapping("/links")
        public ResponseEntity<LinkResponse> removeLink(@RequestHeader("Tg-Chat-Id") Long chatId,
            @RequestBody RemoveLinkRequest request) {
            LinkResponse response = new LinkResponse(1L, request.getLink());
            return ResponseEntity.ok(response);
        }
    }
