package game.battleship.ui;

import game.battleship.core.GameSession;
import game.battleship.factory.BattlefieldVisibilityStrategyFactory;
import game.battleship.strategy.visibility.BattlefieldVisibilityStrategy;

public class BattlefieldDisplay {
    public static void printBoard(GameSession session) {
        BattlefieldVisibilityStrategy strategy = BattlefieldVisibilityStrategyFactory.
                getStrategy(session.getGameMode());

        BattlefieldRenderer renderer = new BattlefieldRenderer(session, strategy);
        renderer.render();
    }
}
