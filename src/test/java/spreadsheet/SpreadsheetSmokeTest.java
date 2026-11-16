package spreadsheet;

import java.nio.file.Files;
import java.nio.file.Path;

/** Small self-test runner; no external testing library is required. */
public class SpreadsheetSmokeTest {
    public static void main(String[] args) throws Exception {
        Spreadsheet sheet = new Spreadsheet(5, 5);
        check("", sheet.getCellContent("A1"));
        sheet.setCellContent("A1", "Hello");
        sheet.setCellContent("B2", "0042");
        sheet.setCellContent("C3", "=1+2");
        sheet.setCellContent("D4", "Line 1\nLine 2 = ok");

        check("Hello", sheet.getCellContent("A1"));
        check("0042", sheet.getCellContent("B2"));
        check("=1+2", sheet.getCellContent("C3"));
        if (!(sheet.getCell("A1").getContent() instanceof TextContent)) throw new AssertionError("Text type");
        if (!(sheet.getCell("B2").getContent() instanceof NumberContent)) throw new AssertionError("Number type");
        if (!(sheet.getCell("C3").getContent() instanceof FormulaContent)) throw new AssertionError("Formula type");

        Path temporary = Files.createTempFile("spreadsheet-test-", ".properties");
        try {
            SpreadsheetStorage storage = new SpreadsheetStorage();
            storage.save(sheet, temporary);
            Spreadsheet loaded = storage.load(temporary);
            check("Hello", loaded.getCellContent("A1"));
            check("0042", loaded.getCellContent("B2"));
            check("=1+2", loaded.getCellContent("C3"));
            check("Line 1\nLine 2 = ok", loaded.getCellContent("D4"));
            loaded.setCellContent("A1", "");
            check("", loaded.getCellContent("A1"));
            check("AA12", CellAddress.toA1(11, 26));
            if (CellAddress.parse("aa12").getColumn() != 26) throw new AssertionError("Address parsing");
            boolean rejected = false;
            try { loaded.getCellContent("Z99"); }
            catch (IllegalArgumentException expected) { rejected = true; }
            if (!rejected) throw new AssertionError("Invalid address was accepted");
            System.out.println("ALL TESTS PASSED: set, view, types, save, load, clear, addresses.");
        } finally {
            Files.deleteIfExists(temporary);
        }
    }

    private static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new AssertionError("Expected <" + expected + "> but was <" + actual + ">");
        }
    }
}
