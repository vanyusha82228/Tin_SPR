package edu.java.scrapper.configuration;


import edu.java.scrapper.emums.AccessType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotNull
    @Bean
    Scheduler scheduler,
    @NotBlank
    String githubBaseUrl,
    @NotBlank
    String stackOverflowBaseUrl,
    @NotNull
    AccessType databaseAccessType

) {
    @Override public String githubBaseUrl() {
        return githubBaseUrl;
    }

    @Override public String stackOverflowBaseUrl() {
        return stackOverflowBaseUrl;
    }

    public record Scheduler(boolean enable, @NotNull Duration interval, @NotNull Duration forceCheckDelay) {
    }
}
