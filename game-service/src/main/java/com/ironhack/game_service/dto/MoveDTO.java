package com.ironhack.game_service.dto;

import com.ironhack.game_service.enums.EndResult;
import com.ironhack.game_service.enums.Piece;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MoveDTO {
    private Long id;
    private Long gameId;
    private String move;
    private Piece piece;
    private Boolean capture;
    private Boolean inCheck;
    private EndResult result;
    private String fen;
}
