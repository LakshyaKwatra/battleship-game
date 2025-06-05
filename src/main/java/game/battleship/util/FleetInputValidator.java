package game.battleship.util;

import game.battleship.model.Coordinate;
import game.battleship.model.ValidationResult;
import game.battleship.model.Zone;

import java.util.Set;

public class FleetInputValidator {

    public static ValidationResult validate(Set<Coordinate> coordinates, int battlefieldSize, Zone zone) {

        for (Coordinate coordinate : coordinates) {
            if (CoordinateUtils.isOutOfBounds(coordinate, battlefieldSize)) {
                return ValidationResult.failure("Ship extends out of battlefield bounds.");
            }
            if (!zone.containsCoordinate(coordinate)) {
                return ValidationResult.failure("Ship must lie entirely within player's zone.");
            }
            if (zone.overlapsWithExistingShips(coordinate)) {
                return ValidationResult.failure("Ship overlaps with an existing ship.");
            }
        }
        return ValidationResult.success();
    }

}

