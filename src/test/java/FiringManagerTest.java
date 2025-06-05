import game.battleship.core.FiringManager;
import game.battleship.enums.FiringStrategyType;
import game.battleship.factory.FiringStrategyFactory;
import game.battleship.model.*;
import game.battleship.util.FiringInputValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mockStatic;

public class FiringManagerTest {

    private FiringManager firingManager;
    private Player shooter;
    private Player opponent;
    private Zone shooterZone;
    private Zone opponentZone;
    private Ship opponentShip;
    private Coordinate target;

    @BeforeEach
    void setUp() {
        firingManager = new FiringManager();

        shooterZone = new Zone(10, 0, 2);
        opponentZone = new Zone(10, 3, 5);

        shooter = new Player("P1", "Lakshya", shooterZone, null);
        opponent = new Player("P2", "Priya", opponentZone, null);

        target = new Coordinate(3, 0);

        Cell cell = new Cell(target);
        opponentZone.getCellsMap().put(target, cell);

        opponentShip = new Ship("S1", 1, Set.of(target), opponentZone);
        opponentZone.addShip(opponentShip);
    }

    @Test
    void testInvalidFiringCoordinate() {
        try (MockedStatic<FiringInputValidator> mockedValidator = mockStatic(FiringInputValidator.class)) {
            mockedValidator.when(() -> FiringInputValidator.validate(eq(shooter), eq(target), anyList(), anyInt(), anySet()))
                    .thenReturn(ValidationResult.failure("Invalid shot"));

            FiringResult result = firingManager.evaluateFiringResult(shooter, target, List.of(shooter, opponent), 10);

            assertFalse(result.isValidShot());
            assertEquals("Invalid shot", result.getMessage());
        }
    }

    @Test
    void testFiringMiss() {
        Coordinate missCoordinate = new Coordinate(4, 1);
        Cell missCell = new Cell(missCoordinate);
        opponentZone.getCellsMap().put(missCoordinate, missCell);

        try (MockedStatic<FiringInputValidator> mockedValidator = mockStatic(FiringInputValidator.class)) {
            mockedValidator.when(() -> FiringInputValidator.validate(eq(shooter), eq(missCoordinate), anyList(), anyInt(), anySet()))
                    .thenReturn(ValidationResult.success());

            FiringResult result = firingManager.evaluateFiringResult(shooter, missCoordinate, List.of(shooter, opponent), 10);

            assertTrue(result.isValidShot());
            assertFalse(result.isHit());
            assertTrue(opponentZone.getCell(missCoordinate).isFired());
            assertEquals("Miss!", result.getMessage());
            assertTrue(firingManager.getFiredCoordinates().contains(missCoordinate));
        }
    }

    @Test
    void testFiringHitAndDestroy() {
        try (MockedStatic<FiringInputValidator> mockedValidator = mockStatic(FiringInputValidator.class)) {
            mockedValidator.when(() -> FiringInputValidator.validate(eq(shooter), eq(target), anyList(), anyInt(), anySet()))
                    .thenReturn(ValidationResult.success());

            FiringResult result = firingManager.evaluateFiringResult(shooter, target, List.of(shooter, opponent), 10);

            assertTrue(result.isValidShot());
            assertTrue(result.isHit());
            assertEquals("Hit!", result.getMessage());
            assertTrue(opponentZone.getCell(target).isFired());
            assertTrue(opponentShip.isDestroyed());
            assertTrue(firingManager.getFiredCoordinates().contains(target));
        }
    }

    @Test
    void testFiringOnAlreadyDestroyedShip() {
        opponentShip.destroy();

        try (MockedStatic<FiringInputValidator> mockedValidator = mockStatic(FiringInputValidator.class)) {
            mockedValidator.when(() -> FiringInputValidator.validate(eq(shooter), eq(target), anyList(), anyInt(), anySet()))
                    .thenReturn(ValidationResult.success());

            FiringResult result = firingManager.evaluateFiringResult(shooter, target, List.of(shooter, opponent), 10);

            assertTrue(result.isValidShot());
            assertFalse(result.isHit());
            assertEquals("Miss!", result.getMessage());
        }
    }

    @Test
    void testDuplicateFireOnSameCoordinate() {
        // First fire is successful
        try (MockedStatic<FiringInputValidator> mockedValidator = mockStatic(FiringInputValidator.class)) {
            mockedValidator.when(() -> FiringInputValidator.validate(eq(shooter), eq(target), anyList(), anyInt(), anySet()))
                    .thenReturn(ValidationResult.success());

            FiringResult firstFire = firingManager.evaluateFiringResult(shooter, target, List.of(shooter, opponent), 10);
            assertTrue(firstFire.isValidShot());
        }

        // Second fire at same coordinate should be invalid
        try (MockedStatic<FiringInputValidator> mockedValidator = mockStatic(FiringInputValidator.class)) {
            mockedValidator.when(() -> FiringInputValidator.validate(eq(shooter), eq(target), anyList(), anyInt(), anySet()))
                    .thenReturn(ValidationResult.failure("Coordinate already fired at"));

            FiringResult secondFire = firingManager.evaluateFiringResult(shooter, target, List.of(shooter, opponent), 10);

            assertFalse(secondFire.isValidShot());
            assertEquals("Coordinate already fired at", secondFire.getMessage());
        }
    }

    @Test
    void testFiringAtOwnZoneIsInvalid() {
        Coordinate ownCoordinate = new Coordinate(1, 0);
        shooterZone.getCellsMap().put(ownCoordinate, new Cell(ownCoordinate));

        try (MockedStatic<FiringInputValidator> mockedValidator = mockStatic(FiringInputValidator.class)) {
            mockedValidator.when(() -> FiringInputValidator.validate(eq(shooter), eq(ownCoordinate), anyList(), anyInt(), anySet()))
                    .thenReturn(ValidationResult.failure("Cannot fire at own zone"));

            FiringResult result = firingManager.evaluateFiringResult(shooter, ownCoordinate, List.of(shooter, opponent), 10);

            assertFalse(result.isValidShot());
            assertEquals("Cannot fire at own zone", result.getMessage());
        }
    }

}
