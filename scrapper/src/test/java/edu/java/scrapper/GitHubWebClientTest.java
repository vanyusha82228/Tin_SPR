package edu.java.scrapper;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import edu.java.configuration.ApplicationConfig;
import edu.java.dto.GitHubRepositoryDTO;
import edu.java.github.GitHubWebClient;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.Duration;
import java.time.OffsetDateTime;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(WireMockExtension.class)
public class GitHubWebClientTest {
    @Mock
    private ApplicationConfig applicationConfig;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();


    @Test
    public void testFetchRepositoryInfo() {
        // Инициализация мока для запроса к репозиторию GitHub
        stubFor(get(urlEqualTo("/repos/owner/repo"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("{\"name\": \"example-repo\", \"description\": \"This is a test repository\", \"updated_at\": \"2022-02-25T10:15:30Z\"}")));

        // Создание экземпляра WebClient с базовым URL WireMock сервера
        WebClient.Builder webClientBuilder = WebClient.builder();
        WebClient webClient = webClientBuilder.baseUrl("http://localhost:" + wireMockRule.port()).build();

        // Создание экземпляра ApplicationConfig
        ApplicationConfig applicationConfig = new ApplicationConfig(new ApplicationConfig.Scheduler(true, Duration.ofMinutes(10), Duration.ofMinutes(5)), "githubBaseUrl", "stackOverflowBaseUrl");

        // Создание экземпляра GitHubWebClient с WebClient и тестируем метод
        GitHubWebClient gitHubWebClient = new GitHubWebClient(webClientBuilder, applicationConfig);
        GitHubRepositoryDTO repositoryInfo = gitHubWebClient.fetchRepositoryInfo("owner", "repo");


        // Проверка полученных данных
        assertNotNull(repositoryInfo);
        assertEquals("example-repo", repositoryInfo.getName());
        assertEquals("This is a test repository", repositoryInfo.getDescription());
        assertEquals(OffsetDateTime.parse("2022-02-25T10:15:30Z"), repositoryInfo.getUpdatedAt());
    }
}
