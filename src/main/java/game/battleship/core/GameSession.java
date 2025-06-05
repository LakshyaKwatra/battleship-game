package game.battleship.core;

import game.battleship.enums.GameMode;
import game.battleship.enums.GameState;
import game.battleship.model.Coordinate;
import game.battleship.model.FiringResult;
import game.battleship.model.Player;
import game.battleship.ui.BattlefieldDisplay;

import java.util.*;

public class GameSession {
    private final String sessionId;
    private final int battlefieldSize;
    private final List<Player> players;
    private GameMode gameMode;

    private final TurnManager turnManager;
    private final FiringManager firingManager;
    private final GameStateManager gameStateManager;
    private final FleetManager fleetManager;
    private final ScoreManager scoreManager;

    public GameSession(String sessionId, int battlefieldSize, List<Player> players) {
        this.sessionId = sessionId;
        this.battlefieldSize = battlefieldSize;
        this.players = players;
        this.turnManager = new TurnManager(players);
        this.firingManager = new FiringManager();
        this.gameStateManager = new GameStateManager();
        this.fleetManager = new FleetManager();
        this.scoreManager = new ScoreManager();
        this.gameMode = GameMode.FULL_VISIBILITY;
        players.forEach(player -> scoreManager.registerPlayer(player.getId()));
    }

    public String getSessionId() {
        return sessionId;
    }

    public int getBattlefieldSize() {
        return battlefieldSize;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public Player getCurrentPlayer() {
        return turnManager.getCurrentPlayer();
    }

    public GameState getGameState() {
        return gameStateManager.getGameState();
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public boolean addShip(String shipId, int shipSize, List<Coordinate> coordinatesPerPlayer) {
        if(!gameStateManager.canAddShip()) {
            return false;
        }
        boolean shipAdded = fleetManager.addShipToAllPlayers(shipId, shipSize, coordinatesPerPlayer, players, battlefieldSize);
        if (shipAdded) {
           gameStateManager.setGameState(GameState.READY_TO_PLAY);
        }
        return shipAdded;
    }



    public void start() {
        if(!gameStateManager.startGame()) {
            return;
        }
        BattlefieldDisplay.printBoard(this);

        while (!gameStateManager.isGameEnded()) {
            Player currentPlayer = turnManager.getCurrentPlayer();

            System.out.println("\nIt's " + currentPlayer.getName() + "'s turn.");
            Coordinate target = currentPlayer
                    .getFiringStrategy()
                    .chooseTarget(currentPlayer, this, firingManager.getFiredCoordinates());

            FiringResult firingResult = firingManager
                    .evaluateFiringResult(currentPlayer, target, players, battlefieldSize);

            System.out.println(firingResult.getMessage());

            if(!firingResult.isValidShot()) {
                continue;
            }

            scoreManager.update(currentPlayer.getId(), firingResult);
            BattlefieldDisplay.printBoard(this);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            gameStateManager.evaluateGameState(players);
            if (!gameStateManager.isGameEnded()) {
                turnManager.moveToNextPlayer();
            }
        }

        announceWinner();
        scoreManager.printStats(players);
    }


    private void announceWinner() {
        Player winner = gameStateManager.getWinner();
        if (winner != null) {
            System.out.println("\nGame Over! Winner: " + winner.getName());
        } else {
            System.out.println("\nGame Over! It's a draw.");
        }
    }

}

