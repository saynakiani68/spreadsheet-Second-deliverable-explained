package spreadsheet;

/** Converts between array indexes and spreadsheet addresses such as A1 or AA12. */
public class CellAddress {
    private final int row;
    private final int column;

    private CellAddress(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public static CellAddress parse(String address) {
        if (address == null) {
            throw new IllegalArgumentException("Cell address is required.");
        }
        String normalized = address.trim().toUpperCase(java.util.Locale.ROOT);
        if (!normalized.matches("[A-Z]+[1-9][0-9]*")) {
            throw new IllegalArgumentException("Invalid cell address: " + address);
        }
        int split = 0;
        while (split < normalized.length() && Character.isLetter(normalized.charAt(split))) {
            split++;
        }
        int col = 0;
        try {
            for (int i = 0; i < split; i++) {
                col = Math.addExact(Math.multiplyExact(col, 26), normalized.charAt(i) - 'A' + 1);
            }
            int row = Integer.parseInt(normalized.substring(split));
            return new CellAddress(row - 1, col - 1);
        } catch (ArithmeticException ex) {
            throw new IllegalArgumentException("Cell address is too large: " + address, ex);
        }
    }

    public static String toA1(int row, int column) {
        if (row < 0 || column < 0) {
            throw new IllegalArgumentException("Negative row or column.");
        }
        StringBuilder letters = new StringBuilder();
        int index = column;
        do {
            letters.append((char) ('A' + (index % 26)));
            index = index / 26 - 1;
        } while (index >= 0);
        return letters.reverse().append(row + 1).toString();
    }
}
