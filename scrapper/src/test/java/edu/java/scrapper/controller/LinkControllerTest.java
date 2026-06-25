package edu.java.scrapper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.java.scrapper.controllers.LinkController;
import edu.java.scrapper.domain.jdbc.JdbcLinkService;
import edu.java.scrapper.domain.model.Link;
import edu.java.scrapper.dto.request.AddLinkRequest;
import edu.java.scrapper.dto.request.RemoveLinkRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(LinkController.class)
public class LinkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JdbcLinkService linkService;

    private final Long tgChatId = 123L;

    @BeforeEach
    void setup() {
        Link firstLink = new Link();
        firstLink.setId(1L);
        firstLink.setUri("https://example.com/1");
        Link secondLink = new Link();
        secondLink.setId(2L);
        secondLink.setUri("https://example.com/2");
        Link changedLink = new Link();
        changedLink.setId(3L);
        changedLink.setUri("https://example.com");

        when(linkService.listAll(anyLong())).thenReturn(Arrays.asList(firstLink, secondLink));
        when(linkService.add(anyLong(), any(URI.class))).thenReturn(changedLink);
        when(linkService.remove(anyLong(), any(URI.class))).thenReturn(changedLink);
    }

    @Test
    public void testGetAllLinks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/links")
                .header("Tg-Chat-Id", tgChatId))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAddLink() throws Exception {
        AddLinkRequest addLinkRequest = new AddLinkRequest("https://example.com");
        mockMvc.perform(MockMvcRequestBuilders.post("/links")
                .header("Tg-Chat-Id", tgChatId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addLinkRequest)))
            .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testRemoveLink() throws Exception {
        RemoveLinkRequest removeLinkRequest = new RemoveLinkRequest("https://example.com");
        mockMvc.perform(MockMvcRequestBuilders.delete("/links")
                .header("Tg-Chat-Id", tgChatId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(removeLinkRequest)))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
