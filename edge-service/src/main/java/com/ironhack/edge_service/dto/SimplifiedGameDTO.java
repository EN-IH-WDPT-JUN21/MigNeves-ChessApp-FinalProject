package com.ironhack.edge_service.dto;

import com.ironhack.edge_service.enums.EndResult;
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
}
