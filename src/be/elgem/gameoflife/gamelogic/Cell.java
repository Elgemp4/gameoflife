package be.elgem.gameoflife.gamelogic;

public class Cell {
    private int attenantCellsCount;
    private boolean isAlive;

    public Cell() {
        attenantCellsCount = 0;
        isAlive = false;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void addAttenantCell() {
        attenantCellsCount++;
    }

    public void removeAttenantCell() {
        attenantCellsCount--;
    }

    public int getAttenantCellsCount() {
        return attenantCellsCount;
    }
}
