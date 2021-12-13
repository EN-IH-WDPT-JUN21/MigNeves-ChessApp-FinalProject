package com.ironhack.edge_service.controller;

import com.ironhack.edge_service.dto.FinishedGamesDTO;
import com.ironhack.edge_service.dto.GameCreatedDTO;
import com.ironhack.edge_service.dto.GameDTO;
import com.ironhack.edge_service.dto.SimplifiedGameDTO;
import com.ironhack.edge_service.proxy.GameProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.PathParam;
import java.util.List;
import java.util.UUID;

@RestController
public class EdgeController {

    @Autowired
    private GameProxy gameProxy;

    @GetMapping(path = "/chess/game", params = {"userId"})
    @ResponseStatus(HttpStatus.OK)
    private List<SimplifiedGameDTO> getGamesFromUser(@PathParam("userId") Long userId) {
        return gameProxy.getGamesFromUser(userId);
    }

    @GetMapping(path = "/chess/game", params = {"gameId", "password"})
    @ResponseStatus(HttpStatus.OK)
    private GameDTO getGameFromId(@PathParam("gameId") Long gameId, @PathParam("password") String password) {
        return gameProxy.getGameFromId(gameId, password);
    }

    @PostMapping("/chess/game")
    @ResponseStatus(HttpStatus.CREATED)
    private GameCreatedDTO addGame(@RequestBody GameCreatedDTO gameDTO) {
        System.out.println(gameDTO);
        return gameProxy.addGame(gameDTO);
    }

    @PutMapping("/chess/game")
    @ResponseStatus(HttpStatus.OK)
    private GameDTO updateGame(@RequestBody GameDTO gameDTO) {
        return gameProxy.updateGame(gameDTO);
    }

    @DeleteMapping("/chess/game/{gameId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteGame(@PathVariable("gameId") Long gameId) {
        gameProxy.deleteGame(gameId);
    }

    @GetMapping(path = "/chess/game", params = {"keys"})
    @ResponseStatus(HttpStatus.OK)
    public List<SimplifiedGameDTO> getGamesFromKeys(@PathParam("keys") String[] keys) {
        return gameProxy.getGamesFromKeys(keys);
    }

    @GetMapping(path = "/chess/game", params = {"page"})
    @ResponseStatus(HttpStatus.OK)
    public FinishedGamesDTO getAllFinishedGames(@PathParam("page") int page) {
        return gameProxy.getAllFinishedGames(page);
    }
}
