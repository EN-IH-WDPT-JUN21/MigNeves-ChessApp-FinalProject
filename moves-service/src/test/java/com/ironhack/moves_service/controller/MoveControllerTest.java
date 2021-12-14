package com.ironhack.moves_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.moves_service.dao.Move;
import com.ironhack.moves_service.dto.MoveDTO;
import com.ironhack.moves_service.enums.EndResult;
import com.ironhack.moves_service.enums.Piece;
import com.ironhack.moves_service.repository.MoveRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(properties = {
        "server.port = 8400",
        "spring.application.name=moves-service",
        "spring.h2.console.enabled=true",
        "spring.datasource.name=chess-test",
        "spring.jpa.show-sql=true",
        "spring.datasource.generate-unique-name=false",
        "server.error.include-message = always"
})
class MoveControllerTest {

    final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    MoveController moveController;

    @Autowired
    MoveRepository moveRepository;
    MockMvc mockMvc;

    Move ruyLopez1;
    Move ruyLopez2;
    Move ruyLopez3;
    Move ruyLopez4;
    Move ruyLopez5;

    Move bongCloud1;
    Move bongCloud2;
    Move bongCloud3;
    Move bongCloud4;


    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        ruyLopez1 = new Move(1L, "e2e4", Piece.WHITE_PAWN, false, false, EndResult.UNFINISHED, "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1");
        ruyLopez2 = new Move(1L, "e7e5", Piece.BLACK_PAWN, false, false, EndResult.UNFINISHED, "rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR w KQkq e6 0 2");
        ruyLopez3 = new Move(1L, "g1f3", Piece.WHITE_KNIGHT, false, false, EndResult.UNFINISHED, "rnbqkbnr/pppp1ppp/8/4p3/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2");
        ruyLopez4 = new Move(1L, "b8c6", Piece.BLACK_KNIGHT, false, false, EndResult.UNFINISHED, "r1bqkbnr/pppp1ppp/2n5/4p3/4P3/5N2/PPPP1PPP/RNBQKB1R w KQkq - 2 3");
        ruyLopez5 = new Move(1L, "f1b5", Piece.WHITE_BISHOP, false, false, EndResult.UNFINISHED, "r1bqkbnr/pppp1ppp/2n5/1B2p3/4P3/5N2/PPPP1PPP/RNBQK2R b KQkq - 3 3");

        bongCloud1 = new Move(2L, "e2e4", Piece.WHITE_PAWN, false, false, EndResult.UNFINISHED, "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1");
        bongCloud2 = new Move(2L, "e7e5", Piece.BLACK_PAWN, false, false, EndResult.UNFINISHED, "rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR w KQkq e6 0 2");
        bongCloud3 = new Move(2L, "e1e2", Piece.WHITE_KING, false, false, EndResult.UNFINISHED, "rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPPKPPP/RNBQ1BNR b kq - 1 2");
        bongCloud4 = new Move(2L, "e8e7", Piece.BLACK_KING, false, false, EndResult.DRAW, "rnbq1bnr/ppppkppp/8/4p3/4P3/8/PPPPKPPP/RNBQ1BNR w - - 2 3");

        moveRepository.saveAll(List.of(ruyLopez1, ruyLopez2, ruyLopez3, ruyLopez4, ruyLopez5, bongCloud1, bongCloud2, bongCloud3, bongCloud4));
    }

    @AfterEach
    public void tearDown() {
        moveRepository.deleteAll();
    }

    @Test
    @DisplayName("Get moves from game")
    void getMovesFromGame_Valid() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/chess/move/1")).andExpect(status().isOk()).andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("e2e4"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("e7e5"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("g1f3"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("b8c6"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("f1b5"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("WHITE_PAWN"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("BLACK_PAWN"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("WHITE_KNIGHT"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("BLACK_KNIGHT"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("WHITE_BISHOP"));

        assertFalse(mvcResult.getResponse().getContentAsString().contains("e1e2"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("e8e7"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("WHITE_KING"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("BLACK_KING"));
    }

    @Test
    @DisplayName("Add invalid move")
    void addMoveToGame_Invalid() throws Exception {
        String body = objectMapper.writeValueAsString(
                new MoveDTO(
                        null,
                        1L,
                        "b5c6",
                        Piece.WHITE_BISHOP,
                        true,
                        false,
                        EndResult.UNFINISHED,
                        ""
                )
        );

        MvcResult mvcResult = mockMvc.perform(post("/chess/move")
            .content(body).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest()).andReturn();

        body = objectMapper.writeValueAsString(
                new MoveDTO(
                        null,
                        2L,
                        "e7e8",
                        Piece.BLACK_KING,
                        false,
                        false,
                        EndResult.UNFINISHED,
                        ""
                )
        );

        mvcResult = mockMvc.perform(post("/chess/move")
                .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();

        body = objectMapper.writeValueAsString(
                new MoveDTO(
                        null,
                        2L,
                        "e2e1",
                        Piece.WHITE_KING,
                        false,
                        false,
                        EndResult.UNFINISHED,
                        ""
                )
        );

        mvcResult = mockMvc.perform(post("/chess/move")
                .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @DisplayName("Add valid move")
    void addMoveToGame_Valid() throws Exception {
        String body = objectMapper.writeValueAsString(
                new MoveDTO(
                        null,
                        1L,
                        "a7a6",
                        Piece.BLACK_PAWN,
                        false,
                        false,
                        EndResult.UNFINISHED,
                        ""
                )
        );

        MvcResult mvcResult = mockMvc.perform(post("/chess/move")
                .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("1"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("a7a6"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("BLACK_PAWN"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("UNFINISHED"));
    }

    @Test
    @DisplayName("Delete moves from game")
    void deleteMovesFromGame() throws Exception {
        mockMvc.perform(delete("/chess/move/2")).andExpect(status().isNoContent()).andReturn();
        MvcResult mvcResult =  mockMvc.perform(get("/chess/move/2")).andExpect(status().isOk()).andReturn();

        assertFalse(mvcResult.getResponse().getContentAsString().contains("e2e4"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("e7e5"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("e1e2"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("e8e7"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("WHITE_PAWN"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("BLACK_PAWN"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("WHITE_KING"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("BLACK_KING"));
    }

    @Test
    @DisplayName("Get last fen element")
    void getLastFen() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/chess/move/fen/1")).andExpect(status().isOk()).andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("r1bqkbnr/pppp1ppp/2n5/1B2p3/4P3/5N2/PPPP1PPP/RNBQK2R b KQkq - 3 3"));
    }
}