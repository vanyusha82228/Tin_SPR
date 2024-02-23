package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.interfaceForProject.Command;
import java.util.List;
import org.springframework.stereotype.Component;
import static edu.java.bot.processor.UserMessageProcessorImpl.sendMessageInChat;

@Component
public class HelpCommand implements Command {
    private final List<Command> commandList;

    public HelpCommand(List<Command> commandList) {
        this.commandList = commandList;
    }

    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String description() {
        return "Вывести окно с командами";
    }

    @Override
    public SendMessage handle(Update update) {
        StringBuilder builder = new StringBuilder();
        builder.append("Список доступных команд:\n");

        for (Command command : commandList) {
            builder.append(command.command()).append(" - ").append(command.description()).append("\n");
        }

        return sendMessageInChat(update, builder.toString());
    }

    @Override
    public boolean supports(Update update) {
        return update.message() != null && update.message().text() != null && update.message().text().equals(command());
    }

}
