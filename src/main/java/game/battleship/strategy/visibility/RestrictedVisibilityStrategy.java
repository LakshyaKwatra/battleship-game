package game.battleship.strategy.visibility;

import game.battleship.model.Cell;
import game.battleship.model.Coordinate;
import game.battleship.model.Player;
import game.battleship.model.Ship;

public class RestrictedVisibilityStrategy implements BattlefieldVisibilityStrategy {

    @Override
    public String getSymbol(Cell cell, Player cellOwner, Player viewingPlayer, Coordinate coordinate) {
        boolean isOwnZone = cellOwner.getId().equals(viewingPlayer.getId());
        Ship ship = cellOwner.getZone().getShip(coordinate);

        if (cell.isDestroyed()) return "X  ";
        if (cell.isFired()) return "O  ";
        if (isOwnZone && cell.isOccupied()) return ship.getId();
        return ".  ";
    }
}
