package be.elgem.gameoflife.render;

import be.elgem.gameoflife.gui.GameCanvas;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Renderer {
    final private Camera camera;
    final private GameCanvas gameCanvas;

    public Renderer(GameCanvas gameCanvas) {
        this.gameCanvas = gameCanvas;

        this.camera = gameCanvas.getCamera();
    }

    /**
     * Affiche le jeu à l'écran
     *
     * @param bufferStrategy
     */
    public void render(BufferStrategy bufferStrategy) {

        Graphics graphics = bufferStrategy.getDrawGraphics();

        drawBackground(graphics);

        drawCells(graphics);

        drawGrid(graphics);

        graphics.dispose();

        bufferStrategy.show();
    }

    /**
     * Dessine l'arrière plan en noir
     *
     * @param graphics
     */
    private void drawBackground(Graphics graphics) {
        graphics.setColor(Color.BLACK);

        graphics.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
    }

    /**
     * Dessine le grille du jeu
     *
     * @param graphics
     */
    private void drawGrid(Graphics graphics) {
        Dimension canvasSize = gameCanvas.getPreferredSize();

        graphics.setColor(Color.gray);

        for (int yIndex = 0; yIndex <= camera.getNumberDisplayedCellsY() + 1; yIndex++) {
            int yPos = (int) (camera.getCellSize() * yIndex - camera.getYOffset());

            graphics.drawLine(0, yPos, (int) canvasSize.getWidth(), yPos);
        }

        for (int xIndex = 0; xIndex <= camera.getNumberDisplayedCellsX() + 1; xIndex++) {
            int xPos = (int) (camera.getCellSize() * xIndex - camera.getXOffset());

            graphics.drawLine(xPos, 0, xPos, (int) canvasSize.getHeight());
        }
    }

    /**
     * Dessine les cellules du jeu
     *
     * @param graphics
     */
    private void drawCells(Graphics graphics) {
        boolean[][] cellsArray = gameCanvas.getGame().getCellGrid().getBooleanGrid();
        graphics.setColor(Color.WHITE);

        for (int y = 0; y < gameCanvas.getHeight() + camera.getCellSize(); y += camera.getCellSize()) {
            for (int x = 0; x < gameCanvas.getWidth() + camera.getCellSize(); x += camera.getCellSize()) {
                Index index = camera.getCellIndexFromPosition(new Position(x, y));
                if (index == null) {
                    continue;
                }
                if (cellsArray[index.getYIndex()][index.getXIndex()]) {
                    graphics.fillRect(x - camera.getXOffset(), y - camera.getYOffset(), (int) camera.getCellSize(), (int) camera.getCellSize());
                }
            }
        }
    }

}
