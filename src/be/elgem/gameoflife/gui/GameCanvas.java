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
import java.awt.image.BufferStrategy;

public class GameCanvas extends JPanel implements MouseMotionListener, MouseListener, MouseWheelListener{
    private Game game;

    private MainWindow window;

    private Camera camera;
    private Renderer renderer;

    private Position lastMousePosition = null;

    private boolean isHovering = false;

    private BufferStrategy bufferStrategy;

    public GameCanvas(MainWindow window, int width, int height) {
        super();

        this.game = new Game(this);

        this.window = window;

        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(width, height));
    }

    public void postWindowCreationLoading() {
        this.setDoubleBuffered(true);

        this.camera = new Camera(0, 0, 40, this);
        this.camera.setX(CellGrid.getNumberCol() * camera.getCellSize() / 2);
        this.camera.setY(CellGrid.getNumberRow() * camera.getCellSize() / 2);

        this.renderer = new Renderer(this);

        addMouseWheelListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        this.repaint();
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
            repaint();
        }

        else if(clickedIndex == null) {
            return;
        }

        else if (SwingUtilities.isLeftMouseButton(e)) {
            game.getCellGrid().putCell(clickedIndex.getXIndex(), clickedIndex.getYIndex());
            repaint();
        }

        else if (SwingUtilities.isRightMouseButton(e)) {
            game.getCellGrid().removeCell(clickedIndex.getXIndex(), clickedIndex.getYIndex());
            repaint();
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
            repaint();
        } else if (SwingUtilities.isRightMouseButton(e)) {
            game.getCellGrid().removeCell(clickedIndex.getXIndex(), clickedIndex.getYIndex());
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(SwingUtilities.isMiddleMouseButton(e))
            lastMousePosition = new Position(e.getX(), e.getY());
            renderer.showGrid(true);
            repaint();

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        lastMousePosition = new Position(e.getX(), e.getY());

        System.out.println(SwingUtilities.isMiddleMouseButton(e));
        if(SwingUtilities.isMiddleMouseButton(e) == true && isHovering == false)
            renderer.showGrid(false);
            repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        isHovering = true;
        renderer.showGrid(true);
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        isHovering = false;

        if(!SwingUtilities.isMiddleMouseButton(e)) {
            renderer.showGrid(false);
            repaint();
        }

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        camera.zoom(-1 * e.getWheelRotation());

        repaint();
    }



//    @Override
//    public void paint(Graphics g) {
//        render();
//
//        if(camera!=null)
//            camera.actualizeDisplayedCells();
//    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

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
}
