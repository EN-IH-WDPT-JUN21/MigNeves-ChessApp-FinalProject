package com.ironhack.game_service.utils;

import com.ironhack.game_service.dao.Game;
import com.ironhack.game_service.dto.GameDTO;
import com.ironhack.game_service.dto.MoveDTO;
import com.ironhack.game_service.enums.EndResult;
import com.ironhack.game_service.proxy.MoveProxy;
import com.ironhack.game_service.repository.GameRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Component
public class Validator {

    private final MoveProxy moveProxy;
    private final GameRepository gameRepository;
    private final MoveServiceMock moveServiceMock;
    @Value("${running.test}")
    private boolean test;

    public Validator(MoveProxy moveProxy, GameRepository gameRepository, MoveServiceMock moveServiceMock) {
        this.moveProxy = moveProxy;
        this.gameRepository = gameRepository;
        this.moveServiceMock = moveServiceMock;
    }

    public List<MoveDTO> getValidatedMoves(Long gameId, int halfMoves) {
        try {
            List<MoveDTO> moves;
            if (!test) {
                moves = this.moveProxy.getMovesFromGame(gameId);
            } else {
                moves = moveServiceMock.getMockMoves();
            }
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

    public boolean validatePassword(String password, Game game) {
        if (password.equals(game.getWhitePassword())) {
            return true;
        } else if (password.equals(game.getBlackPassword())) {
            return false;
        } else {
            System.out.println(password + " " + game.getWhitePassword());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The password doesn't match");
        }
    }
}
