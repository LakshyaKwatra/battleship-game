import game.battleship.core.FleetManager;
import game.battleship.model.Coordinate;
import game.battleship.model.Player;
import game.battleship.model.ValidationResult;
import game.battleship.model.Zone;
import game.battleship.util.FleetInputValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mockStatic;

public class FleetManagerTest {

    private FleetManager fleetManager;
    private Player player1;
    private Player player2;
    private Zone zone1;
    private Zone zone2;

    @BeforeEach
    void setup() {
        fleetManager = new FleetManager();
        zone1 = new Zone(10, 0, 4);
        zone2 = new Zone(10, 5, 9);

        player1 = new Player("P1", "Lakshya", zone1, null);
        player2 = new Player("P2", "Priya", zone2, null);
    }

    @Test
    void testAddSquareShipToAllPlayers_successful() {
        // For 2x2 ships, choose top-left corners such that full square fits
        Coordinate topLeft1 = new Coordinate(2, 2); // Covers (2,2),(2,3),(3,2),(3,3)
        Coordinate topLeft2 = new Coordinate(5, 6); // Covers (5,6),(5,7),(6,6),(6,7)

        List<Coordinate> topLefts = List.of(topLeft1, topLeft2);
        List<Player> players = List.of(player1, player2);

        boolean result = fleetManager.addShipToAllPlayers("S1", 2, topLefts, players, 10);

        assertTrue(result);
        assertEquals(1, player1.getZone().getShips().size());
        assertEquals(1, player2.getZone().getShips().size());

        // Ensure all 4 cells in 2x2 square are marked as occupied
        assertTrue(player1.getZone().getCell(new Coordinate(2, 2)).isOccupied());
        assertTrue(player1.getZone().getCell(new Coordinate(2, 3)).isOccupied());
        assertTrue(player1.getZone().getCell(new Coordinate(3, 2)).isOccupied());
        assertTrue(player1.getZone().getCell(new Coordinate(3, 3)).isOccupied());

        assertTrue(player2.getZone().getCell(new Coordinate(5, 6)).isOccupied());
        assertTrue(player2.getZone().getCell(new Coordinate(5, 7)).isOccupied());
        assertTrue(player2.getZone().getCell(new Coordinate(6, 6)).isOccupied());
        assertTrue(player2.getZone().getCell(new Coordinate(6, 7)).isOccupied());
    }

    @Test
    void testAddShipToAllPlayers_invalidCoordinatesSize() {
        List<Coordinate> topLefts = List.of(new Coordinate(0, 1)); // Only one coordinate for two players
        List<Player> players = List.of(player1, player2);

        boolean result = fleetManager.addShipToAllPlayers("S1", 2, topLefts, players, 10);
        assertFalse(result);
        assertEquals(0, player1.getZone().getShips().size());
        assertEquals(0, player2.getZone().getShips().size());
    }

    @Test
    void testAddShipToAllPlayers_validationFails() {
        Coordinate topLeft1 = new Coordinate(0, 1);
        Coordinate topLeft2 = new Coordinate(6, 6);
        List<Coordinate> topLefts = List.of(topLeft1, topLeft2);
        List<Player> players = List.of(player1, player2);

        try (MockedStatic<FleetInputValidator> mockValidator = mockStatic(FleetInputValidator.class)) {
            mockValidator.when(() ->
                            FleetInputValidator.validate(anySet(), eq(10), any(Zone.class)))
                    .thenReturn(ValidationResult.failure("Overlap"));

            boolean result = fleetManager.addShipToAllPlayers("S1", 2, topLefts, players, 10);

            assertFalse(result);
            assertEquals(0, player1.getZone().getShips().size());
            assertEquals(0, player2.getZone().getShips().size());
        }
    }
}
