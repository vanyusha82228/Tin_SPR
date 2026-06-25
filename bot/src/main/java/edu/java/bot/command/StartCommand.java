package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperClient;
import edu.java.bot.interfaceForProject.Command;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import static edu.java.bot.servicebot.SendMessageInChat.sendMessageInChat;

@Component
public class StartCommand implements Command {
    private final ScrapperClient scrapperClient;

    public StartCommand(ScrapperClient scrapperClient) {
        this.scrapperClient = scrapperClient;
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
        Long chatId = update.message().chat().id();
        try {
            scrapperClient.registerChat(chatId).block();
            return sendMessageInChat(update, "Вы успешно зарегистрированы!");
        } catch (WebClientResponseException.BadRequest exception) {
            return sendMessageInChat(update, "Вы уже зарегистрированы!");
        } catch (RuntimeException exception) {
            return sendMessageInChat(update, "Не удалось зарегистрировать чат. Попробуйте позже.");
        }
    }

    @Override
    public boolean supports(Update update) {
        return update.message() != null && update.message().text() != null && update.message().text().equals(command());
    }
}
