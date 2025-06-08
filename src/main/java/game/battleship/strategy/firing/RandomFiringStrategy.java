package game.battleship.strategy.firing;

import game.battleship.core.GameSession;
import game.battleship.model.Coordinate;
import game.battleship.model.Player;
import game.battleship.model.Zone;
import game.battleship.util.CoordinateUtils;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class RandomFiringStrategy implements FiringStrategy {
    private final Random random = new Random();

    @Override
    public Coordinate chooseTarget(Player player, Map<Player, Zone> playerZoneMap) {


        List<Coordinate> possibleTargets = CoordinateUtils.getValidOpponentZoneCoordinates(player, playerZoneMap)
                .stream()
                .toList();

        if (possibleTargets.isEmpty()) {
            throw new IllegalStateException("No available targets left to fire.");
        }

        Coordinate target = possibleTargets.get(random.nextInt(possibleTargets.size()));
        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return target;

    }
}
