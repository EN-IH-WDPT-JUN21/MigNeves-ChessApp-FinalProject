package com.ironhack.drawservice.dto;

import com.ironhack.drawservice.enums.EndResult;
import com.ironhack.drawservice.enums.Piece;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
