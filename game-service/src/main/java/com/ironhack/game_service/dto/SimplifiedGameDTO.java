package com.ironhack.game_service.dto;

import com.ironhack.game_service.dao.Game;
import com.ironhack.game_service.enums.EndResult;
import com.ironhack.game_service.enums.GameType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimplifiedGameDTO {
    private Long id;
    private GameType gameType;
    private Long whitePiecesPlayerId;
    private Long blackPiecesPlayerId;
    private Integer numberMoves;
    private EndResult result;
    private LocalDateTime startDate;

    public static SimplifiedGameDTO GameToSimplifiedGameDTO(Game game) {
        int moves = game.getHalfMoves() % 2 == 0 ? game.getHalfMoves() / 2 : (game.getHalfMoves() + 1) / 2;
        return new SimplifiedGameDTO(
                game.getId(),
                game.getGameType(),
                game.getWhitePiecesPlayerId(),
                game.getBlackPiecesPlayerId(),
                moves,
                game.getResult(),
                game.getStartDate()
        );
    }
}
