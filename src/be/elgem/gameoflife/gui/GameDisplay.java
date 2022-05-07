package be.elgem.gameoflife.gui;

import be.elgem.gameoflife.gamelogic.GameLogic;
import be.elgem.gameoflife.gamelogic.Game;
import be.elgem.gameoflife.gamelogic.Input;
import be.elgem.gameoflife.render.Camera;
import be.elgem.gameoflife.render.Renderer;

import javax.swing.*;
import java.awt.*;

public class GameDisplay extends JComponent{
    private static GameDisplay gameDisplayClass;

    private Game game;
    private MainWindow window;

    private Camera camera;
    private Renderer renderer;
    private Input input;

    public GameDisplay(int width, int height) {
        super();

        GameDisplay.gameDisplayClass = this;

        this.game = new Game();

        this.window = MainWindow.getMainWindowClass();

        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(width, height));

        this.setDoubleBuffered(true);

        this.camera = new Camera(2000000, 2000000, 40);

        this.renderer = new Renderer();

        this.input = new Input();

        addMouseWheelListener(input);
        addMouseListener(input);
        addMouseMotionListener(input);
        addKeyListener(input);

        this.repaint();
    }


    public void postWindowCreationLoading() {
        this.camera.actualizeDisplayedCells();
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        camera.actualizeDisplayedCells();
        renderer.render(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(window.getWidth(), window.getHeight());
    }

    public Game getGame() {
        return game;
    }

    public Camera getCamera() {
        return camera;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public static GameDisplay getGameDisplayClass() {
        return gameDisplayClass;
    }
}
