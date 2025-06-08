package game.battleship.config;

import game.battleship.enums.ConsoleBackgroundColor;
import game.battleship.enums.ConsoleTextColor;

public class GameTheme {
    public static final int CELL_SPACING = 0;
    public static final int BORDER_WIDTH = 3;


    // Colors and Styles
    public static final ConsoleTextColor BORDER_COLOR = ConsoleTextColor.BRIGHT_CYAN;
    public static final ConsoleTextColor ROW_COLUMN_LABEL_COLOR = ConsoleTextColor.BRIGHT_WHITE;
    public static final ConsoleTextColor SHIP_COLOR = ConsoleTextColor.BRIGHT_YELLOW;
    public static final ConsoleBackgroundColor SHIP_BACKGROUND_COLOR = ConsoleBackgroundColor.BG_BRIGHT_BLACK;
    public static final ConsoleTextColor CELL_COLOR = ConsoleTextColor.BRIGHT_BLACK;
    public static final ConsoleTextColor FIRED_CELL_COLOR = ConsoleTextColor.RED;
    public static final ConsoleTextColor DESTROYED_SHIP_COLOR = ConsoleTextColor.BRIGHT_RED;
    public static final ConsoleTextColor PLAYER_LABEL_COLOR = ConsoleTextColor.BRIGHT_WHITE;
    public static final ConsoleTextColor PLAYER_CURRENT_LABEL_COLOR = ConsoleTextColor.BLACK;
    public static final ConsoleTextColor TITLE_BORDER_COLOR = ConsoleTextColor.BRIGHT_YELLOW;
    public static final ConsoleTextColor TITLE_TEXT_COLOR = ConsoleTextColor.BRIGHT_BLUE;
    public static final ConsoleTextColor HIT_TEXT_COLOR = ConsoleTextColor.BRIGHT_GREEN;
    public static final ConsoleTextColor MISS_TEXT_COLOR = ConsoleTextColor.RED;
    public static final ConsoleBackgroundColor PLAYER_LABEL_BACKGROUND_COLOR = ConsoleBackgroundColor.BG_BLACK;
    public static final ConsoleBackgroundColor PLAYER_CURRENT_LABEL_BACKGROUND_COLOR = ConsoleBackgroundColor.BG_GREEN;
    public static final ConsoleBackgroundColor PLAYER_DEAD_LABEL_BACKGROUND_COLOR = ConsoleBackgroundColor.BG_RED;
    public static final ConsoleBackgroundColor DEFEATED_ZONE_BACKGROUND_COLOR = ConsoleBackgroundColor.BG_BLACK;



    // Borders
    public static final String BORDER_TOP_LEFT_CORNER = "┌";
    public static final String BORDER_TOP_RIGHT_CORNER = "┐";
    public static final String BORDER_BOTTOM_LEFT_CORNER = "└";
    public static final String BORDER_BOTTOM_RIGHT_CORNER = "┘";
    public static final String BORDER_HORIZONTAL = "─";
    public static final String BORDER_VERTICAL = "│";
    public static final String BORDER_CONNECTOR_TOP = "┬";
    public static final String BORDER_CONNECTOR_BOTTOM = "┴";
    public static final String BORDER_CONNECTOR_LEFT = "├";
    public static final String BORDER_CONNECTOR_RIGHT = "┤";
    public static final String BORDER_CROSS_CONNECTOR = "┼";
    public static final int CELL_WIDTH = 5;
}
