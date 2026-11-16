package spreadsheet;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

/** Console interface: one menu option for each supported user action. */
public class Main {
    private final Scanner scanner = new Scanner(System.in);
    private final SpreadsheetStorage storage = new SpreadsheetStorage();
    private Spreadsheet spreadsheet = new Spreadsheet(10, 10);

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        System.out.println("Spreadsheet - First Deliverable");
        boolean running = true;
        while (running) {
            showMenu();
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> createSpreadsheet();
                    case "2" -> setContent();
                    case "3" -> viewContent();
                    case "4" -> showSpreadsheet();
                    case "5" -> saveSpreadsheet();
                    case "6" -> loadSpreadsheet();
                    case "0" -> running = false;
                    default -> System.out.println("Choose a valid option.");
                }
            } catch (IllegalArgumentException | IOException exception) {
                System.out.println("Error: " + exception.getMessage());
            }
        }
        System.out.println("Goodbye!");
    }

    private void showMenu() {
        System.out.println("\n1. New spreadsheet");
        System.out.println("2. Set cell content");
        System.out.println("3. View cell content");
        System.out.println("4. Show spreadsheet contents");
        System.out.println("5. Save spreadsheet");
        System.out.println("6. Load spreadsheet");
        System.out.println("0. Exit");
        System.out.print("Select an option: ");
    }

    private void createSpreadsheet() {
        System.out.print("Number of rows (1-500): ");
        int rows = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Number of columns (1-100): ");
        int columns = Integer.parseInt(scanner.nextLine().trim());
        spreadsheet = new Spreadsheet(rows, columns);
        System.out.println("New empty spreadsheet created.");
    }

    private void setContent() {
        System.out.print("Cell address (e.g. A1): ");
        String address = scanner.nextLine();
        System.out.print("Enter raw content (empty to clear): ");
        String content = scanner.nextLine();
        spreadsheet.setCellContent(address, content);
        System.out.println("Content saved in cell " + address.trim().toUpperCase(java.util.Locale.ROOT) + ".");
    }

    private void viewContent() {
        System.out.print("Cell address (e.g. A1): ");
        String address = scanner.nextLine();
        String content = spreadsheet.getCellContent(address);
        System.out.println(address.trim().toUpperCase(java.util.Locale.ROOT) + " content: "
                + (content.isEmpty() ? "[empty]" : content));
    }

    private void showSpreadsheet() {
        System.out.println("Stored cell contents (NOT calculated values):");
        boolean any = false;
        for (int row = 0; row < spreadsheet.getRowCount(); row++) {
            for (int col = 0; col < spreadsheet.getColumnCount(); col++) {
                String address = CellAddress.toA1(row, col);
                String content = spreadsheet.getCellContent(address);
                if (!content.isEmpty()) {
                    System.out.println(address + " = " + content);
                    any = true;
                }
            }
        }
        if (!any) {
            System.out.println("All cells are empty.");
        }
    }

    private void saveSpreadsheet() throws IOException {
        System.out.print("File path (e.g. my-sheet.properties): ");
        Path path = Path.of(scanner.nextLine().trim());
        storage.save(spreadsheet, path);
        System.out.println("Spreadsheet saved to " + path.toAbsolutePath());
    }

    private void loadSpreadsheet() throws IOException {
        System.out.print("File path to load: ");
        Path path = Path.of(scanner.nextLine().trim());
        Spreadsheet loaded = storage.load(path);
        spreadsheet = loaded; // Keep the previous spreadsheet if loading fails.
        System.out.println("Spreadsheet loaded: " + spreadsheet.getRowCount()
                + " rows x " + spreadsheet.getColumnCount() + " columns.");
    }
}
