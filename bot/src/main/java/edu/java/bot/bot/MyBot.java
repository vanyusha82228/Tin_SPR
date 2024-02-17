package edu.java.bot.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.interfaceForProject.Bot;
import edu.java.bot.processor.DefaultUserMessageProcessor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
public class MyBot implements Bot {
    private final DefaultUserMessageProcessor messageProcessor;
    private final TelegramBot telegramBot;

    @Autowired
    public MyBot(ApplicationConfig applicationConfig, DefaultUserMessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
        this.telegramBot = new TelegramBot(applicationConfig.telegramToken());
    }

    @Override
    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        telegramBot.execute(request);
    }

    @Override
    public int process(List<Update> updates) {
        int confirmedUpdates = 0;
        for (Update update : updates) {
            try {
                SendMessage response = messageProcessor.process(update);
                if (response != null) {
                    execute(response);
                }
                confirmedUpdates++;
            } catch (Exception e) {
                log.error("Error processing update: {}", e.getMessage());
            }
        }
        return confirmedUpdates;
    }

    @Override
    public void start() {
        log.info("Starting bot...");
        telegramBot.setUpdatesListener(updates -> {
            log.debug("Received {} updates", updates.size());
            process(updates);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
        log.info("Bot started successfully!");
    }

    @Override
    public void close() {
        log.info("Stopping bot...");
        telegramBot.removeGetUpdatesListener();
        log.info("Bot stopped successfully!");
    }
}
