package edu.java.bot.command;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.interfaceForProject.Command;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements Command {

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
        String messageText = "Список доступных команд:\n" +
            "/start - зарегистрировать пользователя\n" +
            "/help - вывести окно с командами\n" +
            "/track - начать отслеживание ссылки\n" +
            "/untrack - прекратить отслеживание ссылки\n" +
            "/list - показать список отслеживаемых ссылок";
        return new SendMessage(update.message().chat().id(), messageText);
    }


}
