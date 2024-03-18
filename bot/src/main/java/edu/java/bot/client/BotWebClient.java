package edu.java.bot.client;


import java.net.URI;
import edu.java.bot.dto.responseDto.LinkUpdate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class BotWebClient {
    private final WebClient webClient;

    public BotWebClient(URI baseUri) {
        this.webClient = WebClient.create(baseUri.toString());
    }

    public Mono<Void> sendUpdate(LinkUpdate linkUpdate) {
        return webClient.post()
            .uri("/updates")
            .bodyValue(linkUpdate)
            .retrieve()
            .bodyToMono(Void.class);
    }
}
