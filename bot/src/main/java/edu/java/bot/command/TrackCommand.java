package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperClient;
import edu.java.bot.interfaceForProject.Command;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import static edu.java.bot.servicebot.SendMessageInChat.sendMessageInChat;

@Component
public class TrackCommand implements Command {
    private final ScrapperClient scrapperClient;
    private final Set<Long> waitingForLinkChats = new HashSet<>();

    public TrackCommand(ScrapperClient scrapperClient) {
        this.scrapperClient = scrapperClient;
    }

    @Override
    public String command() {
        return "/track";
    }

    @Override
    public String description() {
        return "Начать отслеживание ссылки";
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        String text = update.message().text().trim();
        String link = extractLink(text);

        if (link == null && !waitingForLinkChats.contains(chatId)) {
            waitingForLinkChats.add(chatId);
            return sendMessageInChat(update, "Введите ссылку для отслеживания.");
        }

        if (link == null) {
            link = text;
        }

        waitingForLinkChats.remove(chatId);
        try {
            scrapperClient.addLink(chatId, link).block();
            return sendMessageInChat(update, "Ссылка " + link + " успешно отслеживается.");
        } catch (WebClientResponseException.NotFound exception) {
            return sendMessageInChat(update, "Сначала зарегистрируйтесь командой /start.");
        } catch (RuntimeException exception) {
            return sendMessageInChat(update, "Не удалось добавить ссылку. Проверьте ссылку и попробуйте позже.");
        }
    }

    @Override
    public boolean supports(Update update) {
        if (update.message() == null || update.message().text() == null || update.message().chat() == null) {
            return false;
        }
        Long chatId = update.message().chat().id();
        return waitingForLinkChats.contains(chatId) || update.message().text().startsWith(command());
    }

    private String extractLink(String text) {
        String[] parts = text.split("\\s+", 2);
        if (parts.length == 2 && command().equals(parts[0])) {
            return parts[1].trim();
        }
        return null;
    }
}
