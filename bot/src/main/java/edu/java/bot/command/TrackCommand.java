package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.interfaceForProject.Command;
import edu.java.bot.interfaceForProject.UserRepository;
import org.springframework.stereotype.Component;
import static edu.java.bot.servicebot.SendMessageInChat.sendMessageInChat;

@Component
public class TrackCommand implements Command {
    private final UserRepository userRepository;
    private boolean waitingForLink = false;

    public TrackCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
            Long userId = update.message().from().id();
            String link = update.message().text();

            userRepository.addUserLink(userId, link);
            waitingForLink = false;
            return sendMessageInChat(update, "Ссылка " + link + " успешно отслеживается.");
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

}
