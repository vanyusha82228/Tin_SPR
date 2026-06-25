package edu.java.bot.commandstest;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.client.ScrapperClient;
import edu.java.bot.command.StartCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StartCommandsTest {
    private StartCommand startCommand;
    private ScrapperClient scrapperClient;

    @BeforeEach
    public void setUp() {
        scrapperClient = mock(ScrapperClient.class);
        startCommand = new StartCommand(scrapperClient);
    }

    @Test
    public void testCommand() {
        assertEquals("/start", startCommand.command());
    }

    @Test
    public void testDescription() {
        assertEquals("Зарегистрировать пользователя", startCommand.description());
    }

    @Test
    public void testHandleRegistersChat() {
        Update update = updateWithChat("/start", 12345L);
        when(scrapperClient.registerChat(12345L)).thenReturn(Mono.empty());

        startCommand.handle(update);

        verify(scrapperClient).registerChat(12345L);
    }

    @Test
    public void testSupports() {
        assertEquals(true, startCommand.supports(updateWithChat("/start", 12345L)));
    }

    private Update updateWithChat(String text, long chatId) {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);

        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn(text);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(chatId);
        return update;
    }
}
