package com.ironhack.game_service.dto;

import com.ironhack.game_service.dao.Game;
import com.ironhack.game_service.enums.EndResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameDTO {
    private Long id;
    private EndResult result;
    private LocalDateTime startDate;
    private List<MoveDTO> moves;
    private MoveDTO moveToAdd;
    private String password;
    private boolean colorWhite;

    public static GameDTO GameToGameDTO(Game game, boolean color, String fen) {
        return GameToGameDTO(game, color, new ArrayList<>());
    }

    public static GameDTO GameToGameDTO(Game game, boolean color, List<MoveDTO> moves) {
        return new GameDTO(
                game.getId(),
                game.getResult(),
                game.getStartDate(),
                moves,
                null,
                null,
                color
        );
    }
}
