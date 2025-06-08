package game.battleship.strategy.firing;

import game.battleship.exception.InvalidInputException;
import game.battleship.model.Coordinate;
import game.battleship.model.Player;
import game.battleship.model.Zone;
import game.battleship.ui.GameTextRenderer;
import game.battleship.util.CoordinateUtils;

import java.util.Map;
import java.util.Scanner;

public class HumanFiringStrategy implements FiringStrategy {
    private final Scanner scanner;

    public HumanFiringStrategy(Scanner scanner) {
        if (scanner == null) {
            throw new IllegalArgumentException("Scanner cannot be null");
        }
        this.scanner = scanner;
    }

    @Override
    public Coordinate chooseTarget(Player player, Map<Player, Zone> playerZoneMap) {

        while (true) {
            System.out.println(player.getName() + ", enter coordinates to fire (x y):");
            String input = scanner.nextLine().trim();

            try {
                return CoordinateUtils.parseCoordinate(input);

            } catch (InvalidInputException e) {
                GameTextRenderer.printErrorMessage("Invalid input: " + e.getMessage());
            }

        }
    }
}
