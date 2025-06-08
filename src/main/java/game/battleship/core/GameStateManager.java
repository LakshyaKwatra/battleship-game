package game.battleship.core;

import game.battleship.enums.GameState;
import game.battleship.model.Player;
import game.battleship.model.Zone;
import game.battleship.ui.GameTextRenderer;

import java.util.Map;

public class GameStateManager {
    private GameState state = GameState.INITIALIZED;
    private Player winner = null;
    private final Map<Player, Zone> playerZoneMap;

    public GameStateManager(Map<Player, Zone> playerZoneMap) {
        this.playerZoneMap = playerZoneMap;
    }

    public boolean startGame() {
        if (state == GameState.READY_TO_PLAY) {
            state = GameState.IN_PROGRESS;
            GameTextRenderer.printGameTitle();
            return true;
        }

        switch (state) {
            case INITIALIZED -> System.out.println("Please add ships to start playing.");
            case IN_PROGRESS -> System.out.println("Game is already in progress.");
            case ENDED -> System.out.println("Game has already ended.");
            default -> System.out.println("Unknown game state.");
        }
        return false;
    }

    public void evaluateGameState() {
        int aliveCount = 0;
        Player lastAlive = null;

        for (Player player : playerZoneMap.keySet()) {
            Zone zone = playerZoneMap.get(player);
            if (zone != null && !zone.isDefeated()) {
                aliveCount++;
                lastAlive = player;
            }
        }

        if (aliveCount == 1) {
            state = GameState.ENDED;
            winner = lastAlive;
        } else if (aliveCount == 0) {
            state = GameState.ENDED;
            winner = null; // Draw
        } else {
            state = GameState.IN_PROGRESS;
            winner = null;
        }
    }


    public boolean isGameEnded() {
        return state == GameState.ENDED;
    }

    public GameState getGameState() {
        return state;
    }

    public void setGameState(GameState gameState) {
        this.state = gameState;
    }

    public Player getWinner() {
        return winner;
    }

    public boolean canAddShip() {
        boolean canAddShip = state == GameState.INITIALIZED || state == GameState.READY_TO_PLAY;
        if (!canAddShip) {
            System.out.println("Ship can only be added if game is initialized or ready to play");
        }
        return canAddShip;
    }
}
