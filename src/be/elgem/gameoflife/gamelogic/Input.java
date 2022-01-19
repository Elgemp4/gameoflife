package be.elgem.gameoflife.gamelogic;

import be.elgem.gameoflife.gui.GameDisplay;
import be.elgem.gameoflife.render.Camera;
import be.elgem.gameoflife.render.Index;
import be.elgem.gameoflife.render.Position;
import be.elgem.gameoflife.render.Renderer;

import javax.swing.*;
import java.awt.event.*;

public class Input implements MouseMotionListener, MouseListener, MouseWheelListener, KeyListener {
    //https://stackoverflow.com/questions/5344823/how-can-i-listen-for-key-presses-within-java-swing-across-all-components

    private GameDisplay gameDisplay;
    private Camera camera;
    private Game game;
    private Renderer renderer;

    private boolean[] pressedKey;

    private Position lastMousePosition = null;
    private boolean isHovering = false;

    public Input(GameDisplay gameDisplay) {
        this.gameDisplay = gameDisplay;
        
        this.camera = gameDisplay.getCamera();
        
        this.game = gameDisplay.getGame();
        
        this.renderer = gameDisplay.getRenderer();

        pressedKey = new boolean[256];
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Position clickPosition = new Position(e.getX(), e.getY());
        Index clickedIndex = gameDisplay.getCamera().getCellIndexFromPosition(clickPosition);

        if (isInMovingMode(e)) {
            int deltaX = lastMousePosition.getXPos() - e.getX();
            int deltaY = lastMousePosition.getYPos() - e.getY();

            camera.setX(camera.getX() + deltaX);
            camera.setY(camera.getY() + deltaY);

            updateLastMousePosition(e);
            gameDisplay.repaint();
        }

        else if(clickedIndex == null) {
            return;
        }

        else if (SwingUtilities.isLeftMouseButton(e)) {
            game.getCellGrid().putCell(clickedIndex.getXIndex(), clickedIndex.getYIndex());
            gameDisplay.repaint();
        }

        else if (SwingUtilities.isRightMouseButton(e)) {
            game.getCellGrid().removeCell(clickedIndex.getXIndex(), clickedIndex.getYIndex());
            gameDisplay.repaint();
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
            gameDisplay.repaint();
        } else if (SwingUtilities.isRightMouseButton(e)) {
            game.getCellGrid().removeCell(clickedIndex.getXIndex(), clickedIndex.getYIndex());
            gameDisplay.repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        updateLastMousePosition(e);
        renderer.showGrid(true);
        gameDisplay.repaint();

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        updateLastMousePosition(e);

        if(isInMovingMode(e) && isHovering == false)
            renderer.showGrid(false);
        gameDisplay.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        gameDisplay.requestFocus();

        isHovering = true;

        renderer.showGrid(true);
        gameDisplay.repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        isHovering = false;

        if(!isInMovingMode(e)) {
            renderer.showGrid(false);
            gameDisplay.repaint();
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        gameDisplay.requestFocus();

        camera.zoom(-1 * e.getWheelRotation());

        gameDisplay.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
        pressedKey[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKey[e.getKeyCode()] = false;
    }

    public boolean isPressed(int keyCode) {
        return pressedKey[keyCode];
    }

    private void updateLastMousePosition(MouseEvent e) {
        lastMousePosition = new Position(e.getX(), e.getY());
    }

    private boolean isInMovingMode(MouseEvent e) {
        return SwingUtilities.isMiddleMouseButton(e) || isPressed(KeyEvent.VK_CONTROL);
    }
}
