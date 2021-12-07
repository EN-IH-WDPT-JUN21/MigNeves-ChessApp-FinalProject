package com.ironhack.game_service.controller;

import com.ironhack.game_service.dto.GameCreatedDTO;
import com.ironhack.game_service.dto.GameDTO;
import com.ironhack.game_service.dto.SimplifiedGameDTO;
import com.ironhack.game_service.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.PathParam;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/chess/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping(path = "", params = {"userId"})
    @ResponseStatus(HttpStatus.OK)
    public List<SimplifiedGameDTO> getGamesFromUser(@PathParam("userId") Long userId) {
        return gameService.getGamesFromUser(userId);
    }

    @GetMapping(path = "", params = {"gameId", "password"})
    @ResponseStatus(HttpStatus.OK)
    public GameDTO getGameFromId(@PathParam("gameId") Long gameId, @PathParam("password") String password) {
        return gameService.getCompleteGameFromId(gameId, password);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameCreatedDTO addGame(@RequestBody GameCreatedDTO gameDTO) {
        return gameService.addGame(gameDTO);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public GameDTO updateGame(@RequestBody GameDTO gameDTO) {
        return gameService.updateGame(gameDTO);
    }

    @DeleteMapping("/{gameId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGame(@PathVariable("gameId") Long gameId) {
        gameService.deleteGame(gameId);
    }

}
