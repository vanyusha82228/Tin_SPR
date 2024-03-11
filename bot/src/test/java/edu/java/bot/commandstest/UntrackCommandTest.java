package edu.java.bot.commandstest;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.command.UntrackCommand;
import edu.java.bot.interfaceForProject.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UntrackCommandTest {
    private UntrackCommand untrackCommand;
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        untrackCommand = new UntrackCommand(userRepository);
    }

    @Test
    public void testCommand(){
        assertEquals("/untrack", untrackCommand.command());
    }

    @Test
    public void testDescription() {
        assertEquals("Прекратить отслеживание ссылки", untrackCommand.description());
    }


    @Test
    public void testHandle_WhenLinkNotFound() {
        // Mock объекты
        List<String> trackedLinks = new ArrayList<>();
        trackedLinks.add("https://example.com/link1");
        when(userRepository.listLinkByUserId(12345L)).thenReturn(trackedLinks);

        // Mock объекты
        Update mockUpdate = mock(Update.class);
        Message mockMessage = mock(Message.class);
        Chat mockChat = mock(Chat.class);

        // Настройка поведения макетов
        when(mockUpdate.message()).thenReturn(mockMessage);
        when(mockMessage.text()).thenReturn("https://example.com/link2"); // ссылка, которая не находится в репозитории
        when(mockMessage.chat()).thenReturn(mockChat); // мокируем chat
        when(mockChat.id()).thenReturn(12345L); // возвращаем идентификатор чата

        // Обработка команды /untrack
        untrackCommand.handle(mockUpdate);

        // Проверка, что ссылка не была удалена, так как она не была найдена
        assertEquals(1, trackedLinks.size());
    }

}
