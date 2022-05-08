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
    private Drawer drawer;

    private boolean[] pressedKey;
    private boolean hasBeenStoppedByClick;

    private Position lastMousePosition = null;
    private boolean isHovering = false;

    public Input() {
        this.gameDisplay = GameDisplay.getGameDisplayClass();
        
        this.camera = this.gameDisplay.getCamera();
        
        this.game = this.gameDisplay.getGame();
        
        this.renderer = this.gameDisplay.getRenderer();

        this.drawer = new Drawer();

        pressedKey = new boolean[256];
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Position clickPosition = new Position(e.getX(), e.getY());
        Index clickedIndex = gameDisplay.getCamera().getCellIndexFromPosition(clickPosition);

        if (SwingUtilities.isMiddleMouseButton(e) || isPressed(KeyEvent.VK_CONTROL)) {
            int deltaX = lastMousePosition.getXPos() - e.getX();
            int deltaY = lastMousePosition.getYPos() - e.getY();

            camera.setX(camera.getX() + deltaX);
            camera.setY(camera.getY() + deltaY);
        }

        else if(clickedIndex != null) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                drawer.drawLine(camera.getCellIndexFromPosition(lastMousePosition), clickedIndex);
            }
            else if (SwingUtilities.isRightMouseButton(e)) {
                game.getGAME_LOGIC().removeCell(clickedIndex);

            }
        }

        gameDisplay.repaint();
        updateLastMousePosition(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Position clickPosition = new Position(e.getX(), e.getY());
        Index clickedIndex = camera.getCellIndexFromPosition(clickPosition);

        if(clickedIndex == null) {
            return;
        }

        if (SwingUtilities.isLeftMouseButton(e)) {
            game.getGAME_LOGIC().putCell(clickedIndex);
            gameDisplay.repaint();
        } else if (SwingUtilities.isRightMouseButton(e)) {
            game.getGAME_LOGIC().removeCell(clickedIndex);
            gameDisplay.repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        updateLastMousePosition(e);
        if(game.getGAME_LOOP().isRunning() && !SwingUtilities.isMiddleMouseButton(e)) {
            hasBeenStoppedByClick = true;
            game.stop();
        }
        else{
            hasBeenStoppedByClick = false;
        }
//        renderer.showGrid(true);
        gameDisplay.repaint();

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        updateLastMousePosition(e);
        if(hasBeenStoppedByClick && !SwingUtilities.isMiddleMouseButton(e)){
            game.start();
        }

//        if(SwingUtilities.isMiddleMouseButton(e) == true && isHovering == false)
//            renderer.showGrid(false);
        gameDisplay.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        isHovering = true;
        gameDisplay.requestFocus();

        renderer.showGrid(true);
        gameDisplay.repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        isHovering = false;


        if(!SwingUtilities.isMiddleMouseButton(e)) {
            renderer.showGrid(false);
            gameDisplay.repaint();
        }

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        camera.zoom(-1 * e.getWheelRotation());

        gameDisplay.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
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

}
