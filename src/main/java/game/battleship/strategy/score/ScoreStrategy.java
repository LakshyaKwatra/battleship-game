package game.battleship.strategy.score;

public interface ScoreStrategy {
    int calculateScore(int hits, int misses, int numberOfShipsLeft);
}
