package com.ironhack.game_service.service;

import com.ironhack.game_service.dao.Game;
import com.ironhack.game_service.dto.*;
import com.ironhack.game_service.enums.EndResult;
import com.ironhack.game_service.proxy.MoveProxy;
import com.ironhack.game_service.repository.GameRepository;
import com.ironhack.game_service.utils.MoveServiceMock;
import com.ironhack.game_service.utils.Validator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final MoveProxy moveProxy;
    private final Validator validator;
    private final MoveServiceMock moveServiceMock;
    @Value("${running.test}")
    private boolean test;

    public GameService(GameRepository gameRepository, MoveProxy moveProxy, Validator validator, MoveServiceMock moveServiceMock) {
        this.gameRepository = gameRepository;
        this.moveProxy = moveProxy;
        this.validator = validator;
        this.moveServiceMock = moveServiceMock;
    }

    public List<SimplifiedGameDTO> getGamesFromUser(Long userId) {
        return this.gameRepository.findByWhitePiecesPlayerIdOrBlackPiecesPlayerIdOrderByStartDateDesc(userId, userId)
                .stream().map(SimplifiedGameDTO::GameToSimplifiedGameDTO)
                .collect(Collectors.toList());
    }

    public GameDTO getCompleteGameFromId(Long gameId, String password) {
        Optional<Game> game = this.gameRepository.findById(gameId);
        if (game.isPresent()) {
            boolean whiteColor = validator.validatePassword(password, game.get());
            GameDTO gameDTO = GameDTO.GameToGameDTO(game.get(), whiteColor);
            gameDTO.setMoves(validator.getValidatedMoves(gameId, game.get().getHalfMoves()));
            return gameDTO;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no game with id " + gameId);
        }
    }

    public GameCreatedDTO addGame(GameCreatedDTO gameDTO) {
        Game game;
        try {
            game = new Game(gameDTO.getGameType(), gameDTO.getWhitePiecesPlayerId(), gameDTO.getBlackPiecesPlayerId(), gameDTO.getWhiteOwner());
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatus(), e.getMessage());
        }
        gameRepository.save(game);
        return GameCreatedDTO.GameToGameCreatedDTO(game);
    }

    public GameDTO updateGame(GameDTO gameDTO) {
        try {
            Game game = validator.validateUpdate(gameDTO);
            boolean whiteColor = validator.validatePassword(gameDTO.getPassword(), game);
            List<MoveDTO> moves;
            if (!test) {
                moves = moveProxy.addMoveToGame(gameDTO.getMoveToAdd());
            } else {
                moves = moveServiceMock.getMockMoves();
            }
            if (moves.size() == game.getHalfMoves() + 1) {
                game.setHalfMoves(game.getHalfMoves() + 1);
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Moves saved and moves done do not match!");
            }
            game.setResult(gameDTO.getMoveToAdd().getResult());
            game.setFen(gameDTO.getFen());
            return GameDTO.GameToGameDTO(gameRepository.save(game), whiteColor, moves);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatus(), e.getMessage());
        }
    }

    public void deleteGame(Long gameID) {
        Optional<Game> game = gameRepository.findById(gameID);
        if (game.isPresent()) {
            try {
                if (!test) {
                    moveProxy.deleteMovesFromGame(gameID);
                }
                gameRepository.deleteById(gameID);
            } catch (ResponseStatusException e) {
                throw new ResponseStatusException(e.getStatus(), e.getMessage());
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no game with id " + gameID);
        }
    }

    public List<SimplifiedGameDTO> getGamesFromKeys(String[] keys) {
        Map<Long, String> passwords = new HashMap<>();
        List<Long> ids = new ArrayList<>();
        List<Game> games = new ArrayList<>();
        for (String key : keys) {
            String[] passwordComponents = key.split("-");
            if (passwordComponents.length == 2) {
                String password = passwordComponents[0];
                Long id;
                try {
                    id = Long.valueOf(passwordComponents[1]);
                } catch (NumberFormatException e) {
                    continue;
                }
                passwords.put(id, password);
                ids.add(id);
            }
            games = gameRepository.findByIdInOrderByStartDateDesc(ids);
            games.removeIf(game -> !game.getWhitePassword().equals(passwords.get(game.getId())) && !game.getBlackPassword().equals(passwords.get(game.getId())));
        }

        List<SimplifiedGameDTO> simplfiedGames = new ArrayList<>();
        for (Game game : games) {
            SimplifiedGameDTO simplfiedGame = SimplifiedGameDTO.GameToSimplifiedGameDTO(game);
            if ((game.getWhiteOwner() && game.getWhitePassword().equals(passwords.get(game.getId()))) ||
                    (!game.getWhiteOwner() && game.getBlackPassword().equals(passwords.get(game.getId())))) {
                simplfiedGame.setOwner(true);
            }
            simplfiedGames.add(simplfiedGame);
        }
        return simplfiedGames;
    }

    public FinishedGamesDTO getAllFinishedGames(int page) {
        List<Game> games = gameRepository.findByResultNotOrderByStartDateDesc(EndResult.UNFINISHED);
        int pages = games.size() % 10 == 0 ? games.size() / 10 : games.size() / 10 + 1;
        if (page > pages || page <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This page does not exist");
        }
        List<SimplifiedGameDTO> simplifiedGames = new ArrayList<>();
        for (int i = (page - 1) * 10; (i < (page-1)*10 + 10 && i < games.size()); i++){
            simplifiedGames.add(SimplifiedGameDTO.GameToSimplifiedGameDTO(games.get(i)));
        }
        return new FinishedGamesDTO(simplifiedGames, pages);
    }
}
