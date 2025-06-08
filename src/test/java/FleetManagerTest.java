package game.battleship.core.action;

import game.battleship.model.*;
import game.battleship.util.CoordinateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class FleetManagerTest {

    private Player player1;
    private Player player2;
    private Zone zone1;
    private Zone zone2;
    private Map<Player, Zone> playerZoneMap;
    private FleetManager fleetManager;

    private final int battlefieldSize = 10;

    @BeforeEach
    void setup() {
        player1 = new Player("p1", new PlayerConfig("Lakshya", null), null);
        player2 = new Player("p2", new PlayerConfig("Priya", null), null);

        zone1 = new Zone(battlefieldSize, 0, battlefieldSize/2 - 1, player1);
        zone2 = new Zone(battlefieldSize, battlefieldSize/2, battlefieldSize - 1, player2);

        playerZoneMap = new HashMap<>();
        playerZoneMap.put(player1, zone1);
        playerZoneMap.put(player2, zone2);

        fleetManager = new FleetManager(playerZoneMap, List.of(player1, player2), battlefieldSize);
    }

    @Test
    void testAddShipToAllZones_success() {
        String shipId = "S1";
        int shipSize = 2;
        System.out.println(zone2.getEndX());

        Coordinate topLeft1 = new Coordinate(0, 0);
        Coordinate topLeft2 = new Coordinate(5, 1);
        Set<Coordinate> coords1 = CoordinateUtils.generateShipCoordinates(topLeft1, shipSize);
        Set<Coordinate> coords2 = CoordinateUtils.generateShipCoordinates(topLeft2, shipSize);
        List<Coordinate> topLefts = List.of(topLeft1, topLeft2);

        boolean result = fleetManager.addShipToAllZones(shipId, shipSize, topLefts);
        assertTrue(result);

        assertEquals(1, zone1.getShips().size());
        assertEquals(1, zone2.getShips().size());

        Ship ship1 = zone1.getShips().get(0);
        Ship ship2 = zone2.getShips().get(0);

        assertEquals(shipId, ship1.getId());
        assertEquals(shipId, ship2.getId());

        assertEquals(coords1, ship1.getCoordinates());
        assertEquals(coords2, ship2.getCoordinates());
        assertEquals(1, zone1.getShips().size());
        assertEquals(1, zone2.getShips().size());

    }

    @Test
    void testAddShipToAllZones_failsDueToMismatchedCoordinates() {
        boolean result = fleetManager.addShipToAllZones("S2", 3, List.of(new Coordinate(0, 0)));
        assertFalse(result);
        assertTrue(zone1.getShips().isEmpty());
        assertTrue(zone2.getShips().isEmpty());
    }

    @Test
    void testAddShipToAllZones_failsDueToOverlap() {

        Coordinate existingTopLeft = new Coordinate(1, 1);
        int shipSize = 2;
        boolean result = fleetManager.addShipToAllZones("S3", shipSize, List.of(existingTopLeft, new Coordinate(5, 1)));
        assertTrue (result);
        assertEquals(1, zone1.getShips().size());
        assertEquals(1, zone2.getShips().size());

        Coordinate overlapTopLeft = new Coordinate(2, 1); // overlaps with above

        result = fleetManager.addShipToAllZones("S4", shipSize,
                List.of(overlapTopLeft, new Coordinate(4, 4)));
        assertFalse(result);

        // no ships should get added
        assertEquals(1, zone1.getShips().size());
        assertEquals(1, zone2.getShips().size());
    }
}

