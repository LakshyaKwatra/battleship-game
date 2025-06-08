package game.battleship.ui;

import game.battleship.config.GameTheme;
import game.battleship.model.*;
import game.battleship.strategy.visibility.GameVisibilityStrategy;
import game.battleship.util.UIUtils;

import java.util.Map;

public class GridRenderer {

    public static void printColumnHeaders(int size, int labelWidth, Map<Coordinate, Player> ownerMap) {
        System.out.println();
        System.out.print(" ".repeat(labelWidth + GameTheme.CELL_SPACING + GameTheme.BORDER_WIDTH));
        for (int col = 0; col < size; col++) {
            System.out.print(TextColorizer.colorize(UIUtils.padCenter(String.valueOf(col), GameTheme.CELL_WIDTH),
                    GameTheme.ROW_COLUMN_LABEL_COLOR, null));
            if (col < size - 1) {
                Coordinate c1 = new Coordinate(col, 0), c2 = new Coordinate(col + 1, 0);
                System.out.print(isZoneBoundary(c1, c2, ownerMap) ? " ".repeat(GameTheme.BORDER_WIDTH) : " ".repeat(GameTheme.CELL_SPACING));
            }
        }
        System.out.println();
    }

    public static void printTopBorder(int size, int labelWidth, Map<Coordinate, Player> ownerMap) {
        System.out.print(" ".repeat(labelWidth + GameTheme.CELL_SPACING));
        System.out.print(" ".repeat((GameTheme.BORDER_WIDTH - 1) / 2));
        System.out.print(TextColorizer.colorize(GameTheme.BORDER_TOP_LEFT_CORNER, GameTheme.BORDER_COLOR, null));
        System.out.print(TextColorizer.colorize(GameTheme.BORDER_HORIZONTAL.repeat(GameTheme.BORDER_WIDTH / 2), GameTheme.BORDER_COLOR, null));
        for (int col = 0; col < size; col++) {
            System.out.print(TextColorizer.colorize(GameTheme.BORDER_HORIZONTAL.repeat(GameTheme.CELL_WIDTH), GameTheme.BORDER_COLOR, null));
            if (col < size - 1) {
                Coordinate cur = new Coordinate(col, 0), next = new Coordinate(col + 1, 0);
                System.out.print(isZoneBoundary(cur, next, ownerMap)
                        ? TextColorizer.colorize(GameTheme.BORDER_HORIZONTAL.repeat((GameTheme.BORDER_WIDTH - 1) / 2)
                        + GameTheme.BORDER_CONNECTOR_TOP
                        + GameTheme.BORDER_HORIZONTAL.repeat(GameTheme.BORDER_WIDTH / 2), GameTheme.BORDER_COLOR, null)
                        : TextColorizer.colorize(GameTheme.BORDER_HORIZONTAL.repeat(GameTheme.CELL_SPACING), GameTheme.BORDER_COLOR, null));
            }
        }
        System.out.print(TextColorizer.colorize(GameTheme.BORDER_HORIZONTAL.repeat((GameTheme.BORDER_WIDTH - 1) / 2), GameTheme.BORDER_COLOR, null));
        System.out.println(TextColorizer.colorize(GameTheme.BORDER_TOP_RIGHT_CORNER, GameTheme.BORDER_COLOR, null));
    }

    public static void printBottomBorder(int size, int labelWidth, Map<Coordinate, Player> ownerMap) {
        System.out.print(" ".repeat(labelWidth + GameTheme.CELL_SPACING));
        System.out.print(" ".repeat((GameTheme.BORDER_WIDTH - 1) / 2));
        System.out.print(TextColorizer.colorize(GameTheme.BORDER_BOTTOM_LEFT_CORNER, GameTheme.BORDER_COLOR, null));
        System.out.print(TextColorizer.colorize(GameTheme.BORDER_HORIZONTAL.repeat(GameTheme.BORDER_WIDTH / 2), GameTheme.BORDER_COLOR, null));
        for (int col = 0; col < size; col++) {
            System.out.print(TextColorizer.colorize(GameTheme.BORDER_HORIZONTAL.repeat(GameTheme.CELL_WIDTH), GameTheme.BORDER_COLOR, null));
            if (col < size - 1) {
                Coordinate cur = new Coordinate(col, 0), next = new Coordinate(col + 1, 0);
                System.out.print(isZoneBoundary(cur, next, ownerMap)
                        ? TextColorizer.colorize(GameTheme.BORDER_HORIZONTAL.repeat((GameTheme.BORDER_WIDTH - 1) / 2)
                        + GameTheme.BORDER_CONNECTOR_BOTTOM
                        + GameTheme.BORDER_HORIZONTAL.repeat(GameTheme.BORDER_WIDTH / 2), GameTheme.BORDER_COLOR, null)
                        : TextColorizer.colorize(GameTheme.BORDER_HORIZONTAL.repeat(GameTheme.CELL_SPACING), GameTheme.BORDER_COLOR, null));
            }
        }
        System.out.print(TextColorizer.colorize(GameTheme.BORDER_HORIZONTAL.repeat((GameTheme.BORDER_WIDTH - 1) / 2), GameTheme.BORDER_COLOR, null));
        System.out.println(TextColorizer.colorize(GameTheme.BORDER_BOTTOM_RIGHT_CORNER, GameTheme.BORDER_COLOR, null));
    }

    public static void printGrid(int size, int labelWidth,
                                 Map<Coordinate, Player> coordinatePlayerMap,
                                 Map<Player, Zone> playerZoneMap,
                                 Player viewer,
                                 GameVisibilityStrategy strategy) {

        for (int row = 0; row < size; row++) {
            System.out.printf(TextColorizer.colorize("%" + labelWidth + "d", GameTheme.ROW_COLUMN_LABEL_COLOR, null), row);
            System.out.print(" ".repeat(GameTheme.CELL_SPACING) + TextColorizer.colorize(UIUtils.padCenter(GameTheme.BORDER_VERTICAL, GameTheme.BORDER_WIDTH), GameTheme.BORDER_COLOR, null));

            for (int col = 0; col < size; col++) {
                Coordinate coord = new Coordinate(col, row);
                Player owner = coordinatePlayerMap.get(coord);
                Zone zone = (owner != null) ? playerZoneMap.get(owner) : null;

                Cell cell = (zone != null) ? zone.getCell(coord) : null;
                Ship ship = (zone != null) ? zone.getShipAt(coord) : null;
                String shipId = (ship != null) ? ship.getId() : null;

                String symbol = CellSymbolRenderer.renderSymbol(cell, shipId, owner, viewer, strategy, playerZoneMap);
                System.out.print(symbol);

                if (col < size - 1) {
                    Coordinate next = new Coordinate(col + 1, row);
                    Player nextOwner = coordinatePlayerMap.get(next);

                    if (isZoneBoundary(coord, next, coordinatePlayerMap)) {
                        System.out.print(TextColorizer.colorize(UIUtils.padCenter(GameTheme.BORDER_VERTICAL, GameTheme.BORDER_WIDTH), GameTheme.BORDER_COLOR, null));
                    } else {
                        String sid1 = (ship != null) ? ship.getId() : null;
                        String sid2 = null;
                        Zone nextZone = (nextOwner != null) ? playerZoneMap.get(nextOwner) : null;
                        Ship nextShip = (nextZone != null) ? nextZone.getShipAt(next) : null;
                        if (nextShip != null) sid2 = nextShip.getId();

                        if (sid1 != null && sid1.equals(sid2)) {
                            System.out.print(TextColorizer.colorize(" ".repeat(GameTheme.CELL_SPACING), null, GameTheme.SHIP_BACKGROUND_COLOR));
                        } else {
                            System.out.print(" ".repeat(GameTheme.CELL_SPACING));
                        }
                    }
                }
            }

            System.out.println(TextColorizer.colorize(UIUtils.padCenter(GameTheme.BORDER_VERTICAL, GameTheme.BORDER_WIDTH), GameTheme.BORDER_COLOR, null));
        }
    }


    private static boolean isZoneBoundary(Coordinate c1, Coordinate c2, Map<Coordinate, Player> map) {
        Player p1 = map.get(c1), p2 = map.get(c2);
        return p1 != null && p2 != null && !p1.getId().equals(p2.getId());
    }
}
