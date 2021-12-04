package com.ironhack.moves_service.dto;

import com.ironhack.moves_service.dao.Move;
import com.ironhack.moves_service.enums.EndResult;
import com.ironhack.moves_service.enums.Piece;
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
    @NotNull
    private Long gameId;
    @NotBlank
    private String move;
    @NotNull
    private Piece piece;
    @NotNull
    private Boolean capture;
    @NotNull
    private Boolean inCheck;
    @NotNull
    private EndResult result;

    public static MoveDTO moveToMoveDTO(Move move) {
        return new MoveDTO(
                move.getId(),
                move.getGameId(),
                move.getMove(),
                move.getPiece(),
                move.getCapture(),
                move.getInCheck(),
                move.getResult()
        );
    }
}
