package com.ironhack.game_service.dto;

import com.ironhack.game_service.dao.Game;
import com.ironhack.game_service.enums.EndResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimplifiedGameDTO {
    private Long id;
    private Integer numberMoves;
    private EndResult result;
    private LocalDateTime startDate;
    private String fen;
    private Boolean owner;

    public static SimplifiedGameDTO GameToSimplifiedGameDTO(Game game, String fen) {
        int moves = game.getHalfMoves() % 2 == 0 ? game.getHalfMoves() / 2 : (game.getHalfMoves() + 1) / 2;
        return new SimplifiedGameDTO(
                game.getId(),
                moves,
                game.getResult(),
                game.getStartDate(),
                fen,
                false
        );
    }
}
