package com.ironhack.game_service.dao;

import com.ironhack.game_service.enums.EndResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang.RandomStringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer halfMoves;
    @Enumerated(EnumType.STRING)
    private EndResult result;
    private LocalDateTime startDate;
    private String whitePassword;
    private String blackPassword;
    private Boolean whiteOwner;

    public Game(Boolean whiteOwner) {
        setWhiteOwner(whiteOwner);
        setHalfMoves(0);
        setResult(EndResult.UNFINISHED);
        setStartDate(LocalDateTime.now(ZoneId.of("Europe/London")));
        setWhitePassword(RandomStringUtils.randomAlphanumeric(10));
        String blackPassword = RandomStringUtils.randomAlphanumeric(10);
        while (blackPassword.equals(getWhitePassword())) {
            blackPassword = RandomStringUtils.randomAlphanumeric(10);
        }
        setBlackPassword(blackPassword);
    }
}
