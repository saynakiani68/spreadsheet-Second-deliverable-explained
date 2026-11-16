package spreadsheet;

/** Numeric input, stored exactly as it was written by the user. */
public class NumberContent implements CellContent {
    private final String text;

    public NumberContent(String text) {
        this.text = text;
    }

    @Override
    public String getRawText() {
        return text;
    }
}
