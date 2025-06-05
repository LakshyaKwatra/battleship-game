package game.battleship.factory;

import game.battleship.enums.FiringStrategyType;
import game.battleship.core.GameSession;
import game.battleship.model.Player;
import game.battleship.model.PlayerConfig;
import game.battleship.model.Zone;
import game.battleship.strategy.firing.FiringStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class GameSessionFactory {

    private final Scanner scanner;
    private final BotFactory botFactory = new BotFactory();

    public GameSessionFactory(Scanner scanner) {
        this.scanner = scanner;
    }

    public GameSession createBotGameSession(String sessionId, int battlefieldSize, int numberOfPlayers, FiringStrategyType firingStrategyType) {

        int zoneWidth = battlefieldSize / numberOfPlayers;

        List<Zone> zones = createZones(numberOfPlayers, zoneWidth, battlefieldSize);
        List<Player> players = botFactory.createBotPlayers(zones, numberOfPlayers, firingStrategyType, scanner);
        return new GameSession(sessionId, battlefieldSize, players);
    }

    public GameSession createGameSession(String sessionId, int battlefieldSize, List<PlayerConfig> playerConfigs) {
        int numberOfPlayers = playerConfigs.size();
        int zoneWidth = battlefieldSize / numberOfPlayers;

        List<Zone> zones = createZones(numberOfPlayers, zoneWidth, battlefieldSize);
        List<Player> players = createPlayers(playerConfigs, zones, numberOfPlayers);
        return new GameSession(sessionId, battlefieldSize, players);
    }

    private List<Zone> createZones(int numPlayers, int zoneWidth, int battlefieldSize) {
        List<Zone> zones = new ArrayList<>();
        int assignedColumnsWidth = 0;

        for (int i = 0; i < numPlayers; i++) {
            int startX = assignedColumnsWidth;
            int currentWidth = (i == numPlayers - 1)
                    ? battlefieldSize - assignedColumnsWidth
                    : zoneWidth;
            int endX = startX + currentWidth - 1;
            zones.add(new Zone(battlefieldSize, startX, endX));
            assignedColumnsWidth += currentWidth;
        }
        return zones;
    }

    private List<Player> createPlayers(List<PlayerConfig> playerConfigs, List<Zone> zones, int numPlayers) {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            PlayerConfig config = playerConfigs.get(i);
            Zone playerZone = zones.get(i);

            FiringStrategy strategy = FiringStrategyFactory.getStrategy(config.getFiringStrategyType(), scanner);

            Player player = new Player(UUID.randomUUID().toString(), config.getName(), playerZone, strategy);
            players.add(player);
        }
        return players;
    }

}