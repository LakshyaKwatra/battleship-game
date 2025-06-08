package game.battleship.strategy.visibility;

import game.battleship.enums.CellViewType;
import game.battleship.model.Cell;
import game.battleship.model.Coordinate;
import game.battleship.model.Player;

public interface GameVisibilityStrategy {
    CellViewType getSymbol(Cell cell, String shipId, Player owner, Player viewer);
}
