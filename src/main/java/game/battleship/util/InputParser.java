package game.battleship.util;

import game.battleship.exception.InvalidInputException;
import game.battleship.model.Coordinate;

public class InputParser {

    public static Coordinate parseCoordinate(String input, int battlefieldSize) throws InvalidInputException {
        String[] parts = input.trim().split("\\s+");
        if (parts.length != 2) {
            throw new InvalidInputException("Expected exactly two integers separated by space.");
        }
        try {
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            return new Coordinate(x, y);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid number format.");
        }
    }
}

