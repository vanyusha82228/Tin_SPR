package edu.java.scrapper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.scrapper.domain.jdbcInterface.LinkService;
import edu.java.scrapper.controllers.LinkController;
import edu.java.scrapper.domain.model.UserLink;
import edu.java.scrapper.dto.request.AddLinkRequest;
import edu.java.scrapper.dto.request.RemoveLinkRequest;
import edu.java.scrapper.dto.response.LinkResponse;
import edu.java.scrapper.dto.response.ListLinksResponse;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@WebMvcTest(LinkController.class)
public class LinkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LinkService linkService;

    @Test
    public void testGetAllLinks() throws Exception {
        when(linkService.listAll(anyLong())).thenReturn(Arrays.asList(new UserLink(), new UserLink()));

        mockMvc.perform(MockMvcRequestBuilders.get("/links")
                .header("Tg-Chat-Id", 123L))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAddLink() throws Exception {
        AddLinkRequest addLinkRequest = new AddLinkRequest("https://example.com");
        when(linkService.add(anyLong(), any(URI.class))).thenReturn(new UserLink());

        mockMvc.perform(MockMvcRequestBuilders.post("/links")
                .header("Tg-Chat-Id", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addLinkRequest)))
            .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testRemoveLink() throws Exception {
        RemoveLinkRequest removeLinkRequest = new RemoveLinkRequest("https://example.com");
        doNothing().when(linkService).remove(anyLong(), any(URI.class));

        mockMvc.perform(MockMvcRequestBuilders.delete("/links")
                .header("Tg-Chat-Id", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(removeLinkRequest)))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

