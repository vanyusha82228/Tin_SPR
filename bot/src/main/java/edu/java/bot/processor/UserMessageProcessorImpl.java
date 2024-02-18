package edu.java.bot.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.interfaceForProject.Command;
import edu.java.bot.interfaceForProject.UserMessageProcessor;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class UserMessageProcessorImpl implements UserMessageProcessor {
    private final List<Command> commandList;

    public UserMessageProcessorImpl(List<Command> commandList) {
        this.commandList = commandList;

    }

    @Override
    public List<? extends Command> commands() {
        return commandList;
    }

    @Override
    public SendMessage process(Update update) {
        // Проверяем, есть ли текстовое сообщение в обновлении
        if (update.message() == null || update.message().text() == null) {
            return new SendMessage(update.message().chat().id(), "Unsupported message format");
        }

        // Ищем подходящую команду для обработки сообщения пользователя
        for (Command command : commandList) {
            if (command.supports(update)) {
                return command.handle(update);
            }
        }

        // Если ни одна из команд не подходит, возвращаем сообщение о том, что команда неизвестна
        return new SendMessage(update.message().chat().id(), "Unknown command");
    }
}
