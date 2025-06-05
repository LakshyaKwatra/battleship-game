package game.battleship.model;

import game.battleship.enums.FiringStrategyType;

public class PlayerConfig {
    private final String name;
    private final FiringStrategyType firingStrategyType;

    public PlayerConfig(String name, FiringStrategyType firingStrategyType) {
        this.name = name;
        this.firingStrategyType = firingStrategyType;
    }

    public String getName() {
        return name;
    }
    public FiringStrategyType getFiringStrategyType() {
        return firingStrategyType;
    }
}

