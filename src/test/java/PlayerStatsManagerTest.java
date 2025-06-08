import game.battleship.core.PlayerStatsManager;
import game.battleship.model.*;
import game.battleship.strategy.score.ScoreStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerStatsManagerTest {

    private Player player1;
    private Player player2;
    private Zone zone1;
    private Zone zone2;
    private Map<Player, Zone> playerZoneMap;
    private ScoreStrategy scoreStrategy;
    private PlayerStatsManager statsManager;

    @BeforeEach
    void setUp() {
        player1 = new Player("p1", new PlayerConfig("Alice", null), null);
        player2 = new Player("p2", new PlayerConfig("Bob", null), null);

        zone1 = mock(Zone.class);
        when(zone1.getNumberOfAliveShips()).thenReturn(3);

        zone2 = mock(Zone.class);
        when(zone2.getNumberOfAliveShips()).thenReturn(2);

        playerZoneMap = new HashMap<>();
        playerZoneMap.put(player1, zone1);
        playerZoneMap.put(player2, zone2);

        scoreStrategy = mock(ScoreStrategy.class);
        statsManager = new PlayerStatsManager(playerZoneMap, scoreStrategy);
    }

    @Test
    void testInitialization_createsStatsForAllPlayers() {
        assertNotNull(statsManager.getStatsForPlayer("p1"));
        assertNotNull(statsManager.getStatsForPlayer("p2"));
    }

    @Test
    void testUpdateNumberOfAliveShipsStatAllPlayers_updatesCorrectly() {
        statsManager.updateNumberOfAliveShipsStatAllPlayers();

        assertEquals(3, statsManager.getStatsForPlayer("p1").getNumberOfAliveShips());
        assertEquals(2, statsManager.getStatsForPlayer("p2").getNumberOfAliveShips());
    }

    @Test
    void testUpdateStats_onHit() {
        when(scoreStrategy.calculateScore(eq(1), eq(0), anyInt())).thenReturn(100);
        when(scoreStrategy.calculateScore(eq(0), eq(0), anyInt())).thenReturn(0);

        FiringResult result = FiringResult.builder()
                .validShot(true)
                .hit(true)
                .attackingPlayer(player1)
                .hitPlayer(player2)
                .build();

        statsManager.updateStats(result);

        PlayerSessionStats stats = statsManager.getStatsForPlayer("p1");
        assertEquals(1, stats.getHits());
        assertEquals(0, stats.getMisses());
        assertEquals(100, stats.getScore());

        PlayerSessionStats hitPlayerStats = statsManager.getStatsForPlayer("p2");
        assertEquals(2, hitPlayerStats.getNumberOfAliveShips());
    }

    @Test
    void testUpdateStats_onMiss() {
        when(scoreStrategy.calculateScore(eq(0), eq(1), anyInt())).thenReturn(50);

        FiringResult result = FiringResult.builder()
                .validShot(true)
                .hit(false)
                .attackingPlayer(player1)
                .build();

        statsManager.updateStats(result);

        PlayerSessionStats stats = statsManager.getStatsForPlayer("p1");
        assertEquals(0, stats.getHits());
        assertEquals(1, stats.getMisses());
        assertEquals(50, stats.getScore());
    }

    @Test
    void testUpdateStats_ignoresInvalidShot() {
        FiringResult result = FiringResult.builder()
                .validShot(false)
                .hit(false)
                .attackingPlayer(player1)
                .build();

        statsManager.updateStats(result);

        PlayerSessionStats stats = statsManager.getStatsForPlayer("p1");
        assertEquals(0, stats.getHits());
        assertEquals(0, stats.getMisses());
        assertEquals(0, stats.getScore());
    }

    @Test
    void testResetStats_clearsHitsAndMisses() {
        PlayerSessionStats stats = statsManager.getStatsForPlayer("p1");
        stats.recordHit();
        stats.recordMiss();
        stats.setScore(999);

        statsManager.resetStats();

        PlayerSessionStats resetStats = statsManager.getStatsForPlayer("p1");
        assertEquals(0, resetStats.getHits());
        assertEquals(0, resetStats.getMisses());
        assertEquals(999, resetStats.getScore(), "Score remains untouched");
    }
}
