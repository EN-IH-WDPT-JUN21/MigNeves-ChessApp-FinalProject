package com.ironhack.game_service.utils;

import com.ironhack.game_service.dao.Game;
import com.ironhack.game_service.dto.GameDTO;
import com.ironhack.game_service.dto.MoveDTO;
import com.ironhack.game_service.enums.EndResult;
import com.ironhack.game_service.enums.GameType;
import com.ironhack.game_service.proxy.MoveProxy;
import com.ironhack.game_service.repository.GameRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Component
public class Validator {

    private final MoveProxy moveProxy;
    private final GameRepository gameRepository;

    public Validator(MoveProxy moveProxy, GameRepository gameRepository) {
        this.moveProxy = moveProxy;
        this.gameRepository = gameRepository;
    }

    public List<MoveDTO> getValidatedMoves(Long gameId, int halfMoves) {
        try {
            List<MoveDTO> moves = this.moveProxy.getMovesFromGame(gameId);
            if (moves.size() != halfMoves) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Number of moves does not match!");
            } else {
                return moves;
            }
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatus(), e.getMessage());
        }
    }

    public Game validateUpdate(GameDTO gameDTO) {
        Optional<Game> game = gameRepository.findById(gameDTO.getId());
        if (game.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no game with id " + gameDTO.getId());
        }
        if (gameDTO.getMoveToAdd() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Game update must have a move to add");
        }
        if (!gameDTO.getMoveToAdd().getGameId().equals(gameDTO.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Move added to incorrect game");
        }
        if (!game.get().getResult().equals(EndResult.UNFINISHED)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The game has ended, no more moves allowed!");
        }
        return game.get();
    }
}
