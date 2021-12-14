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

    // Method to get a complete game from the id
    public GameDTO getCompleteGameFromId(Long gameId, String password) {
        Optional<Game> game = this.gameRepository.findById(gameId);
        // If the game exists check whether the password is valid, check if moves are in sync and return
        if (game.isPresent()) {
            boolean whiteColor = validator.validatePassword(password, game.get());
            List<MoveDTO> moves = validator.getValidatedMoves(gameId, game.get().getHalfMoves());
            return GameDTO.GameToGameDTO(game.get(), whiteColor, moves);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no game with id " + gameId);
        }
    }

    // Method to create a new game
    public GameCreatedDTO addGame(GameCreatedDTO gameDTO) {
        // Create game and assign the ownership to white or black (frontend dependent)
        Game game = new Game(gameDTO.getWhiteOwner());
        gameRepository.save(game);
        return GameCreatedDTO.GameToGameCreatedDTO(game);
    }

    // Method to update a game
    public GameDTO updateGame(GameDTO gameDTO) {
        try {
            // Check whether it is a valid update and the password is valid
            Game game = validator.validateUpdate(gameDTO);
            boolean whiteColor = validator.validatePassword(gameDTO.getPassword(), game);
            List<MoveDTO> moves;
            // Fetch the moves from move-service
            if (!test) {
                moves = moveProxy.addMoveToGame(gameDTO.getMoveToAdd());
            } else {
                moves = moveServiceMock.getMockMoves();
            }
            // Check whether moves are in sync with the game
            if (moves.size() == game.getHalfMoves() + 1) {
                game.setHalfMoves(game.getHalfMoves() + 1);
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Moves saved and moves done do not match!");
            }
            // Update game result
            game.setResult(gameDTO.getMoveToAdd().getResult());
            return GameDTO.GameToGameDTO(gameRepository.save(game), whiteColor, moves);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatus(), e.getMessage());
        }
    }

    // Method to delete a game
    public void deleteGame(Long gameID) {
        Optional<Game> game = gameRepository.findById(gameID);
        // Delete all moves and then if successful delete game
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

    // Get all games from keys
    public List<SimplifiedGameDTO> getGamesFromKeys(String[] keys) {
        Map<Long, String> passwords = new HashMap<>();
        List<Long> ids = new ArrayList<>();
        List<Game> games = new ArrayList<>();

        // for each key split the key in the password and id
        // ignore invalid keys
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
        }
        // Get games from valid keys and remove if passwords do not match
        games = gameRepository.findByIdInOrderByStartDateDesc(ids);
        games.removeIf(game -> !game.getWhitePassword().equals(passwords.get(game.getId())) && !game.getBlackPassword().equals(passwords.get(game.getId())));

        // Get latest fen position from move-service and add to SimplifiedGame as well as the ownership
        List<SimplifiedGameDTO> simplifiedGames = new ArrayList<>();
        for (Game game : games) {
            String fen = moveProxy.getLastFenElement(game.getId()).getFen();
            SimplifiedGameDTO simplifiedGame = SimplifiedGameDTO.GameToSimplifiedGameDTO(game, fen);
            if ((game.getWhiteOwner() && game.getWhitePassword().equals(passwords.get(game.getId()))) ||
                    (!game.getWhiteOwner() && game.getBlackPassword().equals(passwords.get(game.getId())))) {
                simplifiedGame.setOwner(true);
            }
            simplifiedGames.add(simplifiedGame);
        }
        return simplifiedGames;
    }

    // Get all finished games
    public FinishedGamesDTO getAllFinishedGames(int page) {
        // Get all games that have finished and divide them into pages of 10 games
        List<Game> games = gameRepository.findByResultNotOrderByStartDateDesc(EndResult.UNFINISHED);
        int pages = Math.max(games.size() % 10 == 0 ? games.size() / 10 : games.size() / 10 + 1, 1);
        if (page > pages || page <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This page does not exist");
        }
        List<SimplifiedGameDTO> simplifiedGames = new ArrayList<>();
        for (int i = (page - 1) * 10; (i < (page - 1) * 10 + 10 && i < games.size()); i++) {
            String fen = moveProxy.getLastFenElement(games.get(i).getId()).getFen();
            simplifiedGames.add(SimplifiedGameDTO.GameToSimplifiedGameDTO(games.get(i), fen));
        }
        return new FinishedGamesDTO(simplifiedGames, pages);
    }
}
