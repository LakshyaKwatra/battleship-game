package game.battleship.repository;

import game.battleship.core.GameSession;

import java.util.List;

public interface GameSessionRepository {

    void save(GameSession session);

    GameSession findById(String sessionId);

    void delete(String sessionId);

    List<GameSession> findAll();
}