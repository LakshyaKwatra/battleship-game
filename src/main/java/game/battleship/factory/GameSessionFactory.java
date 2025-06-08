package game.battleship.factory;

import game.battleship.enums.FiringStrategyType;
import game.battleship.core.GameSession;
import game.battleship.enums.GameVisibilityMode;
import game.battleship.model.Player;
import game.battleship.model.PlayerConfig;
import game.battleship.strategy.score.DefaultScoreStrategy;

import java.util.List;
import java.util.Scanner;

public class GameSessionFactory {

    private final Scanner scanner;

    public GameSessionFactory(Scanner scanner) {
        this.scanner = scanner;
    }

    public GameSession createBotGameSession(String sessionId, int battlefieldSize, int numberOfPlayers, FiringStrategyType firingStrategyType, GameVisibilityMode gameVisibilityMode) {
        List<Player> players = PlayerFactory.createBotPlayers(numberOfPlayers, firingStrategyType, scanner);
        return new GameSession(sessionId, battlefieldSize, players, gameVisibilityMode, new DefaultScoreStrategy());
    }

    public GameSession createGameSession(String sessionId, int battlefieldSize, List<PlayerConfig> playerConfigs, GameVisibilityMode gameVisibilityMode) {
        int numberOfPlayers = playerConfigs.size();
        int zoneWidth = battlefieldSize / numberOfPlayers;

//        List<Zone> zones = createZones(numberOfPlayers, zoneWidth, battlefieldSize);
        List<Player> players = PlayerFactory.createPlayers(numberOfPlayers, playerConfigs, scanner);
        return new GameSession(sessionId, battlefieldSize, players, gameVisibilityMode, new DefaultScoreStrategy());
    }

//    private List<Zone> createZones(int numPlayers, int zoneWidth, int battlefieldSize) {
//        List<Zone> zones = new ArrayList<>();
//
//        int baseZoneWidth = battlefieldSize / numPlayers;
//        int remainder = battlefieldSize % numPlayers;
//
//        int currentStartX = 0;
//
//        for (int i = 0; i < numPlayers; i++) {
//            int extra = (i < remainder) ? 1 : 0;  // Distribute remaining columns
//            int currentWidth = baseZoneWidth + extra;
//            int endX = currentStartX + currentWidth - 1;
//
//            zones.add(new Zone(battlefieldSize, currentStartX, endX));
//            currentStartX = endX + 1;
//        }
//
//        return zones;
//    }

}