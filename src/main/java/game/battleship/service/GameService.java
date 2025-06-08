package game.battleship.service;

import game.battleship.enums.GameVisibilityMode;
import game.battleship.model.*;
import game.battleship.repository.InMemoryGameSessionRepository;
import game.battleship.core.GameSessionManager;
import game.battleship.enums.FiringStrategyType;
import game.battleship.factory.GameSessionFactory;
import game.battleship.core.GameSession;

import java.util.*;

public class GameService {
    private final GameSessionFactory gameSessionFactory;
    private final GameSessionManager sessionManager;

    public GameService() {
        this.gameSessionFactory = new GameSessionFactory(new Scanner(System.in));
        this.sessionManager = new GameSessionManager(new InMemoryGameSessionRepository());
    }

    public String initGame(int battlefieldSize, List<PlayerConfig> playerConfigs, GameVisibilityMode gameVisibilityMode) {
        String sessionId = UUID.randomUUID().toString();
        if(battlefieldSize < playerConfigs.size()) {
            System.out.println("You need to have a bigger battlefield");
            return null;
        }
        GameSession session = gameSessionFactory.createGameSession(sessionId, battlefieldSize, playerConfigs, gameVisibilityMode);
        sessionManager.saveSession(session);
        return sessionId;
    }

    public String initBotGame(int battlefieldSize, int numberOfPlayers, GameVisibilityMode gameVisibilityMode) {
        if(battlefieldSize < numberOfPlayers) {
            System.out.println("You need to have a bigger battlefield");
            return null;
        }
        String sessionId = UUID.randomUUID().toString();
        GameSession session = gameSessionFactory.createBotGameSession(sessionId,
                battlefieldSize,
                numberOfPlayers,
                FiringStrategyType.RANDOMIZED_BOT,
                gameVisibilityMode);
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

        gameSession.viewBattleField();
    }

    public void addBotShipsToAllPlayers(String sessionId, int numberOfShips) {
        GameSession gameSession = sessionManager.getValidatedSession(sessionId);
        if(gameSession == null) {
            return;
        }
        gameSession.addBotShipsToAllPlayers(numberOfShips);
    }

    public void simulateBotGame(int battlefieldSize, int numberOfPlayers, int numberOfShips, GameVisibilityMode gameVisibilityMode) {
        String sessionId = initBotGame(battlefieldSize, numberOfPlayers, gameVisibilityMode);
        GameSession gameSession = sessionManager.getValidatedSession(sessionId);
        if(gameSession == null) {
            return;
        }
        gameSession.addBotShipsToAllPlayers(numberOfShips);
        gameSession.start();
    }


}
