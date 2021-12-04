package com.ironhack.moves_service.utils;

import com.ironhack.moves_service.dao.Move;
import com.ironhack.moves_service.dto.MoveDTO;
import com.ironhack.moves_service.enums.EndResult;
import com.ironhack.moves_service.repository.MoveRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
public class Validator {

    private final MoveRepository moveRepository;

    public Validator(MoveRepository moveRepository) {
        this.moveRepository = moveRepository;
    }

    public void validateMove(MoveDTO moveDTO) {
        List<Move> moves = moveRepository.findByGameIdOrderByIdAsc(moveDTO.getGameId());
        try {
            validateColor(moveDTO, moves.size());
            if (moves.size() >= 1) {
                validateGameEnded(moves.get(moves.size() - 1));
            }
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatus(), e.getMessage());
        }
    }

    private void validateColor(MoveDTO moveDTO, long numHalfMoves) {
        if (numHalfMoves % 2 == 0) {
            if (moveDTO.getPiece().toString().startsWith("BLACK_")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong, white to move!");
            }
        } else {
            if (moveDTO.getPiece().toString().startsWith("WHITE_")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong, black to move!");
            }
        }
    }

    private void validateGameEnded(Move move) {
        if (!move.getResult().equals(EndResult.UNFINISHED)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The game has ended, no more moves allowed!");
        }
    }
}
