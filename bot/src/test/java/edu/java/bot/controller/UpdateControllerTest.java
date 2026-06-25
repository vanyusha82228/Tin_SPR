package edu.java.bot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.bot.controllers.UpdateController;
import edu.java.bot.dto.responseDto.LinkUpdate;
import edu.java.bot.servicebot.ServiceBot;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UpdateController.class)
public class UpdateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ServiceBot serviceBot;

    @Test
    void testHandleUpdateSendsMessagesToChats() throws Exception {
        LinkUpdate linkUpdate = new LinkUpdate(
            1L,
            "https://example.com",
            "Example description",
            List.of(100L, 200L)
        );

        mockMvc.perform(post("/updates")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(linkUpdate)))
            .andExpect(status().isOk());

        ArgumentCaptor<com.pengrad.telegrambot.request.SendMessage> captor =
            ArgumentCaptor.forClass(com.pengrad.telegrambot.request.SendMessage.class);
        verify(serviceBot, times(2)).execute(captor.capture());
        assertThat(captor.getAllValues()).hasSize(2);
    }
}
