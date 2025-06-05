package game.battleship.util;

import game.battleship.model.Coordinate;
import game.battleship.model.Player;
import game.battleship.model.ValidationResult;

import java.util.List;
import java.util.Set;

public class FiringInputValidator {

    public static ValidationResult validate(Player shooter, Coordinate target, List<Player> players, int battlefieldSize, Set<Coordinate> firedCoordinates) {
        if (firedCoordinates.contains(target)) {
            return ValidationResult.failure("Coordinate already fired upon.");
        }
        if (CoordinateUtils.isOutOfBounds(target, battlefieldSize)) {
            return ValidationResult.failure("Target out of battlefield bounds.");
        }
        if (shooter.getZone().containsCoordinate(target)) {
            return ValidationResult.failure("Cannot fire in your own zone.");
        }
        return ValidationResult.success();
    }
}
