package game.battleship.validation;

import game.battleship.model.Coordinate;
import game.battleship.model.validation.FleetInputValidationRequest;
import game.battleship.model.validation.ValidationResult;
import game.battleship.util.CoordinateUtils;

import java.util.Comparator;
import java.util.stream.Collectors;

public class FleetValidator implements Validator<FleetInputValidationRequest> {

    @Override
    public ValidationResult validate(FleetInputValidationRequest validationRequest) {

        for (Coordinate coordinate : validationRequest.getShipCoordinates()) {
            if (CoordinateUtils.isOutOfBounds(coordinate, validationRequest.getBattlefieldSize())) {
                return ValidationResult.failure("Ship extends out of battlefield bounds.");
            }
            if (!validationRequest.getZone().containsCoordinate(coordinate)) {
                System.out.println(validationRequest.getZone().getStartX());
                System.out.println(validationRequest.getZone().getEndX());
                System.out.println(validationRequest.getShipCoordinates().stream().sorted(Comparator.comparingInt(a -> a.x)).collect(Collectors.toList()));
                return ValidationResult.failure("Ship must lie entirely within player's zone.");
            }
            if (validationRequest.getZone().overlapsWithExistingShips(coordinate)) {
                return ValidationResult.failure("Ship overlaps with an existing ship.");
            }
        }
        return ValidationResult.success();
    }

}

