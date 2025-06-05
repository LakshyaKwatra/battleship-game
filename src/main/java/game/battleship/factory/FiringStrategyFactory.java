package game.battleship.factory;

import game.battleship.enums.FiringStrategyType;
import game.battleship.strategy.firing.FiringStrategy;
import game.battleship.strategy.firing.HumanFiringStrategy;
import game.battleship.strategy.firing.RandomFiringStrategy;

import java.util.Scanner;

public class FiringStrategyFactory {
    public static FiringStrategy getStrategy(FiringStrategyType strategyType, Scanner scanner) {
        switch (strategyType) {
            case HUMAN:
                return new HumanFiringStrategy(scanner);
            case RANDOM:
                return new RandomFiringStrategy();
            default:
                throw new IllegalArgumentException("Unknown strategy type: " + strategyType);
        }
    }
}

