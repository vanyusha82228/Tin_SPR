package edu.java.scrapper.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.scrapper.domain.jdbcInterface.TgChatService;
import edu.java.scrapper.controllers.ChatController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;

@WebMvcTest(ChatController.class)
public class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TgChatService tgChatService;

    @Test
    public void testRegisterChat() throws Exception {
        Long chatId = 123L;
        doNothing().when(tgChatService).register(chatId);

        mockMvc.perform(MockMvcRequestBuilders.post("/tg-chat/{id}", chatId))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteChat() throws Exception {
        Long chatId = 123L;
        doNothing().when(tgChatService).unregister(chatId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/tg-chat/{id}", chatId))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
}




