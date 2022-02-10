package be.elgem.gameoflife.render;

import be.elgem.gameoflife.gamelogic.ByteCell;
import be.elgem.gameoflife.gamelogic.CellGrid;
import be.elgem.gameoflife.gamelogic.GameLoop;
import be.elgem.gameoflife.gui.GameDisplay;

import java.awt.*;

public class Renderer {
    final private Camera camera;
    final private GameDisplay gameDisplay;
    final private GameLoop gameLoop;

    private boolean isDarkTheme = true;
    private boolean isVisible = false;

    private Color lightBackground;

    private EGridVisibility gridVisibility = EGridVisibility.HYBRID;

    private long maxRenderRate = (long) (1.0 / 60.0) * 1000;

    private long nextRenderTime;

    public Renderer(GameDisplay gameDisplay) {
        this.gameDisplay = gameDisplay;

        this.camera = gameDisplay.getCamera();

        this.gameLoop = gameDisplay.getGame().getGameLoop();

        lightBackground = new Color(230,230,230);

        nextRenderTime = System.currentTimeMillis() + maxRenderRate;
    }

    /**
     * Affiche le jeu à l'écran
     *
     */
    public void render(Graphics graphics) {
        if(System.currentTimeMillis()>=nextRenderTime) {
            nextRenderTime = System.currentTimeMillis() + maxRenderRate;

            try {
                Toolkit.getDefaultToolkit().sync();

                drawBackground(graphics);

                drawCells(graphics);

                drawGrid(graphics);

                drawFPSCount(graphics);

                graphics.dispose();
            } catch (Exception e) {
            }
        }

    }

    /**
     * Dessine l'arrière plan en noir
     *
     * @param graphics
     */
    private void drawBackground(Graphics graphics) {
        graphics.setColor(getBackgroundColor());

        graphics.fillRect(0, 0, gameDisplay.getWidth(), gameDisplay.getHeight());
    }

    /**
     * Dessine le grille du jeu
     *
     * @param graphics
     */
    private void drawGrid(Graphics graphics) {
        if(!canGridBeDisplayed() || camera.getCellSize()==Camera.getMinZoom()){
            return;
        }

        Dimension canvasSize = gameDisplay.getPreferredSize();

        Position minPos = camera.getScreenPositionFromGamePosition(new Position(0, 0));
        Position maxPos = camera.getScreenPositionFromGamePosition(new Position((int) (camera.getCellSize() * CellGrid.getNumberCol()), (int)(camera.getCellSize() * CellGrid.getNumberRow())));

        graphics.setColor(Color.gray);

        //Horizontal lines
        for (int yIndex = 1; yIndex <= camera.getNumberDisplayedCellsY() + 1; yIndex++) {
            int yPos = Camera.clamp(minPos.getYPos(), maxPos.getYPos(), (int) (camera.getCellSize() * yIndex - camera.getYOffset()));

            graphics.drawLine(minPos.getXPos(), yPos, maxPos.getXPos(), yPos);
        }

        //Vertical lines
        for (int xIndex = 0; xIndex <= camera.getNumberDisplayedCellsX() + 1; xIndex++) {
            int xPos = Camera.clamp(minPos.getXPos(), maxPos.getXPos(), (int) (camera.getCellSize() * xIndex - camera.getXOffset()));

            graphics.drawLine(xPos, minPos.getYPos(), xPos, maxPos.getYPos());
        }
    }

    /**
     * Dessine les cellules du jeu
     *
     * @param graphics
     */
    private void drawCells(Graphics graphics) {
        CellGrid cellsGrid = gameDisplay.getGame().getCellGrid();
//        byte[][] byteCellMatrices = cellsGrid.getCellMatrix();
        graphics.setColor(getCellColor());

        for (int y = 0; y <= gameDisplay.getHeight() + camera.getCellSize(); y += camera.getCellSize()) {
            for (int x = 0; x <= gameDisplay.getWidth() + camera.getCellSize(); x += camera.getCellSize()) {
                Index index = camera.getCellIndexFromPosition(new Position(x, y));
                if (index == null) {
                    continue;
                }
                if (cellsGrid.isAlive(index.getXIndex(),index.getYIndex())) {
//                    graphics.setColor(Color.white);
                    graphics.fillRect(x - Math.abs(camera.getXOffset()), y - Math.abs(camera.getYOffset()), (int) camera.getCellSize(), (int) camera.getCellSize());

                }
//                graphics.setColor(Color.red);
//                graphics.drawString(""+ ByteCell.getAdjacentCellCount(byteCellMatrices[index.getYIndex()][index.getXIndex()]), x - camera.getXOffset(), (int)(y - camera.getYOffset() + camera.getCellSize()));
            }
        }
    }

    private void drawFPSCount(Graphics graphics) {
        graphics.setColor(Color.green);
        graphics.drawString("UPS : " + gameLoop.getUps(), 20,15);
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
        gameDisplay.getGame().render();
    }

    private Color getBackgroundColor() {
        return (isDarkTheme) ? Color.BLACK : lightBackground;
    }

    private Color getCellColor() {
        return (isDarkTheme) ? Color.WHITE : Color.BLACK;
    }

    public void setDarkTheme(boolean darkTheme) {
        isDarkTheme = darkTheme;
    }
}
