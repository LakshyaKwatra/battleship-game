package game.battleship.ui;

import game.battleship.core.GameSession;
import game.battleship.model.*;
import game.battleship.strategy.visibility.BattlefieldVisibilityStrategy;

import java.util.*;

public class BattlefieldRenderer {
    private final GameSession gameSession;
    private final BattlefieldVisibilityStrategy viewStrategy;

    public BattlefieldRenderer(GameSession session, BattlefieldVisibilityStrategy strategy) {
        this.gameSession = session;
        this.viewStrategy = strategy;
    }

    public void render() {
        Map<Coordinate, Player> coordToOwner = new HashMap<>();
        Map<Coordinate, Cell> coordToCell = new HashMap<>();
        Set<Integer> verticalSeparators = new HashSet<>();

        List<Player> players = gameSession.getPlayers();
        int battlefieldSize = gameSession.getBattlefieldSize();
        Player viewer = gameSession.getCurrentPlayer();

        for (Player player : players) {
            Zone zone = player.getZone();
            verticalSeparators.add(zone.getEndX() + 1);
            for (Cell cell : zone.getCells()) {
                coordToOwner.put(cell.getCoordinate(), player);
                coordToCell.put(cell.getCoordinate(), cell);
            }
        }

        printColumnHeaders(battlefieldSize, verticalSeparators);
        printGrid(battlefieldSize, coordToCell, coordToOwner, viewer, verticalSeparators);
        printPlayerLabels(players);
    }

    private void printColumnHeaders(int size, Set<Integer> separators) {
        System.out.print("   ");
        for (int i = 0; i < size; i++) {
            System.out.printf("%2d    ", i);
            if (separators.contains(i + 1)) System.out.print(" |   ");
        }
        System.out.println();
    }

    private void printGrid(int size, Map<Coordinate, Cell> cellMap, Map<Coordinate, Player> ownerMap,
                           Player viewer, Set<Integer> separators) {
        for (int col = 0; col < size; col++) {
            System.out.printf("%2d", col);
            for (int row = 0; row < size; row++) {
                Coordinate coord = new Coordinate(row, col);
                Cell cell = cellMap.get(coord);
                Player owner = ownerMap.get(coord);
                String symbol = viewStrategy.getSymbol(cell, owner, viewer, coord);
                System.out.print("  " + symbol);
                if (separators.contains(row + 1)) System.out.print("   |   ");
            }
            System.out.println();
        }
    }

    private void printPlayerLabels(List<Player> players) {
        System.out.print("    ");
        for (Player player : players) {
            int width = (player.getZone().getEndX() - player.getZone().getStartX() + 1) * 7;
            String name = centerText(player.getName(), width);
            System.out.print(name + "  ");
        }
        System.out.println();
    }

    private String centerText(String text, int width) {
        int pad = Math.max(0, (width - text.length()) / 2);
        return " ".repeat(pad) + text + " ".repeat(Math.max(1, width - pad - text.length()));
    }
}
