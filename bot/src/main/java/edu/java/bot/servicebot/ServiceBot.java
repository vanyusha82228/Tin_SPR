package edu.java.bot.servicebot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import edu.java.bot.interfaceForProject.UserMessageProcessor;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class ServiceBot {
    private final TelegramBot bot;
    private final UserMessageProcessor messageProcessor;

    public ServiceBot(TelegramBot bot, UserMessageProcessor messageProcessor, UserMessageProcessor messageProcessor1) {
        this.bot = bot;
        this.messageProcessor = messageProcessor1;
    }

    public void updatesListener() {
        // Запускаем бота для прослушивания обновлений
        bot.setUpdatesListener(updates -> {
            return process(updates);
        });

        log.info("Бот успешно запущен и готов принимать обновления.");
    }

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

    public void close() {
        bot.removeGetUpdatesListener();
    }

    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        bot.execute(request);
    }
}
