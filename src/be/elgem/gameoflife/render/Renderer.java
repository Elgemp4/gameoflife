package be.elgem.gameoflife.render;

import be.elgem.gameoflife.gamelogic.GameLogic;
import be.elgem.gameoflife.gamelogic.GameLoop;
import be.elgem.gameoflife.gui.GameDisplay;

import java.awt.*;

public class Renderer {
    final private Camera camera;
    final private GameDisplay gameDisplay;
    final private GameLoop gameLoop;

    private boolean isDarkTheme = true;
    private boolean isVisible = false;

    private Color lightBackground, darkBackground;

    private EGridVisibility gridVisibility = EGridVisibility.HYBRID;

    private long maxRenderRate = (long) (1.0 / 60) * 1000;

    private long nextRenderTime;

    public Renderer() {
        this.gameDisplay = GameDisplay.getGameDisplayClass();

        this.camera = gameDisplay.getCamera();

        this.gameLoop = gameDisplay.getGame().getGAME_LOOP();

        lightBackground = new Color(255,255,255);

        darkBackground = new Color(26, 26, 26);

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

                drawBackground(graphics); //Marche

                drawCells(graphics); //Marche pas => d'abords régler problème de caméra et posage de cellules

                drawGrid(graphics);//Marche

                drawFPSCount(graphics); //Marche

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

        graphics.setColor(Color.gray);

        //Horizontal lines
        for (int rows = 1; rows <= camera.getNumberDisplayedCellsY() + 1; rows++) {
            int yPos = camera.getCellSize() * rows - camera.getYOffset();

            graphics.drawLine(0, yPos, (int) canvasSize.getWidth(), yPos);
        }

        //Vertical lines
        for (int columns = 1; columns <= camera.getNumberDisplayedCellsX() + 1; columns++) {
            int xPos = camera.getCellSize() * columns - camera.getXOffset();

            graphics.drawLine(xPos, 0, xPos, (int) canvasSize.getHeight());
        }
    }

    /**
     * Dessine les cellules du jeu
     *
     * @param graphics
     */
    private void drawCells(Graphics graphics) {
        GameLogic gameLogic = gameDisplay.getGame().getGAME_LOGIC();
        graphics.setColor(getCellColor());

        for (int y = 0; y <= gameDisplay.getHeight() + camera.getCellSize(); y += camera.getCellSize()) {
            for (int x = 0; x <= gameDisplay.getWidth() + camera.getCellSize(); x += camera.getCellSize()) {
                Index index = camera.getCellIndexFromPosition(new Position(x, y));

                if (gameLogic.isAlive(index.getXIndex(),index.getYIndex())) {
                    graphics.fillRect(x - camera.getXOffset(), y - camera.getYOffset(), camera.getCellSize(), camera.getCellSize());

                }
//                debugCellCount(index, graphics, x, y);
            }
        }
    }

    private void debugCellCount(Index index, Graphics graphics, int x, int y) {
        graphics.setColor(Color.red);
        graphics.drawString("" + gameDisplay.getGame().getGAME_LOGIC().getSurroundingCells(index.getXIndex(), index.getYIndex()), x - camera.getXOffset(), y - camera.getYOffset() + camera.getCellSize());
        graphics.setColor(Color.white);
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
        return (isDarkTheme) ? darkBackground : lightBackground;
    }

    private Color getCellColor() {
        return (isDarkTheme) ? Color.WHITE : Color.BLACK;
    }

    public void setDarkTheme(boolean darkTheme) {
        isDarkTheme = darkTheme;
    }
}
