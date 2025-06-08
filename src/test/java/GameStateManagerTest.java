import game.battleship.core.GameStateManager;
import game.battleship.core.action.FleetManager;
import game.battleship.enums.GameState;
import game.battleship.model.Coordinate;
import game.battleship.model.Player;
import game.battleship.model.PlayerConfig;
import game.battleship.model.Zone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GameStateManagerTest {

    private Player player1;
    private Player player2;
    private Zone zone1;
    private Zone zone2;
    private Map<Player, Zone> playerZoneMap;
    private GameStateManager manager;
    private FleetManager fleetManager;

    @BeforeEach
    void setup() {
        player1 = new Player("p1", new PlayerConfig("Lakshya", null), null);
        player2 = new Player("p2", new PlayerConfig("Angel", null), null);

        int battlefieldSize = 10;

        zone1 = new Zone(battlefieldSize, 0, 4, player1);
        zone2 = new Zone(battlefieldSize, 5, 9, player2);

        playerZoneMap = new HashMap<>();
        playerZoneMap.put(player1, zone1);
        playerZoneMap.put(player2, zone2);

        manager = new GameStateManager(playerZoneMap);
        fleetManager = new FleetManager(playerZoneMap, List.of(player1, player2), battlefieldSize);
    }

    @Test
    void testStartGame_successWhenReadyToPlay() {
        manager.setGameState(GameState.READY_TO_PLAY);

        boolean started = manager.startGame();

        assertTrue(started);
        assertEquals(GameState.IN_PROGRESS, manager.getGameState());
    }

    @Test
    void testStartGame_failsWhenNotReady() {
        manager.setGameState(GameState.INITIALIZED);

        boolean started = manager.startGame();

        assertFalse(started);
        assertEquals(GameState.INITIALIZED, manager.getGameState());
    }

    @Test
    void testEvaluateGameState_onlyOneAlive_shouldEndGameAndSetWinner() {
        int shipSize = 2;
        fleetManager.addShipToAllZones("ship1", shipSize, List.of(new Coordinate(1,1), new Coordinate(5, 5)));
        assertEquals(1, zone1.getShips().size());
        assertEquals(1, zone2.getShips().size());
        assertTrue(zone1.getShips().get(0).isAlive());
        assertTrue(zone2.getShips().get(0).isAlive());

        zone1.destroyShip(zone1.getShips().get(0));

        assertFalse(zone1.getShips().get(0).isAlive());
        assertTrue(zone2.getShips().get(0).isAlive());

        manager.evaluateGameState();

        assertTrue(manager.isGameEnded());
        assertEquals(GameState.ENDED, manager.getGameState());
        assertEquals(player2, manager.getWinner());
    }

    @Test
    void testEvaluateGameState_drawCondition() {
        int shipSize = 2;
        fleetManager.addShipToAllZones("ship1", shipSize, List.of(new Coordinate(1,1), new Coordinate(5, 5)));
        assertEquals(1, zone1.getShips().size());
        assertEquals(1, zone2.getShips().size());
        assertTrue(zone1.getShips().get(0).isAlive());
        assertTrue(zone2.getShips().get(0).isAlive());

        zone1.destroyShip(zone1.getShips().get(0));
        zone2.destroyShip(zone2.getShips().get(0));

        assertFalse(zone1.getShips().get(0).isAlive());
        assertFalse(zone2.getShips().get(0).isAlive());

        manager.evaluateGameState();

        assertTrue(manager.isGameEnded());
        assertEquals(GameState.ENDED, manager.getGameState());
        assertNull(manager.getWinner());
    }

    @Test
    void testEvaluateGameState_multipleAlive_shouldNotEndGame() {
        manager.evaluateGameState();

        assertFalse(manager.isGameEnded());
        assertEquals(GameState.IN_PROGRESS, manager.getGameState());
        assertNull(manager.getWinner());
    }

    @Test
    void testCanAddShip_allowedStates() {
        manager.setGameState(GameState.INITIALIZED);
        assertTrue(manager.canAddShip());

        manager.setGameState(GameState.READY_TO_PLAY);
        assertTrue(manager.canAddShip());
    }

    @Test
    void testCanAddShip_notAllowedStates() {
        manager.setGameState(GameState.IN_PROGRESS);
        assertFalse(manager.canAddShip());

        manager.setGameState(GameState.ENDED);
        assertFalse(manager.canAddShip());
    }

}
