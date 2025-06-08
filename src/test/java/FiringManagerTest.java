import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import game.battleship.core.action.FiringManager;
import game.battleship.model.*;
import game.battleship.model.validation.*;
import game.battleship.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class FiringManagerTest {

    private Validator<FiringInputValidationRequest> validator;
    private FiringManager firingManager;

    private Player shooter;
    private Player opponent;
    private Zone shooterZone;
    private Zone opponentZone;

    @BeforeEach
    public void setup() {
        validator = mock(Validator.class);

        shooter = new Player("shooter1", null, null);
        opponent = new Player("opponent1", null, null);

        shooterZone = new Zone(5, 0, 4, shooter);
        opponentZone = new Zone(5, 5, 9, opponent);

        Map<Player, Zone> playerZoneMap = new HashMap<>();
        playerZoneMap.put(shooter, shooterZone);
        playerZoneMap.put(opponent, opponentZone);

        firingManager = new FiringManager(playerZoneMap, validator);
    }

    @Test
    public void testEvaluateFiringResult_InvalidShot() {
        when(validator.validate(any())).thenReturn(ValidationResult.failure("Invalid input"));

        Coordinate target = new Coordinate(5, 1);
        FiringResult result = firingManager.evaluateFiringResult(shooter, target);

        assertFalse(result.isValidShot());
        assertFalse(result.isHit());
        assertEquals(shooter, result.getAttackingPlayer());
        assertEquals("Invalid input", result.getErrorMessage());
        verify(validator).validate(any());
    }

    @Test
    public void testEvaluateFiringResult_ValidShot_Miss() {
        when(validator.validate(any())).thenReturn(ValidationResult.success());

        Coordinate target = new Coordinate(5, 1);

        FiringResult result = firingManager.evaluateFiringResult(shooter, target);

        assertTrue(result.isValidShot());
        assertFalse(result.isHit());
        assertEquals(shooter, result.getAttackingPlayer());
        assertNull(result.getHitPlayer());
        assertNull(result.getHitShip());

        Cell targetCell = opponentZone.getCell(target);
        assertTrue(targetCell.isFired());
    }

    @Test
    public void testEvaluateFiringResult_ValidShot_Hit() {
        when(validator.validate(any())).thenReturn(ValidationResult.success());

        Coordinate shipCoordinate = new Coordinate(5, 1);
        Set<Coordinate> shipCoords = new HashSet<>();
        shipCoords.add(shipCoordinate);
        Ship ship = new Ship("ship1", 1, shipCoords, opponentZone);
        opponentZone.addShip(ship);

        FiringResult result = firingManager.evaluateFiringResult(shooter, shipCoordinate);

        assertTrue(result.isValidShot());
        assertTrue(result.isHit());
        assertEquals(shooter, result.getAttackingPlayer());
        assertEquals(opponent, result.getHitPlayer());
        assertEquals(ship, result.getHitShip());
        assertFalse(ship.isAlive());

        Cell targetCell = opponentZone.getCell(shipCoordinate);
        assertTrue(targetCell.isFired());
        assertTrue(targetCell.isDestroyed());
    }
    
}
