package edu.java.scrapper.configuration;

import edu.java.bot.client.BotWebClient;
import edu.java.scrapper.client.ScrapperWebClient;
import java.net.URI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebClientConfig {

    @Bean
    public ScrapperWebClient scrapperWebClient(ApplicationConfig applicationConfig) {
        String selectedBaseUrl;
        if (!applicationConfig.githubBaseUrl().isBlank()) {
            selectedBaseUrl = applicationConfig.githubBaseUrl();
        } else {
            selectedBaseUrl = applicationConfig.stackOverflowBaseUrl();
        }
        return new ScrapperWebClient(URI.create(selectedBaseUrl));
    }

    @Bean
    public BotWebClient botWebClient(ApplicationConfig applicationConfig) {
        return new BotWebClient(URI.create(applicationConfig.botBaseUrl()));
    }
}
