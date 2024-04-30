package edu.java.scrapper.controllers;

import edu.java.scrapper.domain.jdbcInterface.LinkService;
import edu.java.scrapper.domain.model.UserLink;
import edu.java.scrapper.dto.request.AddLinkRequest;
import edu.java.scrapper.dto.request.RemoveLinkRequest;
import edu.java.scrapper.dto.response.LinkResponse;
import edu.java.scrapper.dto.response.ListLinksResponse;
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
    private final LinkService linkService;

    @Autowired
    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping("/links")
    public ResponseEntity<ListLinksResponse> getAllLinks(@RequestHeader("Tg-Chat-Id") Long tgChatId) {
        Collection<UserLink> links = linkService.listAll(tgChatId);
        List<LinkResponse> linkResponses = links.stream()
            .map(link -> {
                String userId = link.getUserId() != null ? link.getUserId().toString() : "null";
                return new LinkResponse(link.getLinkId().getId(), userId);
            })
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
        UserLink addedLink = linkService.add(chatId, url);
        if (addedLink == null) {
            return ResponseEntity.notFound().build();
        }
        LinkResponse response = new LinkResponse(addedLink.getLinkId().getId(), request.getLink());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/links")
    public ResponseEntity<LinkResponse> removeLink(
        @RequestHeader("Tg-Chat-Id") Long chatId,
        @RequestBody RemoveLinkRequest request
    ) {
        URI url = URI.create(request.getLink());
        UserLink removedLink = linkService.remove(chatId, url);
        if (removedLink == null) {
            return ResponseEntity.notFound().build();
        }
        LinkResponse response = new LinkResponse(removedLink.getLinkId().getId(), request.getLink());
        return ResponseEntity.ok(response);
    }
}
