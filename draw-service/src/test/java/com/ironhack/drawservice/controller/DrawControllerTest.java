package com.ironhack.drawservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.drawservice.dto.MoveDTO;
import com.ironhack.drawservice.enums.EndResult;
import com.ironhack.drawservice.enums.Piece;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(properties = {
        "server.port = 8200",
        "spring.application.name=draw-service",
        "running.test=true"
})
class DrawControllerTest {

    final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void checkDraw() throws Exception {
        MoveDTO moveDTO = new MoveDTO(
                null,
                null,
                null,
                Piece.BLACK_BISHOP,
                false,
                null,
                null,
                "3k4/8/8/2N2nb1/6B1/8/8/3K4 w - - 100 51"
        );
        String body = objectMapper.writeValueAsString(moveDTO);
        MvcResult mvcResult = mockMvc.perform(put("/chess/draw").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("DRAW"));
    }
}