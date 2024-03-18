package edu.java.scrapper.controller;



import edu.java.controllers.ChatController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ChatController.class)
public class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRegisterChat() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/tg-chat/{id}", 123L)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void testDeleteChat() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/tg-chat/{id}", 123L)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}


