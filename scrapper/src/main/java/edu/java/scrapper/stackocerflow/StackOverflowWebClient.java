package edu.java.scrapper.stackocerflow;


import edu.java.scrapper.configuration.ApplicationConfig;
import edu.java.scrapper.dto.clintsDto.StackOverflowResponseDTO;
import edu.java.scrapper.interfaceForClient.StackOverflowClientInterface;
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
    public StackOverflowResponseDTO fetchQuestionInfo(int questionId) {
        StackOverflowResponseDTO result = webClient.get()
            .uri("/questions/{ids}", questionId)
            .retrieve()
            .bodyToMono(StackOverflowResponseDTO.class)
            .block();
        if (result == null || result.getItems() == null || result.getItems().isEmpty()) {
            log.info("Не удалось получить вопрос");
        }
        return result;
    }
}
