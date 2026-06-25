package edu.java.bot.client;

import edu.java.bot.dto.requestDto.AddLinkRequest;
import edu.java.bot.dto.requestDto.RemoveLinkRequest;
import edu.java.bot.dto.responseDto.LinkResponse;
import edu.java.bot.dto.responseDto.ListLinksResponse;
import java.net.URI;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ScrapperClient {
    private static final String LINKS_URI = "/links";
    private static final String CHAT_ID_HEADER = "Tg-Chat-Id";

    private final WebClient webClient;

    public ScrapperClient(URI scrapperBaseUri) {
        this.webClient = WebClient.create(scrapperBaseUri.toString());
    }

    public Mono<Void> registerChat(long chatId) {
        return webClient.post()
            .uri("/tg-chat/{id}", chatId)
            .retrieve()
            .bodyToMono(Void.class);
    }

    public Mono<LinkResponse> addLink(long chatId, String link) {
        return webClient.post()
            .uri(LINKS_URI)
            .header(CHAT_ID_HEADER, String.valueOf(chatId))
            .bodyValue(new AddLinkRequest(link))
            .retrieve()
            .bodyToMono(LinkResponse.class);
    }

    public Mono<LinkResponse> removeLink(long chatId, String link) {
        return webClient.method(org.springframework.http.HttpMethod.DELETE)
            .uri(LINKS_URI)
            .header(CHAT_ID_HEADER, String.valueOf(chatId))
            .bodyValue(new RemoveLinkRequest(link))
            .retrieve()
            .bodyToMono(LinkResponse.class);
    }

    public Mono<ListLinksResponse> listLinks(long chatId) {
        return webClient.get()
            .uri(LINKS_URI)
            .header(CHAT_ID_HEADER, String.valueOf(chatId))
            .retrieve()
            .bodyToMono(ListLinksResponse.class);
    }
}
