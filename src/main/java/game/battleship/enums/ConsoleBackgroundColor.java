package game.battleship.enums;

public enum ConsoleBackgroundColor {

    BG_BLACK("\u001B[40m"), BG_RED("\u001B[41m"), BG_GREEN("\u001B[42m"), BG_YELLOW("\u001B[43m"),
    BG_BLUE("\u001B[44m"), BG_PURPLE("\u001B[45m"), BG_CYAN("\u001B[46m"), BG_WHITE("\u001B[47m"),
    BG_BRIGHT_BLACK("\u001B[100m"), BG_BRIGHT_RED("\u001B[101m"), BG_BRIGHT_GREEN("\u001B[102m"),
    BG_BRIGHT_YELLOW("\u001B[103m"), BG_BRIGHT_BLUE("\u001B[104m"), BG_BRIGHT_PURPLE("\u001B[105m"),
    BG_BRIGHT_CYAN("\u001B[106m"), BG_BRIGHT_WHITE("\u001B[107m"),

    RESET("\u001B[0m");

    private final String code;

    ConsoleBackgroundColor(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
