# My Spreadsheet Application — First Java Deliverable

This repository contains the first implementation of my spreadsheet application for Software Engineering. I concentrated on the features requested for this delivery: the domain classes, entering and viewing the **original contents** of cells, and saving and loading the spreadsheet.



## What I implemented

| Requirement | What I did |
|---|---|
| Domain model | I created `Spreadsheet`, `Cell`, and the `CellContent` types. |
| Enter cell content | I implemented `Spreadsheet.setCellContent(...)` and menu option 2. |
| View cell content | I implemented `Spreadsheet.getCellContent(...)` and menu options 3 and 4. |
| Save spreadsheet | I implemented `SpreadsheetStorage.save(...)` and menu option 5. |
| Load spreadsheet | I implemented `SpreadsheetStorage.load(...)` and menu option 6. |

For this delivery, I intentionally **do not evaluate formulas**. If I enter `=1+2`, the program displays `=1+2`, not `3`. This follows the written requirement to manipulate **content rather than value**.



## The demonstration I prepared

1. I select menu option **2**, enter `A1`, and set its content to `Hello`.
2. I select option **2**, enter `B2`, and set its content to `42`.
3. I select option **2**, enter `C3`, and set its content to `=1+2`.
4. I select option **4** and expect to see the original strings:

   ```text
   A1 = Hello
   B2 = 42
   C3 = =1+2
   ```

5. I select option **5** and save the sheet as `my-sheet.properties`.
6. I select option **1** and create a new empty sheet.
7. I select option **6** and load `my-sheet.properties`.
8. I select option **4** again to confirm that the same cell contents were restored.

## Why I chose this design

I used `Cell[][]` because the spreadsheet is rectangular. In the  class, you explained that a two-dimensional array is a reasonable starting point, even if a sparse representation might be more efficient later. I created `CellAddress` to translate names such as `B2` into row and column indexes. I used `CellContent` and separate text, number, and formula classes to recognize different kinds of input while retaining their original strings.

I separated `SpreadsheetStorage` from `Spreadsheet` so file handling does not become part of the cell model. I used a simple console menu because you explained in class that the main focus is the design and behavior, not a graphical interface. Finally, I kept parsing and evaluation out of this version, because they belong to later roadmap stages.

## The main files and what I use them for

- `src/main/java/spreadsheet/Spreadsheet.java`: I use it to manage the rectangular grid.
- `src/main/java/spreadsheet/Cell.java`: I use it to represent one cell.
- `src/main/java/spreadsheet/CellContent.java`, `TextContent.java`, `NumberContent.java`, `FormulaContent.java`: I use them to represent different types of original input.
- `src/main/java/spreadsheet/CellContentFactory.java`: I use it to choose the type of content to create.
- `src/main/java/spreadsheet/CellAddress.java`: I use it to interpret addresses such as `A1`.
- `src/main/java/spreadsheet/SpreadsheetStorage.java`: I use it to save and load `.properties` files.
- `src/main/java/spreadsheet/Main.java`: I use it to provide the menu.
- `src/test/java/spreadsheet/SpreadsheetSmokeTest.java`: I use it to check the basic behaviors independently.
- `examples/example-sheet.properties`: I included this example file to demonstrate stored contents.


## Current limits and future work

My current version limits sheets to 500 rows and 100 columns. It does not compute formulas, interpret cell references, detect circular dependencies, implement functions, or import Excel/CSV files. I intend to add these only in later project stages, after the first version is working and tested.

