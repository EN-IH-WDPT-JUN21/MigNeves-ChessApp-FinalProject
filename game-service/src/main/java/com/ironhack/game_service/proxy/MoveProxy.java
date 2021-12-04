package com.ironhack.game_service.proxy;

import com.ironhack.game_service.dto.MoveDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("moves-service")
public interface MoveProxy {

    @GetMapping("/chess/move/{gameId}")
    List<MoveDTO> getMovesFromGame(@PathVariable("gameId") Long gameId);

    @PostMapping("/chess/move")
    List<MoveDTO> addMoveToGame(@RequestBody MoveDTO moveDTO);

    @DeleteMapping("/chess/move/{gameId}")
    void deleteMovesFromGame(@PathVariable("gamedId") Long gameId);
}
