package spreadsheet;

/** A cell has a fixed position and optional content. */
public class Cell {
    private final int row;
    private final int column;
    private CellContent content;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public CellContent getContent() {
        return content;
    }

    public String getRawContent() {
        return content == null ? "" : content.getRawText();
    }

    public void setContent(CellContent content) {
        this.content = content;
    }
}
