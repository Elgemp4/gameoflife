package be.elgem.gameoflife.render;

import be.elgem.gameoflife.gamelogic.CellGrid;
import be.elgem.gameoflife.gamelogic.GameLoop;
import be.elgem.gameoflife.gui.GameCanvas;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Renderer {
    final private Camera camera;
    final private GameCanvas gameCanvas;
    final private GameLoop gameLoop;

    private boolean isVisible = false;

    private EGridVisibility gridVisibility = EGridVisibility.HYBRID;

    public Renderer(GameCanvas gameCanvas) {
        this.gameCanvas = gameCanvas;

        this.camera = gameCanvas.getCamera();

        this.gameLoop = gameCanvas.getGame().getGameLoop();
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

        drawFPSCount(graphics);

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
        if(!canGridBeDisplayed()){
            return;
        }

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
        CellGrid cellsGrid = gameCanvas.getGame().getCellGrid();
        byte[][] byteCellMatrices = cellsGrid.getCellMatrix();
        graphics.setColor(Color.WHITE);

        for (int y = 0; y < gameCanvas.getHeight() + camera.getCellSize(); y += camera.getCellSize()) {
            for (int x = 0; x < gameCanvas.getWidth() + camera.getCellSize(); x += camera.getCellSize()) {
                Index index = camera.getCellIndexFromPosition(new Position(x, y));
                if (index == null) {
                    continue;
                }
                if (cellsGrid.isAlive(index.getXIndex(),index.getYIndex())) {
                    graphics.setColor(Color.white);
                    graphics.fillRect(x - camera.getXOffset(), y - camera.getYOffset(), (int) camera.getCellSize(), (int) camera.getCellSize());

                }
//                graphics.setColor(Color.red);
//                graphics.drawString(""+ ByteCell.getAdjacentCellCount(byteCellMatrices[index.getYIndex()][index.getXIndex()]), x - camera.getXOffset(), (int)(y - camera.getYOffset() + camera.getCellSize()));
            }
        }
    }

    private void drawFPSCount(Graphics graphics) {
        graphics.setColor(Color.green);
        graphics.drawString("FPS : " + gameLoop.getFps(), 5,15);
    }

    public boolean canGridBeDisplayed() {
        switch (gridVisibility){
            case ALWAYS_SHOWN:
                return true;
            case ALWAYS_HIDE:
                return false;
            default:
                return isVisible;
        }
    }

    public void showGrid(boolean visible) {
        this.isVisible = visible;
    }

    public void setGridVisibility(EGridVisibility gridVisibility) {
        this.gridVisibility = gridVisibility;
    }
}
