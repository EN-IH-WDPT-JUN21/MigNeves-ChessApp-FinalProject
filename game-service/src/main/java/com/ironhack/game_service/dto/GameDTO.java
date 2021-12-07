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
import java.util.UUID;

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
    private String password;
    private boolean colorWhite;
    private String fen;

    public static GameDTO GameToGameDTO(Game game, boolean color) {
        return GameToGameDTO(game, color, new ArrayList<>());
    }

    public static GameDTO GameToGameDTO(Game game, boolean color, List<MoveDTO> moves) {
        return new GameDTO(
                game.getId(),
                game.getGameType(),
                game.getWhitePiecesPlayerId(),
                game.getBlackPiecesPlayerId(),
                game.getResult(),
                game.getStartDate(),
                moves,
                null,
                null,
                color,
                game.getFen()
        );
    }
}
