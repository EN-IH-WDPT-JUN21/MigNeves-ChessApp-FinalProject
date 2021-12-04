package com.ironhack.game_service.controller;

import com.ironhack.game_service.dto.GameDTO;
import com.ironhack.game_service.dto.SimplifiedGameDTO;
import com.ironhack.game_service.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.PathParam;
import java.util.List;

@RestController
@RequestMapping("chess/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping(path = "", params = {"userId"})
    @ResponseStatus(HttpStatus.OK)
    private List<SimplifiedGameDTO> getGamesFromUser(@PathParam("userId") Long userId) {
        return gameService.getGamesFromUser(userId);
    }

    @GetMapping(path = "", params = {"gameId"})
    @ResponseStatus(HttpStatus.OK)
    private GameDTO getGameFromId(@PathParam("gameId") Long gameId) {
        return gameService.getCompleteGameFromId(gameId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private SimplifiedGameDTO addGame(@RequestBody GameDTO gameDTO) {
        return gameService.addGame(gameDTO);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    private GameDTO updateGame(@RequestBody GameDTO gameDTO) {
        return gameService.updateGame(gameDTO);
    }

    @DeleteMapping("/{gameId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteGame(@PathVariable("gameId") Long gameId) {
        gameService.deleteGame(gameId);
    }

}
