package com.ironhack.moves_service.service;

import com.ironhack.moves_service.dao.Move;
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

    public List<MoveDTO> getMovesFromGame(Long gameId) {
        return moveRepository.findByGameIdOrderByIdAsc(gameId)
                .stream().map(MoveDTO::moveToMoveDTO)
                .collect(Collectors.toList());
    }

    public List<MoveDTO> addMoveToGame(MoveDTO moveDTO) {
        try {
            validator.validateMove(moveDTO);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatus(), e.getMessage());
        }
        moveRepository.save(new Move(moveDTO));
        return moveRepository.findByGameIdOrderByIdAsc(moveDTO.getGameId())
                .stream().map(MoveDTO::moveToMoveDTO)
                .collect(Collectors.toList());
    }

    public void deleteMovesFromGame(Long gameId) {
        List<Move> moves = this.moveRepository.findByGameIdOrderByIdAsc(gameId);
        this.moveRepository.deleteAll(moves);
    }

}
