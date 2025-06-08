package game.battleship.model;

public class FiringResult {
    private final boolean validShot;
    private final boolean hit;
    private final Player attackingPlayer;
    private final Player hitPlayer;
    private final Ship hitShip;
    private final String errorMessage;

    private FiringResult(Builder builder) {
        this.validShot = builder.validShot;
        this.hit = builder.hit;
        this.attackingPlayer = builder.attackingPlayer;
        this.hitPlayer = builder.hitPlayer;
        this.hitShip = builder.hitShip;
        this.errorMessage = builder.errorMessage;
    }

    public boolean isValidShot() {
        return validShot;
    }

    public boolean isHit() {
        return hit;
    }

    public Player getHitPlayer() {
        return hitPlayer;
    }

    public Ship getHitShip() {
        return hitShip;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Player getAttackingPlayer() {
        return attackingPlayer;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private boolean validShot;
        private boolean hit;
        private Player attackingPlayer;
        private Player hitPlayer;
        private Ship hitShip;
        private String errorMessage;

        public Builder validShot(boolean validShot) {
            this.validShot = validShot;
            return this;
        }

        public Builder hit(boolean hit) {
            this.hit = hit;
            return this;
        }

        public Builder attackingPlayer(Player attackingPlayer) {
            this.attackingPlayer = attackingPlayer;
            return this;
        }

        public Builder hitPlayer(Player hitPlayer) {
            this.hitPlayer = hitPlayer;
            return this;
        }

        public Builder hitShip(Ship hitShip) {
            this.hitShip = hitShip;
            return this;
        }

        public Builder errorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public FiringResult build() {
            return new FiringResult(this);
        }
    }
}
