package game.battleship.model;

import game.battleship.enums.ConsoleBackgroundColor;
import game.battleship.enums.ConsoleTextColor;

public class CellRenderInfo {
    private final String symbol;
    private final ConsoleTextColor textColor;
    private final ConsoleBackgroundColor backgroundColor;

    public CellRenderInfo(String symbol,
                          ConsoleTextColor textColor,
                          ConsoleBackgroundColor backgroundColor) {
        this.symbol = symbol;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
    }

    public String getSymbol() {
        return symbol;
    }

    public ConsoleTextColor getTextColor() {
        return textColor;
    }

    public ConsoleBackgroundColor getBackgroundColor() {
        return backgroundColor;
    }
}
