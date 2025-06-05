package game.battleship;

import game.battleship.core.GameSessionManager;
import game.battleship.enums.FiringStrategyType;
import game.battleship.enums.GameMode;
import game.battleship.model.PlayerConfig;
import game.battleship.repository.GameSessionRepository;
import game.battleship.repository.InMemoryGameSessionRepository;
import game.battleship.service.GameService;
import game.battleship.util.CoordinateUtils;

import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        GameService gameService = new GameService();

        String sessionId = gameService.initBotGame(13, 5);
        gameService.addShip(sessionId, "SH1", 2, CoordinateUtils.fromInts(0, 0, 2, 1, 4,3, 6,0, 10, 2));
        gameService.addShip(sessionId, "SH2", 1, CoordinateUtils.fromInts(0, 3, 2, 5, 4, 0, 7, 5, 9, 9 ));
        gameService.addShip(sessionId, "SH3", 2, CoordinateUtils.fromInts(0, 6, 2,7, 4, 8, 6,7, 11, 8));
//        gameService.viewBattlefield(sessionId);
//        gameService.setGameMode(sessionId, GameMode.RESTRICTED_VISIBILITY);

        gameService.startGame(sessionId);

//        PlayerConfig player1Config = new PlayerConfig("lakshya", FiringStrategyType.HUMAN);
//        PlayerConfig player2Config = new PlayerConfig("Bot-1", FiringStrategyType.RANDOM);
//        PlayerConfig player3Config = new PlayerConfig("Bot-2", FiringStrategyType.RANDOM);
//        String sessionId2 = gameService.initGame(6, List.of(player1Config, player2Config, player3Config));
//        gameService.addShip(sessionId2, "SH1", 2, CoordinateUtils.fromInts(0, 0, 2, 0, 4, 0));
//        gameService.addShip(sessionId2, "SH2", 1, CoordinateUtils.fromInts(0, 2, 3, 2, 4, 2));
//        gameService.addShip(sessionId2, "SH3", 2, CoordinateUtils.fromInts(0, 4, 2, 4, 4, 4));
//        gameService.viewBattlefield(sessionId2);
//        gameService.startGame(sessionId2);
    }
}