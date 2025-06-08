package game.battleship.decorator;

import game.battleship.enums.ConsoleTextColor;
import game.battleship.enums.ConsoleStyle;

public class TextStyleDecorator implements FormattedText {
    private final FormattedText inner;
    private final ConsoleStyle style;

    public TextStyleDecorator(FormattedText inner, ConsoleStyle style) {
        this.inner = inner;
        this.style = style;
    }

    @Override
    public String render() {
        return style + inner.render() + ConsoleTextColor.RESET;
    }
}

