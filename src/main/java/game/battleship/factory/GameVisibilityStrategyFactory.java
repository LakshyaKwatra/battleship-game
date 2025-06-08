package game.battleship.factory;

import game.battleship.enums.GameVisibilityMode;
import game.battleship.strategy.visibility.GameVisibilityStrategy;
import game.battleship.strategy.visibility.FullVisibilityStrategy;
import game.battleship.strategy.visibility.HiddenBotsVisibilityStrategy;
import game.battleship.strategy.visibility.RestrictedVisibilityStrategy;

public class GameVisibilityStrategyFactory {
    public static GameVisibilityStrategy getStrategy(GameVisibilityMode gameVisibilityMode) {
        switch (gameVisibilityMode) {
            case RESTRICTED_VISIBILITY:
                return new RestrictedVisibilityStrategy();
            case FULL_VISIBILITY:
                return new FullVisibilityStrategy();
            case HIDDEN_BOTS_VISIBILITY:
                return new HiddenBotsVisibilityStrategy();
            default:
                System.out.println("Unknown visibility strategy type");;
        }
        return null;
    }
}
