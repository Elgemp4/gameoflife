package be.elgem.gameoflife.gui;

import be.elgem.gameoflife.gamelogic.GameLogic;
import be.elgem.gameoflife.gamelogic.Game;
import be.elgem.gameoflife.gamelogic.Input;
import be.elgem.gameoflife.render.Camera;
import be.elgem.gameoflife.render.Renderer;

import javax.swing.*;
import java.awt.*;

public class GameDisplay extends JComponent{
    private Game game;
    private MainWindow window;

    private Camera camera;
    private Renderer renderer;
    private Input input;

    public GameDisplay(MainWindow window, int width, int height) {
        super();

        this.game = new Game(this);

        this.window = window;

        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(width, height));

        this.setDoubleBuffered(true);

        this.camera = new Camera(2000000, 2000000, 40, this);

        this.renderer = new Renderer(this);

        this.input = new Input(this);

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

    public Input getInput() {
        return input;
    }

    public MainWindow getWindow() {
        return window;
    }
}
