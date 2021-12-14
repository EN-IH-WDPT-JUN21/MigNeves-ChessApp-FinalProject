package com.ironhack.edge_service.dto;

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
}
