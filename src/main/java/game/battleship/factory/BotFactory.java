package game.battleship.factory;

import game.battleship.enums.FiringStrategyType;
import game.battleship.model.Player;
import game.battleship.model.Zone;
import game.battleship.strategy.firing.FiringStrategy;

import java.util.*;

public class BotFactory {

    public List<Player> createBotPlayers(List<Zone> zones, int numPlayers, FiringStrategyType firingStrategyType, Scanner scanner) {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            Zone playerZone = zones.get(i);

            FiringStrategy strategy = FiringStrategyFactory.getStrategy(firingStrategyType, scanner);

            Player player = new Player(UUID.randomUUID().toString(), "Bot-" + (i+1), playerZone, strategy);
            players.add(player);
        }
        return players;
    }

}
