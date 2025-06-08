package game.battleship.util;

import game.battleship.exception.InvalidInputException;
import game.battleship.model.Coordinate;
import game.battleship.model.Player;
import game.battleship.model.Zone;
import game.battleship.ui.GameTextRenderer;

import java.util.*;
import java.util.stream.Collectors;

public class CoordinateUtils {

    public static boolean isOutOfBounds(Coordinate coordinate, int battlefieldSize) {
        return coordinate.x < 0 || coordinate.x >= battlefieldSize || coordinate.y < 0 || coordinate.y >= battlefieldSize;
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

    public static Set<Coordinate> getValidOpponentZoneCoordinates(Player shooter, Map<Player, Zone> playerZoneMap) {
        return playerZoneMap.entrySet().stream()
                .filter(entry -> !entry.getKey().getId().equals(shooter.getId()))
                .filter(entry -> !entry.getValue().isDefeated())
                .flatMap(entry -> entry.getValue().getCoordinates().stream()
                        .filter(coordinate -> !entry.getValue().getCell(coordinate).isDestroyed() &&
                                !entry.getValue().getCell(coordinate).isFired()))
                .collect(Collectors.toSet());
    }

    public static List<Coordinate> fromInts(int... topLeftCoordinates) {
        if (topLeftCoordinates.length % 2 != 0) {
            GameTextRenderer.printErrorMessage("Number of integers must be even (pairs of x and y).");
        }

        List<Coordinate> coordinates = new ArrayList<>();
        for (int i = 0; i < topLeftCoordinates.length; i += 2) {
            coordinates.add(new Coordinate(topLeftCoordinates[i], topLeftCoordinates[i + 1]));
        }
        return coordinates;
    }

    public static Coordinate parseCoordinate(String input) throws InvalidInputException {
        String[] parts = input.trim().split("\\s+");
        if (parts.length != 2) {
            throw new InvalidInputException("Expected exactly two integers separated by space.");
        }
        try {
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            return new Coordinate(x, y);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid number format.");
        }
    }
}
