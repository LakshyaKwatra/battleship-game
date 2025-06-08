package game.battleship.ui;

import game.battleship.config.GameTheme;
import game.battleship.enums.CellViewType;
import game.battleship.mapper.SymbolMapper;
import game.battleship.model.*;
import game.battleship.strategy.visibility.GameVisibilityStrategy;
import game.battleship.util.UIUtils;

import java.util.Map;

public class CellSymbolRenderer {

    public static String renderSymbol(Cell cell, String shipId, Player owner, Player viewer,
                                      GameVisibilityStrategy strategy,
                                      Map<Player, Zone> playerZoneMap) {

        CellViewType cellViewType = strategy.getSymbol(cell, shipId, owner, viewer);

        CellRenderInfo cellRenderInfo = SymbolMapper.map(cellViewType,
                cell, shipId, playerZoneMap.get(owner).isDefeated());


        return TextColorizer.colorize(UIUtils.padCenter(cellRenderInfo.getSymbol(), GameTheme.CELL_WIDTH),
                cellRenderInfo.getTextColor(), cellRenderInfo.getBackgroundColor());
    }
}


