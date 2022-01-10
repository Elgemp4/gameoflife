package be.elgem.gameoflife.gamelogic;

/**
 * La class CellGrid est une classe représentant un grille de cellule d'un jeu de la vie, chaque cellule est un byte
 * dont les trois premiers bits sont inutilisés, les quatres suivants encodent le nombre de cellules adjacentes et
 * le dernier encode si la cellule est vivante.
 */
public class CellGrid {

    private static int NUMBER_ROW = 50;
    private static int NUMBER_COL = 50;

    private byte[][] byteCellGrid;

    public CellGrid(int numberRow, int numberCol) {
        CellGrid.NUMBER_ROW = numberRow;
        CellGrid.NUMBER_COL = numberCol;

        byteCellGrid = new byte[NUMBER_COL][NUMBER_ROW];
    }

    public CellGrid(byte[][] byteCellGrid) {
        this.byteCellGrid = byteCellGrid;
    }

    /**
     * Mets une cellule en x y
     * @param x
     * @param y
     */
    public void putCell(int x, int y) {
        if(!ByteCell.isAlive(byteCellGrid[y][x])) {
            byteCellGrid[y][x] = ByteCell.setAlive(byteCellGrid[y][x], true);
            informAdditionSurroundingCells(x, y);
        }
    }

    /**
     * Enlève un cellule en x y
     * @param x
     * @param y
     */
    public void removeCell(int x, int y) {
        if(ByteCell.isAlive(byteCellGrid[y][x])) {
            byteCellGrid[y][x] = ByteCell.setAlive(byteCellGrid[y][x], false);
            informRemovalSurroundingCells(x, y);
        }

    }

    public void informAdditionSurroundingCells(int x, int y) {
        for (int yOffset = -1; yOffset <= 1; yOffset++) {
            for (int xOffset = -1; xOffset <= 1; xOffset++) {
                if(yOffset!=0 || xOffset!=0) {
                    int searchX = x + xOffset;
                    int searchY = y + yOffset;

                    if(isInGrid(searchX, searchY)) {
                        byteCellGrid[searchY][searchX] = ByteCell.incrementAdjacentCellCount(byteCellGrid[searchY][searchX]);
                    }
                }
            }
        }
    }

    public void informRemovalSurroundingCells(int x, int y) {
        for (int yOffset = -1; yOffset <= 1; yOffset++) {
            for (int xOffset = -1; xOffset <= 1; xOffset++) {
                if(yOffset!=0 || xOffset!=0) {
                    int searchX = x + xOffset;
                    int searchY = y + yOffset;

                    if(isInGrid(searchX, searchY)) {
                        byteCellGrid[searchY][searchX] = ByteCell.decrementAdjacentCellCount(byteCellGrid[searchY][searchX]);
                    }
                }
            }
        }
    }

    /**
     * Reset la grille de boolean en une nouvelle grille
     */
    public void reset() {
        byteCellGrid = new byte[NUMBER_COL][NUMBER_ROW];
    }

    /**
     * Créer un copie sans pointeur de la grille de boolean
     * @return
     */
    public CellGrid cloneCellGrid() {
        byte[][] copiedByteCellGrid = new byte[byteCellGrid.length][byteCellGrid[0].length];

        for (int y = 0; y < copiedByteCellGrid.length; y++) {
            copiedByteCellGrid[y] = byteCellGrid[y].clone();
        }

        return new CellGrid(copiedByteCellGrid);
    }

    /**
     * Conte le nombre de cellules vivantes autour d'une autre cellule
     * @param x
     * @param y
     * @return
     */
    public int getSurroundingCells(int x, int y) {
        return ByteCell.getAdjacentCellCount(byteCellGrid[y][x]);
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
        return ByteCell.isAlive(byteCellGrid[y][x]);
    }

    /**
     * Retourne la grille de boolean du jeu
     * @return
     */
    public byte[][] getCellMatrix() {
        return byteCellGrid;
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
