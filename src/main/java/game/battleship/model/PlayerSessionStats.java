package game.battleship.model;

public class PlayerSessionStats {

    private int hits;
    private int misses;

    public void recordHit() {
        hits++;
    }

    public void recordMiss() {
        misses++;
    }

    public int getHits() {
        return hits;
    }

    public int getMisses() {
        return misses;
    }

    public void reset() {
        hits = 0;
        misses = 0;
    }

    @Override
    public String toString() {
        return String.format("Hits: %d, Misses: %d", hits, misses);
    }
}
