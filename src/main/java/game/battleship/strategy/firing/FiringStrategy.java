package game.battleship.strategy.firing;

import game.battleship.core.GameSession;
import game.battleship.model.Coordinate;
import game.battleship.model.Player;
import game.battleship.model.Zone;

import java.util.Map;
import java.util.Set;

public interface FiringStrategy {
    Coordinate chooseTarget(Player player, Map<Player, Zone> playerZoneMap);
}
