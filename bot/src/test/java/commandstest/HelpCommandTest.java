package commandstest;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.command.HelpCommand;
import edu.java.bot.interfaceForProject.Command;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HelpCommandTest {

    private HelpCommand helpCommand;
    private Update mockUpdate;

    @BeforeEach
    public void setUp() {
        List<Command> commandList = new ArrayList<>();
        commandList.add(mock(Command.class));

        helpCommand = new HelpCommand(commandList);
        mockUpdate = mock(Update.class);
        Message mockMessage = mock(Message.class);
        when(mockUpdate.message()).thenReturn(mockMessage);
        when(mockMessage.text()).thenReturn("/help");
    }

    @Test
    public void testCommand() {
        assertEquals("/help", helpCommand.command());
    }

    @Test
    public void testDescription() {
        assertEquals("Вывести окно с командами", helpCommand.description());
    }

    @Test
    public void testHandle() {
        mockUpdate = mock(Update.class);
        Message mockMessage = mock(Message.class);
        Chat mockChat = mock(Chat.class);

        when(mockUpdate.message()).thenReturn(mockMessage);
        when(mockMessage.chat()).thenReturn(mockChat);
        when(mockChat.id()).thenReturn(12345L);
        when(mockMessage.text()).thenReturn("/help");
    }

    @Test
    public void testSupports() {
        assertTrue(helpCommand.supports(mockUpdate));
    }
}
