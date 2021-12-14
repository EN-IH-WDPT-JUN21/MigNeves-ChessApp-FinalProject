package com.ironhack.moves_service.service;

import com.ironhack.moves_service.dao.Move;
import com.ironhack.moves_service.dto.FenDTO;
import com.ironhack.moves_service.dto.MoveDTO;
import com.ironhack.moves_service.repository.MoveRepository;
import com.ironhack.moves_service.utils.Validator;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MoveService {

    private final MoveRepository moveRepository;
    private final Validator validator;

    public MoveService(MoveRepository moveRepository, Validator validator) {
        this.moveRepository = moveRepository;
        this.validator = validator;
    }

    // Method to get all moves from a game ordered from first to last move
    public List<MoveDTO> getMovesFromGame(Long gameId) {
        return moveRepository.findByGameIdOrderByIdAsc(gameId)
                .stream().map(MoveDTO::moveToMoveDTO)
                .collect(Collectors.toList());
    }

    // Method to add a move to a game
    public List<MoveDTO> addMoveToGame(MoveDTO moveDTO) {
        try {
            // Validate move
            validator.validateMove(moveDTO);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatus(), e.getMessage());
        }
        // Save valid move and return
        moveRepository.save(new Move(moveDTO));
        return moveRepository.findByGameIdOrderByIdAsc(moveDTO.getGameId())
                .stream().map(MoveDTO::moveToMoveDTO)
                .collect(Collectors.toList());
    }

    // Delete all moves from a game
    public void deleteMovesFromGame(Long gameId) {
        List<Move> moves = this.moveRepository.findByGameIdOrderByIdAsc(gameId);
        this.moveRepository.deleteAll(moves);
    }

    // Get last fen position
    public FenDTO getLastFen(Long gameId) {
        List<Move> moves = this.moveRepository.findByGameIdOrderByIdAsc(gameId);
        if (moves.size() >= 1) {
            return new FenDTO(moves.get(moves.size() - 1).getFen());
        } else  {
            return new FenDTO(null);
        }
    }

}
