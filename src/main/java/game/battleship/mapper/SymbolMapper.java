package game.battleship.mapper;

import game.battleship.enums.CellViewType;
import game.battleship.enums.ConsoleBackgroundColor;
import game.battleship.enums.ConsoleTextColor;
import game.battleship.model.Cell;
import game.battleship.ui.CellRenderInfo;
import game.battleship.config.GameTheme;

public class SymbolMapper {

    public static CellRenderInfo map(CellViewType cellViewType, Cell cell, String shipId, boolean isInDefeatedZone) {
        String symbol = ".";
        ConsoleTextColor textColor = GameTheme.CELL_COLOR;
        ConsoleBackgroundColor bgColor = null;

        switch (cellViewType) {
            case EMPTY -> symbol = ".";
            case DESTROYED -> {
                symbol = "X";
                textColor = GameTheme.DESTROYED_SHIP_COLOR;
                bgColor = GameTheme.SHIP_BACKGROUND_COLOR;
                if (cell != null && cell.isFired()) {
                    bgColor = ConsoleBackgroundColor.BG_BLACK;
                }
            }
            case FIRED -> {
                symbol = "O";
                textColor = GameTheme.FIRED_CELL_COLOR;
            }
            case SHIP -> {
                symbol = shipId != null ? shipId : "#";
                textColor = GameTheme.SHIP_COLOR;
                bgColor = GameTheme.SHIP_BACKGROUND_COLOR;
            }
            default -> {
                symbol = ".";
            }
        }

        if (isInDefeatedZone) {
            symbol = "X";
            if (cellViewType == CellViewType.DESTROYED) {
                bgColor = GameTheme.DEFEATED_ZONE_BACKGROUND_COLOR;
            }
        }

        return new CellRenderInfo(symbol, textColor, bgColor);
    }
}

