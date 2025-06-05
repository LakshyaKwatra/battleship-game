import game.battleship.core.ScoreManager;
import game.battleship.model.FiringResult;
import game.battleship.model.PlayerSessionStats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreManagerTest {

    private ScoreManager scoreManager;
    private final String playerId = "P1";

    @BeforeEach
    void setup() {
        scoreManager = new ScoreManager();
        scoreManager.registerPlayer(playerId);
    }

    @Test
    void testRegisterPlayerAndGetStats() {
        PlayerSessionStats stats = scoreManager.getStatsForPlayer(playerId);
        assertNotNull(stats);
        assertEquals(0, stats.getHits());
        assertEquals(0, stats.getMisses());
    }

    @Test
    void testUpdateWithHit() {
        FiringResult hitResult = new FiringResult(true, true, null, null, "Hit!");
        scoreManager.update(playerId, hitResult);

        PlayerSessionStats stats = scoreManager.getStatsForPlayer(playerId);
        assertEquals(1, stats.getHits());
        assertEquals(0, stats.getMisses());
    }

    @Test
    void testUpdateWithMiss() {
        FiringResult missResult = new FiringResult(true, false, null, null, "Miss!");
        scoreManager.update(playerId, missResult);

        PlayerSessionStats stats = scoreManager.getStatsForPlayer(playerId);
        assertEquals(0, stats.getHits());
        assertEquals(1, stats.getMisses());
    }

    @Test
    void testUpdateWithInvalidShot() {
        FiringResult invalidResult = new FiringResult(false, false, null, null, "Invalid shot");
        scoreManager.update(playerId, invalidResult);

        PlayerSessionStats stats = scoreManager.getStatsForPlayer(playerId);
        assertEquals(0, stats.getHits());
        assertEquals(0, stats.getMisses());
    }

    @Test
    void testResetStats() {
        FiringResult hitResult = new FiringResult(true, true, null, null, "Hit!");
        FiringResult missResult = new FiringResult(true, false, null, null, "Miss!");

        scoreManager.update(playerId, hitResult);
        scoreManager.update(playerId, missResult);

        PlayerSessionStats statsBeforeReset = scoreManager.getStatsForPlayer(playerId);
        assertEquals(1, statsBeforeReset.getHits());
        assertEquals(1, statsBeforeReset.getMisses());

        scoreManager.resetStats();

        PlayerSessionStats statsAfterReset = scoreManager.getStatsForPlayer(playerId);
        assertEquals(0, statsAfterReset.getHits());
        assertEquals(0, statsAfterReset.getMisses());
    }

}

