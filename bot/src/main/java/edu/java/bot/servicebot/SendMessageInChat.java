package edu.java.bot.servicebot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class SendMessageInChat {
    private SendMessageInChat() {
    }

    public static SendMessage sendMessageInChat(Update update, String text) {
        return new SendMessage(update.message().chat().id(), text);
    }
}
