package game.battleship.factory;

import game.battleship.enums.FiringStrategyType;
import game.battleship.model.Player;
import game.battleship.model.PlayerConfig;
import game.battleship.strategy.firing.FiringStrategy;

import java.util.*;

public class PlayerFactory {

    public static List<Player> createBotPlayers(int numberOfPlayers, FiringStrategyType firingStrategyType, Scanner scanner) {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            FiringStrategy strategy = FiringStrategyFactory.getStrategy(firingStrategyType, scanner);
            Player player = new Player(UUID.randomUUID().toString(), new PlayerConfig("Bot-" + (i+1), firingStrategyType), strategy);
            players.add(player);
        }
        return players;
    }

    public static List<Player> createPlayers(int numberOfPlayers, List<PlayerConfig> playerConfigs, Scanner scanner) {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            PlayerConfig config = playerConfigs.get(i);
            FiringStrategy strategy = FiringStrategyFactory.getStrategy(config.getFiringStrategyType(), scanner);

            Player player = new Player(UUID.randomUUID().toString(), config, strategy);
            players.add(player);
        }
        return players;
    }

}
