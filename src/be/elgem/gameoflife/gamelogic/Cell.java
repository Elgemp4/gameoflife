package be.elgem.gameoflife.gamelogic;

public class Cell {
    private int adjacentCellCount;
    private boolean isAlive;

    public Cell() {
        adjacentCellCount = 0;
        isAlive = false;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void incrementAdjacentCellCount() {
        adjacentCellCount++;
    }

    public void decrementAdjacentCellCount() {
        adjacentCellCount--;
    }

    public int getAdjacentCellCount() {
        return adjacentCellCount;
    }
}
