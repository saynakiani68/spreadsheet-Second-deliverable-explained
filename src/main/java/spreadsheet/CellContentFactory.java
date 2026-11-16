package spreadsheet;

/** Decides which domain content type represents an input string. */
public class CellContentFactory {
    public CellContent create(String rawText) {
        if (rawText == null) {
            throw new IllegalArgumentException("Cell content cannot be null.");
        }
        if (rawText.isEmpty()) {
            return null; // An empty input clears the cell.
        }
        if (rawText.startsWith("=")) {
            return new FormulaContent(rawText);
        }
        try {
            double number = Double.parseDouble(rawText);
            if (Double.isFinite(number)) {
                return new NumberContent(rawText);
            }
        } catch (NumberFormatException ignored) {
            // Not a number: store it as text instead.
        }
        return new TextContent(rawText);
    }
}
