package com.ironhack.game_service.dto;

import com.ironhack.game_service.dao.Game;
import com.ironhack.game_service.enums.EndResult;
import com.ironhack.game_service.enums.GameType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameDTO {
    private Long id;
    private GameType gameType;
    private Long whitePiecesPlayerId;
    private Long blackPiecesPlayerId;
    private EndResult result;
    private LocalDateTime startDate;
    private List<MoveDTO> moves;
    private MoveDTO moveToAdd;

    public static GameDTO GameToGameDTO(Game game) {
        return new GameDTO(
                game.getId(),
                game.getGameType(),
                game.getWhitePiecesPlayerId(),
                game.getBlackPiecesPlayerId(),
                game.getResult(),
                game.getStartDate(),
                new ArrayList<>(),
                null
        );
    }
}
