package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.interfaceForProject.Command;
import org.springframework.stereotype.Component;
import static edu.java.bot.processor.UserMessageProcessorImpl.sendMessageInChat;

@Component
public class UntrackCommand implements Command {
    private final TrackCommand trackCommand;
    private boolean waitingForLink = false;

    public UntrackCommand(TrackCommand trackCommand) {
        this.trackCommand = trackCommand;
    }

    @Override
    public String command() {
        return "/untrack";
    }

    @Override
    public String description() {
        return "Прекратить отслеживание ссылки";
    }

    @Override
    public SendMessage handle(Update update) {
        if (waitingForLink) {
            String link = update.message().text();
            if (trackCommand.getTrackedLinks().contains(link)) {
                trackCommand.getTrackedLinks().remove(link);
                String mesDeteils = "Ссылка " + link + " успешно удалена.";
                waitingForLink = false;
                return sendMessageInChat(update, mesDeteils);
            } else {
                waitingForLink = false;
                String mesDeteils = link + " не найдена в списке отслеживаемых ссылок.";
                return sendMessageInChat(update, mesDeteils);
            }
        } else {
            waitingForLink = true;
            return sendMessageInChat(update, "Введите ссылку для удаления.");
        }
    }


    @Override
    public boolean supports(Update update) {
        return waitingForLink || (update.message() != null && update.message().text() != null
            && update.message().text().startsWith(command()));
    }
}
