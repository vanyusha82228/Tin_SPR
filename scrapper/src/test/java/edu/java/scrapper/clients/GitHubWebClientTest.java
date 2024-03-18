package edu.java.scrapper.clients;


import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.configuration.ApplicationConfig;
import edu.java.dto.clintsDto.GitHubRepositoryDTO;
import edu.java.github.GitHubWebClient;
import org.junit.jupiter.api.*;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.Duration;
import java.time.OffsetDateTime;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GitHubWebClientTest {
    private static WireMockServer wireMockServer;
    private GitHubWebClient gitHubWebClient;

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
            baseUrl,
            ""
        );
        gitHubWebClient = new GitHubWebClient(WebClient.builder(), applicationConfig);
    }

    @Test
    public void testFetchRepositoryInfo() {
        // Определяем тестовые данные
        String owner = "testOwner";
        String repositoryName = "testRepo";
        String description = "Test repository";
        OffsetDateTime updatedAt = OffsetDateTime.now();

        // Задаем имитацию сервера
        wireMockServer.stubFor(get(urlPathEqualTo("/repos/" + owner + "/" + repositoryName))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("{\"name\": \"" + repositoryName + "\", " +
                    "\"description\": \"" + description + "\", " +
                    "\"updated_at\": \"" + updatedAt + "\"}")));

        // Вызываем метод, который мы тестируем
        GitHubRepositoryDTO repositoryDTO = gitHubWebClient.fetchRepositoryInfo(owner, repositoryName);

        // Проверяем, что DTO заполнен корректно
        Assertions.assertEquals(repositoryName, repositoryDTO.getName());
        Assertions.assertEquals(description, repositoryDTO.getDescription());
        assertTrue(updatedAt.isEqual(repositoryDTO.getUpdatedAt()));
    }

    @AfterAll
    public static void afterAll() {
        wireMockServer.stop();
    }
}

