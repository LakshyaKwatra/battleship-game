import game.battleship.core.GameStateManager;
import game.battleship.enums.GameState;
import game.battleship.model.Player;
import game.battleship.model.Zone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameStateManagerTest {

    private GameStateManager gameStateManager;

    @BeforeEach
    void setUp() {
        gameStateManager = new GameStateManager();
    }

    @Test
    void testStartGameOnlyWhenReadyToPlay() {
        assertEquals(GameState.INITIALIZED, gameStateManager.getGameState());
        assertFalse(gameStateManager.startGame());

        gameStateManager.setGameState(GameState.READY_TO_PLAY);
        assertTrue(gameStateManager.startGame());
        assertEquals(GameState.IN_PROGRESS, gameStateManager.getGameState());

        gameStateManager.setGameState(GameState.ENDED);
        assertFalse(gameStateManager.startGame());
    }

    @Test
    void testEvaluateGameState_AllPlayersAlive() {
        Player p1 = mock(Player.class);
        Player p2 = mock(Player.class);
        when(p1.getZone()).thenReturn(mock(Zone.class));
        when(p2.getZone()).thenReturn(mock(Zone.class));
        when(p1.getZone().hasUndestroyedShips()).thenReturn(true);
        when(p2.getZone().hasUndestroyedShips()).thenReturn(true);

        gameStateManager.evaluateGameState(List.of(p1, p2));
        assertEquals(GameState.IN_PROGRESS, gameStateManager.getGameState());
        assertNull(gameStateManager.getWinner());
    }

    @Test
    void testEvaluateGameState_OnePlayerAlive() {
        Player alive = mock(Player.class);
        Player dead = mock(Player.class);
        when(alive.getZone()).thenReturn(mock(Zone.class));
        when(dead.getZone()).thenReturn(mock(Zone.class));
        when(alive.getZone().hasUndestroyedShips()).thenReturn(true);
        when(dead.getZone().hasUndestroyedShips()).thenReturn(false);

        gameStateManager.evaluateGameState(List.of(alive, dead));
        assertEquals(GameState.ENDED, gameStateManager.getGameState());
        assertEquals(alive, gameStateManager.getWinner());
    }

    @Test
    void testEvaluateGameState_NoPlayersAlive_Draw() {
        Player p1 = mock(Player.class);
        Player p2 = mock(Player.class);
        when(p1.getZone()).thenReturn(mock(Zone.class));
        when(p2.getZone()).thenReturn(mock(Zone.class));
        when(p1.getZone().hasUndestroyedShips()).thenReturn(false);
        when(p2.getZone().hasUndestroyedShips()).thenReturn(false);

        gameStateManager.evaluateGameState(List.of(p1, p2));
        assertEquals(GameState.ENDED, gameStateManager.getGameState());
        assertNull(gameStateManager.getWinner());
    }

    @Test
    void testCanAddShip() {
        gameStateManager.setGameState(GameState.INITIALIZED);
        assertTrue(gameStateManager.canAddShip());

        gameStateManager.setGameState(GameState.READY_TO_PLAY);
        assertTrue(gameStateManager.canAddShip());

        gameStateManager.setGameState(GameState.IN_PROGRESS);
        assertFalse(gameStateManager.canAddShip());

        gameStateManager.setGameState(GameState.ENDED);
        assertFalse(gameStateManager.canAddShip());
    }

}

