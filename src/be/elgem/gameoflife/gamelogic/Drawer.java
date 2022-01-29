package be.elgem.gameoflife.gamelogic;

import be.elgem.gameoflife.render.Index;
import be.elgem.gameoflife.render.Position;

public class Drawer {
    private Game game;

    public Drawer(Game game) {
        this.game = game;
    }

    protected void drawLine(Index startIndex, Index endIndex) {
        if (startIndex.equals(endIndex)) {
            game.getCellGrid().putCell(startIndex.getXIndex(), startIndex.getYIndex());
            return;
        }

        int deltaX = Math.abs(endIndex.getXIndex() - startIndex.getXIndex());
        int deltaY = Math.abs(endIndex.getYIndex() - startIndex.getYIndex());
        if (deltaX > deltaY) {
            if (startIndex.getXIndex() < endIndex.getXIndex()) {
                drawLineHorizontal(startIndex, endIndex);
            } else {
                drawLineHorizontal(endIndex, startIndex);
            }
        } else if (deltaX < deltaY) {
            if (startIndex.getYIndex() < endIndex.getYIndex()) {
                drawLineVertically(startIndex, endIndex);
            } else {
                drawLineVertically(endIndex, startIndex);
            }
        } else {
            drawLineDiagonally(startIndex, endIndex);
        }
    }

    private void drawLineHorizontal(Index startIndex, Index endIndex) {
        int deltaX = endIndex.getXIndex() - startIndex.getXIndex();
        int deltaY = endIndex.getYIndex() - startIndex.getYIndex();

        int movingX = startIndex.getXIndex();
        int movingY = startIndex.getYIndex();

        int slope = 2 * deltaY - deltaX;

        for (int x = 0; x < deltaX; x++) {
            if (slope < 0) {
                slope += 2 * deltaY;
            } else {
                slope += 2 * deltaY - 2 * deltaX;
                movingY++;
            }
            movingX++;

            game.getCellGrid().putCell(movingX, movingY);
        }
    }

    private void drawLineVertically(Index startIndex, Index endIndex) {
        int deltaX = endIndex.getXIndex() - startIndex.getXIndex();
        int deltaY = endIndex.getYIndex() - startIndex.getYIndex();

        int movingX = startIndex.getXIndex();
        int movingY = startIndex.getYIndex();

        int slope = 2 * deltaX - deltaY;

        for (int y = 0; y < deltaY; y++) {
            if (slope < 0) {
                slope += 2 * deltaX;
            } else {
                slope += 2 * deltaX - 2 * deltaY;
                movingX++;
            }
            movingY++;

            game.getCellGrid().putCell(movingX, movingY);
        }
    }

    private void drawLineDiagonally(Index startIndex, Index endIndex) {
        int movingX = startIndex.getXIndex();
        int movingY = startIndex.getYIndex();

        int appendX = (startIndex.getXIndex() < endIndex.getXIndex()) ? +1 : -1;
        int appendY = (startIndex.getYIndex() < endIndex.getYIndex()) ? +1 : -1;


        for (int x = startIndex.getXIndex(); x < endIndex.getXIndex(); x++) {
            game.getCellGrid().putCell(movingX, movingY);

            movingX += appendX;
            movingY += appendY;
        }
    }
}
