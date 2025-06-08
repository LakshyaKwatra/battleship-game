package game.battleship.strategy.visibility;

import game.battleship.enums.CellViewType;
import game.battleship.model.Cell;
import game.battleship.model.Coordinate;
import game.battleship.model.Player;

public class RestrictedVisibilityStrategy implements GameVisibilityStrategy {

    @Override
    public CellViewType getSymbol(Cell cell, String shipId, Player cellOwner, Player viewingPlayer) {
        if (cell == null) return CellViewType.EMPTY;
        boolean isOwnZone = viewingPlayer.getId().equals(cellOwner.getId());
        if (cell.isDestroyed()) return CellViewType.DESTROYED;
        if (cell.isFired()) return CellViewType.FIRED;
        if (isOwnZone && shipId != null) return CellViewType.SHIP;
        return CellViewType.EMPTY;
    }
}
