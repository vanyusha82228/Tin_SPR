package edu.java.scrapper.configuration;

import edu.java.bot.client.BotWebClient;
import edu.java.scrapper.domain.jdbcInterface.LinkService;
import edu.java.scrapper.domain.jdbcInterface.LinkUpdater;
import edu.java.scrapper.domain.jdbcInterface.TgChatService;
import edu.java.scrapper.domain.jpaRepository.JpaChatRepository;
import edu.java.scrapper.domain.jpaRepository.JpaLinkRepository;
import edu.java.scrapper.domain.jpaRepository.JpaUserLinkRepository;
import edu.java.scrapper.domain.jpaRepository.JpaUserRepository;
import edu.java.scrapper.domain.jpaService.JpaLinkService;
import edu.java.scrapper.domain.jpaService.JpaLinkUpdateService;
import edu.java.scrapper.domain.jpaService.JpaTgChatService;
import edu.java.scrapper.github.GitHubWebClient;
import edu.java.scrapper.stackocerflow.StackOverflowWebClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfiguration {
    @Bean
    public LinkService linkService(
        JpaUserLinkRepository userLinkRepository,
        JpaUserRepository userRepository
    ) {
        return new JpaLinkService(userLinkRepository, userRepository);
    }

    @Bean
    public LinkUpdater linkUpdater(
        GitHubWebClient gitHubWebClient,

        StackOverflowWebClient stackOverflowWebClient,
        JpaLinkRepository linkRepository,
        BotWebClient botWebClient
    ) {
        return new JpaLinkUpdateService(gitHubWebClient, stackOverflowWebClient, linkRepository, botWebClient);
    }

    @Bean
    public TgChatService tgChatService(
        JpaChatRepository chatRepository
    ) {
        return new JpaTgChatService(chatRepository);
    }
}
