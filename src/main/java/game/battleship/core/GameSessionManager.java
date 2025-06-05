package game.battleship.core;

import game.battleship.repository.GameSessionRepository;

import java.util.List;

public class GameSessionManager {
    private final GameSessionRepository repository;

    public GameSessionManager(GameSessionRepository repository) {
        this.repository = repository;
    }

    public void saveSession(GameSession session) {
        System.out.println("Session created with session id: " + session.getSessionId());
        repository.save(session);
    }

    public GameSession getSession(String sessionId) {
        return repository.findById(sessionId);
    }

    public void deleteSession(String sessionId) {
        repository.delete(sessionId);
    }

    public List<GameSession> getAllSessions() {
        return List.copyOf(repository.findAll());
    }

    public GameSession getValidatedSession(String sessionId) {
        GameSession session = this.getSession(sessionId);
        if (session == null) {
            System.out.println("Game session not found.");
        }
        return session;
    }
}

