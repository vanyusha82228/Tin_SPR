package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.interfaceForProject.Command;
import org.springframework.stereotype.Component;

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
        long chatId = update.message().chat().id();
        if (waitingForLink) {
            String link = update.message().text();
            if (trackCommand.getTrackedLinks().contains(link)) {
                trackCommand.getTrackedLinks().remove(link);
                waitingForLink = false;
                return new SendMessage(chatId, "Ссылка " + link + " успешно удалена.");
            } else {
                waitingForLink = false;
                return new SendMessage(chatId, link + " не найдена в списке отслеживаемых ссылок.");
            }
        } else {
            waitingForLink = true;
            return new SendMessage(chatId, "Введите ссылку для удаления.");
        }
    }


    @Override
    public boolean supports(Update update) {
        return waitingForLink || (update.message() != null && update.message().text() != null
            && update.message().text().startsWith(command()));
    }
}
