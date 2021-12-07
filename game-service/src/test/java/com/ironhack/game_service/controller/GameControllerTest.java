package com.ironhack.game_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.game_service.dao.Game;
import com.ironhack.game_service.dto.GameDTO;
import com.ironhack.game_service.dto.MoveDTO;
import com.ironhack.game_service.enums.EndResult;
import com.ironhack.game_service.enums.GameType;
import com.ironhack.game_service.enums.Piece;
import com.ironhack.game_service.repository.GameRepository;
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

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(properties = {
        "server.port = 8300",
        "spring.application.name=game-service",
        "spring.h2.console.enabled=true",
        "spring.datasource.name=chess-test",
        "spring.jpa.show-sql=true",
        "spring.datasource.generate-unique-name=false",
        "server.error.include-message = always",
        "running.test=true"
})
class GameControllerTest {

    final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    GameRepository gameRepository;
    MockMvc mockMvc;

    Game game1;
    Game game2;

    @BeforeEach
    void setUp() {
        objectMapper.findAndRegisterModules();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        game1 = new Game(null,
                GameType.UNKNOWN_UNKNOWN,
                1L,
                2L,
                4,
                EndResult.DRAW,
                LocalDateTime.of(2021, 1, 6, 0, 0),
                "AAAAAAAAAA",
                "BBBBBBBBBB",
                "rnbq1bnr/ppppkppp/8/4p3/4P3/8/PPPPKPPP/RNBQ1BNR w - - 2 3");

        game2 = new Game(null,
                GameType.UNKNOWN_UNKNOWN,
                1L,
                2L,
                3,
                EndResult.UNFINISHED,
                LocalDateTime.of(2021, 2, 9, 0, 0),
                "CCCCCCCCCC",
                "DDDDDDDDDD",
                "rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPPKPPP/RNBQ1BNR b kq - 1 2");
        gameRepository.saveAll(List.of(game1, game2));
    }

    @AfterEach
    void tearDown() {
        gameRepository.deleteAll();
    }

    @Test
    @DisplayName("Get games from user id")
    void getGamesFromUser() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/chess/game")
                .param("userId", "1"))
                .andExpect(status().isOk()).andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("UNKNOWN_UNKNOWN"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("UNFINISHED"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("DRAW"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("2021-02-09T00:00:00"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("2021-01-06T00:00:00"));
    }

    @Test
    @DisplayName("Get game from game id")
    void getGameFromId_Valid() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/chess/game")
                .param("gameId", "1")
                .param("password", "AAAAAAAAAA"))
                .andExpect(status().isOk()).andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("UNKNOWN_UNKNOWN"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("DRAW"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("2021-01-06T00:00:00"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("e2e4"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("e7e5"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("e1e2"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("e8e7"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("WHITE_PAWN"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("BLACK_PAWN"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("WHITE_KING"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("BLACK_KING"));
    }

    @Test
    @DisplayName("Get corrupted game from game id")
    void getGameFromId_Invalid() throws Exception {
        mockMvc.perform(get("/chess/game")
                .param("gameId", "2")
                .param("password", "DDDDDDDDDD"))
                .andExpect(status().isInternalServerError()).andReturn();
    }

    @Test
    @DisplayName("Get game from game id and wrong password")
    void getGameFromId_InvalidPasswword() throws Exception {
        mockMvc.perform(get("/chess/game")
                .param("gameId", "2")
                .param("password", "ABCDEFGHIJ"))
                .andExpect(status().isUnauthorized()).andReturn();
    }

    @Test
    @DisplayName("Add a new game")
    void addGame() throws Exception {
        String body = objectMapper.writeValueAsString(
                new Game(
                        GameType.USER_USER,
                        1L,
                        2L)
        );
        MvcResult mvcResult = mockMvc.perform(post("/chess/game")
                .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated()).andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("USER_USER"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("1"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("2"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("3"));
    }

    @Test
    @DisplayName("Update game")
    void updateGame_ValidUpdate() throws Exception {
        String body = objectMapper.writeValueAsString(
                new GameDTO(
                        2L,
                        GameType.UNKNOWN_UNKNOWN,
                        null,
                        null,
                        EndResult.UNFINISHED,
                        null,
                        new ArrayList<>(),
                        new MoveDTO(
                            null,
                            2L,
                            "e8e7",
                            Piece.BLACK_KING,
                            false,
                            false,
                            EndResult.DRAW
                        ),
                        "DDDDDDDDDD",
                        false,
                        "rnbq1bnr/ppppkppp/8/4p3/4P3/8/PPPPKPPP/RNBQ1BNR w - - 2 3"
                )
        );

        MvcResult mvcResult = mockMvc.perform(put("/chess/game")
            .content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("UNKNOWN_UNKNOWN"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("DRAW"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("e2e4"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("e7e5"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("e1e2"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("e8e7"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("WHITE_PAWN"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("BLACK_PAWN"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("WHITE_KING"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("BLACK_KING"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("rnbq1bnr/ppppkppp/8/4p3/4P3/8/PPPPKPPP/RNBQ1BNR w - - 2 3"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("2021-02-09T00:00:00"));
    }

    @Test
    @DisplayName("Invalid update")
    void updateGame_InvalidUpdate() throws Exception {
        String body = objectMapper.writeValueAsString(
                new GameDTO(
                        1L,
                        GameType.UNKNOWN_UNKNOWN,
                        null,
                        null,
                        EndResult.UNFINISHED,
                        null,
                        new ArrayList<>(),
                        new MoveDTO(
                                null,
                                2L,
                                "e8e7",
                                Piece.BLACK_KING,
                                false,
                                false,
                                EndResult.DRAW
                        ),
                        "DDDDDDDDDD",
                        false,
                        "rnbq1bnr/ppppkppp/8/4p3/4P3/8/PPPPKPPP/RNBQ1BNR w - - 2 3"
                )
        );

        MvcResult mvcResult = mockMvc.perform(put("/chess/game")
                .content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andReturn();

        body = objectMapper.writeValueAsString(
                new GameDTO(
                        1L,
                        GameType.UNKNOWN_UNKNOWN,
                        null,
                        null,
                        EndResult.UNFINISHED,
                        null,
                        new ArrayList<>(),
                        new MoveDTO(
                                null,
                                1L,
                                "e8e7",
                                Piece.BLACK_KING,
                                false,
                                false,
                                EndResult.DRAW
                        ),
                        "DDDDDDDDDD",
                        false,
                        "rnbq1bnr/ppppkppp/8/4p3/4P3/8/PPPPKPPP/RNBQ1BNR w - - 2 3"
                )
        );

        mvcResult = mockMvc.perform(put("/chess/game")
                .content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andReturn();

        body = objectMapper.writeValueAsString(
                new GameDTO(
                        3L,
                        GameType.UNKNOWN_UNKNOWN,
                        null,
                        null,
                        EndResult.UNFINISHED,
                        null,
                        new ArrayList<>(),
                        new MoveDTO(
                                null,
                                3L,
                                "e8e7",
                                Piece.BLACK_KING,
                                false,
                                false,
                                EndResult.DRAW
                        ),
                        "DDDDDDDDDD",
                        false,
                        "rnbq1bnr/ppppkppp/8/4p3/4P3/8/PPPPKPPP/RNBQ1BNR w - - 2 3"
                )
        );

        mvcResult = mockMvc.perform(put("/chess/game")
                .content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound()).andReturn();

        body = objectMapper.writeValueAsString(
                new GameDTO(
                        2L,
                        GameType.UNKNOWN_UNKNOWN,
                        null,
                        null,
                        EndResult.UNFINISHED,
                        null,
                        new ArrayList<>(),
                        null,
                        "DDDDDDDDDD",
                        false,
                        "rnbq1bnr/ppppkppp/8/4p3/4P3/8/PPPPKPPP/RNBQ1BNR w - - 2 3"
                )
        );

        mvcResult = mockMvc.perform(put("/chess/game")
                .content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @DisplayName("Delete game")
    void deleteGame() throws Exception {
        mockMvc.perform(delete("/chess/game/1")).andExpect(status().isNoContent()).andReturn();
        mockMvc.perform(get("/chess/game").param("gameId", "1").param("password", "CCCCCCCCCC")).andExpect(status().isNotFound()).andReturn();

    }
}