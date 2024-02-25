package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.interfaceForProject.Command;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import static edu.java.bot.servicebot.SendMessageInChat.sendMessageInChat;

@Component
public class StartCommand implements Command {
    private final Map<Long, String> users = new HashMap<>();

    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "Зарегистрировать пользователя";
    }

    @Override
    public SendMessage handle(Update update) {
        Long userId = update.message().from().id();
        String userName = update.message().from().username();
        if (users.containsKey(userId)) {
            return sendMessageInChat(update, "Вы зарегистрированы!");
        } else {
            users.put(userId, userName);
            return sendMessageInChat(update, "Вы успешно зарегистрированы!");
        }
    }

    @Override
    public boolean supports(Update update) {
        return update.message() != null && update.message().text() != null && update.message().text().equals(command());
    }

    public Map<Long, String> getUsers() {
        return users;
    }
}
