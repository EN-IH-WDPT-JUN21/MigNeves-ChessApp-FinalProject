package com.ironhack.drawservice.utils;

import com.ironhack.drawservice.dto.MoveDTO;
import com.ironhack.drawservice.enums.EndResult;
import com.ironhack.drawservice.enums.Piece;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MoveServiceMock {
    public List<MoveDTO> getMockMoves() {
        List<MoveDTO> moves = new ArrayList<>();
        moves.add(new MoveDTO(1L, 1L, "d2d3", Piece.WHITE_PAWN, false, false, EndResult.UNFINISHED, "rnbqkbnr/pppppppp/8/8/8/3P4/PPP1PPPP/RNBQKBNR b KQkq - 0 1"));
        moves.add(new MoveDTO(2L, 1L, "d7d6", Piece.BLACK_PAWN, false, false, EndResult.UNFINISHED, "rnbqkbnr/ppp1pppp/3p4/8/8/3P4/PPP1PPPP/RNBQKBNR w KQkq - 0 2"));
        moves.add(new MoveDTO(3L, 1L, "c1d2", Piece.WHITE_BISHOP, false, false, EndResult.UNFINISHED, "rnbqkbnr/ppp1pppp/3p4/8/8/3P4/PPPBPPPP/RN1QKBNR b KQkq - 1 2"));
        moves.add(new MoveDTO(4L, 1L, "c8d7", Piece.BLACK_BISHOP, false, false, EndResult.UNFINISHED, "rn1qkbnr/pppbpppp/3p4/8/8/3P4/PPPBPPPP/RN1QKBNR w KQkq - 2 3"));
        moves.add(new MoveDTO(5L, 1L, "d2c1", Piece.WHITE_BISHOP, false, false, EndResult.UNFINISHED, "rn1qkbnr/pppbpppp/3p4/8/8/3P4/PPP1PPPP/RNBQKBNR b KQkq - 3 3"));
        moves.add(new MoveDTO(6L, 1L, "d7c8", Piece.BLACK_BISHOP, false, false, EndResult.UNFINISHED, "rnbqkbnr/ppp1pppp/3p4/8/8/3P4/PPP1PPPP/RNBQKBNR w KQkq - 4 4"));
        moves.add(new MoveDTO(7L, 1L, "c1d2", Piece.WHITE_BISHOP, false, false, EndResult.UNFINISHED, "rnbqkbnr/ppp1pppp/3p4/8/8/3P4/PPPBPPPP/RN1QKBNR b KQkq - 5 4"));
        moves.add(new MoveDTO(8L, 1L, "c8d7", Piece.BLACK_BISHOP, false, false, EndResult.UNFINISHED, "rn1qkbnr/pppbpppp/3p4/8/8/3P4/PPPBPPPP/RN1QKBNR w KQkq - 6 5"));
        moves.add(new MoveDTO(9L, 1L, "d2c1", Piece.WHITE_BISHOP, false, false, EndResult.UNFINISHED, "rn1qkbnr/pppbpppp/3p4/8/8/3P4/PPP1PPPP/RNBQKBNR b KQkq - 7 5"));
        return moves;
    }
}