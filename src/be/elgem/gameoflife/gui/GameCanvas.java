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

public class GameCanvas extends Canvas implements MouseMotionListener, MouseListener, MouseWheelListener {
    final private Game game;

    private Camera camera;
    private Renderer renderer;

    private Position lastMousePosition = null;

    public GameCanvas(int width, int height) {
        super();

        game = new Game(this);


        this.setFocusable(true);
        this.setPreferredSize(new Dimension(width, height));
    }

    public void postWindowCreationLoading() {
        this.createBufferStrategy(2);

        this.camera = new Camera(0, 0, 40, this);
        this.camera.setX((int) (CellGrid.getNumberCol() * camera.getCellSize() / 2));
        this.camera.setX((int) (CellGrid.getNumberRow() * camera.getCellSize() / 2));

        this.renderer = new Renderer(this);

        addMouseWheelListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

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
        Position clickPosition = new Position(e.getX(), e.getY());
        Index clickedIndex = camera.getCellIndexFromPosition(clickPosition);

        if (SwingUtilities.isLeftMouseButton(e)) {
            game.getCellGrid().putCell(clickedIndex.getXIndex(), clickedIndex.getYIndex());
            render();
        } else if (SwingUtilities.isRightMouseButton(e)) {
            game.getCellGrid().removeCell(clickedIndex.getXIndex(), clickedIndex.getYIndex());
            render();
        } else if (SwingUtilities.isMiddleMouseButton(e)) {
            int deltaX = lastMousePosition.getXPos() - e.getX();
            int deltaY = lastMousePosition.getYPos() - e.getY();

            camera.setX(camera.getX() + deltaX);
            camera.setY(camera.getY() + deltaY);

            lastMousePosition = new Position(e.getX(), e.getY());
            render();
        }

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

    @Override
    public void mouseClicked(MouseEvent e) {
        Position clickPosition = new Position(e.getX(), e.getY());
        Index clickedIndex = camera.getCellIndexFromPosition(clickPosition);

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
        lastMousePosition = new Position(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        lastMousePosition = new Position(e.getX(), e.getY());
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        Position clickPosition = new Position(e.getX(), e.getY());
        Index clickedIndex = camera.getCellIndexFromPosition(clickPosition);

        System.out.println(e.getWheelRotation());
        camera.setZoomLevel(Math.max(10, camera.getZoomLevel() + e.getWheelRotation() * -10));

        Position newCamPos = camera.getPositionFromIndex(new Index((int) (clickedIndex.getXIndex() - camera.getNumberDisplayedCellsX() / 2), (int) (clickedIndex.getYIndex() - camera.getNumberDisplayedCellsY() / 2)));

        camera.setX(newCamPos.getXPos());
        camera.setY(newCamPos.getYPos());

        render();
    }
}
