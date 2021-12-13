package com.ironhack.edge_service.dto;

import com.ironhack.edge_service.enums.EndResult;
import com.ironhack.edge_service.enums.GameType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
