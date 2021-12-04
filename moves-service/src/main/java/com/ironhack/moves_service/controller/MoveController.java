package com.ironhack.moves_service.controller;

import com.ironhack.moves_service.dto.MoveDTO;
import com.ironhack.moves_service.service.MoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("chess/move")
public class MoveController {

    @Autowired
    private MoveService moveService;

    @GetMapping("/{gameId}")
    @ResponseStatus(HttpStatus.OK)
    public List<MoveDTO> getMovesFromGame(@PathVariable("gameId") Long gameId) {
        return this.moveService.getMovesFromGame(gameId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MoveDTO> addMoveToGame(@RequestBody @Valid MoveDTO moveDTO) {
        return this.moveService.addMoveToGame(moveDTO);
    }

    @DeleteMapping("/{gameId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovesFromGame(@PathVariable("gameId") Long gameId) {
        this.moveService.deleteMovesFromGame(gameId);
    }
}
