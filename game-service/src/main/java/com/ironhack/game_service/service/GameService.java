package com.ironhack.game_service.service;

import com.ironhack.game_service.dao.Game;
import com.ironhack.game_service.dto.GameDTO;
import com.ironhack.game_service.dto.MoveDTO;
import com.ironhack.game_service.dto.SimplifiedGameDTO;
import com.ironhack.game_service.proxy.MoveProxy;
import com.ironhack.game_service.repository.GameRepository;
import com.ironhack.game_service.utils.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final MoveProxy moveProxy;
    private final Validator validator;

    public GameService(GameRepository gameRepository, MoveProxy moveProxy, Validator validator) {
        this.gameRepository = gameRepository;
        this.moveProxy = moveProxy;
        this.validator = validator;
    }

    public List<SimplifiedGameDTO> getGamesFromUser(Long userId) {
        return this.gameRepository.findByWhitePiecesPlayerIdOrBlackPiecesPlayerIdOrderByStartDateDesc(userId, userId)
                .stream().map(SimplifiedGameDTO::GameToSimplifiedGameDTO)
                .collect(Collectors.toList());
    }

    public GameDTO getCompleteGameFromId(Long gameId) {
        Optional<Game> game = this.gameRepository.findById(gameId);
        if (game.isPresent()) {
            GameDTO gameDTO = GameDTO.GameToGameDTO(game.get());
            gameDTO.setMoves(validator.getValidatedMoves(gameId, game.get().getHalfMoves()));
            return gameDTO;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no game with id " + gameId);
        }
    }

    public SimplifiedGameDTO addGame(GameDTO gameDTO) {
        Game game;
        try {
            game = new Game(gameDTO.getGameType(), gameDTO.getWhitePiecesPlayerId(), gameDTO.getBlackPiecesPlayerId());
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatus(), e.getMessage());
        }
        gameRepository.save(game);
        return SimplifiedGameDTO.GameToSimplifiedGameDTO(game);
    }

    public GameDTO updateGame(GameDTO gameDTO) {
        Game game = validator.validateUpdate(gameDTO);
        try {
            List<MoveDTO> moves = moveProxy.addMoveToGame(gameDTO.getMoveToAdd());
            if (moves.size() == game.getHalfMoves() + 1) {
                game.setHalfMoves(game.getHalfMoves() + 1);
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Moves saved and moves done do not match!");
            }
            game.setResult(gameDTO.getMoveToAdd().getResult());
            return GameDTO.GameToGameDTO(gameRepository.save(game));
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatus(), e.getMessage());
        }
    }

    public void deleteGame(Long gameID) {
        Optional<Game> game = gameRepository.findById(gameID);
        if (game.isPresent()) {
            try {
                moveProxy.deleteMovesFromGame(gameID);
                gameRepository.deleteById(gameID);
            } catch (ResponseStatusException e) {
                throw new ResponseStatusException(e.getStatus(), e.getMessage());
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no game with id " + gameID);
        }
    }
}
