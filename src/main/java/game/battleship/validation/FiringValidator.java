package game.battleship.validation;

import game.battleship.model.*;
import game.battleship.model.validation.FiringInputValidationRequest;
import game.battleship.model.validation.ValidationResult;
import game.battleship.util.CoordinateUtils;

import java.util.Map;
import java.util.Set;

public class FiringValidator implements Validator<FiringInputValidationRequest> {

    @Override
    public ValidationResult validate(FiringInputValidationRequest validationRequest) {
        if (validationRequest == null) {
            return ValidationResult.failure("Validation request cannot be null.");
        }

        var target = validationRequest.getTarget();
        var shooter = validationRequest.getShooter();
        Map<Player, Zone> playerZoneMap = validationRequest.getPlayerZoneMap();

        Set<Coordinate> validCoordinates = CoordinateUtils
                .getValidOpponentZoneCoordinates(shooter, playerZoneMap);

        if (!validCoordinates.contains(target)) {
            return ValidationResult.failure("Coordinate unavailable for firing.");
        }

        return ValidationResult.success();
    }
}
