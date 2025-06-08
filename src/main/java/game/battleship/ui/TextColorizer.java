package game.battleship.ui;

import game.battleship.enums.ConsoleBackgroundColor;
import game.battleship.enums.ConsoleTextColor;

public class TextColorizer {
    private static final String RESET = "\u001B[0m";

    public static String colorize(String text, ConsoleTextColor textColor, ConsoleBackgroundColor backgroundColor) {
        StringBuilder sb = new StringBuilder();
        if (textColor != null) {
            sb.append(textColor);
        }
        if (backgroundColor != null) {
            sb.append(backgroundColor);
        }
        sb.append(text);
        sb.append(RESET);
        return sb.toString();
    }
}
