package edu.java.controllers;

import edu.java.domain.jdbc.JdbcLinkService;
import edu.java.domain.model.Link;
import edu.java.dto.request.AddLinkRequest;
import edu.java.dto.request.RemoveLinkRequest;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinksResponse;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LinkController {
    private final JdbcLinkService linkService;

    @Autowired
    public LinkController(JdbcLinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping("/links")
    public ResponseEntity<ListLinksResponse> getAllLinks(@RequestHeader("Tg-Chat-Id") Long tgChatId) {
        Collection<Link> links = linkService.listAll(tgChatId);
        List<LinkResponse> linkResponses = links.stream()
            .map(link -> new LinkResponse(link.getId(), link.getUri()))
            .collect(Collectors.toList());
        ListLinksResponse response = new ListLinksResponse(linkResponses, linkResponses.size());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/links")
    public ResponseEntity<LinkResponse> addLink(
        @RequestHeader("Tg-Chat-Id") Long chatId,
        @RequestBody AddLinkRequest request
    ) {
        URI url = URI.create(request.getLink());
        Link addedLink = linkService.add(chatId, url);
        LinkResponse response = new LinkResponse(addedLink.getId(), addedLink.getUri());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/links")
    public ResponseEntity<LinkResponse> removeLink(
        @RequestHeader("Tg-Chat-Id") Long chatId,
        @RequestBody RemoveLinkRequest request
    ) {
        URI url = URI.create(request.getLink());
        Link removedLink = linkService.remove(chatId, url);
        LinkResponse response = new LinkResponse(removedLink.getId(), removedLink.getUri());
        return ResponseEntity.ok(response);
    }
}
