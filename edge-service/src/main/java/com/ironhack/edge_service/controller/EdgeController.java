package com.ironhack.edge_service.controller;

import com.ironhack.edge_service.dto.*;
import com.ironhack.edge_service.enums.EndResult;
import com.ironhack.edge_service.proxy.DrawProxy;
import com.ironhack.edge_service.proxy.GameProxy;
import com.ironhack.edge_service.proxy.MovesProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.ws.rs.PathParam;
import java.util.List;

@RestController
public class EdgeController {

    @Autowired
    private GameProxy gameProxy;

    @Autowired
    private MovesProxy movesProxy;

    @Autowired
    private DrawProxy drawProxy;

    // Method to get a complete game from id
    @GetMapping(path = "/chess/game", params = {"gameId", "password"})
    @ResponseStatus(HttpStatus.OK)
    private GameDTO getGameFromId(@PathParam("gameId") Long gameId, @PathParam("password") String password) {
        return gameProxy.getGameFromId(gameId, password);
    }

    // Method to create a new game
    @PostMapping("/chess/game")
    @ResponseStatus(HttpStatus.CREATED)
    private GameCreatedDTO addGame(@RequestBody GameCreatedDTO gameDTO) {
        return gameProxy.addGame(gameDTO);
    }

    // Method to update a currently open game
    @PutMapping("/chess/game")
    @ResponseStatus(HttpStatus.OK)
    private GameDTO updateGame(@RequestBody GameDTO gameDTO) {
        // If the game has not finished check whether it is a draw and then proceed to update.
        if (gameDTO.getMoveToAdd().getResult() == EndResult.UNFINISHED) {
            MoveDTO moveDTO = drawProxy.checkDraw(gameDTO.getMoveToAdd());
            if (moveDTO.getResult() == EndResult.DRAW) {
                gameDTO.setResult(EndResult.DRAW);
                gameDTO.setMoveToAdd(moveDTO);
            }
        }
        return gameProxy.updateGame(gameDTO);
    }

    // Method to delete a game
    @DeleteMapping("/chess/game/{gameId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteGame(@PathVariable("gameId") Long gameId) {
        gameProxy.deleteGame(gameId);
    }

    // Method to get games from multiple keys
    @GetMapping(path = "/chess/game", params = {"keys"})
    @ResponseStatus(HttpStatus.OK)
    public List<SimplifiedGameDTO> getGamesFromKeys(@PathParam("keys") String[] keys) {
        return gameProxy.getGamesFromKeys(keys);
    }

    // Method to get all finished games
    @GetMapping(path = "/chess/game", params = {"page"})
    @ResponseStatus(HttpStatus.OK)
    public FinishedGamesDTO getAllFinishedGames(@PathParam("page") int page) {
        return gameProxy.getAllFinishedGames(page);
    }

    // Method to get all moves from a game
    @GetMapping("/chess/move/{gameId}")
    @ResponseStatus(HttpStatus.OK)
    public List<MoveDTO> getMovesFromGame(@PathVariable("gameId") Long gameId) {
        // Only return moves if the game has already ended
        List<MoveDTO> moves = movesProxy.getMovesFromGame(gameId);
        if (moves.get(moves.size() - 1).getResult().equals(EndResult.UNFINISHED)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The game has not yet ended!");
        } else {
            return moves;
        }
    }
}
