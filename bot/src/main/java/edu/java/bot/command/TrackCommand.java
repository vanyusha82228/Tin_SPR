package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.interfaceForProject.Command;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class TrackCommand implements Command {
    private final List<String> trackedLinks = new ArrayList<>();
    private boolean waitingForLink = false;

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
        long chatId = update.message().chat().id();

        if (waitingForLink) {
            String link = update.message().text();
            if (!trackedLinks.contains(link)) {
                trackedLinks.add(link);
                waitingForLink = false;
                return new SendMessage(chatId, "Ссылка " + link + " успешно отслеживается.");
            } else {
                return new SendMessage(chatId, "Ссылка уже отслеживается.");
            }
        } else {
            waitingForLink = true;
            return new SendMessage(chatId, "Введите ссылку для отслеживания.");
        }

    }

    @Override
    public boolean supports(Update update) {
        return waitingForLink || (update.message() != null && update.message().text() != null
            && update.message().text().startsWith(command()));
    }

    public List<String> getTrackedLinks() {
        return trackedLinks;
    }
}
