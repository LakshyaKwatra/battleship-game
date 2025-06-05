import game.battleship.core.TurnManager;
import game.battleship.model.Player;
import game.battleship.model.Zone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TurnManagerTest {

    private Player p1;
    private Player p2;
    private Player p3;
    private TurnManager turnManager;

    @BeforeEach
    void setUp() {
        p1 = mock(Player.class);
        p2 = mock(Player.class);
        p3 = mock(Player.class);

        Zone z1 = mock(Zone.class);
        Zone z2 = mock(Zone.class);
        Zone z3 = mock(Zone.class);

        when(p1.getZone()).thenReturn(z1);
        when(p2.getZone()).thenReturn(z2);
        when(p3.getZone()).thenReturn(z3);

        // Initially, all players are alive
        when(z1.hasUndestroyedShips()).thenReturn(true);
        when(z2.hasUndestroyedShips()).thenReturn(true);
        when(z3.hasUndestroyedShips()).thenReturn(true);

        turnManager = new TurnManager(List.of(p1, p2, p3));
    }

    @Test
    void testGetCurrentPlayerInitially() {
        assertEquals(p1, turnManager.getCurrentPlayer());
    }

    @Test
    void testMoveToNextPlayer() {
        turnManager.moveToNextPlayer();
        assertEquals(p2, turnManager.getCurrentPlayer());

        turnManager.moveToNextPlayer();
        assertEquals(p3, turnManager.getCurrentPlayer());

        turnManager.moveToNextPlayer(); // wraps around
        assertEquals(p1, turnManager.getCurrentPlayer());
    }

    @Test
    void testSkipsDestroyedPlayers() {
        // Only p1 and p3 are alive
        when(p2.getZone().hasUndestroyedShips()).thenReturn(false);

        turnManager.moveToNextPlayer();
        assertEquals(p3, turnManager.getCurrentPlayer());

        turnManager.moveToNextPlayer();
        assertEquals(p1, turnManager.getCurrentPlayer());
    }

    @Test
    void testOnlyOnePlayerAlive() {
        when(p1.getZone().hasUndestroyedShips()).thenReturn(true);
        when(p2.getZone().hasUndestroyedShips()).thenReturn(false);
        when(p3.getZone().hasUndestroyedShips()).thenReturn(false);

        turnManager.moveToNextPlayer();
        assertEquals(p1, turnManager.getCurrentPlayer());

        turnManager.moveToNextPlayer();
        assertEquals(p1, turnManager.getCurrentPlayer());
    }

}
