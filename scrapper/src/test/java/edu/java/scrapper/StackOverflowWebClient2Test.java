package edu.java.scrapper;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.configuration.ApplicationConfig;
import edu.java.dto.StackOverflowQuestionDTO;
import edu.java.dto.StackOverflowResponseDTO;
import edu.java.stackocerflow.StackOverflowWebClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StackOverflowWebClient2Test {
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
        String stackOverflowResponse = """
    {
        "items": [
            {
                "tags": [
                    "java",
                    "jvm",
                    "runtime"
                ],
                "owner": {
                    "account_id": 10709140,
                    "reputation": 352,
                    "user_id": 12616968,
                    "user_type": "registered",
                    "profile_image": "https://www.gravatar.com/avatar/6ba865101df41e4f318a101e5fc62280?s=256&d=identicon&r=PG&f=y&so-version=2",
                    "display_name": "mig001",
                    "link": "https://stackoverflow.com/users/12616968/mig001"
                },
                "is_answered": true,
                "view_count": 39,
                "answer_count": 1,
                "score": 2,
                "last_activity_date": 1709986474,
                "creation_date": 1709978980,
                "question_id": 78131948,
                "content_license": "CC BY-SA 4.0",
                "link": "https://stackoverflow.com/questions/78131948/will-jvm-optimise-this-mathmatical-operation",
                "title": "Will JVM optimise this mathmatical operation?"
            }
        ],
        "has_more": false,
        "quota_max": 10000,
        "quota_remaining": 9982
    }
    """;


        // Задаем имитацию сервера
        wireMockServer.stubFor(get(urlPathEqualTo("/questions/78131948"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(stackOverflowResponse)));

        // Вызываем метод, который мы тестируем
        StackOverflowResponseDTO responseDTO = stackOverflowWebClient.fetchQuestionInfo(78131948);

        // Проверяем, что DTO заполнен корректно
        assertNotNull(responseDTO);
        assertTrue(responseDTO.getItems() != null && !responseDTO.getItems().isEmpty());

        StackOverflowQuestionDTO questionDTO = responseDTO.getItems().get(0);
        assertNotNull(questionDTO);
        assertEquals(78131948, questionDTO.getQuestionId());
        assertEquals("Will JVM optimise this mathmatical operation?", questionDTO.getTitle());

        OffsetDateTime expectedCreationDate = OffsetDateTime.ofInstant(Instant.ofEpochSecond(1709978980), ZoneOffset.UTC);
        assertEquals(expectedCreationDate, questionDTO.getCreationDate());
    }

    @AfterAll
    public static void afterAll() {
        wireMockServer.stop();
    }
}
