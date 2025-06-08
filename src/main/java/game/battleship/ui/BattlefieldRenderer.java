package game.battleship.ui;

import game.battleship.model.*;
import game.battleship.strategy.visibility.GameVisibilityStrategy;

import java.util.List;
import java.util.Map;

public class BattlefieldRenderer {

    private final GameVisibilityStrategy visibilityStrategy;
    private final int size;
    private final int rowLabelWidth;
    private final Map<Player, Zone> playerZoneMap;
    private final Map<Coordinate, Player> coordinatePlayerMap;
    private final List<Player> players;

    public BattlefieldRenderer(GameVisibilityStrategy strategy, int size, Map<Player, Zone> playerZoneMap, Map<Coordinate, Player> coordinatePlayerMap, List<Player> players) {
        this.visibilityStrategy = strategy;
        this.size = size;
        this.playerZoneMap = playerZoneMap;
        this.coordinatePlayerMap = coordinatePlayerMap;
        this.players = players;
        this.rowLabelWidth = String.valueOf(size - 1).length();
    }

    public void render(Player viewer) {
        GridRenderer.printColumnHeaders(size, rowLabelWidth, coordinatePlayerMap);
        GridRenderer.printTopBorder(size, rowLabelWidth, coordinatePlayerMap);
        GridRenderer.printGrid(size, rowLabelWidth, coordinatePlayerMap, playerZoneMap, viewer, visibilityStrategy);
        GridRenderer.printBottomBorder(size, rowLabelWidth, coordinatePlayerMap);
        PlayerLabelRenderer.printPlayerLabels(players, playerZoneMap, viewer, rowLabelWidth);
    }
}
