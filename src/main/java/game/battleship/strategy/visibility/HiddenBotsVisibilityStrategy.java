package game.battleship.strategy.visibility;

import game.battleship.enums.CellViewType;
import game.battleship.enums.FiringStrategyType;
import game.battleship.model.Cell;
import game.battleship.model.Coordinate;
import game.battleship.model.Player;

public class HiddenBotsVisibilityStrategy implements GameVisibilityStrategy {

    @Override
    public CellViewType getSymbol(Cell cell, String shipId, Player owner, Player viewer) {
        if (cell == null) return CellViewType.EMPTY;

        boolean isBotZone = owner.getPlayerConfig().getFiringStrategyType() == FiringStrategyType.RANDOMIZED_BOT;

        if (cell.isDestroyed()) return CellViewType.DESTROYED;
        if (cell.isFired()) return CellViewType.FIRED;
        if (!isBotZone && shipId != null) return CellViewType.SHIP;
        return CellViewType.EMPTY;
    }
}
