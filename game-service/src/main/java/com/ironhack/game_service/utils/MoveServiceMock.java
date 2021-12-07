package com.ironhack.game_service.utils;

import com.ironhack.game_service.dto.MoveDTO;
import com.ironhack.game_service.enums.EndResult;
import com.ironhack.game_service.enums.Piece;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MoveServiceMock {
    public List<MoveDTO> getMockMoves() {
        List<MoveDTO> moves = new ArrayList<>();
        moves.add(new MoveDTO(1L, 1L, "e2e4", Piece.WHITE_PAWN, false, false, EndResult.UNFINISHED));
        moves.add(new MoveDTO(2L, 1L, "e7e5", Piece.BLACK_PAWN, false, false, EndResult.UNFINISHED));
        moves.add(new MoveDTO(3L, 1L, "e1e2", Piece.WHITE_KING, false, false, EndResult.UNFINISHED));
        moves.add(new MoveDTO(4L, 1L, "e8e7", Piece.BLACK_KING, false, false, EndResult.DRAW));
        return moves;
    }
}
