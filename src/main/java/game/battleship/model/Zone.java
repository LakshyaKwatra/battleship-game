package game.battleship.model;

import java.util.*;

public class Zone {
    private final int startX;
    private final int endX;
    private final int height;
    private final Player player;
    private final Map<Coordinate, Ship> coordinateShipMap = new HashMap<>();
    private final Map<Coordinate, Cell> cellsMap = new HashMap<>();
    private final List<Ship> ships = new ArrayList<>();

    private int numberOfAliveShips = 0;
    private boolean isDefeated = false;

    public Zone(int height, int startX, int endX, Player player) {
        this.height = height;
        this.startX = startX;
        this.endX = endX;
        this.player = player;
        initializeCells();
    }

    public int getStartX() {
        return startX;
    }

    public int getEndX() {
        return endX;
    }

    public Cell getCell(Coordinate coordinate) {
        return cellsMap.get(coordinate);
    }

    public Map<Coordinate, Cell> getCellsMap() {
        return cellsMap;
    }

    public List<Cell> getCells() {
        return new ArrayList<>(cellsMap.values());
    }

    public boolean isDefeated() {
        return isDefeated;
    }


    public Ship getShipAt(Coordinate coordinate) {
        return coordinateShipMap.get(coordinate);
    }

    public List<Ship> getShips() {
        return Collections.unmodifiableList(ships);
    }

    public void addShip(Ship ship) {
        ships.add(ship);
        numberOfAliveShips++;
        for (Coordinate coordinate : ship.getCoordinates()) {
            coordinateShipMap.put(coordinate, ship);
        }
    }

    public boolean containsCoordinate(Coordinate coordinate) {
        return cellsMap.containsKey(coordinate);
    }

    public Set<Coordinate> getCoordinates() {
        return Collections.unmodifiableSet(cellsMap.keySet());
    }

    public boolean overlapsWithExistingShips(Set<Coordinate> newCoordinates) {
        return newCoordinates.stream().anyMatch(coordinateShipMap::containsKey);
    }

    public boolean overlapsWithExistingShips(Coordinate newCoordinate) {
        return coordinateShipMap.containsKey(newCoordinate);
    }

    private void initializeCells() {
        for (int x = startX; x <= endX; x++) {
            for (int y = 0; y < height; y++) {
                Coordinate coordinate = new Coordinate(x, y);
                cellsMap.put(coordinate, new Cell(coordinate));
            }
        }
    }

    public int getNumberOfAliveShips() {
        return numberOfAliveShips;
    }

    public void destroyShip(Ship ship) {
        if (ships.contains(ship) && ship.isAlive()) {
            ship.destroy();
            numberOfAliveShips = Math.max(0, numberOfAliveShips - 1);
            if(numberOfAliveShips == 0) {
                isDefeated = true;
            }
        }
    }

    public Player getPlayer() {
        return player;
    }
}
