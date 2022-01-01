package be.elgem.gameoflife.gui;

import be.elgem.gameoflife.gamelogic.CellGrid;
import be.elgem.gameoflife.gamelogic.Game;
import be.elgem.gameoflife.render.Camera;
import be.elgem.gameoflife.render.Index;
import be.elgem.gameoflife.render.Position;
import be.elgem.gameoflife.render.Renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameCanvas extends Canvas implements MouseMotionListener, MouseListener, MouseWheelListener{
    final private Game game;

    private MainWindow window;

    private Camera camera;
    private Renderer renderer;

    private Position lastMousePosition = null;

    public GameCanvas(MainWindow window, int width, int height) {
        super();

        game = new Game(this);

        this.window = window;

        this.setFocusable(true);
        this.setPreferredSize(new Dimension(width, height));
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Position clickPosition = new Position(e.getX(), e.getY());
        Index clickedIndex = camera.getCellIndexFromPosition(clickPosition);


        if (SwingUtilities.isMiddleMouseButton(e)) {
            int deltaX = lastMousePosition.getXPos() - e.getX();
            int deltaY = lastMousePosition.getYPos() - e.getY();

            camera.setX(camera.getX() + deltaX);
            camera.setY(camera.getY() + deltaY);

            lastMousePosition = new Position(e.getX(), e.getY());
            render();
        }

        else if(clickedIndex == null) {
            return;
        }

        else if (SwingUtilities.isLeftMouseButton(e)) {
            game.getCellGrid().putCell(clickedIndex.getXIndex(), clickedIndex.getYIndex());
            render();
        }

        else if (SwingUtilities.isRightMouseButton(e)) {
            game.getCellGrid().removeCell(clickedIndex.getXIndex(), clickedIndex.getYIndex());
            render();
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Position clickPosition = new Position(e.getX(), e.getY());
        Index clickedIndex = camera.getCellIndexFromPosition(clickPosition);

        if(clickedIndex == null) {
            return;
        }

        if (SwingUtilities.isLeftMouseButton(e)) {
            game.getCellGrid().putCell(clickedIndex.getXIndex(), clickedIndex.getYIndex());
            render();
        } else if (SwingUtilities.isRightMouseButton(e)) {
            game.getCellGrid().removeCell(clickedIndex.getXIndex(), clickedIndex.getYIndex());
            render();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(SwingUtilities.isMiddleMouseButton(e))
            lastMousePosition = new Position(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(SwingUtilities.isMiddleMouseButton(e))
            lastMousePosition = new Position(e.getX(), e.getY());
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        renderer.showGrid(true);
        render();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        renderer.showGrid(false);
        render();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        camera.zoom(-1 * e.getWheelRotation());

        render();
    }

    public void postWindowCreationLoading() {
        this.createBufferStrategy(2);

        this.camera = new Camera(0, 0, 40, this);
        this.camera.setX(CellGrid.getNumberCol() * camera.getCellSize() / 2);
        this.camera.setY(CellGrid.getNumberRow() * camera.getCellSize() / 2);

        this.renderer = new Renderer(this);

        addMouseWheelListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        this.render();
    }

    @Override
    public void paint(Graphics g) {
        render();
        camera.actualizeDisplayedCells();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(window.getWidth() - MainWindow.OPTION_PANE_SIZE, window.getHeight());
    }

    public void render() {
        renderer.render(this.getBufferStrategy());
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
}
