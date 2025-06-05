package game.battleship.model;

public class Cell {
    private final Coordinate coordinate;
    private boolean isFired;
    private boolean isOccupied;
    private boolean isDestroyed;

    public Cell(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.isDestroyed = false;
        this.isFired = false;
        this.isOccupied = false;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public boolean isFired() {
        return isFired;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void markFired() {
        this.isFired = true;
    }

    public void markOccupied() {
        this.isOccupied = true;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void markDestroyed() {
        isDestroyed = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell)) return false;
        Cell other = (Cell) o;
        return coordinate.equals(other.coordinate);
    }

    @Override
    public int hashCode() {
        return coordinate.hashCode();
    }

}

