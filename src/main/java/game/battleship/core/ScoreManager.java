package game.battleship.core;

import game.battleship.model.FiringResult;
import game.battleship.model.Player;
import game.battleship.model.PlayerSessionStats;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreManager {

    private final Map<String, PlayerSessionStats> playerStats = new HashMap<>();

    public void registerPlayer(String playerId) {
        playerStats.putIfAbsent(playerId, new PlayerSessionStats());
    }

    public void update(String playerId, FiringResult firingResult) {
        PlayerSessionStats stats = playerStats.get(playerId);
        if (stats == null) return;

        if (!firingResult.isValidShot()) {
            return;
        }

        if (firingResult.isHit()) {
            stats.recordHit();
        } else {
            stats.recordMiss();
        }
    }

    public PlayerSessionStats getStatsForPlayer(String playerId) {
        return playerStats.getOrDefault(playerId, new PlayerSessionStats());
    }

    public Map<String, PlayerSessionStats> getAllStats() {
        return new HashMap<>(playerStats);
    }

    public void resetStats() {
        for (PlayerSessionStats stats : playerStats.values()) {
            stats.reset();
        }
    }

    public void printStats(List<Player> players) {
        System.out.println("===== GAME STATS =====");
        for (Player player : players) {
            PlayerSessionStats stats = playerStats.get(player.getId());
            if (stats != null) {
                System.out.println(player.getName() + " -> " + stats);
            }
        }
    }
}

