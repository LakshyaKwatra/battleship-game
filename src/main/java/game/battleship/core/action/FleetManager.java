package game.battleship.core.action;

import game.battleship.model.*;
import game.battleship.model.validation.FleetInputValidationRequest;
import game.battleship.model.validation.ValidationResult;
import game.battleship.ui.GameTextRenderer;
import game.battleship.util.CoordinateUtils;
import game.battleship.validation.FleetValidator;
import game.battleship.validation.Validator;

import java.util.*;

public class FleetManager {

    private final Validator<FleetInputValidationRequest> validator = new FleetValidator();
    private final Map<Player, Zone> playerZoneMap;
    private final List<Player> players;
    private final int battlefieldSize;


    public FleetManager(Map<Player, Zone> playerZoneMap, List<Player> players, int battlefieldSize) {
        this.playerZoneMap = playerZoneMap;
        this.battlefieldSize = battlefieldSize;
        this.players = players;
    }

    public boolean addShipToAllZones(String shipId, int shipSize, List<Coordinate> topLeftCoordinates) {

        if (topLeftCoordinates.size() != players.size()) {
            GameTextRenderer.printErrorMessage("Positions list must match number of players.");
            return false;
        }

        List<Set<Coordinate>> validCoordinates = new ArrayList<>();

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            Coordinate topLeft = topLeftCoordinates.get(i);
            Zone zone = playerZoneMap.get(player);
            Set<Coordinate> shipCoordinates = CoordinateUtils.generateShipCoordinates(topLeft, shipSize);

            FleetInputValidationRequest request = FleetInputValidationRequest.builder()
                    .shipCoordinates(shipCoordinates)
                    .battlefieldSize(battlefieldSize)
                    .zone(zone)
                    .build();

            ValidationResult validationResult = validator.validate(request);

            if (!validationResult.isValid()) {
                GameTextRenderer.printErrorMessage("Validation failed for Player: " + player.getName() + " - " + validationResult.getMessage());
                return false;
            }

            validCoordinates.add(shipCoordinates);
        }

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            addShipToZone(shipId, shipSize, player, validCoordinates.get(i));
        }

        System.out.println("Ship added!");
        return true;
    }

    private void addShipToZone(String shipId, int shipSize, Player player, Set<Coordinate> shipCoordinates) {
        Zone zone = playerZoneMap.get(player);
        Ship ship = new Ship(shipId, shipSize, shipCoordinates, zone);
        zone.addShip(ship);
    }

    public Map<Player, Zone> getPlayerZoneMap() {
        return playerZoneMap;
    }
}
