import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collections;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import edu.java.bot.bot.MyBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import edu.java.bot.interfaceForProject.Command;
import edu.java.bot.interfaceForProject.UserMessageProcessor;
import edu.java.bot.processor.UserMessageProcessorImpl;

@SpringBootTest
public class BotTests {

    @Mock
    private UserMessageProcessorImpl messageProcessor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUnknownCommand() {
        MyBot bot = new MyBot("YOUR_TELEGRAM_TOKEN", messageProcessor);

        Update update = mock(Update.class);
        Message message = mock(Message.class);

        when(message.text()).thenReturn(null);
        when(update.message()).thenReturn(message);

        bot.process(new ArrayList<>());

        verify(bot).execute(any(SendMessage.class));
    }
}
