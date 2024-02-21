package edu.java.bot.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import edu.java.bot.command.HelpCommand;
import edu.java.bot.command.ListCommand;
import edu.java.bot.command.StartCommand;
import edu.java.bot.command.TrackCommand;
import edu.java.bot.command.UntrackCommand;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.interfaceForProject.Bot;
import edu.java.bot.interfaceForProject.UserMessageProcessor;
import edu.java.bot.processor.UserMessageProcessorImpl;
import java.util.List;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
        this.bot.execute(new SetMyCommands(new StartCommand().toApiCommand(),
            new HelpCommand().toApiCommand(),
            new ListCommand().toApiCommand(),
            new TrackCommand().toApiCommand(),
            new UntrackCommand().toApiCommand()));

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
