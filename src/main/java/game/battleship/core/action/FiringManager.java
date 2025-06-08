package game.battleship.core.action;

import game.battleship.model.*;
import game.battleship.model.validation.FiringInputValidationRequest;
import game.battleship.model.validation.ValidationResult;
import game.battleship.validation.FiringValidator;
import game.battleship.validation.Validator;

import java.util.Map;

public class FiringManager {

    private final Map<Player, Zone> playerZoneMap;
    private final Validator<FiringInputValidationRequest> validator;

    public FiringManager(Map<Player, Zone> playerZoneMap, Validator<FiringInputValidationRequest> validator) {
        this.playerZoneMap = playerZoneMap;
        this.validator = validator;
    }

    public FiringResult evaluateFiringResult(Player shooter, Coordinate target) {

        FiringInputValidationRequest validationRequest = FiringInputValidationRequest.builder()
                .shooter(shooter)
                .target(target)
                .playerZoneMap(playerZoneMap)
                .build();

        ValidationResult validationResult = validator.validate(validationRequest);
        if (!validationResult.isValid()) {
            return FiringResult.builder()
                    .validShot(false)
                    .hit(false)
                    .attackingPlayer(shooter)
                    .errorMessage(validationResult.getMessage())
                    .build();
        }

        for (Player targetPlayer : playerZoneMap.keySet()) {
            if (targetPlayer.getId().equals(shooter.getId())) {
                continue;
            }

            Zone zone = playerZoneMap.get(targetPlayer);
            if (zone != null && zone.containsCoordinate(target)) {
                Cell targetCell = zone.getCell(target);
                Ship ship = zone.getShipAt(target);
                if(ship != null) {
                    zone.destroyShip(ship);
                    targetCell.markFired();

                    return FiringResult.builder()
                            .validShot(true)
                            .hit(true)
                            .attackingPlayer(shooter)
                            .hitPlayer(targetPlayer)
                            .hitShip(ship).build();
                }
                targetCell.markFired();
                break;
            }
        }

        return FiringResult.builder()
                .validShot(true)
                .hit(false)
                .attackingPlayer(shooter).build();
    }

}
