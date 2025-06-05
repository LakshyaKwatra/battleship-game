package game.battleship.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Ship {
    private final String id;
    private final int size;
    private final Map<Coordinate, Cell> cells = new HashMap<>();
    private boolean destroyed;

    public Ship(String id, int size, Set<Coordinate> coordinates, Zone zone) {
        this.id = id;
        this.size = size;
        this.destroyed = false;

        initialize(coordinates, zone);
    }

    public String getId() {
        return id;
    }

    public int getSize() {
        return size;
    }

    public Set<Coordinate> getCoordinates() {
        return cells.keySet();
    }

    public boolean containsCoordinate(Coordinate coordinate) {
        return cells.containsKey(coordinate);
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void destroy() {
        cells.values().forEach(Cell::markDestroyed);
        this.destroyed = true;
    }

    private void initialize(Set<Coordinate> coordinates, Zone zone) {
        for (Coordinate coordinate : coordinates) {
            Cell cell = zone.getCell(coordinate);
            if (cell == null) {
                throw new IllegalArgumentException("Invalid coordinate for ship: " + coordinate);
            }

            cell.markOccupied();
            this.cells.put(coordinate, cell);
        }
    }

}