package com.ironhack.game_service.dao;

import com.ironhack.game_service.enums.EndResult;
import com.ironhack.game_service.enums.GameType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private GameType gameType;
    private Long whitePiecesPlayerId;
    private Long blackPiecesPlayerId;
    private Integer halfMoves;
    @Enumerated(EnumType.STRING)
    private EndResult result;
    private LocalDateTime startDate;
    private String whitePassword;
    private String blackPassword;
    private String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    private Boolean whiteOwner;

    public Game(GameType gameType, Long whitePiecesPlayerId, Long blackPiecesPlayerId, Boolean whiteOwner) {
        setGameType(gameType);
        setWhiteOwner(whiteOwner);
        switch (gameType) {
            case UNKNOWN_UNKNOWN -> {
                setWhitePiecesPlayerId(null);
                setBlackPiecesPlayerId(null);
            }
            case USER_USER -> {
                if (blackPiecesPlayerId != null && whitePiecesPlayerId != null) {
                    setWhitePiecesPlayerId(whitePiecesPlayerId);
                    setBlackPiecesPlayerId(blackPiecesPlayerId);
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A user to user game must be between two active users!");
                }
            }
            default -> {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no game type " + gameType.toString());
            }
        }
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
