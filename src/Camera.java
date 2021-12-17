public class Camera {
    private int x;
    private int y;

    private int zoomLevel;

    private GameCanvas canvas;

    private int cellSize;
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
        return x % cellSize;
    }

    public int getYOffset() {
        return y % cellSize;
    }


    public Index getCellIndexFromPosition(Position position) {

        int xIndex = (position.getXPos() + x) / cellSize;
        int yIndex = (position.getYPos() + y) / cellSize;

        if(!Game.isInGrid(xIndex, yIndex)){
            return null;
        }

        return new Index(xIndex, yIndex);
    }

    public Position getPositionFromIndex(Index index) {
        int xPos = (index.getXIndex() + x / cellSize) * cellSize;
        int yPos = (index.getYIndex() + y / cellSize) * cellSize;

        return new Position(xPos, yPos);
    }


    private void actualizePracticalData() {
        cellSize = canvas.getWidth() / (Game.NUMBER_COL / zoomLevel);

        numberDisplayedCellsX = canvas.getHeight() / cellSize + 1;

        numberDisplayedCellsY = canvas.getWidth() / cellSize + 1;
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

    public int getCellSize() {
        return cellSize;
    }

    public int getNumberDisplayedCellsY() {
        return numberDisplayedCellsY;
    }

    public int getNumberDisplayedCellsX() {
        return numberDisplayedCellsX;
    }
}
