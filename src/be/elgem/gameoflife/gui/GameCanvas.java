package be.elgem.gameoflife.gui;

import be.elgem.gameoflife.gamelogic.CellGrid;
import be.elgem.gameoflife.gamelogic.Game;
import be.elgem.gameoflife.render.Camera;
import be.elgem.gameoflife.render.Renderer;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class GameCanvas extends Canvas implements MouseMotionListener {
    final private Game game;

    private Camera camera;
    private Renderer renderer;

    public GameCanvas(int width, int height) {
        super();

        game = new Game(this);



        this.setFocusable(true);
        this.setPreferredSize(new Dimension(width, height));
    }

    public void postWindowCreationLoading() {
        this.createBufferStrategy(2);

        this.camera = new Camera(CellGrid.getNumberCol() / 2, CellGrid.getNumberRow() / 2, 400, this);
        this.renderer = new Renderer(this);

        this.render();
    }

    @Override
    public void paint(Graphics g) {
        render();
    }

    public void render() {
        renderer.render(this.getBufferStrategy());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1 -> game.getCellGrid().putCell(e.getX(), e.getY());
            case MouseEvent.BUTTON2 -> game.getCellGrid().removeCell(e.getX(), e.getY());
        }

        render();
        System.out.println("ici");
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    public Game getGame() {
        return game;
    }

    public Camera getCamera() {
        return camera;
    }
}
