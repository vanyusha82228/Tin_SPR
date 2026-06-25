package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperClient;
import edu.java.bot.dto.responseDto.LinkResponse;
import edu.java.bot.dto.responseDto.ListLinksResponse;
import edu.java.bot.interfaceForProject.Command;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import static edu.java.bot.servicebot.SendMessageInChat.sendMessageInChat;

@Component
public class ListCommand implements Command {
    private final ScrapperClient scrapperClient;

    public ListCommand(ScrapperClient scrapperClient) {
        this.scrapperClient = scrapperClient;
    }

    @Override
    public String command() {
        return "/list";
    }

    @Override
    public String description() {
        return "Вывести список отслеживаемых ссылок";
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        try {
            ListLinksResponse response = scrapperClient.listLinks(chatId).block();
            List<LinkResponse> links = response == null ? List.of() : response.getLinks();
            if (links == null || links.isEmpty()) {
                return sendMessageInChat(update, "Список отслеживаемых ссылок пуст.");
            }

            StringBuilder builder = new StringBuilder("Список отслеживаемых ссылок:\n");
            for (LinkResponse link : links) {
                builder.append(link.getUri()).append("\n");
            }
            return sendMessageInChat(update, builder.toString());
        } catch (WebClientResponseException.NotFound exception) {
            return sendMessageInChat(update, "Сначала зарегистрируйтесь командой /start.");
        } catch (RuntimeException exception) {
            return sendMessageInChat(update, "Не удалось получить список ссылок. Попробуйте позже.");
        }
    }

    @Override
    public boolean supports(Update update) {
        return update.message() != null && update.message().text() != null && update.message().text().equals(command());
    }
}
