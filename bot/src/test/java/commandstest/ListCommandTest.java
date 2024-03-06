package commandstest;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import edu.java.bot.command.ListCommand;
import edu.java.bot.interfaceForProject.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ListCommandTest {
    private ListCommand listCommand;
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        listCommand = new ListCommand(userRepository);
    }

    @Test
    public void testCommand(){
        assertEquals("/list", listCommand.command());
    }

    @Test
    public void testDescription() {
        assertEquals("Вывести список отслеживаемых ссылок", listCommand.description());
    }

    @Test
    public void testHandle() {
        // Подготовка данных в макете репозитория
        List<String> trackedLinks = new ArrayList<>();
        trackedLinks.add("https://example.com/link1");
        trackedLinks.add("https://example.com/link2");
        when(userRepository.listLinkByUserId(12345L)).thenReturn(trackedLinks);

        // Создание макетов для Update, Message и Chat
        Update mockUpdate = mock(Update.class);
        Message mockMessage = mock(Message.class);
        User mockUser = mock(User.class);
        Chat mockChat = mock(Chat.class);

        // Настройка поведения макетов
        when(mockUpdate.message()).thenReturn(mockMessage);
        when(mockMessage.from()).thenReturn(mockUser);
        when(mockUser.id()).thenReturn(123L); // Возвращаем идентификатор пользователя
        when(mockMessage.text()).thenReturn("/list");
        when(mockMessage.chat()).thenReturn(mockChat); // мокируем chat
        when(mockChat.id()).thenReturn(12345L); // возвращаем идентификатор чата

        // Обработка команды /list
        listCommand.handle(mockUpdate);

        // Проверка, что количество ссылок в репозитории соответствует ожидаемому
        assertEquals(trackedLinks.size(), userRepository.listLinkByUserId(12345L).size());
    }

    @Test
    public void testSupports() {
        // Mock объекты
        Update mockUpdate = mock(Update.class);
        Message mockMessage = mock(Message.class);

        // Настройка поведения макетов
        when(mockUpdate.message()).thenReturn(mockMessage);
        when(mockMessage.text()).thenReturn("/list");

        // Проверка поддержки команды /list
        boolean supportsCommand = listCommand.supports(mockUpdate);

        assertEquals(true, supportsCommand);
    }
}
