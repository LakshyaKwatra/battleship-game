package game.battleship.model.validation;

import game.battleship.model.Coordinate;
import game.battleship.model.Zone;

import java.util.Set;

import java.util.HashSet;

public class FleetInputValidationRequest {

    private final Set<Coordinate> shipCoordinates;
    private final int battlefieldSize;
    private final Zone zone;

    private FleetInputValidationRequest(Builder builder) {
        this.shipCoordinates = builder.shipCoordinates;
        this.battlefieldSize = builder.battlefieldSize;
        this.zone = builder.zone;
    }

    public Set<Coordinate> getShipCoordinates() {
        return shipCoordinates;
    }

    public int getBattlefieldSize() {
        return battlefieldSize;
    }

    public Zone getZone() {
        return zone;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Set<Coordinate> shipCoordinates = new HashSet<>();
        private int battlefieldSize;
        private Zone zone;

        public Builder shipCoordinates(Set<Coordinate> shipCoordinates) {
            this.shipCoordinates = shipCoordinates != null ? shipCoordinates : new HashSet<>();
            return this;
        }

        public Builder battlefieldSize(int battlefieldSize) {
            this.battlefieldSize = battlefieldSize;
            return this;
        }

        public Builder zone(Zone zone) {
            this.zone = zone;
            return this;
        }

        public FleetInputValidationRequest build() {
            return new FleetInputValidationRequest(this);
        }
    }
}

