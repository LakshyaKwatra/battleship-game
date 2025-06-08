package game.battleship.util;

import game.battleship.model.*;
import game.battleship.model.validation.FleetInputValidationRequest;
import game.battleship.validation.FleetValidator;
import game.battleship.validation.Validator;

import java.util.*;

public class ShipAutoPlacer {

    private static final Random random = new Random();
    private static final Validator<FleetInputValidationRequest> validator = new FleetValidator();

    public static void autoPlaceShips(Map<Player, Zone> playerZoneMap, int numberOfShips, int battlefieldSize) {
        int addedShipCounter = 0;
        int globalAttempts = 0;
        int maxGlobalAttempts = 10000;
        int maxAttemptsPerSize = 100;

        List<Player> players = new ArrayList<>(playerZoneMap.keySet());

        while (addedShipCounter < numberOfShips && globalAttempts < maxGlobalAttempts) {
            int maxShipSize = getMinMaxSize(players, playerZoneMap, battlefieldSize);

            boolean shipPlaced = false;

            for (int shipSize = maxShipSize; shipSize >= 1; shipSize--) {
                for (int attempt = 0; attempt < maxAttemptsPerSize; attempt++) {
                    Map<Player, Set<Coordinate>> placementMap = new HashMap<>();
                    boolean allValid = true;

                    for (Player player : players) {
                        Zone zone = playerZoneMap.get(player);
                        Coordinate topLeft = getRandomTopLeft(zone, battlefieldSize);
                        Set<Coordinate> coords = CoordinateUtils.generateShipCoordinates(topLeft, shipSize);

                        FleetInputValidationRequest request = FleetInputValidationRequest.builder()
                                .shipCoordinates(coords)
                                .battlefieldSize(battlefieldSize)
                                .zone(zone)
                                .build();

                        if (!validator.validate(request).isValid()) {
                            allValid = false;
                            break;
                        }

                        placementMap.put(player, coords);
                    }

                    if (allValid) {
                        for (Player player : players) {
                            Zone zone = playerZoneMap.get(player);
                            Set<Coordinate> coords = placementMap.get(player);
                            Ship ship = new Ship("SH-" + (addedShipCounter + 1), shipSize, coords, zone);
                            zone.addShip(ship);
                        }

                        addedShipCounter++;
                        shipPlaced = true;
                        break; // done with this ship
                    }
                }

                if (shipPlaced) break;
            }

            globalAttempts++;
        }

        if (addedShipCounter < numberOfShips) {
            System.out.println("Only placed " + addedShipCounter + " out of " + numberOfShips + " ships after " + globalAttempts + " attempts.");
        }
    }

    private static Coordinate getRandomTopLeft(Zone zone, int battlefieldSize) {
        int x = zone.getStartX() + random.nextInt(zone.getEndX() - zone.getStartX() + 1);
        int y = random.nextInt(battlefieldSize);
        return new Coordinate(x, y);
    }

    private static int getMinMaxSize(List<Player> players, Map<Player, Zone> playerZoneMap, int battlefieldSize) {
        return players.stream()
                .mapToInt(player -> getMaxFittableShipSize(playerZoneMap.get(player), battlefieldSize))
                .min()
                .orElse(0);
    }

    private static int getMaxFittableShipSize(Zone zone, int battlefieldSize) {
        int maxSize = 0;

        for (Coordinate coord : zone.getCoordinates()) {
            if (zone.getCell(coord).isOccupied()) continue;

            int hFree = 0, vFree = 0;

            for (int dx = coord.x; dx <= zone.getEndX(); dx++) {
                Coordinate check = new Coordinate(dx, coord.y);
                if (!zone.containsCoordinate(check) || zone.getCell(check).isOccupied()) break;
                hFree++;
            }

            for (int dy = coord.y; dy < battlefieldSize; dy++) {
                Coordinate check = new Coordinate(coord.x, dy);
                if (!zone.containsCoordinate(check) || zone.getCell(check).isOccupied()) break;
                vFree++;
            }

            maxSize = Math.max(maxSize, Math.max(hFree, vFree));
        }

        return maxSize;
    }
}
