package edu.java.scrapper.utils;

import edu.java.configuration.ApplicationConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.Duration;

@Configuration
public class CommonConfiguration {
    @Bean
    public ApplicationConfig applicationConfig(){
        return new ApplicationConfig(new ApplicationConfig.Scheduler(true, Duration.ofMinutes(10), Duration.ofMinutes(5)), "githubBaseUrl", "stackOverflowBaseUrl");

    }
}
