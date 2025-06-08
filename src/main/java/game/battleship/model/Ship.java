package game.battleship.model;

import java.util.Set;

public class Ship {
    private final String id;
    private final int size;
    private final Set<Coordinate> occupiedCoordinates;
    private final Zone zone;
    private boolean isAlive;

    public Ship(String id, int size, Set<Coordinate> occupiedCoordinates, Zone zone) {
        this.id = id;
        this.size = size;
        this.isAlive = true;
        this.zone = zone;
        this.occupiedCoordinates = occupiedCoordinates;
        initialize();
    }

    public String getId() {
        return id;
    }

    public int getSize() {
        return size;
    }


    public Set<Coordinate> getCoordinates() {
        return occupiedCoordinates;
    }

    public boolean containsCoordinate(Coordinate coordinate) {
        return occupiedCoordinates.contains(coordinate);
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void destroy() {
        for (Coordinate coordinate: occupiedCoordinates) {
            zone.getCell(coordinate).markDestroyed();
        }
        this.isAlive = false;
    }

    private void initialize() {
        for (Coordinate coordinate : occupiedCoordinates) {
            Cell cell = zone.getCell(coordinate);
            if (cell == null) {
                throw new IllegalArgumentException("Invalid coordinate for ship: " + coordinate);
            }
            cell.markOccupied();
        }
    }

    public Zone getZone() {
        return zone;
    }

    public Player getOwner() {
        return zone.getPlayer();
    }
}