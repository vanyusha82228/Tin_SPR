package edu.java.stackocerflow;

import edu.java.configuration.ApplicationConfig;
<<<<<<< HEAD
import edu.java.dto.StackOverflowQuestionDTO;
=======
import edu.java.dto.StackOverflowResponseDTO;
>>>>>>> main
import edu.java.interfaceForClient.StackOverflowClientInterface;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Log4j2
@Component
public class StackOverflowWebClient implements StackOverflowClientInterface {
    private final WebClient webClient;
    private final String baseUrl;

    public StackOverflowWebClient(WebClient.Builder webClientBuilder, ApplicationConfig applicationConfig) {
        this.baseUrl = applicationConfig.stackOverflowBaseUrl();
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    @Override
<<<<<<< HEAD
    public StackOverflowQuestionDTO fetchQuestionInfo(int questionId) {
        StackOverflowQuestionDTO result = webClient.get()
            .uri("/questions/{id}", questionId)
            .retrieve()
            .bodyToMono(StackOverflowQuestionDTO.class)
            .block();
        if (result == null) {
=======
    public StackOverflowResponseDTO fetchQuestionInfo(int questionId) {
        StackOverflowResponseDTO result = webClient.get()
            .uri("/questions/{ids}", questionId)
            .retrieve()
            .bodyToMono(StackOverflowResponseDTO.class)
            .block();
        if (result == null || result.getItems() == null || result.getItems().isEmpty()) {
>>>>>>> main
            log.info("Не удалось получить вопрос");
        }
        return result;
    }
}
