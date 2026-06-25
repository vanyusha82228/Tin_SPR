package edu.java.scrapper.domain.jdbc;

import edu.java.bot.client.BotWebClient;
import edu.java.bot.dto.responseDto.LinkUpdate;
import edu.java.scrapper.domain.jdbcInterface.LinkUpdater;
import edu.java.scrapper.domain.model.Link;
import edu.java.scrapper.domain.repository.LinkRepository;
import edu.java.scrapper.domain.repository.UserLinkRepository;
import edu.java.scrapper.dto.clintsDto.GitHubRepositoryDTO;
import edu.java.scrapper.dto.clintsDto.StackOverflowQuestionDTO;
import edu.java.scrapper.dto.clintsDto.StackOverflowResponseDTO;
import edu.java.scrapper.github.GitHubWebClient;
import edu.java.scrapper.stackocerflow.StackOverflowWebClient;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class JdbcLinkUpdateService implements LinkUpdater {
    private static final int INTERVAL = 10;
    private static final int GITHUB_PARTS_COUNT = 5;
    private static final int GITHUB_OWNER_INDEX = 3;
    private static final int GITHUB_REPO_INDEX = 4;
    private static final int STACKOVERFLOW_PARTS_COUNT = 5;
    private static final int STACKOVERFLOW_QUESTION_INDEX = 3;
    private static final int STACKOVERFLOW_QUESTION_ID_INDEX = 4;

    private final GitHubWebClient gitHubWebClient;
    private final StackOverflowWebClient stackOverflowWebClient;
    private final LinkRepository linkRepository;
    private final UserLinkRepository userLinkRepository;
    private final BotWebClient botWebClient;

    public JdbcLinkUpdateService(
        GitHubWebClient gitHubWebClient,
        StackOverflowWebClient stackOverflowWebClient,
        LinkRepository linkRepository,
        UserLinkRepository userLinkRepository,
        BotWebClient botWebClient
    ) {
        this.gitHubWebClient = gitHubWebClient;
        this.stackOverflowWebClient = stackOverflowWebClient;
        this.linkRepository = linkRepository;
        this.userLinkRepository = userLinkRepository;
        this.botWebClient = botWebClient;
    }

    @Override
    public void update() {
        log.info("Checking tracked links...");

        for (Link link : linkRepository.findAll()) {
            if (isLinkOutdated(link)) {
                checkAndUpdateLink(link);
            }
        }
    }

    private boolean isLinkOutdated(Link link) {
        OffsetDateTime lastUpdate = link.getUpdatedAt();
        Duration durationSinceLastUpdate = Duration.between(lastUpdate, OffsetDateTime.now());
        return durationSinceLastUpdate.compareTo(Duration.ofSeconds(INTERVAL)) > 0;
    }

    private void checkAndUpdateLink(Link link) {
        String[] gitHubInfo = parseGitHubLink(link.getUri());
        int stackOverflowQuestionId = parseStackOverflowQuestionId(link.getUri());

        if (gitHubInfo != null) {
            GitHubRepositoryDTO repositoryInfo = gitHubWebClient.fetchRepositoryInfo(gitHubInfo[0], gitHubInfo[1]);
            if (repositoryInfo != null && repositoryInfo.getUpdatedAt().isAfter(link.getUpdatedAt())) {
                sendUpdateToBot(link, "GitHub repository was updated: " + link.getUri());
                linkRepository.updateUpdatedAt(link.getId(), repositoryInfo.getUpdatedAt());
            }
        } else if (stackOverflowQuestionId != -1) {
            StackOverflowResponseDTO response = stackOverflowWebClient.fetchQuestionInfo(stackOverflowQuestionId);
            if (response != null && response.getItems() != null && !response.getItems().isEmpty()) {
                for (StackOverflowQuestionDTO questionInfo : response.getItems()) {
                    if (questionInfo.getCreationDate().isAfter(link.getUpdatedAt())) {
                        sendUpdateToBot(link, "StackOverflow question was updated: " + link.getUri());
                        linkRepository.updateUpdatedAt(link.getId(), questionInfo.getCreationDate());
                        break;
                    }
                }
            }
        }
    }

    private String[] parseGitHubLink(String uri) {
        String[] parts = uri.split("/");
        if (parts.length == GITHUB_PARTS_COUNT && "github.com".equals(parts[2])) {
            return new String[] {parts[GITHUB_OWNER_INDEX], parts[GITHUB_REPO_INDEX]};
        }
        return null;
    }

    private int parseStackOverflowQuestionId(String uri) {
        String[] parts = uri.split("/");
        if (parts.length >= STACKOVERFLOW_PARTS_COUNT
            && "stackoverflow.com".equals(parts[2])
            && "questions".equals(parts[STACKOVERFLOW_QUESTION_INDEX])) {
            try {
                return Integer.parseInt(parts[STACKOVERFLOW_QUESTION_ID_INDEX]);
            } catch (NumberFormatException e) {
                return -1;
            }
        }
        return -1;
    }

    private void sendUpdateToBot(Link link, String message) {
        LinkUpdate linkUpdate = new LinkUpdate();
        linkUpdate.setId(link.getId());
        linkUpdate.setUrl(link.getUri());
        linkUpdate.setDescription(message);
        linkUpdate.setTgChatIds(userLinkRepository.findChatIdsByLink(link));

        botWebClient.sendUpdate(linkUpdate)
            .doOnError(error -> log.error("Failed to send update to bot", error))
            .subscribe();
    }
}
