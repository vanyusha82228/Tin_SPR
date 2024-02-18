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
import edu.java.bot.interfaceForProject.Bot;
import edu.java.bot.processor.UserMessageProcessorImpl;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class MyBot implements Bot {
    private final TelegramBot bot;
    private final UserMessageProcessorImpl messageProcessor;


    @Autowired
    public MyBot(@Value("${app.telegram-token}") String telegramToken, UserMessageProcessorImpl messageProcessor) {
        this.bot = new TelegramBot(telegramToken);
        this.messageProcessor = messageProcessor;
        this.bot.execute(new SetMyCommands(new StartCommand().toApiCommand(),
            new HelpCommand().toApiCommand(),
            new ListCommand().toApiCommand(),
            new TrackCommand().toApiCommand(),
            new UntrackCommand().toApiCommand()));

    }

    @Override
    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        bot.execute(request);
    }

    @Override
    public int process(List<Update> updates) {
        for (Update update : updates) {
            try {
                // Обрабатываем каждое обновление
                SendMessage response = messageProcessor.process(update);

                // Отправляем ответ, если он не равен null
                if (response != null) {
                    execute(response);
                }
            } catch (Exception e) {
                log.error("Error processing update: {}", update, e);
            }
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Override
    public void start() {
        // Запускаем бота для прослушивания обновлений
        bot.setUpdatesListener(updates -> {
            process(updates);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });

        log.info("Бот успешно запущен и готов принимать обновления.");
    }

    @Override
    public void close() {
        // Останавливаем прослушивание обновлений
        bot.removeGetUpdatesListener();
    }
}
