package edu.java.bot;

import edu.java.bot.bot.MyBot;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.processor.UserMessageProcessorImpl;
import java.util.ArrayList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
public class BotApplication {
    public static void main(String[] args) {

        SpringApplication.run(BotApplication.class, args);
        MyBot myBot = new MyBot("6980678180:AAHiy9VAoc9kGyTy8DuziHh2_rFaSQnC6w4",
            new UserMessageProcessorImpl(new ArrayList<>()));
        myBot.start();
    }
}
