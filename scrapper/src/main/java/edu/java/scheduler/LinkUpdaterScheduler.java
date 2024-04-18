package edu.java.scheduler;

import edu.java.domain.jdbcInterface.LinkService;
import edu.java.domain.model.Link;
import edu.java.interfaceForClient.GitHubClientInterface;
import edu.java.interfaceForClient.StackOverflowClientInterface;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@EnableScheduling
public class LinkUpdaterScheduler {

    private final GitHubClientInterface gitHubClient;
    private final StackOverflowClientInterface stackOverflowClient;
    private final LinkService linkService;
    private final BotWebClient botWebClient;

    @Autowired
    public LinkUpdaterScheduler(
        GitHubClientInterface gitHubClient,
        StackOverflowClientInterface stackOverflowClient,
        LinkService linkService,
        BotWebClient botWebClient
    ) {
        this.gitHubClient = gitHubClient;
        this.stackOverflowClient = stackOverflowClient;
        this.linkService = linkService;
        this.botWebClient = botWebClient;
    }

    @Scheduled(fixedDelayString = "#{@scheduler.interval.toMillis()}")
    public void update() {
        log.info("Обновление ссылок...");

        // Получаем список всех ссылок из базы данных

    }

    private void sendUpdateToBot(Link link) {
        // Отправляем уведомление о обновлении ссылки боту

    }

}
