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

    public void render(BufferStrategy bufferStrategy) {

        Graphics graphics = bufferStrategy.getDrawGraphics();

        drawBackground(graphics);

        drawGrid(graphics);

        drawCells(graphics);

        graphics.dispose();

        bufferStrategy.show();
    }

    private void drawBackground(Graphics graphics) {
        graphics.setColor(Color.BLACK);

        graphics.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
    }

    private void drawGrid(Graphics graphics) {
        graphics.setColor(Color.lightGray);

        int test = 0;

        for (int yIndex = 0; yIndex <= camera.getNumberDisplayedCellsY(); yIndex++) {
            int yPos = (int)(camera.getCellSize() * yIndex - camera.getYOffset());

            graphics.drawLine(0, yPos, gameCanvas.getHeight(), yPos);
        }

        for (int xIndex = 0; xIndex <= camera.getNumberDisplayedCellsX(); xIndex++) {
            int xPos = (int)(camera.getCellSize() * xIndex - camera.getXOffset());

            graphics.drawLine(xPos, 0, xPos, gameCanvas.getWidth());
        }
    }

    private void drawCells(Graphics graphics) {
        boolean[][] cellsArray = gameCanvas.getGame().getCellGrid().getBooleanGrid();
        graphics.setColor(Color.WHITE);

        for (int y = 0; y < gameCanvas.getHeight(); y += camera.getCellSize()) {
            for (int x = 0; x < gameCanvas.getWidth(); x += camera.getCellSize()) {
                Index index = camera.getCellIndexFromPosition(new Position(x, y));
                if(index==null){
                    continue;
                }
                if (cellsArray[index.getYIndex()][index.getXIndex()]) {
                    graphics.fillRect(x - camera.getXOffset() , y - camera.getYOffset(), (int)camera.getCellSize(), (int)camera.getCellSize());
                }
            }
        }
    }

}
