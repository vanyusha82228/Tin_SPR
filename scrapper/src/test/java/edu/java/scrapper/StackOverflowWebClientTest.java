package edu.java.scrapper;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.configuration.ApplicationConfig;
import edu.java.dto.StackOverflowQuestionDTO;
import edu.java.stackocerflow.StackOverflowWebClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.Duration;
import java.time.OffsetDateTime;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

public class StackOverflowWebClientTest {
    private static WireMockServer wireMockServer;
    private StackOverflowWebClient stackOverflowWebClient;

    @BeforeAll
    public static void beforeAll() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
    }

    @BeforeEach
    public void setUp() {
        wireMockServer.resetAll();
        String baseUrl = wireMockServer.baseUrl();
        ApplicationConfig applicationConfig = new ApplicationConfig(
            new ApplicationConfig.Scheduler(true, Duration.ofSeconds(5), Duration.ofSeconds(5)),
            "",
            baseUrl
        );
        stackOverflowWebClient = new StackOverflowWebClient(WebClient.builder(), applicationConfig);
    }

    @Test
    public void testFetchQuestionInfo() {
        // Определяем тестовые данные
        int questionId = 12345;
        String title = "Test question";
        OffsetDateTime creationDate = OffsetDateTime.now();

        // Задаем имитацию сервера
        wireMockServer.stubFor(get(urlPathEqualTo("/questions/" + questionId))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("{\"questionId\": " + questionId + ", " +
                    "\"title\": \"" + title + "\", " +
                    "\"creationDate\": \"" + creationDate + "\"}")));

        // Вызываем метод, который мы тестируем
        StackOverflowQuestionDTO questionDTO = stackOverflowWebClient.fetchQuestionInfo(questionId);

        // Проверяем, что DTO заполнен корректно
        Assertions.assertEquals(questionId, questionDTO.getQuestionId());
        Assertions.assertEquals(title, questionDTO.getTitle());
        Assertions.assertEquals(creationDate, questionDTO.getCreationDate());
    }

    @AfterAll
    public static void afterAll() {
        wireMockServer.stop();
    }
}
