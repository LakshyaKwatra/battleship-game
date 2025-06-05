package game.battleship.model;

public class FiringResult {
    private final boolean validShot;
    private final boolean hit;
    private final Player hitPlayer;
    private final Ship hitShip;
    private final String message;

    public FiringResult(boolean validShot, boolean hit, Player hitPlayer, Ship hitShip, String message) {
        this.validShot = validShot;
        this.hit = hit;
        this.hitPlayer = hitPlayer;
        this.hitShip = hitShip;
        this.message = message;
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

    public String getMessage() {
        return message;
    }
}
