package game.battleship.ui;

import game.battleship.config.GameTheme;
import game.battleship.decorator.PlainText;
import game.battleship.decorator.TextStyleDecorator;
import game.battleship.enums.ConsoleStyle;
import game.battleship.enums.ConsoleTextColor;
import game.battleship.enums.ConsoleBackgroundColor;
import game.battleship.model.Player;
import game.battleship.model.Zone;
import game.battleship.util.UIUtils;

import java.util.List;
import java.util.Map;

public class PlayerLabelRenderer {

    public static void printPlayerLabels(List<Player> players, Map<Player, Zone> playerZoneMap, Player viewer, int labelWidth) {
        System.out.print(" ".repeat(labelWidth + GameTheme.CELL_SPACING + GameTheme.BORDER_WIDTH));
        for (Player player : players) {
            Zone zone = playerZoneMap.get(player);
            if(zone == null) {
                continue;
            }
            int totalWidth = getZoneDisplayWidth(zone);
            String name = UIUtils.padCenter(player.getName(), totalWidth);

            ConsoleTextColor fg;
            ConsoleBackgroundColor bg;

            if (viewer != null && player.getId().equals(viewer.getId())) {
                fg = GameTheme.PLAYER_CURRENT_LABEL_COLOR;
                bg = GameTheme.PLAYER_CURRENT_LABEL_BACKGROUND_COLOR;
            } else if (zone.isDefeated()) {
                fg = GameTheme.PLAYER_CURRENT_LABEL_COLOR;
                bg = GameTheme.PLAYER_DEAD_LABEL_BACKGROUND_COLOR;
            } else {
                fg = GameTheme.PLAYER_LABEL_COLOR;
                bg = GameTheme.PLAYER_LABEL_BACKGROUND_COLOR;
            }

            String styledName = TextColorizer.colorize(name, fg, bg);
            styledName = new TextStyleDecorator(new PlainText(styledName), ConsoleStyle.BOLD).render();

            System.out.print(styledName + " ".repeat(GameTheme.BORDER_WIDTH));
        }
        System.out.println();
    }

    private static int getZoneDisplayWidth(Zone zone) {
        int zoneWidth = zone.getEndX() - zone.getStartX() + 1;
        return zoneWidth * GameTheme.CELL_WIDTH + (zoneWidth - 1) * GameTheme.CELL_SPACING;
    }
}
