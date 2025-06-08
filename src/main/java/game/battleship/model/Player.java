package game.battleship.model;

import game.battleship.strategy.firing.FiringStrategy;

public class Player {
    private final String id;
    private final FiringStrategy firingStrategy;
    private final PlayerConfig playerConfig;


    public Player(String id, PlayerConfig playerConfig, FiringStrategy firingStrategy) {
        this.id = id;
        this.playerConfig = playerConfig;
        this.firingStrategy = firingStrategy;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return playerConfig.getName();
    }

    public FiringStrategy getFiringStrategy() {
        return firingStrategy;
    }

    public PlayerConfig getPlayerConfig() {
        return playerConfig;
    }

}
