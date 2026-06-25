package edu.java.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import java.net.URI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    private final ApplicationConfig applicationConfig;

    public BeanConfiguration(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    @Bean
    public TelegramBot telegramBot(ApplicationConfig applicationConfig) {
        return new TelegramBot(applicationConfig.telegramToken());
    }

    @Bean
    public URI scrapperBaseUri(ApplicationConfig applicationConfig) {
        return URI.create(applicationConfig.scrapperBaseUrl());
    }
}
