package com.ironhack.edge_service.dto;

import com.ironhack.edge_service.enums.EndResult;
import com.ironhack.edge_service.enums.GameType;
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
    private GameType gameType;
    private Long whitePiecesPlayerId;
    private Long blackPiecesPlayerId;
    private Integer numberMoves;
    private EndResult result;
    private LocalDateTime startDate;
}
