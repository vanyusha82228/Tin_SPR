package edu.java.github;

import edu.java.configuration.ApplicationConfig;
import edu.java.dto.clintsDto.GitHubRepositoryDTO;
import edu.java.interfaceForClient.GitHubClientInterface;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Log4j2
@Component
public class GitHubWebClient implements GitHubClientInterface {
    private final WebClient webClient;
    private final String baseUrl;

    public GitHubWebClient(WebClient.Builder webClientBuilder, ApplicationConfig applicationConfig) {
        this.baseUrl = applicationConfig.githubBaseUrl();

        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    @Override
    public GitHubRepositoryDTO fetchRepositoryInfo(String owner, String repositoryName) {
        GitHubRepositoryDTO result = webClient.get()
            .uri("/repos/{owner}/{repo}", owner, repositoryName)
            .retrieve()
            .bodyToMono(GitHubRepositoryDTO.class)
            .block();
        if (result == null) {
            log.info("Не удалось получить репозиторий");
        }
        return result;
    }

}
