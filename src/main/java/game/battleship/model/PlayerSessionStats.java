package game.battleship.model;

public class PlayerSessionStats {

    private int hits;
    private int misses;
    private int numberOfAliveShips;
    private int score;

    public PlayerSessionStats() {
        hits = 0;
        misses = 0;
        numberOfAliveShips = 0;
        score = 0;
    }

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

    public int getNumberOfAliveShips() {
        return numberOfAliveShips;
    }

    public void setNumberOfAliveShips(int numberOfAliveShips) {
        this.numberOfAliveShips = numberOfAliveShips;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
