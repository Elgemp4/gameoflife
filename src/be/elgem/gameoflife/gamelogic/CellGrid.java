package be.elgem.gameoflife.gamelogic;

public class CellGrid {

    private static int NUMBER_ROW = 50;
    private static int NUMBER_COL = 50;

    private boolean[][] cellGrid;

    private GameLoop gameLoop;

    public CellGrid(int numberRow, int numberCol, GameLoop gameLoop) {
        CellGrid.NUMBER_ROW = numberRow;
        CellGrid.NUMBER_COL = numberCol;

        this.gameLoop = gameLoop;

        cellGrid = new boolean[numberCol][numberRow];
    }

    /**
     * Mets une cellule en x y
     * @param x
     * @param y
     */
    public void putCell(int x, int y) {
        System.out.println("x : " + x + " y : " + y);
        cellGrid[y][x] = true;
    }

    /**
     * Enlève un cellule en x y
     * @param x
     * @param y
     */
    public void removeCell(int x, int y) {
        cellGrid[y][x] = false;
    }

    /**
     * Reset la grille de boolean en une nouvelle grille
     */
    public void reset() {
        cellGrid = new boolean[NUMBER_COL][NUMBER_ROW];
    }

    /**
     * Vérifie toute les cellules du tableaux en regardant si elles doivent vivre ou mourir
     */
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

        //Dans le cas d'un reset il peut arriver que la grille soit clonée après que la grille est été reset et à cause
        //de ça la grille n'est pas reset
        if(gameLoop.isRunning()){
            this.cellGrid = cellGridCopy.clone();
        }

    }

    /**
     * Créer un copie sans pointeur de la grille de boolean
     * @return
     */
    private boolean[][] cloneCellGrid() {
        boolean[][] copiedCellGrid = new boolean[cellGrid.length][cellGrid[0].length];

        for (int y = 0; y < copiedCellGrid.length; y++) {
            copiedCellGrid[y] = cellGrid[y].clone();
        }

        return  copiedCellGrid;
    }

    /**
     * Conte le nombre de cellules vivantes autour d'une autre cellule
     * @param x
     * @param y
     * @return
     */
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
    private boolean isAlive(int x, int y) {
        return cellGrid[y][x];
    }

    /**
     * Retourne la grille de boolean du jeu
     * @return
     */
    public boolean[][] getBooleanGrid() {
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
