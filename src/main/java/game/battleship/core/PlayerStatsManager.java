package game.battleship.core;

import game.battleship.model.FiringResult;
import game.battleship.model.Player;
import game.battleship.model.PlayerSessionStats;
import game.battleship.model.Zone;
import game.battleship.strategy.score.ScoreStrategy;

import java.util.HashMap;
import java.util.Map;

public class PlayerStatsManager {
    private final ScoreStrategy scoreStrategy;
    private final Map<String, PlayerSessionStats> sessionStatsMap = new HashMap<>();
    private final Map<Player, Zone> playerZoneMap;

    public PlayerStatsManager(Map<Player, Zone> playerZoneMap, ScoreStrategy scoreStrategy) {
        this.playerZoneMap = playerZoneMap;
        this.scoreStrategy = scoreStrategy;
        initialize();
    }

    private void initialize() {
        for (Player player : playerZoneMap.keySet()) {
            sessionStatsMap.putIfAbsent(player.getId(), new PlayerSessionStats());
        }
    }

    public void updateNumberOfAliveShipsStatAllPlayers() {
        for (Map.Entry<Player, Zone> entry : playerZoneMap.entrySet()) {
            Player player = entry.getKey();
            Zone zone = entry.getValue();
            sessionStatsMap.get(player.getId())
                    .setNumberOfAliveShips(zone.getNumberOfAliveShips());
        }
    }

    public void updateStats(FiringResult firingResult) {
        Player attackingPlayer = firingResult.getAttackingPlayer();
        PlayerSessionStats attackingPlayerStats = this.sessionStatsMap.get(attackingPlayer.getId());
        if (attackingPlayerStats == null) return;

        if (!firingResult.isValidShot()) {
            return;
        }

        if (firingResult.isHit()) {
            attackingPlayerStats.recordHit();

            Player hitPlayer = firingResult.getHitPlayer();
            if (hitPlayer == null) return;

            PlayerSessionStats hitPlayerStats = this.sessionStatsMap.get(hitPlayer.getId());
            if (hitPlayerStats == null) return;

            Zone hitPlayerZone = playerZoneMap.get(hitPlayer);
            if (hitPlayerZone != null) {
                hitPlayerStats.setNumberOfAliveShips(hitPlayerZone.getNumberOfAliveShips());
            }
            hitPlayerStats.setScore(scoreStrategy.calculateScore(hitPlayerStats.getHits(),
                    hitPlayerStats.getMisses(), hitPlayerStats.getNumberOfAliveShips()));
        } else {
            attackingPlayerStats.recordMiss();
        }
        attackingPlayerStats.setScore(scoreStrategy.calculateScore(attackingPlayerStats.getHits(),
                attackingPlayerStats.getMisses(), attackingPlayerStats.getNumberOfAliveShips()));
    }

    public Map<String, PlayerSessionStats> getSessionStatsMap() {
        return sessionStatsMap;
    }

    public PlayerSessionStats getStatsForPlayer(String playerId) {
        return sessionStatsMap.getOrDefault(playerId, new PlayerSessionStats());
    }

    public Map<String, PlayerSessionStats> getAllStats() {
        return new HashMap<>(sessionStatsMap);
    }

    public void resetStats() {
        for (PlayerSessionStats stats : sessionStatsMap.values()) {
            stats.reset();
        }
    }
}
