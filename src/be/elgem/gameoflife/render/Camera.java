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

    private void actualizePracticalData() {
        numberDisplayedCellsX = (int) (canvas.getHeight() / cellSize) + 1;

        numberDisplayedCellsY = (int) (canvas.getWidth() / cellSize) + 1;
    }

    public int getXOffset() {
        return (int)(x % cellSize);
    }

    public int getYOffset() {
        return (int)(y % cellSize);
    }


    public Index getCellIndexFromPosition(Position position) {

        int xIndex = (int)((position.getXPos() + x) / cellSize);
        int yIndex = (int)((position.getYPos() + y) / cellSize);

        if(!CellGrid.isInGrid(xIndex, yIndex)){
            return null;
        }

        return new Index(xIndex, yIndex);
    }

    public Position getPositionFromIndex(Index index) {
        int xPos = (int) ((index.getXIndex() + x / cellSize) * cellSize);
        int yPos = (int) ((index.getYIndex() + y / cellSize) * cellSize);

        return new Position(xPos, yPos);
    }

    public int getX() {
        return (int) x;
    }

    public void setX(double x) {
        if(x>0){
            this.x = x;
        }


        actualizePracticalData();
    }

    public int getY() {
        return (int) y;
    }

    public void setY(double y) {
        if(y>0) {
            this.y = y;
        }


        actualizePracticalData();
    }


    public void zoom(double zoomFactor){
        if(cellSize+zoomFactor<=MAX_ZOOM && cellSize+zoomFactor>=MIN_ZOOM) {//Pour éviter des bouger la caméra lorsque ça ne dézoom pas
            setX(x + zoomFactor * (x / cellSize));
            setY(y + zoomFactor * (y / cellSize));
        }


        cellSize = Math.max(Math.min(cellSize + zoomFactor, MAX_ZOOM), MIN_ZOOM);

        actualizePracticalData();

    }

    public double getCellSize() {
        return cellSize;
    }

    public int getNumberDisplayedCellsY() {
        return numberDisplayedCellsY;
    }

    public int getNumberDisplayedCellsX() {
        return numberDisplayedCellsX;
    }
}
