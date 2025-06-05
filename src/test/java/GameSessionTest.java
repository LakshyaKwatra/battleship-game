import game.battleship.core.GameSession;
import game.battleship.core.TurnManager;
import game.battleship.enums.GameState;
import game.battleship.model.Coordinate;
import game.battleship.model.Player;
import game.battleship.model.Zone;
import game.battleship.ui.BattlefieldDisplay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameSessionTest {

    private GameSession gameSession;
    private Player player1;
    private Player player2;

    @BeforeEach
    void setup() {
        Zone zone1 = new Zone(10, 0, 4);
        Zone zone2 = new Zone(10, 5, 9);

        player1 = new Player("P1", "Alice", zone1, null);
        player2 = new Player("P2", "Bob", zone2, null);

        gameSession = new GameSession("sess1", 10, List.of(player1, player2));
    }

    @Test
    void testAddShipAllowedAndUpdatesGameState() {
        Coordinate coord1 = new Coordinate(0, 0);
        Coordinate coord2 = new Coordinate(5, 5);

        try (MockedStatic<BattlefieldDisplay> mockedDisplay = mockStatic(BattlefieldDisplay.class)) {
            boolean added = gameSession.addShip("S1", 1, List.of(coord1, coord2));
            assertTrue(added);
            assertEquals(GameState.READY_TO_PLAY, gameSession.getGameState());
        }
    }

}
