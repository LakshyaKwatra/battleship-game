package game.battleship.strategy.score;

public class DefaultScoreStrategy implements ScoreStrategy{

    @Override
    public int calculateScore(int hits, int misses, int numberOfShipsLeft) {
        int totalShots = hits + misses;
        double accuracy = totalShots > 0 ? (double) hits / totalShots : 0;

        int baseScore = hits * 10;
        int accuracyBonus = (int) (accuracy * 20);
        int survivalBonus = numberOfShipsLeft * 10;

        return baseScore + accuracyBonus + survivalBonus;
    }
}
