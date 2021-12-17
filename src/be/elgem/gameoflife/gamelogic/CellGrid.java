package be.elgem.gameoflife.gamelogic;

public class CellGrid {

    private static int NUMBER_ROW = 50;
    private static int NUMBER_COL = 50;

    private boolean[][] cellGrid;

    public CellGrid(int numberRow, int numberCol) {
        CellGrid.NUMBER_ROW = numberRow;
        CellGrid.NUMBER_COL = numberCol;

        cellGrid = new boolean[numberCol][numberRow];
    }

    public void putCell(int x, int y) {
        cellGrid[y / NUMBER_ROW][x / NUMBER_COL] = true;
    }

    public void removeCell(int x, int y) {
        cellGrid[y / NUMBER_ROW][x / NUMBER_COL] = false;
    }

    public void reset() {
        cellGrid = new boolean[NUMBER_COL][NUMBER_ROW];
    }

    public void checkCells() {
        boolean[][] cellGridCopy = cloneCellGrid();

        for (int y = 0; y < cellGrid.length; y++) {
            for (int x = 0; x < cellGrid[0].length; x++) {
                int numberCells = countSurroundingCells(x, y);

                if (isAlive(x, y)) {
                    if (numberCells != 2 && numberCells != 3) {
                        cellGridCopy[y][x] = false;
                    }
                } else {
                    if (numberCells == 3) {
                        cellGridCopy[y][x] = true;
                    }
                }
            }
        }

        this.cellGrid = cellGridCopy.clone();
    }

    private boolean[][] cloneCellGrid() {
        boolean[][] copiedCellGrid = new boolean[cellGrid.length][cellGrid[0].length];

        for (int y = 0; y < copiedCellGrid.length; y++) {
            copiedCellGrid[y] = cellGrid[y].clone();
        }

        return  copiedCellGrid;
    }

    private int countSurroundingCells(int x, int y) {
        int aliveCellsCounter = 0;

        for (int yOffset = -1; yOffset <= 1; yOffset++) {
            for (int xOffset = -1; xOffset <= 1; xOffset++) {
                if (xOffset != 0 || yOffset != 0) {
                    int searchX = x + xOffset;
                    int searchY = y + yOffset;

                    if (isInGrid(searchX, searchY)) {
                        if (isAlive(searchX, searchY)) {
                            aliveCellsCounter++;
                        }
                    }
                }

            }
        }

        return aliveCellsCounter;

    }

    public static boolean isInGrid(int x, int y) {
        boolean yCorrect = x >= 0 && x < NUMBER_COL;
        boolean xCorrect = y >= 0 && y < NUMBER_ROW;

        return xCorrect && yCorrect;
    }

    private boolean isAlive(int x, int y) {
        return cellGrid[y][x];
    }

    public boolean[][] getBooleanGrid() {
        return cellGrid;
    }

    public static int getNumberRow() {
        return NUMBER_ROW;
    }

    public static int getNumberCol() {
        return NUMBER_COL;
    }
}
