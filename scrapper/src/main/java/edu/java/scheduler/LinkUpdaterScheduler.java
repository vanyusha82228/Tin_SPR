package edu.java.scheduler;

import edu.java.github.GitHubWebClient;
import edu.java.stackocerflow.StackOverflowWebClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@EnableScheduling
public class LinkUpdaterScheduler {
    private final GitHubWebClient gitHubWebClient;
    private final StackOverflowWebClient stackOverflowWebClient;
    private final static int ID = 123;

    public LinkUpdaterScheduler(GitHubWebClient gitHubWebClient, StackOverflowWebClient stackOverflowWebClient) {
        this.gitHubWebClient = gitHubWebClient;
        this.stackOverflowWebClient = stackOverflowWebClient;
    }

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() {

        log.info("Обновление ссылок...");
    }

}
