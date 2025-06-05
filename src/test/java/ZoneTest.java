import game.battleship.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ZoneTest {

    private Zone zone;

    @BeforeEach
    void setup() {
        zone = new Zone(5, 0, 4);
    }

    @Test
    void testCellsInitialization() {
        assertEquals(25, zone.getCellsMap().size());

        // Check specific cell existence
        Coordinate coord = new Coordinate(2, 3);
        Cell cell = zone.getCell(coord);
        assertNotNull(cell);
        assertEquals(coord, cell.getCoordinate());
    }

    @Test
    void testContainsCoordinate() {
        assertTrue(zone.containsCoordinate(new Coordinate(0, 0)));
        assertTrue(zone.containsCoordinate(new Coordinate(4, 4)));

        // Outside x range
        assertFalse(zone.containsCoordinate(new Coordinate(-1, 2)));
        assertFalse(zone.containsCoordinate(new Coordinate(5, 1)));

        // Outside y range
        assertFalse(zone.containsCoordinate(new Coordinate(2, 5)));
    }

    @Test
    void testAddShipAndOverlap() {
        // Define ship coordinates
        Coordinate c1 = new Coordinate(1, 1);
        Coordinate c2 = new Coordinate(1, 2);
        Set<Coordinate> shipCoords = Set.of(c1, c2);

        Ship ship = new Ship("S1", 2, shipCoords, zone);
        zone.addShip(ship);

        assertEquals(1, zone.getShips().size());
        assertTrue(zone.getShips().contains(ship));

        // Check overlaps
        assertTrue(zone.overlapsWithExistingShips(c1));
        assertTrue(zone.overlapsWithExistingShips(c2));

        // Coordinates overlapping with ship
        Set<Coordinate> overlappingCoords = Set.of(new Coordinate(1, 1), new Coordinate(0, 0));
        assertTrue(zone.overlapsWithExistingShips(overlappingCoords));

        // Coordinates not overlapping
        Set<Coordinate> nonOverlappingCoords = Set.of(new Coordinate(3, 3), new Coordinate(4, 4));
        assertFalse(zone.overlapsWithExistingShips(nonOverlappingCoords));
    }

    @Test
    void testHasUndestroyedShips() {
        assertFalse(zone.hasUndestroyedShips());

        Ship ship = new Ship("S1", 1, Set.of(new Coordinate(0, 0)), zone);
        zone.addShip(ship);

        assertTrue(zone.hasUndestroyedShips());

        ship.destroy();
        assertFalse(zone.hasUndestroyedShips());
    }
}

