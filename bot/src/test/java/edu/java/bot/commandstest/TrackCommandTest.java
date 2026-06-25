package edu.java.bot.commandstest;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.client.ScrapperClient;
import edu.java.bot.command.TrackCommand;
import edu.java.bot.dto.responseDto.LinkResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TrackCommandTest {
    private TrackCommand trackCommand;
    private ScrapperClient scrapperClient;

    @BeforeEach
    public void setUp() {
        scrapperClient = mock(ScrapperClient.class);
        trackCommand = new TrackCommand(scrapperClient);
    }

    @Test
    public void testCommand() {
        assertEquals("/track", trackCommand.command());
    }

    @Test
    public void testDescription() {
        assertEquals("Начать отслеживание ссылки", trackCommand.description());
    }

    @Test
    public void testHandleTracksLinkFromSameMessage() {
        String link = "https://example.com/link1";
        Update update = updateWithChat("/track " + link, 12345L);
        when(scrapperClient.addLink(12345L, link)).thenReturn(Mono.just(new LinkResponse(1L, link)));

        trackCommand.handle(update);

        verify(scrapperClient).addLink(12345L, link);
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
