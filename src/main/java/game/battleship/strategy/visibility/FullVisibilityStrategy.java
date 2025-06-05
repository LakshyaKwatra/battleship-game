package game.battleship.strategy.visibility;

import game.battleship.model.Cell;
import game.battleship.model.Coordinate;
import game.battleship.model.Player;
import game.battleship.model.Ship;

public class FullVisibilityStrategy implements BattlefieldVisibilityStrategy {

    @Override
    public String getSymbol(Cell cell, Player cellOwner, Player viewingPlayer, Coordinate coordinate) {
        Ship ship = cellOwner.getZone().getShip(coordinate);
        if (cell.isDestroyed()) return "X  ";
        if (cell.isFired()) return "O  ";
        if (cell.isOccupied()) return ship.getId();
        return ".  ";
    }
}
