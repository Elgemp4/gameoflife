package be.elgem.gameoflife.gamelogic;

/**
 * La class CellGrid est une classe représentant un grille de cellule d'un jeu de la vie, chaque cellule est un byte
 * dont les trois premiers bits sont inutilisés, les quatres suivants encodent le nombre de cellules adjacentes et
 * le dernier encode si la cellule est vivante.
 */
public class CellGrid {

    private static int NUMBER_ROW;
    private static int NUMBER_COL;

    private byte[][] byteCellGrid;

    private int[][] chunkGrid;
    private int chunkSize;


    public CellGrid(int numberRow, int numberCol) {
        CellGrid.NUMBER_ROW = numberRow;
        CellGrid.NUMBER_COL = numberCol;

        chunkSize = 50;

        byteCellGrid = new byte[NUMBER_ROW][NUMBER_COL];

        chunkGrid = new int[NUMBER_ROW / chunkSize][NUMBER_COL / chunkSize];

    }

    public CellGrid(byte[][] byteCellGrid) {
        this.byteCellGrid = byteCellGrid;

        CellGrid.NUMBER_ROW = byteCellGrid.length;
        CellGrid.NUMBER_COL = byteCellGrid[0].length;

        chunkSize = NUMBER_COL / 200;

        chunkGrid = new int[NUMBER_ROW / chunkSize][NUMBER_COL / chunkSize];
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
                        chunkGrid[searchY / chunkSize][searchX / chunkSize]++;
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
                        chunkGrid[searchY / chunkSize][searchX / chunkSize]--;
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
     * Vérifie toute les cellules du tableaux en regardant si elles doivent vivre ou mourir
     */
    public void checkCells() {
        CellGrid cellGridCopy = cloneCellGrid();
        byte[][] cellGridCopyByteCellMatrices = cellGridCopy.getCellMatrix();

        int interation = 0;

        for (int yChunk = 0; yChunk < chunkGrid.length; yChunk++) {
            for (int xChunk = 0; xChunk < chunkGrid[0].length; xChunk++) {
                if(chunkGrid[yChunk][xChunk]!=0){
                    for (int y = yChunk * chunkSize; y < (yChunk + 1) * chunkSize; y++) {
                        for (int x = xChunk * chunkSize; x < (xChunk + 1) * chunkSize; x++) {
                            interation++;
                            if(cellGridCopyByteCellMatrices[y][x]==0) {continue;}

                            int numberCells = cellGridCopy.getSurroundingCells(x, y);

                            if (this.isAlive(x, y)) {
                                if (numberCells != 2 && numberCells != 3) {
                                    this.removeCell(x, y);
                                }
                            }
                            else {
                                if (numberCells == 3) {
                                    this.putCell(x, y);
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println(interation);
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
