package edu.java.scrapper.controller;




import edu.java.scrapper.controllers.ChatController;
import edu.java.scrapper.domain.jdbc.JdbcTgChatService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ChatController.class)
class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JdbcTgChatService tgChatService;

    @Test
    void testRegisterChat() throws Exception {
        long chatId = 123456L;

        mockMvc.perform(MockMvcRequestBuilders.post("/tg-chat/{id}", chatId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(tgChatService, Mockito.times(1)).register(chatId);
    }

    @Test
    void testDeleteChat() throws Exception {
        long chatId = 123456L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/tg-chat/{id}", chatId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(tgChatService, Mockito.times(1)).unregister(chatId);
    }
}



