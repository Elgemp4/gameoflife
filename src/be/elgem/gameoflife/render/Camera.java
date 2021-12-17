package be.elgem.gameoflife.render;

import be.elgem.gameoflife.gamelogic.CellGrid;
import be.elgem.gameoflife.gamelogic.Game;
import be.elgem.gameoflife.gui.GameCanvas;

public class Camera {
    private int x;
    private int y;

    private int zoomLevel;

    private GameCanvas canvas;

    private double cellSize;
    private int numberDisplayedCellsY;
    private int numberDisplayedCellsX;

    public Camera(int x, int y, int zoomLevel, GameCanvas canvas) {
        this.x = x;

        this.y = y;

        this.zoomLevel = zoomLevel;

        this.canvas = canvas;

        actualizePracticalData();
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


    private void actualizePracticalData() {
        cellSize = canvas.getWidth() / (CellGrid.getNumberCol() / zoomLevel);

        numberDisplayedCellsX = (int) (canvas.getHeight() / cellSize + 1);

        numberDisplayedCellsY = (int) (canvas.getWidth() / cellSize + 1);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;

        actualizePracticalData();
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;

        actualizePracticalData();
    }

    public int getZoomLevel() {
        return zoomLevel;
    }

    public void setZoomLevel(int zoomLevel) {
        this.zoomLevel = zoomLevel;

        actualizePracticalData();
    }

    public double getCellSize() {
        return cellSize;
    }

    public double getNumberDisplayedCellsY() {
        return numberDisplayedCellsY;
    }

    public double getNumberDisplayedCellsX() {
        return numberDisplayedCellsX;
    }
}
