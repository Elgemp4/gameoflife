package be.elgem.gameoflife.gui;

import be.elgem.gameoflife.gamelogic.CellGrid;
import be.elgem.gameoflife.gamelogic.Game;
import be.elgem.gameoflife.gamelogic.Input;
import be.elgem.gameoflife.render.Camera;
import be.elgem.gameoflife.render.Index;
import be.elgem.gameoflife.render.Position;
import be.elgem.gameoflife.render.Renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

public class GamePanel extends JComponent{
    private Game game;
    private MainWindow window;

    private Camera camera;
    private Renderer renderer;
    private Input input;

    public GamePanel(MainWindow window, int width, int height) {
        super();

        this.game = new Game(this);

        this.window = window;

        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(width, height));

        this.setDoubleBuffered(true);

        this.camera = new Camera(0, 0, 40, this);
        this.camera.setX(CellGrid.getNumberCol() * camera.getCellSize() / 2);
        this.camera.setY(CellGrid.getNumberRow() * camera.getCellSize() / 2);

        this.renderer = new Renderer(this);

        this.input = new Input(this);

        window.setJMenuBar(new MenuBar(this));

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

        requestFocus(); //Nécessaire car lorsque l'on clique sur un bouton on perd le focus sur le gamePanel empêchant
                        // ainsi de pouvoir utiliser le clavier

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
