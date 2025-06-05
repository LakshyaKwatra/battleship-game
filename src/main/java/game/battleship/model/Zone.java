package game.battleship.model;

import java.util.*;

public class Zone {
    private final int startX;
    private final int endX;
    private final int height;
    private final int width;
    private final Map<Coordinate, Cell> cellsMap = new HashMap<>();
    private final List<Ship> ships = new ArrayList<>();

    public Zone(int height, int startX, int endX) {
        this.height = height;
        this.startX = startX;
        this.endX = endX;
        this.width = endX - startX + 1;
        initializeCells();
    }

    public int getStartX() {
        return startX;
    }

    public int getEndX() {
        return endX;
    }

    public int getWidth() {
        return width;
    }

    public Cell getCell(Coordinate coordinate) {
        return cellsMap.get(coordinate);
    }

    public Map<Coordinate, Cell> getCellsMap() {
        return cellsMap;
    }

    public List<Cell> getCells() {
        return cellsMap.values().stream().toList();
    }



    public Ship getShip(Coordinate coordinate) {
        for (Ship ship : ships) {
            if (ship.containsCoordinate(coordinate)) return ship;
        }
        return null;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public void addShip(Ship ship) {
        ships.add(ship);
    }

    public boolean containsCoordinate(Coordinate coordinate) {
        return cellsMap.containsKey(coordinate);
    }

    public Set<Coordinate> getCoordinates() {
        return cellsMap.keySet();
    }

    public boolean hasUndestroyedShips() {
        return ships.stream().anyMatch(ship -> !ship.isDestroyed());
    }

    public boolean overlapsWithExistingShips(Set<Coordinate> newCoordinates) {
        return ships.stream()
                .anyMatch(existingShip -> existingShip.getCoordinates().stream().anyMatch(newCoordinates::contains));
    }

    public boolean overlapsWithExistingShips(Coordinate newCoordinate) {
        return ships.stream()
                .anyMatch(existingShip -> existingShip.getCoordinates().contains(newCoordinate));
    }

    private void initializeCells() {
        for (int x = startX; x <= endX; x++) {
            for (int y = 0; y < height; y++) {
                Coordinate coordinate = new Coordinate(x, y);
                cellsMap.put(coordinate, new Cell(coordinate));
            }
        }
    }

}
