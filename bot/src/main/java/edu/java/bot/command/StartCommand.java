package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.interfaceForProject.Command;
import edu.java.bot.user.UserRepository;
import edu.java.bot.user.UserRepositoryImpl;
import org.springframework.stereotype.Component;
import static edu.java.bot.servicebot.SendMessageInChat.sendMessageInChat;

@Component
public class StartCommand implements Command {
    private final UserRepositoryImpl userRepository;

    public StartCommand(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }

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
        if (userRepository.listLinkByUserId(userId).isEmpty()) {
            userRepository.addUserLink(userId, "");
            return sendMessageInChat(update, "Вы успешно зарегистрированы!");
        } else {
            return sendMessageInChat(update, "Вы уже зарегистрированы!");
        }
    }

    @Override
    public boolean supports(Update update) {
        return update.message() != null && update.message().text() != null && update.message().text().equals(command());
    }

}
