package game.battleship.util;

import game.battleship.model.Coordinate;
import game.battleship.model.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CoordinateUtils {

    public static boolean isOutOfBounds(Coordinate coordinate, int battlefieldSize) {
        return coordinate.x < 0 || coordinate.x >= battlefieldSize || coordinate.y < 0 || coordinate.y >= battlefieldSize;
    }

    public static boolean isOutOfBounds(int x, int y, int battlefieldSize) {
        return x < 0 || x >= battlefieldSize || y < 0 || y >= battlefieldSize;
    }

    public static Set<Coordinate> generateShipCoordinates(Coordinate topLeft, int shipSize) {
        Set<Coordinate> coordinates = new HashSet<>();
        for (int dx = 0; dx < shipSize; dx++) {
            for (int dy = 0; dy < shipSize; dy++) {
                coordinates.add(new Coordinate(topLeft.x + dx, topLeft.y + dy));
            }
        }
        return coordinates;
    }

    public static Set<Coordinate> getOpponentZoneCoordinates(Player shooter, List<Player> allPlayers) {
        return allPlayers.stream()
                .filter(p -> !p.getId().equals(shooter.getId()))
                .flatMap(p -> p.getZone().getCoordinates().stream())
                .collect(Collectors.toSet());
    }

    public static List<Coordinate> fromInts(int... topLeftCoordinates) {
        if (topLeftCoordinates.length % 2 != 0) {
            throw new IllegalArgumentException("Number of integers must be even (pairs of x and y).");
        }

        List<Coordinate> coordinates = new ArrayList<>();
        for (int i = 0; i < topLeftCoordinates.length; i += 2) {
            coordinates.add(new Coordinate(topLeftCoordinates[i], topLeftCoordinates[i + 1]));
        }
        return coordinates;
    }
}
