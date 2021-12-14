package com.ironhack.edge_service.proxy;

import com.ironhack.edge_service.dto.FinishedGamesDTO;
import com.ironhack.edge_service.dto.GameCreatedDTO;
import com.ironhack.edge_service.dto.GameDTO;
import com.ironhack.edge_service.dto.SimplifiedGameDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.PathParam;
import java.util.List;
import java.util.UUID;

@FeignClient("game-service")
public interface GameProxy {

    @GetMapping(path = "/chess/game", params = {"gameId", "password"})
    GameDTO getGameFromId(@RequestParam("gameId") Long gameId, @RequestParam("password") String password);

    @PostMapping("/chess/game")
    GameCreatedDTO addGame(@RequestBody GameCreatedDTO gameDTO);

    @PutMapping("/chess/game")
    GameDTO updateGame(@RequestBody GameDTO gameDTO);

    @DeleteMapping("/chess/game/{gameId}")
    void deleteGame(@PathVariable("gameId") Long gameId);

    @GetMapping(path = "/chess/game", params = {"keys"})
    List<SimplifiedGameDTO> getGamesFromKeys(@RequestParam("keys") String[] keys);

    @GetMapping(path = "/chess/game", params = {"page"})
    public FinishedGamesDTO getAllFinishedGames(@RequestParam("page") int page);
}
