package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.interfaceForProject.Command;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import static edu.java.bot.processor.UserMessageProcessorImpl.sendMessageInChat;

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

        if (waitingForLink) {
            String link = update.message().text();

            if (!trackedLinks.contains(link)) {
                trackedLinks.add(link);
                waitingForLink = false;
                String mesDeteils = "Ссылка " + link + " успешно отслеживается.";
                return sendMessageInChat(update, mesDeteils);
            } else {
                return sendMessageInChat(update, "Ссылка уже отслеживается.");
            }
        } else {
            waitingForLink = true;
            return sendMessageInChat(update, "Введите ссылку для отслеживания.");
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
