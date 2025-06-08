package game.battleship.util;

public class UIUtils {

    public static String padCenter(String text, int width) {
        if (text.length() > width) {
            text = text.substring(0, width);
        }
        int pad = (width - text.length()) / 2;
        return " ".repeat(pad) + text + " ".repeat(width - text.length() - pad);
    }

}
