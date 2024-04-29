package edu.java.scrapper.domain.jdbc;

import edu.java.bot.client.BotWebClient;
import edu.java.bot.dto.responseDto.LinkUpdate;
import edu.java.scrapper.domain.jdbcInterface.LinkUpdater;
import edu.java.scrapper.domain.model.Link;
import edu.java.scrapper.domain.repository.LinkRepository;
import edu.java.scrapper.dto.clintsDto.GitHubRepositoryDTO;
import edu.java.scrapper.dto.clintsDto.StackOverflowQuestionDTO;
import edu.java.scrapper.dto.clintsDto.StackOverflowResponseDTO;
import edu.java.scrapper.github.GitHubWebClient;
import edu.java.scrapper.stackocerflow.StackOverflowWebClient;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class JdbcLinkUpdateService implements LinkUpdater {
    private final static int INTERVAL = 10;
    private static final int GITHUB_PARTS_COUNT = 5;
    private final static int GITHUB_OWNER_INDEX = 3;
    private final static int GITHUB_REPO_INDEX = 4;
    private static final int STACKOVERFLOW_PARTS_COUNT = 5;
    private final static int STACKOVERFLOW_QUESTION_INDEX = 3;
    private final static int STACKOVERFLOW_QUESTION_ID_INDEX = 4;
    private final GitHubWebClient gitHubWebClient;
    private final StackOverflowWebClient stackOverflowWebClient;
    private final LinkRepository linkRepository;
    private final BotWebClient botWebClient;

    @Autowired
    public JdbcLinkUpdateService(
        GitHubWebClient gitHubWebClient,
        StackOverflowWebClient stackOverflowWebClient,
        LinkRepository linkRepository,
        BotWebClient botWebClient
    ) {
        this.gitHubWebClient = gitHubWebClient;
        this.stackOverflowWebClient = stackOverflowWebClient;
        this.linkRepository = linkRepository;
        this.botWebClient = botWebClient;
    }

    @Override
    public void update() {
        log.info("Обновление ссылок...");

        List<Link> links = linkRepository.findAll();

        for (Link link : links) {
            // Проверяем, давно ли ссылка не обновлялась
            if (isLinkOutdated(link)) {
                // Проверяем обновления для устаревшей ссылки
                checkAndUpdateLink(link);
            }
        }
    }

    private boolean isLinkOutdated(Link link) {
        OffsetDateTime lastUpdate = link.getUpdatedAt();
        Duration durationSinceLastUpdate = Duration.between(lastUpdate, OffsetDateTime.now());
        Duration interval = Duration.ofSeconds(INTERVAL); // Интервал, после которого ссылка считается устаревшей
        return durationSinceLastUpdate.compareTo(interval) > 0;
    }

    private void checkAndUpdateLink(Link link) {
        // Получаем информацию о ресурсе из ссылки
        String[] gitHubInfo = parseGitHubLink(link.getUri());
        int stackOverflowQuestionId = parseStackOverflowQuestionId(link.getUri());

        // Проверяем обновления для GitHub
        if (gitHubInfo != null) {
            GitHubRepositoryDTO repositoryInfo = gitHubWebClient.fetchRepositoryInfo(gitHubInfo[0], gitHubInfo[1]);
            if (repositoryInfo != null && repositoryInfo.getUpdatedAt().isAfter(link.getUpdatedAt())) {
                // Если обновления есть, создаем объект LinkUpdate и отправляем его в botWebClient
                String message = "Обновление в репозитории на GitHub: " + link.getUri();
                sendUpdateToBot(message);
            }
        } else if (stackOverflowQuestionId != -1) {
            StackOverflowResponseDTO response = stackOverflowWebClient.fetchQuestionInfo(stackOverflowQuestionId);
            if (response != null && response.getItems() != null && !response.getItems().isEmpty()) {
                List<StackOverflowQuestionDTO> questions = response.getItems();
                for (StackOverflowQuestionDTO questionInfo : questions) {
                    if (questionInfo.getCreationDate().isAfter(link.getUpdatedAt())) {
                        // Если обновления есть, создаем объект LinkUpdate и отправляем его в botWebClient
                        String message = "Новый ответ на StackOverflow: " + link.getUri();
                        sendUpdateToBot(message);
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
        } else {
            return null;
        }
    }

    private int parseStackOverflowQuestionId(String uri) {
        String[] parts = uri.split("/");
        if (parts.length >= STACKOVERFLOW_PARTS_COUNT && "stackoverflow.com".equals(parts[2])
            && "questions".equals(parts[STACKOVERFLOW_QUESTION_INDEX])) {
            try {
                return Integer.parseInt(parts[STACKOVERFLOW_QUESTION_ID_INDEX]);
            } catch (NumberFormatException e) {
                return -1;
            }
        } else {
            return -1;
        }
    }

    private void sendUpdateToBot(String message) {
        LinkUpdate linkUpdate = new LinkUpdate();
        linkUpdate.setDescription(message);
        botWebClient.sendUpdate(linkUpdate).subscribe(); // Отправляем уведомление в bot
    }

}
