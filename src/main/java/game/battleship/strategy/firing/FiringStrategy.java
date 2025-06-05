package game.battleship.strategy.firing;

import game.battleship.core.GameSession;
import game.battleship.model.Coordinate;
import game.battleship.model.Player;

import java.util.Set;

public interface FiringStrategy {
    Coordinate chooseTarget(Player player, GameSession gameSession, Set<Coordinate> firedCoordinates);
}
