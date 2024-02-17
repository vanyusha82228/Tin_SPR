package edu.java.bot.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.interfaceForProject.Command;
import edu.java.bot.interfaceForProject.UserMessageProcessor;
import org.springframework.stereotype.Component;
import java.util.List;
@Component
public class DefaultUserMessageProcessor implements UserMessageProcessor {
    private final List<? extends Command> commands;

    public DefaultUserMessageProcessor(List<? extends Command> commands) {
        this.commands = commands;
    }

    @Override
    public List<? extends Command> commands() {
        return commands;
    }

    @Override
    public SendMessage process(Update update) {
        if (update.message() == null || update.message().text() == null) {
            return new SendMessage(update.message().chat().id(), "Получено пустое сообщение.");
        }

        String messageText = update.message().text();
        for (Command command : commands) {
            if (messageText.startsWith(command.command())) {
                return command.handle(update);
            }
        }
        return new SendMessage(update.message().chat().id(), "Неизвестная команда.");
    }
}
