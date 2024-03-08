package edu.java.stackocerflow;

import edu.java.configuration.ApplicationConfig;
import edu.java.dto.StackOverflowQuestionDTO;
import edu.java.interfaceForClient.StackOverflowClientInterface;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Log4j2
@Component
public class StackOverflowWebClient implements StackOverflowClientInterface {
    private final WebClient webClient;
    private final String baseUrl;
    private final ZoneOffset localZoneOffset = ZoneOffset.ofHours(3);

    public StackOverflowWebClient(WebClient.Builder webClientBuilder, ApplicationConfig applicationConfig) {
        this.baseUrl = applicationConfig.stackOverflowBaseUrl();
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    @Override
    public StackOverflowQuestionDTO fetchQuestionInfo(int questionId) {
        StackOverflowQuestionDTO result = webClient.get()
            .uri("/questions/{id}", questionId)
            .retrieve()
            .bodyToMono(StackOverflowQuestionDTO.class)
            .map(dto -> {
                // Создаем новый объект OffsetDateTime с местным смещением
                OffsetDateTime creationDateWithOffset = dto.getCreationDate().withOffsetSameInstant(localZoneOffset);
                dto.setCreationDate(creationDateWithOffset);
                return dto;
            })
            .block();
        if (result == null) {
            log.info("Не удалось получить вопрос");
        }
        return result;
    }
}
