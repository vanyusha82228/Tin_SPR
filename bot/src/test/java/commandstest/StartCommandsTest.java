package commandstest;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.command.StartCommand;
import edu.java.bot.servicebot.SendMessageInChat;
import edu.java.bot.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StartCommandsTest {
    private StartCommand startCommand;
    private UserRepository userRepository;

    @BeforeEach
    public void setUp(){
        userRepository = new UserRepository();
        startCommand = new StartCommand(userRepository);
    }

    @Test
    public void testCommand(){
        assertEquals("/start", startCommand.command());
    }

    @Test
    public void testDescription() {
        assertEquals("Зарегистрировать пользователя", startCommand.description());
    }

    @Test
    public void testHandleUserAlreadyRegistered() {
        // Mock объекты
        Update mockUpdate = mock(Update.class);
        Message mockMessage = mock(Message.class);
        User mockUser = mock(User.class);
        Chat mockChat = mock(Chat.class);

        // Настройка поведения макетов
        when(mockUpdate.message()).thenReturn(mockMessage);
        when(mockMessage.from()).thenReturn(mockUser);
        when(mockUser.id()).thenReturn(123L);
        when(mockUser.username()).thenReturn("newUser");
        when(mockMessage.chat()).thenReturn(mockChat);
        when(mockChat.id()).thenReturn(12345L);

        // Проверка регистрации нового пользователя
        startCommand.handle(mockUpdate);

        // Проверка, что пользователь успешно зарегистрирован
        Map<Long, String> users = userRepository.getUsers();
        assertEquals(1, users.size());
        assertEquals("newUser", users.get(123L));
    }

}
