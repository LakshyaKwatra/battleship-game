package game.battleship.strategy.firing;

import game.battleship.core.GameSession;
import game.battleship.model.Coordinate;
import game.battleship.model.Player;
import game.battleship.util.CoordinateUtils;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class RandomFiringStrategy implements FiringStrategy {
    private final Random random = new Random();

    @Override
    public Coordinate chooseTarget(Player player, GameSession gameSession, Set<Coordinate> firedCoordinates) {


        List<Coordinate> possibleTargets = CoordinateUtils.getOpponentZoneCoordinates(player, gameSession.getPlayers())
                .stream()
                .filter(coordinate -> !firedCoordinates.contains(coordinate))
                .toList();

        if (possibleTargets.isEmpty()) {
            throw new IllegalStateException("No available targets left to fire.");
        }

        Coordinate target = possibleTargets.get(random.nextInt(possibleTargets.size()));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(player.getName() + " fires at " + target);
        return target;

    }
}
