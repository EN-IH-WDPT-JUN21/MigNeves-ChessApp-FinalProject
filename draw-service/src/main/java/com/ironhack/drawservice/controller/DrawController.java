package com.ironhack.drawservice.controller;

import com.ironhack.drawservice.dto.MoveDTO;
import com.ironhack.drawservice.service.DrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chess/draw")
public class DrawController {

    @Autowired
    DrawService drawService;

    // Method to check if the game is a draw
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public MoveDTO checkDraw(@RequestBody MoveDTO moveDTO) {
        return drawService.checkDraw(moveDTO);
    }
}
