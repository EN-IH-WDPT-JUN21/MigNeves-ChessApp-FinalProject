package com.ironhack.edge_service.proxy;

import com.ironhack.edge_service.dto.MoveDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@FeignClient("draw-service")
public interface DrawProxy {
    @PutMapping("/chess/draw")
    @ResponseStatus(HttpStatus.OK)
    MoveDTO checkDraw(@RequestBody MoveDTO moveDTO);
}
