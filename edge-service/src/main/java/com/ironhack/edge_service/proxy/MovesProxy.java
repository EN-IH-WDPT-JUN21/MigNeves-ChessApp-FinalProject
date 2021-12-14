package com.ironhack.edge_service.proxy;

import com.ironhack.edge_service.dto.MoveDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("moves-service")
public interface MovesProxy {

    @GetMapping("/chess/move/{gameId}")
    List<MoveDTO> getMovesFromGame(@PathVariable("gameId") Long gameId);

}
