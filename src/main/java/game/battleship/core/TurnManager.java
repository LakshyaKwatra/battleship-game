package game.battleship.core;

import game.battleship.model.Player;

import java.util.List;

public class TurnManager {
    private final List<Player> players;
    private int currentIndex = 0;

    public TurnManager(List<Player> players) {
        this.players = players;
    }

    public Player getCurrentPlayer() {
        return players.get(currentIndex);
    }

    public void moveToNextPlayer() {
        currentIndex = (currentIndex + 1) % players.size();
    }
}
