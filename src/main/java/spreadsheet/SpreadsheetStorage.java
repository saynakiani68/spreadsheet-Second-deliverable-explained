package spreadsheet;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/** Technical service that saves and loads spreadsheet content in a text file. */
public class SpreadsheetStorage {
    public void save(Spreadsheet sheet, Path file) throws IOException {
        Properties data = new Properties();
        data.setProperty("rows", Integer.toString(sheet.getRowCount()));
        data.setProperty("columns", Integer.toString(sheet.getColumnCount()));
        for (int row = 0; row < sheet.getRowCount(); row++) {
            for (int col = 0; col < sheet.getColumnCount(); col++) {
                String address = CellAddress.toA1(row, col);
                String raw = sheet.getCellContent(address);
                if (!raw.isEmpty()) {
                    data.setProperty("cell." + address, raw);
                }
            }
        }
        Path parent = file.toAbsolutePath().getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        try (Writer writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            data.store(writer, "Spreadsheet data - raw cell contents; no evaluation");
        }
    }

    public Spreadsheet load(Path file) throws IOException {
        Properties data = new Properties();
        try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            data.load(reader);
        }
        try {
            int rows = Integer.parseInt(data.getProperty("rows"));
            int columns = Integer.parseInt(data.getProperty("columns"));
            Spreadsheet sheet = new Spreadsheet(rows, columns);
            for (String key : data.stringPropertyNames()) {
                if (key.startsWith("cell.")) {
                    sheet.setCellContent(key.substring(5), data.getProperty(key));
                }
            }
            return sheet;
        } catch (IllegalArgumentException ex) {
            throw new IOException("Invalid spreadsheet file: " + ex.getMessage(), ex);
        }
    }
}
