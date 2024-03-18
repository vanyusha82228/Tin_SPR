package edu.java.bot.commandstest;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import edu.java.bot.command.TrackCommand;

import edu.java.bot.interfaceForProject.UserRepository;
import edu.java.bot.user.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TrackCommandTest {
    private TrackCommand trackCommand;
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepositoryImpl.class);
        trackCommand = new TrackCommand(userRepository);
    }

    @Test
    public void testCommand(){
        assertEquals("/track", trackCommand.command());
    }

    @Test
    public void testDescription() {
        assertEquals("Начать отслеживание ссылки", trackCommand.description());
    }

    @Test
    public void testHandle_WhenLinkAlreadyTracked() {
        // Подготовка данных в репозитории
        List<String> trackedLinks = new ArrayList<>();
        trackedLinks.add("https://example.com/link1");
        when(userRepository.listLinkByUserId(12345L)).thenReturn(trackedLinks);

        // Mock объекты
        Update mockUpdate = mock(Update.class);
        Message mockMessage = mock(Message.class);
        User mockUser = mock(User.class);
        Chat mockChat = mock(Chat.class);

        // Настройка поведения макетов
        when(mockUpdate.message()).thenReturn(mockMessage);
        when(mockMessage.text()).thenReturn("https://example.com/link1");
        when(mockMessage.from()).thenReturn(mockUser);
        when(mockMessage.from().id()).thenReturn(12345L);
        when(mockMessage.chat()).thenReturn(mockChat); // мокируем chat
        when(mockChat.id()).thenReturn(12345L); // возвращаем идентификатор чата

        // Обработка команды /track
        trackCommand.handle(mockUpdate);

        // Проверка, что ссылка успешно добавлена
        assertEquals(1, trackedLinks.size());
        assertEquals("https://example.com/link1", trackedLinks.get(0));
    }
}
