package edu.java.configuration;

import edu.java.github.GitHubWebClient;
import edu.java.interfaceForClient.GitHubClientInterface;
import edu.java.interfaceForClient.StackOverflowClientInterface;
import edu.java.stackocerflow.StackOverflowWebClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.WebClient;

<<<<<<< HEAD
@Validated @Configuration public class ClientConfiguration {
=======
@Validated
@Configuration
public class ClientConfiguration {
>>>>>>> main
    @Bean
    public GitHubClientInterface gitHubClient(WebClient.Builder webClientBuilder, ApplicationConfig applicationConfig) {
        return new GitHubWebClient(webClientBuilder, applicationConfig);
    }

<<<<<<< HEAD
    @Bean public StackOverflowClientInterface stackOverflowClient(
=======
    @Bean
    public StackOverflowClientInterface stackOverflowClient(
>>>>>>> main
        WebClient.Builder webClientBuilder, ApplicationConfig applicationConfig
    ) {
        return new StackOverflowWebClient(webClientBuilder, applicationConfig);
    }

<<<<<<< HEAD
    @Bean public GitHubWebClient gitHubWebClient(WebClient.Builder wedClient, ApplicationConfig applicationConfig) {
        return new GitHubWebClient(wedClient, applicationConfig);
    }

=======
>>>>>>> main
}
