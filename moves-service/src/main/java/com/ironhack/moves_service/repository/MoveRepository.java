package com.ironhack.moves_service.repository;

import com.ironhack.moves_service.dao.Move;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoveRepository extends JpaRepository<Move, Long> {
    List<Move> findByGameIdOrderByIdAsc(Long gameId);
}
