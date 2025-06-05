import game.battleship.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ShipTest {

    private Zone zone;
    private Coordinate coord1;
    private Coordinate coord2;
    private Set<Coordinate> coordinates;

    @BeforeEach
    void setUp() {
        zone = new Zone(10, 0, 4);
        coord1 = new Coordinate(0, 0);
        coord2 = new Coordinate(1, 0);
        coordinates = Set.of(coord1, coord2);

        // Add required cells to zone
        zone.getCellsMap().put(coord1, new Cell(coord1));
        zone.getCellsMap().put(coord2, new Cell(coord2));
    }

    @Test
    void testShipInitializationSuccess() {
        Ship ship = new Ship("S1", 2, coordinates, zone);

        assertEquals("S1", ship.getId());
        assertEquals(2, ship.getSize());
        assertFalse(ship.isDestroyed());
        assertTrue(ship.containsCoordinate(coord1));
        assertTrue(ship.containsCoordinate(coord2));

        // Verify that both cells are marked occupied
        assertTrue(zone.getCell(coord1).isOccupied());
        assertTrue(zone.getCell(coord2).isOccupied());
    }

    @Test
    void testShipInitializationFailsForInvalidCoordinate() {
        Coordinate invalid = new Coordinate(9, 9); // Not added to zone

        Set<Coordinate> badCoordinates = Set.of(invalid);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Ship("S2", 1, badCoordinates, zone)
        );

        assertEquals("Invalid coordinate for ship: " + invalid, exception.getMessage());
    }

    @Test
    void testDestroyMarksShipDestroyedAndCellsDestroyed() {
        Ship ship = new Ship("S3", 2, coordinates, zone);

        ship.destroy();

        assertTrue(ship.isDestroyed());
        for (Coordinate c : coordinates) {
            assertTrue(zone.getCell(c).isDestroyed());
        }
    }

    @Test
    void testGetCoordinatesReturnsCorrectSet() {
        Ship ship = new Ship("S4", 2, coordinates, zone);

        Set<Coordinate> shipCoords = ship.getCoordinates();
        assertEquals(coordinates, shipCoords);
    }
}

