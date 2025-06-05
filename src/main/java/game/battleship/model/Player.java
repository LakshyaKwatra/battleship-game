package game.battleship.model;

import game.battleship.strategy.firing.FiringStrategy;

public class Player {
    private final String id;
    private final String name;
    private Zone zone;
    private final FiringStrategy firingStrategy;


    public Player(String id, String name, Zone zone, FiringStrategy firingStrategy) {
        this.id = id;
        this.name = name;
        this.zone = zone;
        this.firingStrategy = firingStrategy;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public FiringStrategy getFiringStrategy() {
        return firingStrategy;
    }

}
