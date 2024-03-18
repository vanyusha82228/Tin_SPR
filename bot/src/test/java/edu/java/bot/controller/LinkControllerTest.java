package edu.java.bot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.bot.controllers.LinkController;
import edu.java.bot.dto.request.AddLinkRequest;
import edu.java.bot.dto.request.RemoveLinkRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LinkController.class)
public class LinkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllLinks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/links")
                .header("Tg-Chat-Id", 123L))
            .andExpect(status().isOk());
    }

    @Test
    public void testAddLink() throws Exception {
        AddLinkRequest addLinkRequest = new AddLinkRequest("https://example.com");
        mockMvc.perform(MockMvcRequestBuilders.post("/links")
                .header("Tg-Chat-Id", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addLinkRequest)))
            .andExpect(status().isOk());
    }

    @Test
    public void testRemoveLink() throws Exception {
        RemoveLinkRequest removeLinkRequest = new RemoveLinkRequest("https://example.com");
        mockMvc.perform(MockMvcRequestBuilders.delete("/links")
                .header("Tg-Chat-Id", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(removeLinkRequest)))
            .andExpect(status().isOk());
    }
}
