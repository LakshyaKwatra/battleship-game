package game.battleship.core;

import game.battleship.model.*;
import game.battleship.util.CoordinateUtils;
import game.battleship.util.FleetInputValidator;

import java.util.*;

public class FleetManager {

    public boolean addShipToAllPlayers(String shipId, int shipSize, List<Coordinate> topLeftCoordinates, List<Player> players, int battlefieldSize) {
        if (topLeftCoordinates.size() != players.size()) {
            System.out.println("Positions list must match number of players.");
            return false;
        }

        // Validation
        List<Set<Coordinate>> validCoordinates = new ArrayList<>();

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            Coordinate topLeft = topLeftCoordinates.get(i);
            Zone zone = player.getZone();
            Set<Coordinate> shipCoordinates = CoordinateUtils.generateShipCoordinates(topLeft, shipSize);

            ValidationResult validationResult = FleetInputValidator.validate(shipCoordinates, battlefieldSize, zone);

            if (!validationResult.isValid()) {
                System.out.println("Validation failed for Player: " + player.getName() + " - " + validationResult.getMessage());
                return false;
            }

            validCoordinates.add(shipCoordinates);
        }

        // Add Ships for each player
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            addShipToPlayer(shipId, shipSize, player, validCoordinates.get(i));
        }

        System.out.println("Ship added!");
        return true;
    }

    private void addShipToPlayer(String shipId, int shipSize, Player player, Set<Coordinate> shipCoordinates) {
        Zone zone = player.getZone();
        Ship ship = new Ship(shipId, shipSize, shipCoordinates, zone);
        zone.addShip(ship);
    }

}


