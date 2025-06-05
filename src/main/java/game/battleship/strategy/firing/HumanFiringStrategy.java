package game.battleship.strategy.firing;

import game.battleship.core.GameSession;
import game.battleship.exception.InvalidInputException;
import game.battleship.model.Coordinate;
import game.battleship.model.Player;
import game.battleship.model.ValidationResult;
import game.battleship.util.FiringInputValidator;
import game.battleship.util.InputParser;

import java.util.Scanner;
import java.util.Set;

public class HumanFiringStrategy implements FiringStrategy {
    private final Scanner scanner;

    public HumanFiringStrategy(Scanner scanner) {
        if (scanner == null) {
            throw new IllegalArgumentException("Scanner cannot be null for strategy HumanFiringStrategy");
        }
        this.scanner = scanner;
    }

    @Override
    public Coordinate chooseTarget(Player player, GameSession gameSession, Set<Coordinate> firedCoordinates) {

        while (true) {
            System.out.println(player.getName() + ", enter coordinates to fire (x y):");
            String input = scanner.nextLine().trim();

            try {
                Coordinate coordinate = InputParser.parseCoordinate(input, gameSession.getBattlefieldSize());
                ValidationResult result = FiringInputValidator.validate(player,
                        coordinate,
                        gameSession.getPlayers(),
                        gameSession.getBattlefieldSize(),
                        firedCoordinates);

                if (result.isValid()) {
                    return coordinate;
                }
                System.out.println("Invalid input: " + result.getMessage());

            } catch (InvalidInputException e) {
                System.out.println("Invalid input: " + e.getMessage());
            }

        }
    }
}
