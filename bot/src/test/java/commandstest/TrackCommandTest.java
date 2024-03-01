package commandstest;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import edu.java.bot.command.TrackCommand;
import edu.java.bot.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TrackCommandTest {
    private TrackCommand trackCommand;
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository = new UserRepository();
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
        userRepository.getTrackedLinks().add("https://example.com/link1");

        // Mock объекты
        Update mockUpdate = mock(Update.class);
        Message mockMessage = mock(Message.class);
        Chat mockChat = mock(Chat.class);

        // Настройка поведения макетов
        when(mockUpdate.message()).thenReturn(mockMessage);
        when(mockMessage.text()).thenReturn("https://example.com/link1");
        when(mockMessage.chat()).thenReturn(mockChat); // мокируем chat
        when(mockChat.id()).thenReturn(12345L); // возвращаем идентификатор чата

        // Обработка команды /track
        trackCommand.handle(mockUpdate);


        List<String> trackedLinks = userRepository.getTrackedLinks();
        assertEquals(1, trackedLinks.size());
        assertEquals("https://example.com/link1", trackedLinks.get(0));
    }
}
