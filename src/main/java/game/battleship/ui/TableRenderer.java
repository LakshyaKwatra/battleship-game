package game.battleship.ui;

import game.battleship.config.GameTheme;
import game.battleship.enums.ConsoleTextColor;
import game.battleship.util.UIUtils;

import java.util.List;

public class TableRenderer {

    public static void renderTable(
            int cellWidth,
            List<String> columnLabels,
            List<String> rowLabels,
            String[][] data,
            String rowHeaderLabel) {

        if (data == null || rowLabels == null || columnLabels == null) return;
        if (data.length != rowLabels.size() || data[0].length != columnLabels.size()) return;

        printTopBorder(cellWidth, columnLabels.size());
        printHeaderRow(cellWidth, columnLabels, rowHeaderLabel);
        printHeaderSeparator(cellWidth, columnLabels.size());
        printDataRows(cellWidth, rowLabels, data);
        printBottomBorder(cellWidth, columnLabels.size());
    }

    private static void printTopBorder(int cellWidth, int colCount) {
        System.out.print(GameTheme.BORDER_TOP_LEFT_CORNER);
        for (int i = 0; i < colCount + 1; i++) {
            System.out.print(GameTheme.BORDER_HORIZONTAL.repeat(cellWidth));
            if (i < colCount) {
                System.out.print(GameTheme.BORDER_CONNECTOR_TOP);
            }
        }
        System.out.println(GameTheme.BORDER_TOP_RIGHT_CORNER);
    }

    private static void printHeaderRow(int cellWidth, List<String> headers, String rowLabel) {
        System.out.print(GameTheme.BORDER_VERTICAL);
        System.out.print(TextColorizer.colorize(UIUtils.padCenter(rowLabel, cellWidth), ConsoleTextColor.BRIGHT_YELLOW, null));
        System.out.print(GameTheme.BORDER_VERTICAL);

        for (String header : headers) {
            System.out.print(TextColorizer.colorize(UIUtils.padCenter(header, cellWidth), ConsoleTextColor.BRIGHT_YELLOW, null));
            System.out.print(GameTheme.BORDER_VERTICAL);
        }
        System.out.println();
    }

    private static void printHeaderSeparator(int cellWidth, int colCount) {
        System.out.print(GameTheme.BORDER_CONNECTOR_LEFT);
        for (int i = 0; i < colCount + 1; i++) {
            System.out.print(GameTheme.BORDER_HORIZONTAL.repeat(cellWidth));
            if (i < colCount) {
                System.out.print(GameTheme.BORDER_CROSS_CONNECTOR);
            }
        }
        System.out.println(GameTheme.BORDER_CONNECTOR_RIGHT);
    }

    private static void printDataRows(int cellWidth, List<String> rowLabels, String[][] data) {
        for (int i = 0; i < rowLabels.size(); i++) {
            System.out.print(GameTheme.BORDER_VERTICAL);
            System.out.print(TextColorizer.colorize(UIUtils.padCenter(rowLabels.get(i), cellWidth), ConsoleTextColor.BRIGHT_BLUE, null));
            System.out.print(GameTheme.BORDER_VERTICAL);
            for (int j = 0; j < data[i].length; j++) {
                ConsoleTextColor color = switch (j) {
                    case 0 -> ConsoleTextColor.BRIGHT_GREEN; // Hits
                    case 1 -> ConsoleTextColor.BRIGHT_RED; // Misses
                    case 2 -> ConsoleTextColor.BRIGHT_CYAN; // Remaining ships
                    default -> ConsoleTextColor.BRIGHT_PURPLE; // Score
                };
                System.out.print(TextColorizer.colorize(UIUtils.padCenter(data[i][j], cellWidth), color, null));
                System.out.print(GameTheme.BORDER_VERTICAL);
            }
            System.out.println();
        }
    }

    private static void printBottomBorder(int cellWidth, int colCount) {
        System.out.print(GameTheme.BORDER_BOTTOM_LEFT_CORNER);
        for (int i = 0; i < colCount + 1; i++) {
            System.out.print(GameTheme.BORDER_HORIZONTAL.repeat(cellWidth));
            if (i < colCount) {
                System.out.print(GameTheme.BORDER_CONNECTOR_BOTTOM);
            }
        }
        System.out.println(GameTheme.BORDER_BOTTOM_RIGHT_CORNER);
    }
}
