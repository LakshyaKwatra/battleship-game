package game.battleship.core.display;

import game.battleship.enums.GameVisibilityMode;
import game.battleship.factory.GameVisibilityStrategyFactory;
import game.battleship.model.Coordinate;
import game.battleship.model.Player;
import game.battleship.model.Zone;
import game.battleship.strategy.visibility.GameVisibilityStrategy;
import game.battleship.ui.BattlefieldRenderer;

import java.util.List;
import java.util.Map;

public class GameDisplayManager {

    private final BattlefieldRenderer battlefieldRenderer;

    public GameDisplayManager(int battlefieldSize, GameVisibilityMode gameVisibilityMode, Map<Player, Zone> playerZoneMap, Map<Coordinate, Player> coordinateOwnerMap, List<Player> players) {
        GameVisibilityStrategy visibilityStrategy = GameVisibilityStrategyFactory.getStrategy(gameVisibilityMode);
        this.battlefieldRenderer = new BattlefieldRenderer(visibilityStrategy, battlefieldSize, playerZoneMap, coordinateOwnerMap, players);
    }

    public void printBattlefield(Player currentPlayer) {
        battlefieldRenderer.render(currentPlayer);
    }
}

