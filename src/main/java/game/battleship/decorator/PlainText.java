package game.battleship.decorator;

public class PlainText implements FormattedText {
    private final String text;

    public PlainText(String text) {
        this.text = text;
    }

    @Override
    public String render() {
        return text;
    }
}
