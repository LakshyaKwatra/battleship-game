package game.battleship.ui;

import game.battleship.config.GameTheme;
import game.battleship.enums.ConsoleTextColor;
import game.battleship.model.Coordinate;
import game.battleship.model.FiringResult;
import game.battleship.model.Player;
import game.battleship.model.PlayerSessionStats;
import game.battleship.util.UIUtils;

import java.util.List;
import java.util.Map;

public class GameTextRenderer {

    private static final String HORIZONTAL_BREAK = "=".repeat(20);

    public static void printFireMessage(String attacker, Coordinate coord) {
        System.out.println(attacker + " fires at " + coord);
    }

    public static void printFiringResultMessage(FiringResult result) {
        if (!result.isValidShot()){
            System.out.println(TextColorizer.colorize(result.getErrorMessage(), GameTheme.MISS_TEXT_COLOR, null));
            return;
        }

        String message = result.isHit()
                ? buildHitMessage(result)
                : buildMissMessage(result.getAttackingPlayer().getName());

        System.out.println(message);
    }

    public static void printGameTitle() {
        printFramedTitle("BATTLESHIP GAME", GameTheme.TITLE_BORDER_COLOR, GameTheme.TITLE_TEXT_COLOR);
    }

    public static void printGameResultMessage(Player winner) {
        System.out.println();
        String message = (winner == null)
                ? "Game Over! It's a draw!"
                : "Game Over! Winner: " + winner.getName();
        printFramedTitle(message, ConsoleTextColor.BRIGHT_GREEN, ConsoleTextColor.BRIGHT_YELLOW);
    }

    public static void printSessionStats(List<Player> players, Map<String, PlayerSessionStats> statsMap) {
        printHeader("GAME STATS");

        String[][] data = new String[players.size()][4];
        List<String> names = players.stream().map(Player::getName).toList();

        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            PlayerSessionStats stats = statsMap.get(p.getId());
            data[i][0] = String.valueOf(stats.getHits());
            data[i][1] = String.valueOf(stats.getMisses());
            data[i][2] = String.valueOf(stats.getNumberOfAliveShips());
            data[i][3] = String.valueOf(stats.getScore());
        }

        TableRenderer.renderTable(12, List.of("Hits", "Misses", "Ships left", "Score"), names, data, "Player");
    }

    public static void printErrorMessage(String message) {
        System.out.println(TextColorizer.colorize(message, GameTheme.MISS_TEXT_COLOR, null));
    }

    private static String buildHitMessage(FiringResult result) {
        return TextColorizer.colorize(
                "Hit! " + result.getAttackingPlayer().getName() +
                        " destroyed " + result.getHitPlayer().getName() +
                        "'s ship " + result.getHitShip().getId() + ".", GameTheme.HIT_TEXT_COLOR, null);
    }

    private static String buildMissMessage(String playerName) {
        return TextColorizer.colorize(
                "Miss! " + playerName + " could not hit any ship.",
                GameTheme.MISS_TEXT_COLOR, null);
    }

    private static void printFramedTitle(String message, ConsoleTextColor borderColor, ConsoleTextColor textColor) {
        String border = "╔" + "═".repeat(40 + 16) + "╗";
        String content = "║ " + 56 + " ║";
        String bottom = "╚" + "═".repeat(40 + 16) + "╝";

        System.out.println(TextColorizer.colorize(border, borderColor, null));
        System.out.println(
                TextColorizer.colorize("║", borderColor, null) + " ".repeat(8) +
                        TextColorizer.colorize(UIUtils.padCenter(message, 40), textColor, null) + " ".repeat(8) +
                        TextColorizer.colorize("║", borderColor, null)
        );
        System.out.println(TextColorizer.colorize(bottom, borderColor, null));
    }

    private static void printHeader(String title) {
        String header = '\n' + HORIZONTAL_BREAK + " " + title + " " + HORIZONTAL_BREAK;
        System.out.println(TextColorizer.colorize(header, ConsoleTextColor.BRIGHT_WHITE, null));
    }
}
