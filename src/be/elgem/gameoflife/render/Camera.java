package be.elgem.gameoflife.render;

import be.elgem.gameoflife.gamelogic.CellGrid;
import be.elgem.gameoflife.gamelogic.Game;
import be.elgem.gameoflife.gui.GameCanvas;

public class Camera {
    private double x;
    private double y;

    private GameCanvas canvas;

    private double cellSize;

    private int numberDisplayedCellsY;
    private int numberDisplayedCellsX;

    final private static int MIN_ZOOM = 2;
    final private static int MAX_ZOOM = 60;

    public Camera(int x, int y, int zoomLevel, GameCanvas canvas) {
        this.x = x;

        this.y = y;

        this.canvas = canvas;

        cellSize = 40;

        actualizePracticalData();
    }

    /**
     * Mets à jour les données pratiques de la caméra
     */
    private void actualizePracticalData() {
        numberDisplayedCellsX = (int) (canvas.getHeight() / cellSize) + 1;

        numberDisplayedCellsY = (int) (canvas.getWidth() / cellSize) + 1;
    }

    /**
     * Obtenir le décalage en x de la caméra
     *
     * @return
     */
    public int getXOffset() {
        return (int) (x % cellSize);
    }

    /**
     * Obtenir le décalage en y de la caméra
     *
     * @return
     */
    public int getYOffset() {
        return (int) (y % cellSize);
    }

    /**
     * Renvoie l'index d'une cellule depuis une position sur l'écran
     *
     * @param position
     * @return
     */
    public Index getCellIndexFromPosition(Position position) {

        int xIndex = (int) ((position.getXPos() + x) / cellSize);
        int yIndex = (int) ((position.getYPos() + y) / cellSize);

        if (!CellGrid.isInGrid(xIndex, yIndex)) {
            return null;
        }

        return new Index(xIndex, yIndex);
    }


//    public Position getPositionFromIndex(Index index) {
//        int xPos = (int) ((index.getXIndex() + x / cellSize) * cellSize);
//        int yPos = (int) ((index.getYIndex() + y / cellSize) * cellSize);
//
//        return new Position(xPos, yPos);
//    }

    /**
     * Renvoie les coordonées en x de la caméra
     *
     * @return
     */
    public int getX() {
        return (int) x;
    }

    /**
     * Définis les coordonées en x de la caméra
     *
     * @param x
     */
    public void setX(double x) {
        if (x > 0) {
            this.x = x;
        }


        actualizePracticalData();
    }

    /**
     * Renvoie les coordonées en y des la caméra
     *
     * @return
     */
    public int getY() {
        return (int) y;
    }

    /**
     * Définis les coordonées en y de la caméra
     *
     * @param y
     */
    public void setY(double y) {
        if (y > 0) {
            this.y = y;
        }

        actualizePracticalData();
    }

    /**
     * Zoom la caméra de la valeur fournie
     *
     * @param zoomFactor
     */
    public void zoom(double zoomFactor) {
        if (cellSize + zoomFactor <= MAX_ZOOM && cellSize + zoomFactor >= MIN_ZOOM) {
            fixCameraPositionAfterZoom(zoomFactor);
        }

        cellSize = Math.max(Math.min(cellSize + zoomFactor, MAX_ZOOM), MIN_ZOOM);

        actualizePracticalData();
    }

    private void fixCameraPositionAfterZoom(double zoomFactor) {
        setX(x + zoomFactor * (x / (cellSize)));
        setY(y + zoomFactor * (y / (cellSize)));
    }

    /**
     * Renvoie la taille d'une cellule
     *
     * @return
     */
    public double getCellSize() {
        return cellSize;
    }

    /**
     * Renvoie le nombre de cellule affichées en y
     *
     * @return
     */
    public int getNumberDisplayedCellsY() {
        return numberDisplayedCellsY;
    }

    /**
     * Renvoie le nombre de cellule affichées en x
     *
     * @return
     */
    public int getNumberDisplayedCellsX() {
        return numberDisplayedCellsX;
    }
}
