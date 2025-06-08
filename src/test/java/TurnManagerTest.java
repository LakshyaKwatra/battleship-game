import game.battleship.core.TurnManager;
import game.battleship.model.Player;
import game.battleship.model.PlayerConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TurnManagerTest {

    private Player player1;
    private Player player2;
    private Player player3;
    private TurnManager turnManager;

    @BeforeEach
    void setup() {
        player1 = new Player("p1", new PlayerConfig("Lakshya", null), null);
        player2 = new Player("p2", new PlayerConfig("Angel", null), null);
        player3 = new Player("p3", new PlayerConfig("Priya", null), null);
        turnManager = new TurnManager(List.of(player1, player2, player3));
    }

    @Test
    void testInitialCurrentPlayer() {
        assertEquals(player1, turnManager.getCurrentPlayer());
    }

    @Test
    void testMoveToNextPlayerOnce() {
        turnManager.moveToNextPlayer();
        assertEquals(player2, turnManager.getCurrentPlayer());
    }

    @Test
    void testMoveToNextPlayerTwice() {
        turnManager.moveToNextPlayer(); // to player2
        turnManager.moveToNextPlayer(); // to player3
        assertEquals(player3, turnManager.getCurrentPlayer());
    }

    @Test
    void testTurnWrapsAround() {
        turnManager.moveToNextPlayer(); // player2
        turnManager.moveToNextPlayer(); // player3
        turnManager.moveToNextPlayer(); // wraps to player1
        assertEquals(player1, turnManager.getCurrentPlayer());
    }

    @Test
    void testMultipleCycles() {
        for (int i = 0; i < 6; i++) {
            turnManager.moveToNextPlayer();
        }
        assertEquals(player1, turnManager.getCurrentPlayer());
    }
}
