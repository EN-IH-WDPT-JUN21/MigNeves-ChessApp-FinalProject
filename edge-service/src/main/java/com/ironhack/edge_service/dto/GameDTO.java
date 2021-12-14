package com.ironhack.edge_service.dto;

import com.ironhack.edge_service.enums.EndResult;
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
public class GameDTO {
    private Long id;
    private EndResult result;
    private LocalDateTime startDate;
    private List<MoveDTO> moves;
    private MoveDTO moveToAdd;
    private String password;
    private boolean colorWhite;
}
