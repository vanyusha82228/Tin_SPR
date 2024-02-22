package edu.java.bot.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.interfaceForProject.Bot;
import edu.java.bot.interfaceForProject.Command;
import edu.java.bot.interfaceForProject.UserMessageProcessor;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class MyBot implements Bot {
    private final TelegramBot bot;
    private final UserMessageProcessor messageProcessor;
    private final ApplicationConfig telegramToken;


    @Autowired
    public MyBot(ApplicationConfig telegramToken, UserMessageProcessor messageProcessor) {
        this.telegramToken = telegramToken;
        this.bot = new TelegramBot(telegramToken.telegramToken());
        this.messageProcessor = messageProcessor;
        BotCommand[] botCommands = messageProcessor.commands().stream()
            .map(Command::toApiCommand)
            .toArray(BotCommand[]::new);
        this.bot.execute(new SetMyCommands(botCommands));


    }

    @Override
    public int process(List<Update> updates) {
        for (Update update : updates) {
            try {
                // Обрабатываем каждое обновление
                SendMessage response = messageProcessor.process(update);

                // Отправляем ответ, если он не равен null
                if (response != null) {
                    bot.execute(response);
                }
            } catch (Exception e) {
                log.error("Error processing update: {}", update, e);
            }
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Override
    @PostConstruct
    public void start() {
        // Запускаем бота для прослушивания обновлений
        bot.setUpdatesListener(updates -> {
            return process(updates);
        });

        log.info("Бот успешно запущен и готов принимать обновления.");
    }

    @Override
    public void close() {
        // Останавливаем прослушивание обновлений
        bot.removeGetUpdatesListener();
    }
}
