package game.battleship;

import game.battleship.enums.FiringStrategyType;
import game.battleship.enums.GameVisibilityMode;
import game.battleship.model.PlayerConfig;
import game.battleship.service.GameService;

import java.util.List;

public class BattleshipApplication {
    public static void main(String[] args) {
//        GameService gameService = new GameService();
//        String sessionId = gameService.initBotGame(8, 2, GameVisibilityMode.FULL_VISIBILITY);
//        gameService.addShip(sessionId, "POING", 3, CoordinateUtils.fromInts(0, 0, 5, 1));
//        gameService.addShip(sessionId, "TOING", 3, CoordinateUtils.fromInts(1, 4, 4, 4));

//        gameService.viewBattlefield(sessionId);

//        gameService.startGame(sessionId);
//        multiplayerDemo();
//        playerVsPlayerDemo();
//        botsVsBotsDemo();
        playerVsBotsDemo();
    }


    public static void playerVsPlayerDemo() {
        List<PlayerConfig> playerConfigs = List.of(new PlayerConfig("Lakshya", FiringStrategyType.HUMAN),
                new PlayerConfig("Angel", FiringStrategyType.HUMAN));

        int battlefieldSize = 10;
        int numberOfShips = 12;
        GameVisibilityMode gameVisibilityMode = GameVisibilityMode.RESTRICTED_VISIBILITY;
        GameService gameService = new GameService();
        String sessionId = gameService.initGame(battlefieldSize, playerConfigs, gameVisibilityMode);
        gameService.addBotShipsToAllPlayers(sessionId, numberOfShips);
        gameService.startGame(sessionId);
    }

    public static void multiplayerDemo() {
        List<PlayerConfig> playerConfigs = List.of(new PlayerConfig("Lakshya", FiringStrategyType.HUMAN),
                new PlayerConfig("Angel", FiringStrategyType.HUMAN),
                new PlayerConfig("Priya", FiringStrategyType.HUMAN),
                new PlayerConfig("Bot-1", FiringStrategyType.RANDOMIZED_BOT));

        int battlefieldSize = 12;
        int numberOfShips = 10;
        GameVisibilityMode gameVisibilityMode = GameVisibilityMode.RESTRICTED_VISIBILITY;
        GameService gameService = new GameService();
        String sessionId = gameService.initGame(battlefieldSize, playerConfigs, gameVisibilityMode);
        gameService.addBotShipsToAllPlayers(sessionId, numberOfShips);
        gameService.viewBattlefield(sessionId);
        gameService.startGame(sessionId);
    }

    public static void botsVsBotsDemo() {
        GameService gameService = new GameService();
        int battlefieldSize = 15;
        int numberOfPlayers = 5;
        int numberOfShips = 10;
        GameVisibilityMode gameVisibilityMode = GameVisibilityMode.FULL_VISIBILITY;
        gameService.simulateBotGame(battlefieldSize, numberOfPlayers, numberOfShips, gameVisibilityMode);
    }

    public static void playerVsBotsDemo() {
        List<PlayerConfig> playerConfigs = List.of(new PlayerConfig("Lakshya", FiringStrategyType.HUMAN),
                new PlayerConfig("Bot-1", FiringStrategyType.RANDOMIZED_BOT),
                new PlayerConfig("Bot-2", FiringStrategyType.RANDOMIZED_BOT),
                new PlayerConfig("Bot-3", FiringStrategyType.RANDOMIZED_BOT),
                new PlayerConfig("Bot-4", FiringStrategyType.RANDOMIZED_BOT));

        int battlefieldSize = 10;
        int numberOfShips = 5;
        GameVisibilityMode gameVisibilityMode = GameVisibilityMode.HIDDEN_BOTS_VISIBILITY;
        GameService gameService = new GameService();
        String sessionId = gameService.initGame(battlefieldSize, playerConfigs, gameVisibilityMode);
        gameService.addBotShipsToAllPlayers(sessionId, numberOfShips);
        gameService.startGame(sessionId);
    }
}