package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.interfaceForProject.Command;
import org.springframework.stereotype.Component;

@Component
public class ListCommand implements Command {
    private final TrackCommand trackCommand;

    public ListCommand(TrackCommand trackCommand) {
        this.trackCommand = trackCommand;
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
        StringBuilder builder = new StringBuilder();
        builder.append("Список отслеживаемых ссылок:\n");
        for (String link : trackCommand.getTrackedLinks()) {
            builder.append(link).append("\n");
        }
        return new SendMessage(update.message().chat().id(), builder.toString());
    }

    @Override
    public boolean supports(Update update) {
        return update.message() != null && update.message().text() != null && update.message().text().equals(command());
    }
}
