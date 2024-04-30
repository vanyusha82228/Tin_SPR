package edu.java.scrapper.configuration;


import edu.java.scrapper.github.GitHubWebClient;
import edu.java.scrapper.interfaceForClient.GitHubClientInterface;
import edu.java.scrapper.interfaceForClient.StackOverflowClientInterface;
import edu.java.scrapper.stackocerflow.StackOverflowWebClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.WebClient;

@Validated
@Configuration
public class ClientConfiguration {
    @Bean
    public GitHubClientInterface gitHubClient(WebClient.Builder webClientBuilder, ApplicationConfig applicationConfig) {
        return new GitHubWebClient(webClientBuilder, applicationConfig);
    }

    @Bean
    public StackOverflowClientInterface stackOverflowClient(
        WebClient.Builder webClientBuilder, ApplicationConfig applicationConfig
    ) {
        return new StackOverflowWebClient(webClientBuilder, applicationConfig);
    }

}
