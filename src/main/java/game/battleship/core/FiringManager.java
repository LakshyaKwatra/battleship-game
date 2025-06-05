package game.battleship.core;

import game.battleship.model.*;
import game.battleship.util.FiringInputValidator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FiringManager {

    private final Set<Coordinate> firedCoordinates = new HashSet<>();

    public Set<Coordinate> getFiredCoordinates() {
        return firedCoordinates;
    }

    public FiringResult evaluateFiringResult(Player shooter, Coordinate target, List<Player> players, int battlefieldSize) {

        ValidationResult validationResult = FiringInputValidator.validate(shooter, target, players, battlefieldSize, firedCoordinates);

        if (!validationResult.isValid()) {
            return new FiringResult(false, false, null, null, validationResult.getMessage());
        }

        firedCoordinates.add(target);

        for (Player targetPlayer : players) {
            if (targetPlayer.getId().equals(shooter.getId())) {
                continue;
            }

            if (targetPlayer.getZone().containsCoordinate(target)) {
                Zone zone = targetPlayer.getZone();
                Cell targetCell = zone.getCell(target);
                Ship ship = zone.getShip(target);


                if (ship != null && !ship.isDestroyed()) {
                    ship.destroy();
                    targetCell.markFired();
                    return new FiringResult(true, true, targetPlayer, ship, "Hit!");
                }
                targetCell.markFired();
                break;
            }
        }
        // If no ship found, it's a miss
        return new FiringResult(true, false, null, null, "Miss!");
    }
}
