package be.elgem.gameoflife.gamelogic;

import be.elgem.gameoflife.render.Index;
import javafx.util.Pair;

public class Drawer {
    private Game game;

    public Drawer(Game game) {
        this.game = game;
    }

    protected void drawLine(Index startIndex, Index endIndex) {
        if (startIndex.equals(endIndex)) {
            game.getGameLogic().putCell(new Pair<>(startIndex.getXIndex(), startIndex.getYIndex()));
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
        } else {
            if (startIndex.getYIndex() < endIndex.getYIndex()) {
                drawLineVertically(startIndex, endIndex);
            } else {
                drawLineVertically(endIndex, startIndex);
            }
        }
    }

    private void drawLineHorizontal(Index startIndex, Index endIndex) {
        int deltaX = endIndex.getXIndex() - startIndex.getXIndex();
        int deltaY = endIndex.getYIndex() - startIndex.getYIndex();

        int positiveDeltaX = Math.abs(deltaX);
        int positiveDeltaY = Math.abs(deltaY);

        int movingX = startIndex.getXIndex();
        int movingY = startIndex.getYIndex();

        int slope = 2 * positiveDeltaY - positiveDeltaX;

        for (int x = 0; x < positiveDeltaX; x++) {
            if (slope < 0) {
                slope += 2 * positiveDeltaY;
            } else {
                slope += 2 * positiveDeltaY - 2 * positiveDeltaX;
                if ((deltaX > 0 && deltaY > 0) || (deltaX < 0 && deltaY < 0))
                    movingY++;
                else
                    movingY--;
            }
            movingX++;

            game.getGameLogic().putCell(new Pair<>(movingX, movingY));
        }
    }

    private void drawLineVertically(Index startIndex, Index endIndex) {
        int deltaX = endIndex.getXIndex() - startIndex.getXIndex();
        int deltaY = endIndex.getYIndex() - startIndex.getYIndex();

        int positiveDeltaX = Math.abs(deltaX);
        int positiveDeltaY = Math.abs(deltaY);

        int movingX = startIndex.getXIndex();
        int movingY = startIndex.getYIndex();

        int slope = 2 * positiveDeltaX - positiveDeltaY;

        for (int y = 0; y < positiveDeltaY; y++) {
            if (slope < 0) {
                slope += 2 * positiveDeltaX;
            } else {
                slope += 2 * positiveDeltaX - 2 * positiveDeltaY;
                if ((deltaX > 0 && deltaY > 0) || (deltaX < 0 && deltaY < 0)) {
                    movingX++;
                }
                else {
                    movingX--;
                }

            }
            movingY++;

            game.getGameLogic().putCell(new Pair<>(movingX, movingY));
        }
    }

}
