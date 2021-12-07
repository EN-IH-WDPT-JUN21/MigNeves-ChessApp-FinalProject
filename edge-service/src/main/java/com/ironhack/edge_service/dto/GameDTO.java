package com.ironhack.edge_service.dto;

import com.ironhack.edge_service.enums.EndResult;
import com.ironhack.edge_service.enums.GameType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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
}
