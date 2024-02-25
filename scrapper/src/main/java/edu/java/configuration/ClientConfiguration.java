package edu.java.configuration;

import edu.java.github.GitHubHttpClient;
import edu.java.interfaceForClient.GitHubClientInterface;
import edu.java.interfaceForClient.StackOverflowClientInterface;
import edu.java.stackocerflow.StackOverflowHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Validated
@Configuration
public class ClientConfiguration {
    @Bean
    public GitHubClientInterface gitHubClientInterface(GitHubHttpClient gitHubHttpClient) {
        return gitHubHttpClient;
    }

    @Bean
    public StackOverflowClientInterface stackOverflowClientInterface(StackOverflowHttpClient stackOverflowHttpClient) {
        return stackOverflowHttpClient;
    }

}
