package be.elgem.gameoflife.render;

import be.elgem.gameoflife.gamelogic.CellGrid;
import be.elgem.gameoflife.gamelogic.GameLoop;
import be.elgem.gameoflife.gui.GamePanel;
import be.elgem.gameoflife.gui.MainWindow;

import java.awt.*;

public class Renderer {
    final private Camera camera;
    final private GamePanel gamePanel;
    final private GameLoop gameLoop;

    private boolean isDarkTheme = true;
    private boolean isVisible = false;

    private Color lightBackground;

    private EGridVisibility gridVisibility = EGridVisibility.HYBRID;

    public Renderer(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        this.camera = gamePanel.getCamera();

        this.gameLoop = gamePanel.getGame().getGameLoop();

        lightBackground = new Color(230,230,230);
    }

    /**
     * Affiche le jeu à l'écran
     *
     */
    public void render(Graphics graphics) {
        try {
            if(MainWindow.isUnix())
                Toolkit.getDefaultToolkit().sync();

            drawBackground(graphics);

            drawCells(graphics);

            drawGrid(graphics);

            drawFPSCount(graphics);

            graphics.dispose();
        }
        catch (Exception e) {}

    }

    /**
     * Dessine l'arrière plan en noir
     *
     * @param graphics
     */
    private void drawBackground(Graphics graphics) {
        graphics.setColor(getBackgroundColor());

        graphics.fillRect(0, 0, gamePanel.getWidth(), gamePanel.getHeight());
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

        Dimension canvasSize = gamePanel.getPreferredSize();

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
        CellGrid cellsGrid = gamePanel.getGame().getCellGrid();
//        byte[][] byteCellMatrices = cellsGrid.getCellMatrix();
        graphics.setColor(getCellColor());

        for (int y = 0; y <= gamePanel.getHeight() + camera.getCellSize(); y += camera.getCellSize()) {
            for (int x = 0; x <= gamePanel.getWidth() + camera.getCellSize(); x += camera.getCellSize()) {
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
        gamePanel.getGame().render();
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
