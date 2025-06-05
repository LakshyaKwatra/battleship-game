package game.battleship.strategy.visibility;

import game.battleship.model.Cell;
import game.battleship.model.Coordinate;
import game.battleship.model.Player;

public interface BattlefieldVisibilityStrategy {
    String getSymbol(Cell cell, Player cellOwner, Player viewingPlayer, Coordinate coordinate);
}
