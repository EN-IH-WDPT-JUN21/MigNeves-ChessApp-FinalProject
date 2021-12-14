package com.ironhack.game_service.dto;

import com.ironhack.game_service.dao.Game;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameCreatedDTO {
    private Long id;
    private String whitePassword;
    private String blackPassword;
    private Boolean whiteOwner;

    public static GameCreatedDTO GameToGameCreatedDTO(Game game) {
        return new GameCreatedDTO(
                game.getId(),
                game.getWhitePassword(),
                game.getBlackPassword(),
                game.getWhiteOwner()
        );
    }
}
