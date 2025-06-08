package game.battleship.model.validation;

import game.battleship.model.Coordinate;
import game.battleship.model.Player;
import game.battleship.model.Zone;

import java.util.*;

public class FiringInputValidationRequest {

    private final Player shooter;
    private final Coordinate target;
    private final Map<Player, Zone> playerZoneMap;

    private FiringInputValidationRequest(Builder builder) {
        this.shooter = builder.shooter;
        this.target = builder.target;
        this.playerZoneMap = builder.playerZoneMap;
    }

    public Player getShooter() {
        return shooter;
    }

    public Coordinate getTarget() {
        return target;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Map<Player, Zone> getPlayerZoneMap() {
        return playerZoneMap;
    }

    public static class Builder {
        private Player shooter;
        private Coordinate target;
        private Map<Player, Zone> playerZoneMap = new HashMap<>();

        public Builder shooter(Player shooter) {
            this.shooter = shooter;
            return this;
        }

        public Builder target(Coordinate target) {
            this.target = target;
            return this;
        }

        public Builder playerZoneMap(Map<Player, Zone> playerZoneMap) {
            this.playerZoneMap = playerZoneMap != null ? playerZoneMap : new HashMap<>();
            return this;
        }

        public FiringInputValidationRequest build() {
            return new FiringInputValidationRequest(this);
        }
    }
}

