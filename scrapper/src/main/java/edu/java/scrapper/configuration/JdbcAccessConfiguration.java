package edu.java.scrapper.configuration;

import edu.java.bot.client.BotWebClient;
import edu.java.scrapper.domain.jdbc.JdbcLinkService;
import edu.java.scrapper.domain.jdbc.JdbcLinkUpdateService;
import edu.java.scrapper.domain.jdbc.JdbcTgChatService;
import edu.java.scrapper.domain.jdbcInterface.LinkService;
import edu.java.scrapper.domain.jdbcInterface.LinkUpdater;
import edu.java.scrapper.domain.jdbcInterface.TgChatService;
import edu.java.scrapper.domain.repository.ChatRepository;
import edu.java.scrapper.domain.repository.LinkRepository;
import edu.java.scrapper.domain.repository.UserLinkRepository;
import edu.java.scrapper.domain.repository.UserRepository;
import edu.java.scrapper.github.GitHubWebClient;
import edu.java.scrapper.stackocerflow.StackOverflowWebClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfiguration {
    @Bean
    public LinkService linkService(
        LinkRepository linkRepository,
        UserLinkRepository userLinkRepository,
        UserRepository userRepository
    ) {
        return new JdbcLinkService(linkRepository, userLinkRepository, userRepository);
    }

    @Bean
    public LinkUpdater linkUpdater(
        GitHubWebClient gitHubWebClient,
        StackOverflowWebClient stackOverflowWebClient,
        LinkRepository linkRepository,
        BotWebClient botWebClient
    ) {
        return new JdbcLinkUpdateService(gitHubWebClient, stackOverflowWebClient, linkRepository, botWebClient);
    }

    @Bean
    public TgChatService tgChatService(
        ChatRepository chatRepository
    ) {
        return new JdbcTgChatService(chatRepository);
    }
}
