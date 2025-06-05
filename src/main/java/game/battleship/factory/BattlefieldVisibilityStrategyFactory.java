package game.battleship.factory;

import game.battleship.enums.GameMode;
import game.battleship.strategy.visibility.BattlefieldVisibilityStrategy;
import game.battleship.strategy.visibility.FullVisibilityStrategy;
import game.battleship.strategy.visibility.RestrictedVisibilityStrategy;

public class BattlefieldVisibilityStrategyFactory {
    public static BattlefieldVisibilityStrategy getStrategy(GameMode gameMode) {
        switch (gameMode) {
            case RESTRICTED_VISIBILITY:
                return new RestrictedVisibilityStrategy();
            case FULL_VISIBILITY:
                return new FullVisibilityStrategy();
            default:
                System.out.println("Unknown visibility strategy type");;
        }
        return null;
    }
}
