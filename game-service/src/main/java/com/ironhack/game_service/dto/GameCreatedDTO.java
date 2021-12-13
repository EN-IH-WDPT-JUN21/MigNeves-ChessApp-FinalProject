package com.ironhack.game_service.dto;

import com.ironhack.game_service.dao.Game;
import com.ironhack.game_service.enums.EndResult;
import com.ironhack.game_service.enums.GameType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameCreatedDTO {
    private Long id;
    private GameType gameType;
    private Long whitePiecesPlayerId;
    private Long blackPiecesPlayerId;
    private String whitePassword;
    private String blackPassword;
    private Boolean whiteOwner;

    public static GameCreatedDTO GameToGameCreatedDTO(Game game) {
        return new GameCreatedDTO(
                game.getId(),
                game.getGameType(),
                game.getWhitePiecesPlayerId(),
                game.getBlackPiecesPlayerId(),
                game.getWhitePassword(),
                game.getBlackPassword(),
                game.getWhiteOwner()
        );
    }
}
