package be.elgem.gameoflife.gamelogic;

import be.elgem.gameoflife.gui.GamePanel;
import be.elgem.gameoflife.render.Camera;
import be.elgem.gameoflife.render.Index;
import be.elgem.gameoflife.render.Position;
import be.elgem.gameoflife.render.Renderer;

import javax.swing.*;
import java.awt.event.*;

public class Input implements MouseMotionListener, MouseListener, MouseWheelListener, KeyListener {
    private GamePanel gamePanel;
    private Camera camera;
    private Game game;
    private Renderer renderer;

    private Position lastMousePosition = null;
    private boolean isHovering = false;

    public Input(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        
        this.camera = gamePanel.getCamera();
        
        this.game = gamePanel.getGame();
        
        this.renderer = gamePanel.getRenderer();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Position clickPosition = new Position(e.getX(), e.getY());
        Index clickedIndex = gamePanel.getCamera().getCellIndexFromPosition(clickPosition);

        if (SwingUtilities.isMiddleMouseButton(e)) {
            int deltaX = lastMousePosition.getXPos() - e.getX();
            int deltaY = lastMousePosition.getYPos() - e.getY();

            camera.setX(camera.getX() + deltaX);
            camera.setY(camera.getY() + deltaY);

            lastMousePosition = new Position(e.getX(), e.getY());
            gamePanel.repaint();
        }

        else if(clickedIndex == null) {
            return;
        }

        else if (SwingUtilities.isLeftMouseButton(e)) {
            game.getCellGrid().putCell(clickedIndex.getXIndex(), clickedIndex.getYIndex());
            gamePanel.repaint();
        }

        else if (SwingUtilities.isRightMouseButton(e)) {
            game.getCellGrid().removeCell(clickedIndex.getXIndex(), clickedIndex.getYIndex());
            gamePanel.repaint();
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
            gamePanel.repaint();
        } else if (SwingUtilities.isRightMouseButton(e)) {
            game.getCellGrid().removeCell(clickedIndex.getXIndex(), clickedIndex.getYIndex());
            gamePanel.repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(SwingUtilities.isMiddleMouseButton(e))
            lastMousePosition = new Position(e.getX(), e.getY());
        renderer.showGrid(true);
        gamePanel.repaint();

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        lastMousePosition = new Position(e.getX(), e.getY());

        System.out.println(SwingUtilities.isMiddleMouseButton(e));
        if(SwingUtilities.isMiddleMouseButton(e) == true && isHovering == false)
            renderer.showGrid(false);
        gamePanel.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        isHovering = true;
        renderer.showGrid(true);
        gamePanel.repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        isHovering = false;

        if(!SwingUtilities.isMiddleMouseButton(e)) {
            renderer.showGrid(false);
            gamePanel.repaint();
        }

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        camera.zoom(-1 * e.getWheelRotation());

        gamePanel.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
