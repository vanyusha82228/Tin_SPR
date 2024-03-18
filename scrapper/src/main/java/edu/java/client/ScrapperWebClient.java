package edu.java.client;


import java.net.URI;
import edu.java.dto.request.AddLinkRequest;
import edu.java.dto.request.RemoveLinkRequest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ScrapperWebClient {
    private final WebClient webClient;
    private final static String URI = "/links";
    private final static String HEADER_NAME = "Tg-Chat-Id";

    public ScrapperWebClient(URI baseUrl) {
        this.webClient = WebClient.create(baseUrl.toString());
    }

    public Mono<Void> registerChat(long chatId) {
        return webClient.post()
            .uri("/tg-chat/{id}", chatId)
            .retrieve()
            .bodyToMono(Void.class);
    }

    public Mono<Void> addLink(long chatId, AddLinkRequest addLinkRequest) {
        return webClient.post()
            .uri(URI)
            .header(HEADER_NAME, String.valueOf(chatId))
            .bodyValue(addLinkRequest)
            .retrieve()
            .bodyToMono(Void.class);
    }

    public Mono<Void> removeLink(long chatId, RemoveLinkRequest removeLinkRequest) {
        return webClient.delete()
            .uri(URI)
            .header(HEADER_NAME, String.valueOf(chatId))
            .retrieve()
            .bodyToMono(Void.class);
    }
}
