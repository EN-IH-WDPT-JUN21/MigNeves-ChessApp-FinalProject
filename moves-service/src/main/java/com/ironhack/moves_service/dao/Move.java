package com.ironhack.moves_service.dao;

import com.ironhack.moves_service.dto.MoveDTO;
import com.ironhack.moves_service.enums.EndResult;
import com.ironhack.moves_service.enums.Piece;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "chess_move")
public class Move {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long gameId;
    private String move;
    @Enumerated(EnumType.STRING)
    private Piece piece;
    private Boolean capture;
    private Boolean inCheck;
    @Enumerated(EnumType.STRING)
    private EndResult result;
    private String fen;

    public Move(Long gameId, String move, Piece piece, boolean capture, boolean check, EndResult result, String fen) {
        setGameId(gameId);
        setMove(move);
        setPiece(piece);
        setCapture(capture);
        setInCheck(check);
        setResult(result);
        setFen(fen);
    }

    public Move(MoveDTO moveDTO) {
        setGameId(moveDTO.getGameId());
        setMove(moveDTO.getMove());
        setPiece(moveDTO.getPiece());
        setCapture(moveDTO.getCapture());
        setInCheck(moveDTO.getInCheck());
        setResult(moveDTO.getResult());
        setFen(moveDTO.getFen());
    }
}
