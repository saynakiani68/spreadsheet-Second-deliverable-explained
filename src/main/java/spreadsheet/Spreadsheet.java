package spreadsheet;

/** Domain model: a rectangular collection of cells. */
public class Spreadsheet {
    private final Cell[][] cells;
    private final CellContentFactory contentFactory;

    public Spreadsheet(int rows, int columns) {
        if (rows < 1 || rows > 500 || columns < 1 || columns > 100) {
            throw new IllegalArgumentException("Rows must be 1-500 and columns 1-100.");
        }
        cells = new Cell[rows][columns];
        contentFactory = new CellContentFactory();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                cells[row][col] = new Cell(row, col);
            }
        }
    }

    public int getRowCount() {
        return cells.length;
    }

    public int getColumnCount() {
        return cells[0].length;
    }

    public void setCellContent(String address, String rawText) {
        getCell(address).setContent(contentFactory.create(rawText));
    }

    public String getCellContent(String address) {
        return getCell(address).getRawContent();
    }

    public Cell getCell(String address) {
        CellAddress position = CellAddress.parse(address);
        if (position.getRow() >= getRowCount() || position.getColumn() >= getColumnCount()) {
            throw new IllegalArgumentException("Cell " + address + " is outside this spreadsheet.");
        }
        return cells[position.getRow()][position.getColumn()];
    }
}
