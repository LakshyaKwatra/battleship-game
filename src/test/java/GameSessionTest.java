import game.battleship.core.GameSession;
import game.battleship.core.GameStateManager;
import game.battleship.enums.GameVisibilityMode;
import game.battleship.enums.GameState;
import game.battleship.model.*;
import game.battleship.strategy.firing.FiringStrategy;
import game.battleship.strategy.score.ScoreStrategy;
import game.battleship.core.action.FiringManager;
import game.battleship.core.action.FleetManager;
import game.battleship.core.display.GameDisplayManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameSessionTest {

    private Player player1, player2;
    private List<Player> players;
    private FleetManager fleetManager;
    private FiringManager firingManager;
    private ScoreStrategy scoreStrategy;
    private GameDisplayManager displayManager;

    private GameSession gameSession;

    private static final int BATTLEFIELD_SIZE = 10;

    @BeforeEach
    void setup() {
        player1 = new Player("p1", new PlayerConfig("Lakshya", null), mock(FiringStrategy.class));
        player2 = new Player("p2", new PlayerConfig("Angel", null), mock(FiringStrategy.class));
        players = List.of(player1, player2);

        scoreStrategy = mock(ScoreStrategy.class);
        // Using real fleetManager/firingManager in GameSession constructor, then override with mocks
        GameSession realSession = new GameSession("session123", BATTLEFIELD_SIZE, players, GameVisibilityMode.FULL_VISIBILITY, scoreStrategy);

        fleetManager = mock(FleetManager.class);
        firingManager = mock(FiringManager.class);
        displayManager = mock(GameDisplayManager.class);

        TestUtils.setField(realSession, "fleetManager", fleetManager);
        TestUtils.setField(realSession, "firingManager", firingManager);
        TestUtils.setField(realSession, "displayManager", displayManager);

        gameSession = realSession;
    }

    @Test
    void testInitialization_createsZonesAndMappings() {
        assertEquals(BATTLEFIELD_SIZE, gameSession.getBattlefieldSize());
        assertEquals(2, gameSession.getPlayerZoneMap().size());

        Zone zone1 = gameSession.getZoneOfPlayer(player1);
        Zone zone2 = gameSession.getZoneOfPlayer(player2);
        assertNotNull(zone1);
        assertNotNull(zone2);

        for (Coordinate coord : zone1.getCoordinates()) {
            assertEquals(player1, gameSession.getOwnerOfCoordinate(coord));
        }
        for (Coordinate coord : zone2.getCoordinates()) {
            assertEquals(player2, gameSession.getOwnerOfCoordinate(coord));
        }
    }

    @Test
    void testAddShip_successful() {
        when(fleetManager.addShipToAllZones(anyString(), anyInt(), anyList())).thenReturn(true);

        boolean result = gameSession.addShip("S1", 2, List.of(new Coordinate(0, 0), new Coordinate(5, 0)));

        assertTrue(result);
        assertEquals(GameState.READY_TO_PLAY, gameSession.getGameState());
    }

    @Test
    void testAddShip_fails_dueToState() {
        TestUtils.setField(gameSession, "gameStateManager", new GameStateManager(gameSession.getPlayerZoneMap()) {{
            setGameState(GameState.IN_PROGRESS);
        }});

        boolean result = gameSession.addShip("S1", 2, List.of(new Coordinate(0, 0), new Coordinate(5, 0)));

        assertFalse(result);
    }


    @Test
    void testMoveToNextActivePlayer_skipsDefeated() {
        Zone zone1 = gameSession.getZoneOfPlayer(player1);
        Zone zone2 = gameSession.getZoneOfPlayer(player2);

        // Simulate defeated player
        zone2.getShips().forEach(zone2::destroyShip); // No ships, means defeated

        Player current = gameSession.getCurrentPlayer();
        gameSession.moveToNextActivePlayer();
        Player next = gameSession.getCurrentPlayer();

        assertNotEquals(current, next);
    }
}
