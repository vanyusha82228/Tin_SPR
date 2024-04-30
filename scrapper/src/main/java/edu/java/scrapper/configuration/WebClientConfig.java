package edu.java.scrapper.configuration;

import edu.java.bot.client.BotWebClient;
import edu.java.scrapper.client.ScrapperWebClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.net.URI;

@Configuration
public class WebClientConfig {

    @Bean
    public URI baseUri(ApplicationConfig applicationConfig) {
        String selectedBaseUrl;
        if (!applicationConfig.githubBaseUrl().isBlank()) {
            selectedBaseUrl = applicationConfig.githubBaseUrl();
        } else {
            selectedBaseUrl = applicationConfig.stackOverflowBaseUrl();
        }
        return URI.create(selectedBaseUrl);
    }

    @Bean
    public ScrapperWebClient scrapperWebClient(URI baseUri) {
        return new ScrapperWebClient(baseUri);
    }
    @Bean
    public BotWebClient botWebClient(URI baseUri) {
        return new BotWebClient(baseUri);
    }
}
