package edu.java.scrapper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.controllers.UpdateController;
import edu.java.dto.responseDto.LinkUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UpdateController.class)
public class UpdateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UpdateController updateController;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testHandleUpdate() throws Exception {
        LinkUpdate linkUpdate = new LinkUpdate(1L, "https://example.com", "Example description", null);

        mockMvc.perform(post("/updates")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(linkUpdate)))
            .andExpect(status().isOk());
    }
}

