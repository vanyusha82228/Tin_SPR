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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GitHubWebClient2Test {
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
        String gitHubResponse = """
    {
        "login":"n0str",
        "id":988885,
        "avatar_url":"https://avatars.githubusercontent.com/u/988885?v=3",
        "gravatar_id":"",
        "url":"https://api.github.com/users/n0str",
        "html_url":"https://github.com/n0str",
        "type":"User",
        "site_admin":false,
        "name":"testRepo",
        "description": "Test repository",
        "public_repos":9,
        "public_gists":14,
        "followers":12,
        "following":20,
        "created_at":"2011-08-18T14:54:56Z",
        "updated_at":"2015-12-23T17:54:08Z"
    }
    """;

        // Задаем имитацию сервера
        wireMockServer.stubFor(get(urlPathEqualTo("/repos/n0str/testRepo"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(gitHubResponse)));

        // Вызываем метод, который мы тестируем
        GitHubRepositoryDTO repositoryDTO = gitHubWebClient.fetchRepositoryInfo("n0str", "testRepo");

        // Проверяем, что DTO заполнен корректно
        assertEquals("testRepo", repositoryDTO.getName());
        assertEquals("Test repository", repositoryDTO.getDescription());
        assertEquals(OffsetDateTime.parse("2015-12-23T17:54:08Z"), repositoryDTO.getUpdatedAt());
    }

    @AfterAll
    public static void afterAll() {
        wireMockServer.stop();
    }
}

