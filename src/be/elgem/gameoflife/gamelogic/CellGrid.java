package be.elgem.gameoflife.gamelogic;

public class CellGrid {

    private static int NUMBER_ROW = 50;
    private static int NUMBER_COL = 50;

    private Cell[][] cellGrid;

    public CellGrid(int numberRow, int numberCol) {
        CellGrid.NUMBER_ROW = numberRow;
        CellGrid.NUMBER_COL = numberCol;

        initCellGrid();
    }

    public CellGrid(Cell[][] cellGrid) {
        this.cellGrid = cellGrid;
    }

    private void initCellGrid() {
        cellGrid = new Cell[NUMBER_COL][NUMBER_ROW];

        for (int y = 0; y < cellGrid.length; y++) {
            for (int x = 0; x < cellGrid[0].length; x++) {
                cellGrid[y][x] = new Cell();
            }
        }
    }

    /**
     * Mets une cellule en x y
     * @param x
     * @param y
     */
    public void putCell(int x, int y) {
        if(!cellGrid[y][x].isAlive()) {
            cellGrid[y][x].setAlive(true);
            addAttenantCell(x, y);
        }
    }

    /**
     * Enlève un cellule en x y
     * @param x
     * @param y
     */
    public void removeCell(int x, int y) {
        if(cellGrid[y][x].isAlive()) {
            cellGrid[y][x].setAlive(false);
            removeAttenantCell(x, y);
        }

    }

    public void addAttenantCell(int x, int y) {
        for (int yOffset = -1; yOffset <= 1; yOffset++) {
            for (int xOffset = -1; xOffset <= 1; xOffset++) {
                if(yOffset!=0 || xOffset!=0) {
                    int searchX = x + xOffset;
                    int searchY = y + yOffset;

                    if(isInGrid(searchX, searchY)) {
                        System.out.println("ici");
                        cellGrid[searchY][searchX].addAttenantCell();
                    }
                }
            }
        }
    }

    public void removeAttenantCell(int x, int y) {
        for (int yOffset = -1; yOffset <= 1; yOffset++) {
            for (int xOffset = -1; xOffset <= 1; xOffset++) {
                if(yOffset!=0 || xOffset!=0) {
                    int searchX = x + xOffset;
                    int searchY = y + yOffset;

                    if(isInGrid(searchX, searchY)) {
                        cellGrid[searchY][searchX].removeAttenantCell();
                    }
                }
            }
        }
    }

    /**
     * Reset la grille de boolean en une nouvelle grille
     */
    public void reset() {
        initCellGrid();
    }

    /**
     * Créer un copie sans pointeur de la grille de boolean
     * @return
     */
    public CellGrid cloneCellGrid() {
        Cell[][] copiedCellGrid = new Cell[cellGrid.length][cellGrid[0].length];

        for (int y = 0; y < copiedCellGrid.length; y++) {
            copiedCellGrid[y] = cellGrid[y].clone();
        }

        return new CellGrid(copiedCellGrid);
    }

    /**
     * Conte le nombre de cellules vivantes autour d'une autre cellule
     * @param x
     * @param y
     * @return
     */
    public int getSurroundingCells(int x, int y) {
        return cellGrid[y][x].getAttenantCellsCount();
    }

    /**
     * Renvoie true si jamais la cellule en x y est dans l'enceinte du tableau et faux dans le cas inverse
     * @param x
     * @param y
     * @return
     */
    public static boolean isInGrid(double x, double y) {
        boolean yCorrect = x >= 0 && x < NUMBER_COL;
        boolean xCorrect = y >= 0 && y < NUMBER_ROW;

        return xCorrect && yCorrect;
    }

    /**
     * Renvoie vrai si jamais la cellule en x y est vivante
     * @param x
     * @param y
     * @return
     */
    public boolean isAlive(int x, int y) {
        return cellGrid[y][x].isAlive();
    }

    /**
     * Retourne la grille de boolean du jeu
     * @return
     */
    public Cell[][] getCellMatrix() {
        return cellGrid;
    }

    /**
     * Retourne le nombre de lignes du jeu
     * @return
     */
    public static int getNumberRow() {
        return NUMBER_ROW;
    }

    /**
     * Retourne le nombre de colones du jeu
     * @return
     */
    public static int getNumberCol() {
        return NUMBER_COL;
    }
}
