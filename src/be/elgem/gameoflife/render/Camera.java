package be.elgem.gameoflife.render;

import be.elgem.gameoflife.gamelogic.CellGrid;
import be.elgem.gameoflife.gui.GamePanel;

import java.awt.*;

public class Camera {
    private double x;
    private double y;

    private GamePanel canvas;

    private double cellSize;

    private double numberDisplayedCellsY;
    private double numberDisplayedCellsX;

    final private static int MIN_ZOOM = 2;
    final private static int MAX_ZOOM = 60;

    public Camera(int x, int y, int cellSize, GamePanel canvas) {
        this.x = x;

        this.y = y;

        this.canvas = canvas;

        this.cellSize = cellSize;

        actualizeDisplayedCells();
    }

    /**
     * Mets à jour les données pratiques de la caméra
     */
    public void actualizeDisplayedCells() {
        Dimension gameCanvasSize = canvas.getPreferredSize();

        numberDisplayedCellsX = gameCanvasSize.getWidth() / cellSize;

        numberDisplayedCellsY = gameCanvasSize.getHeight() / cellSize;
    }

    /**
     * Obtenir le décalage en x de la caméra
     *
     * @return
     */
    public int getXOffset() {
//        System.out.println((int) (x % cellSize));
        if(x>0)
            return (int) (x % cellSize);
        else
            return (int) ((cellSize - Math.abs(x % cellSize))%cellSize);
    }

    /**
     * Obtenir le décalage en y de la caméra
     *
     * @return
     */
    public int getYOffset() {
        if(y>0)
            return (int) (y % cellSize);
        else
            return (int) ((cellSize - Math.abs(y % cellSize))%cellSize);
    }

    /**
     * Renvoie l'index d'une cellule depuis une position sur l'écran
     *
     * @param screenPosition
     * @return
     */
    public Index getCellIndexFromPosition(Position screenPosition) {

        int xIndex = (int) Math.floor((screenPosition.getXPos() + x) / cellSize);
        int yIndex = (int) Math.floor((screenPosition.getYPos() + y) / cellSize);

        if (!CellGrid.isInGrid(xIndex, yIndex)) {
            return null;
        }

        return new Index(xIndex, yIndex);
    }

    public Position getGamePositionFromScreenPosition(Position screenPosition) {
        int posX = (int) (screenPosition.getXPos() + x);
        int posY = (int) (screenPosition.getYPos() + y);

        return new Position(posX, posY);
    }

    public Position getScreenPositionFromGamePosition(Position gamePosition) {
        int posX = clamp(0, canvas.getWidth(), (int) (gamePosition.getXPos() - x));
        int posY= clamp(0, canvas.getHeight(), (int) (gamePosition.getYPos() - y));

        return new Position(posX, posY);
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
        this.x = x;

        actualizeDisplayedCells();
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
        this.y = y;

        actualizeDisplayedCells();
    }

    /**
     * Zoom la caméra de la valeur fournie
     *
     * @param zoomFactor
     */
    public void zoom(double zoomFactor) {
        if (cellSize + zoomFactor <= MAX_ZOOM && cellSize + zoomFactor >= MIN_ZOOM) {
            fixCameraPositionAfterZoom(zoomFactor);

            cellSize = Math.max(Math.min(cellSize + zoomFactor, MAX_ZOOM), MIN_ZOOM);

            actualizeDisplayedCells();
        }


    }

    private void fixCameraPositionAfterZoom(double zoomFactor) {
        /**
         * Pour corriger la position on ajoute la position en y + l'aggrandissement des cellules * le nombres de
         * cellules jusqu'en y + le nombres de cellules affichée à l'écran multiplié par l'aggrandissement le tout
         * divisé par deux sinon ça s'aggrandirais pas enbase à gauche
         */
        setX(x + zoomFactor * (x / cellSize) + zoomFactor * numberDisplayedCellsX/2);
        setY(y + zoomFactor * (y / cellSize) + zoomFactor * numberDisplayedCellsY/2);
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
    public double getNumberDisplayedCellsY() {
        return numberDisplayedCellsY;
    }

    /**
     * Renvoie le nombre de cellule affichées en x
     *
     * @return
     */
    public double getNumberDisplayedCellsX() {
        return numberDisplayedCellsX;
    }

    public void setCellSize(double cellSize) {
        this.cellSize = cellSize;
    }

    public static int clamp(int min, int max, int nb) {
        return Math.min(Math.max(min, nb), max);
    }
}
