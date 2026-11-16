package spreadsheet;

/** A formula stored as text. Formula evaluation belongs to a later deliverable. */
public class FormulaContent implements CellContent {
    private final String formula;

    public FormulaContent(String formula) {
        this.formula = formula;
    }

    @Override
    public String getRawText() {
        return formula;
    }
}
