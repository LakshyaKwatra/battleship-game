package game.battleship.core;

import game.battleship.enums.GameState;
import game.battleship.model.Player;

import java.util.List;

public class GameStateManager {
    private GameState state = GameState.INITIALIZED;
    private Player winner = null;

    public boolean startGame() {
        switch (state) {
            case READY_TO_PLAY:
                state = GameState.IN_PROGRESS;
                System.out.println("Game started.");
                return true;

            case INITIALIZED:
                System.out.println("Please add ships to start playing.");
                break;

            case IN_PROGRESS:
                System.out.println("Game is already in progress.");
                break;

            case ENDED:
                System.out.println("Game has already ended.");
                break;

            default:
                System.out.println("Unknown game state.");
        }
        return false;
    }

    public void evaluateGameState(List<Player> players) {
        int aliveCount = 0;
        Player lastAlive = null;

        for (Player player : players) {
            boolean hasUndestroyedShips = player.getZone().hasUndestroyedShips();
            if (hasUndestroyedShips) {
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
        if(!canAddShip) {
            System.out.println("Ship can only be added if game is initialized or ready to play");
            return false;
        }
        return true;
    }
}
