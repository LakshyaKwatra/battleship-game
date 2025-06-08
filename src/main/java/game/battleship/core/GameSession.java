package game.battleship.core;

import game.battleship.core.action.FiringManager;
import game.battleship.core.action.FleetManager;
import game.battleship.core.display.GameDisplayManager;
import game.battleship.enums.GameVisibilityMode;
import game.battleship.enums.GameState;
import game.battleship.model.*;
import game.battleship.strategy.score.ScoreStrategy;
import game.battleship.ui.GameTextRenderer;
import game.battleship.util.ShipAutoPlacer;
import game.battleship.validation.FiringValidator;

import java.util.*;

public class GameSession {

    private final String sessionId;
    private final int battlefieldSize;
    private final List<Player> players;

    private final Map<Player, Zone> playerZoneMap = new HashMap<>();
    private final Map<Coordinate, Player> coordinateOwnerMap = new HashMap<>();

    private final GameVisibilityMode gameVisibilityMode;

    private final TurnManager turnManager;
    private final FiringManager firingManager;
    private final GameStateManager gameStateManager;
    private final FleetManager fleetManager;
    private final PlayerStatsManager playerStatsManager;
    private final GameDisplayManager displayManager;

    public GameSession(String sessionId, int battlefieldSize, List<Player> players, GameVisibilityMode gameVisibilityMode, ScoreStrategy scoreStrategy) {
        this.sessionId = sessionId;
        this.battlefieldSize = (battlefieldSize / players.size()) * players.size();
        System.out.println(this.battlefieldSize);
        this.players = players;
        this.gameVisibilityMode = gameVisibilityMode;

        initializeZonesAndCoordinateMappings();
        this.turnManager = new TurnManager(players);
        this.firingManager = new FiringManager(playerZoneMap, new FiringValidator());
        this.fleetManager = new FleetManager(playerZoneMap, players, battlefieldSize);
        this.gameStateManager = new GameStateManager(playerZoneMap);
        this.playerStatsManager = new PlayerStatsManager(playerZoneMap, scoreStrategy);
        this.displayManager = new GameDisplayManager(this.battlefieldSize, gameVisibilityMode, playerZoneMap, coordinateOwnerMap, players);
    }

    private void initializeZonesAndCoordinateMappings() {
        int playerZoneWidth = battlefieldSize / players.size();
        int startX = 0;

        for (Player player : players) {
            int endX = startX + playerZoneWidth - 1;
            Zone zone = new Zone(battlefieldSize, startX, endX, player);
            playerZoneMap.put(player, zone);

            for (Coordinate coordinate : zone.getCoordinates()) {
                coordinateOwnerMap.put(coordinate, player);
            }

            startX = endX + 1;
        }
    }


    public Zone getZoneOfPlayer(Player player) {
        return playerZoneMap.get(player);
    }

    public Player getOwnerOfCoordinate(Coordinate coordinate) {
        return coordinateOwnerMap.get(coordinate);
    }

    public Zone getZoneOfCoordinate(Coordinate coordinate) {
        Player owner = coordinateOwnerMap.get(coordinate);
        return owner != null ? playerZoneMap.get(owner) : null;
    }


    public boolean addShip(String shipId, int shipSize, List<Coordinate> topLeftCoordinatesPerZone) {
        if (!gameStateManager.canAddShip()) {
            return false;
        }

        boolean shipAdded = fleetManager.addShipToAllZones(shipId, shipSize, topLeftCoordinatesPerZone);
        if (shipAdded) {
            playerStatsManager.updateNumberOfAliveShipsStatAllPlayers();
            gameStateManager.setGameState(GameState.READY_TO_PLAY);
        }
        return shipAdded;
    }

    public void addBotShipsToAllPlayers(int numberOfShips) {
        ShipAutoPlacer.autoPlaceShips(playerZoneMap, numberOfShips, battlefieldSize);
        playerStatsManager.updateNumberOfAliveShipsStatAllPlayers();
        gameStateManager.setGameState(GameState.READY_TO_PLAY);
    }

    public void viewBattleField() {
        displayManager.printBattlefield(turnManager.getCurrentPlayer());
    }

    public void start() {
        if (!gameStateManager.startGame()) return;

        while (!gameStateManager.isGameEnded()) {
            Player currentPlayer = turnManager.getCurrentPlayer();
            System.out.println("\nIt's " + currentPlayer.getName() + "'s turn.");
            displayManager.printBattlefield(currentPlayer);

            Coordinate target = currentPlayer
                    .getFiringStrategy()
                    .chooseTarget(currentPlayer, playerZoneMap);

            GameTextRenderer.printFireMessage(currentPlayer.getName(), target);

            FiringResult firingResult = firingManager.evaluateFiringResult(currentPlayer, target);

            GameTextRenderer.printFiringResultMessage(firingResult);

            if (!firingResult.isValidShot()) continue;

            playerStatsManager.updateStats(firingResult);
            GameTextRenderer.printSessionStats(players, playerStatsManager.getSessionStatsMap());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            gameStateManager.evaluateGameState();
            if (!gameStateManager.isGameEnded()) {
                moveToNextActivePlayer();
            }
        }

        displayManager.printBattlefield(gameStateManager.getWinner());
        GameTextRenderer.printGameResultMessage(gameStateManager.getWinner());
        GameTextRenderer.printSessionStats(players, playerStatsManager.getSessionStatsMap());
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

    public GameVisibilityMode getGameMode() {
        return gameVisibilityMode;
    }

    public Player getCurrentPlayer() {
        return turnManager.getCurrentPlayer();
    }

    public GameState getGameState() {
        return gameStateManager.getGameState();
    }

    public Map<Player, Zone> getPlayerZoneMap() {
        return playerZoneMap;
    }

    public Map<Coordinate, Player> getCoordinateOwnerMap() {
        return coordinateOwnerMap;
    }

    public void moveToNextActivePlayer() {
        do {
            turnManager.moveToNextPlayer();
        } while (playerZoneMap.get(turnManager.getCurrentPlayer()).isDefeated());
    }


}
