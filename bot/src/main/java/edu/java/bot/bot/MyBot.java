package edu.java.bot.bot;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.interfaceForProject.Bot;
import edu.java.bot.interfaceForProject.Command;
import edu.java.bot.interfaceForProject.UserMessageProcessor;
import edu.java.bot.servicebot.ServiceBot;
import jakarta.annotation.PostConstruct;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyBot implements Bot {
    private final UserMessageProcessor messageProcessor;
    private final ServiceBot serviceBot;


    @Autowired
    public MyBot(UserMessageProcessor messageProcessor, ServiceBot serviceBot) {
        this.messageProcessor = messageProcessor;
        this.serviceBot = serviceBot;
        BotCommand[] botCommands = messageProcessor.commands().stream()
            .map(Command::toApiCommand)
            .toArray(BotCommand[]::new);
        this.serviceBot.execute(new SetMyCommands(botCommands));
    }

    @Override
    public int process(List<Update> updates) {
        return serviceBot.process(updates);
    }

    @Override
    @PostConstruct
    public void start() {
        serviceBot.updatesListener();
    }

    @Override
    public void close() {
        // Останавливаем прослушивание обновлений
        serviceBot.close();
    }
}
