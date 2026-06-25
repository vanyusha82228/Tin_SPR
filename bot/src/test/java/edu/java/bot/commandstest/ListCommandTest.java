package edu.java.bot.commandstest;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.client.ScrapperClient;
import edu.java.bot.command.ListCommand;
import edu.java.bot.dto.responseDto.LinkResponse;
import edu.java.bot.dto.responseDto.ListLinksResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ListCommandTest {
    private ListCommand listCommand;
    private ScrapperClient scrapperClient;

    @BeforeEach
    public void setUp() {
        scrapperClient = mock(ScrapperClient.class);
        listCommand = new ListCommand(scrapperClient);
    }

    @Test
    public void testCommand() {
        assertEquals("/list", listCommand.command());
    }

    @Test
    public void testDescription() {
        assertEquals("Вывести список отслеживаемых ссылок", listCommand.description());
    }

    @Test
    public void testHandle() {
        Update update = updateWithChat("/list", 12345L);
        List<LinkResponse> links = List.of(
            new LinkResponse(1L, "https://example.com/link1"),
            new LinkResponse(2L, "https://example.com/link2")
        );
        when(scrapperClient.listLinks(12345L)).thenReturn(Mono.just(new ListLinksResponse(links, links.size())));

        listCommand.handle(update);

        verify(scrapperClient).listLinks(12345L);
    }

    @Test
    public void testSupports() {
        assertEquals(true, listCommand.supports(updateWithChat("/list", 12345L)));
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
