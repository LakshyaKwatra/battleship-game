package game.battleship.repository;

import game.battleship.core.GameSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryGameSessionRepository implements GameSessionRepository {
    private final Map<String, GameSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void save(GameSession session) {
        sessions.put(session.getSessionId(), session);
    }

    @Override
    public GameSession findById(String sessionId) {
        return sessions.get(sessionId);
    }

    @Override
    public void delete(String sessionId) {
        sessions.remove(sessionId);
    }

    @Override
    public List<GameSession> findAll() {
        return new ArrayList<>(sessions.values());
    }
}
