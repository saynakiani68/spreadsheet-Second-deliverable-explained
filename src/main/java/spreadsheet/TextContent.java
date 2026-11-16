package spreadsheet;

/** Text entered by the user. No calculation is performed. */
public class TextContent implements CellContent {
    private final String text;

    public TextContent(String text) {
        this.text = text;
    }

    @Override
    public String getRawText() {
        return text;
    }
}
