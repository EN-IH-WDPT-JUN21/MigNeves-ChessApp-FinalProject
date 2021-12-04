package com.ironhack.game_service.repository;

import com.ironhack.game_service.dao.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByWhitePiecesPlayerIdOrBlackPiecesPlayerIdOrderByStartDateDesc(Long player1, Long player2);
}
