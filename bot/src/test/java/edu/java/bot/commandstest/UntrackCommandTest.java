package edu.java.bot.commandstest;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.client.ScrapperClient;
import edu.java.bot.command.UntrackCommand;
import edu.java.bot.dto.responseDto.LinkResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UntrackCommandTest {
    private UntrackCommand untrackCommand;
    private ScrapperClient scrapperClient;

    @BeforeEach
    public void setUp() {
        scrapperClient = mock(ScrapperClient.class);
        untrackCommand = new UntrackCommand(scrapperClient);
    }

    @Test
    public void testCommand() {
        assertEquals("/untrack", untrackCommand.command());
    }

    @Test
    public void testDescription() {
        assertEquals("Прекратить отслеживание ссылки", untrackCommand.description());
    }

    @Test
    public void testHandleRemovesLinkFromSameMessage() {
        String link = "https://example.com/link1";
        Update update = updateWithChat("/untrack " + link, 12345L);
        when(scrapperClient.removeLink(12345L, link)).thenReturn(Mono.just(new LinkResponse(1L, link)));

        untrackCommand.handle(update);

        verify(scrapperClient).removeLink(12345L, link);
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
