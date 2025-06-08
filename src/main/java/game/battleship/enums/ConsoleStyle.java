package game.battleship.enums;

public enum ConsoleStyle {
    BOLD("\u001B[1m"),
    UNDERLINE("\u001B[4m"),
    REVERSED("\u001B[7m");

    private final String code;

    ConsoleStyle(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
