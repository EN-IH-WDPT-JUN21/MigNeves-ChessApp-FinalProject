package com.ironhack.game_service.repository;

import com.ironhack.game_service.dao.Game;
import com.ironhack.game_service.enums.EndResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByIdInOrderByStartDateDesc(List<Long> ids);
    List<Game> findByResultNotOrderByStartDateDesc(EndResult result);
}
