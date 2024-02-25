import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.command.HelpCommand;
import edu.java.bot.interfaceForProject.Command;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HelpCommandTest {

    private HelpCommand helpCommand;
    private List<Command> commandList;

    @BeforeEach
    public void setUp() {
        commandList = new ArrayList<>();
        Command mockCommand1 = mock(Command.class);
        when(mockCommand1.command()).thenReturn("/command1");
        when(mockCommand1.description()).thenReturn("Description 1");
        Command mockCommand2 = mock(Command.class);
        when(mockCommand2.command()).thenReturn("/command2");
        when(mockCommand2.description()).thenReturn("Description 2");

        commandList.add(mockCommand1);
        commandList.add(mockCommand2);

        helpCommand = new HelpCommand(commandList);
    }

    @Test
    public void testHandle() {
        Update mockUpdate = mock(Update.class);

        String expectedResponse = "Список доступных команд:\n" +
            "/command1 - Description 1\n" +
            "/command2 - Description 2\n";

        SendMessage sendMessage = helpCommand.handle(mockUpdate);
        assertEquals(expectedResponse, sendMessage.getParameters());
    }

    @Test
    public void testSupports() {
        Update mockUpdate1 = mock(Update.class);
        when(mockUpdate1.message()).thenReturn(null);
        assertFalse(helpCommand.supports(mockUpdate1));

        Update mockUpdate2 = mock(Update.class);
        when(mockUpdate2.message().text()).thenReturn("/help");
        assertTrue(helpCommand.supports(mockUpdate2));

        Update mockUpdate3 = mock(Update.class);
        when(mockUpdate3.message().text()).thenReturn("/not_help");
        assertFalse(helpCommand.supports(mockUpdate3));
    }
}
