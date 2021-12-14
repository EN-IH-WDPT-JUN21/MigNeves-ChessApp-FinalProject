package com.ironhack.game_service.controller;

import com.ironhack.game_service.dto.FinishedGamesDTO;
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

    // Method to get simplified games from multiple keys
    @GetMapping(path = "", params = {"keys"})
    @ResponseStatus(HttpStatus.OK)
    public List<SimplifiedGameDTO> getGamesFromKeys(@PathParam("keys") String[] keys) {
        return gameService.getGamesFromKeys(keys);
    }

    // Method to get a complete game from its id
    @GetMapping(path = "", params = {"gameId", "password"})
    @ResponseStatus(HttpStatus.OK)
    public GameDTO getGameFromId(@PathParam("gameId") Long gameId, @PathParam("password") String password) {
        return gameService.getCompleteGameFromId(gameId, password);
    }

    // Method to add a new game
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameCreatedDTO addGame(@RequestBody GameCreatedDTO gameDTO) {
        return gameService.addGame(gameDTO);
    }

    // Method to update an open game
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public GameDTO updateGame(@RequestBody GameDTO gameDTO) {
        return gameService.updateGame(gameDTO);
    }

    // Method to delete a game
    @DeleteMapping("/{gameId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGame(@PathVariable("gameId") Long gameId) {
        gameService.deleteGame(gameId);
    }

    // Method to get all finished games
    @GetMapping(path = "", params = {"page"})
    @ResponseStatus(HttpStatus.OK)
    public FinishedGamesDTO getAllFinishedGames(@PathParam("page") int page) {
        return gameService.getAllFinishedGames(page);
    }
}
