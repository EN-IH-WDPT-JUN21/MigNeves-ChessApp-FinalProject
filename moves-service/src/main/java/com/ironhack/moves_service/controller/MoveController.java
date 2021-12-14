package com.ironhack.moves_service.controller;

import com.ironhack.moves_service.dto.FenDTO;
import com.ironhack.moves_service.dto.MoveDTO;
import com.ironhack.moves_service.service.MoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/chess/move")
public class MoveController {

    @Autowired
    private MoveService moveService;

    // Method to get all moves from a game
    @GetMapping("/{gameId}")
    @ResponseStatus(HttpStatus.OK)
    public List<MoveDTO> getMovesFromGame(@PathVariable("gameId") Long gameId) {
        return this.moveService.getMovesFromGame(gameId);
    }

    // Method to add a new move
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<MoveDTO> addMoveToGame(@RequestBody @Valid MoveDTO moveDTO) {
        return this.moveService.addMoveToGame(moveDTO);
    }

    // Method to delete all moves from a game
    @DeleteMapping("/{gameId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovesFromGame(@PathVariable("gameId") Long gameId) {
        this.moveService.deleteMovesFromGame(gameId);
    }

    // Method to get the last fen position
    @GetMapping("/fen/{gameId}")
    @ResponseStatus(HttpStatus.OK)
    public FenDTO getLastFenElement(@PathVariable("gameId") Long gameId) {
        return this.moveService.getLastFen(gameId);
    }
}
