package game.battleship.service;

import game.battleship.repository.GameSessionRepository;
import game.battleship.repository.InMemoryGameSessionRepository;
import game.battleship.ui.BattlefieldDisplay;
import game.battleship.core.GameSessionManager;
import game.battleship.enums.FiringStrategyType;
import game.battleship.enums.GameMode;
import game.battleship.factory.GameSessionFactory;
import game.battleship.model.Coordinate;
import game.battleship.core.GameSession;
import game.battleship.model.PlayerConfig;

import java.util.*;

public class GameService {
    private final GameSessionFactory gameSessionFactory;
    private final GameSessionManager sessionManager;

    public GameService() {
        this.gameSessionFactory = new GameSessionFactory(new Scanner(System.in));
        this.sessionManager = new GameSessionManager(new InMemoryGameSessionRepository());
    }

    public String initGame(int battlefieldSize, List<PlayerConfig> playerConfigs) {
        String sessionId = UUID.randomUUID().toString();
        if(battlefieldSize < playerConfigs.size()) {
            System.out.println("You need to have a bigger battlefield");
            return null;
        }
        GameSession session = gameSessionFactory.createGameSession(sessionId, battlefieldSize, playerConfigs);
        sessionManager.saveSession(session);
        return sessionId;
    }

    public String initBotGame(int battlefieldSize, int numberOfPlayers) {
        if(battlefieldSize < numberOfPlayers) {
            System.out.println("You need to have a bigger battlefield");
            return null;
        }
        String sessionId = UUID.randomUUID().toString();
        GameSession session = gameSessionFactory.createBotGameSession(sessionId,
                battlefieldSize,
                numberOfPlayers,
                FiringStrategyType.RANDOM);
        sessionManager.saveSession(session);
        return sessionId;
    }

    public void addShip(String sessionId, String shipId, int size, List<Coordinate> topLeftCoordinates) {
        GameSession gameSession = sessionManager.getValidatedSession(sessionId);

        if (gameSession == null) {
            return;
        }
        gameSession.addShip(shipId, size, topLeftCoordinates);
    }

    public void startGame(String sessionId) {
        GameSession gameSession = sessionManager.getValidatedSession(sessionId);
        if (gameSession == null) {
            return;
        }
        gameSession.start();
        sessionManager.deleteSession(sessionId);
    }

    public void viewBattlefield(String sessionId) {
        GameSession gameSession = sessionManager.getValidatedSession(sessionId);
        if(gameSession == null) {
            return;
        }

        BattlefieldDisplay.printBoard(gameSession);
    }

    public void setGameMode(String sessionId, GameMode gameMode) {
        GameSession gameSession = sessionManager.getValidatedSession(sessionId);
        if(gameSession == null) {
            return;
        }
        gameSession.setGameMode(gameMode);
    }


}
