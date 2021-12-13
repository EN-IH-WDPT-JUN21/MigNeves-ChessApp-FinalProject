package com.ironhack.game_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FinishedGamesDTO {
    List<SimplifiedGameDTO> games;
    int pages;
}
