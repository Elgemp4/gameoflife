import javax.accessibility.AccessibleIcon;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionEvent;

public class Game {
    final static public int NUMBER_ROW = 50;
    final static public int NUMBER_COL = 50;


    private GameLoop gameLoop;

    private GameCanvas gameCanvas;

    private boolean[][] cellGrid;

    private int gameSpeed = 1;

    public Game(GameCanvas gameCanvas) {
        this.gameCanvas = gameCanvas;

        this.gameLoop = new GameLoop(gameSpeed, this);

        cellGrid = new boolean[NUMBER_ROW][NUMBER_COL];
        cellGrid[5][5] = true;
    }

    public void putCell(int x, int y) {
        cellGrid[y / NUMBER_ROW][x / NUMBER_COL] = true;
    }

    public void removeCell(int x, int y) {
        cellGrid[y / NUMBER_ROW][x / NUMBER_COL] = false;
    }

    public void toggleExecution(ActionEvent event) {
        if (gameLoop.isRunning()) {
            gameLoop.stop();
            ((JButton) event.getSource()).setText("Start");
        } else {
            gameLoop.start();
            ((JButton) event.getSource()).setText("Stop");
        }
    }

    public void step(ActionEvent event) {
        update();
        render();
    }

    public void reset(ActionEvent event) {
        if (gameLoop.isRunning()) {
            gameLoop.stop();
        }

        cellGrid = new boolean[NUMBER_ROW][NUMBER_COL];

        gameCanvas.render();

    }

    public void setExecutionSpeed(ChangeEvent changeEvent) {
        gameSpeed = ((JSlider) changeEvent.getSource()).getValue();
        gameLoop.setUpdateRate(gameSpeed);
    }

    public void update() {

    }

    public void render() {
        gameCanvas.render();
    }

    public int getGameSpeed() {
        return gameSpeed;
    }

    public void setGameSpeed(int gameSpeed) {
        this.gameSpeed = gameSpeed;
    }

    public boolean[][] getCellGrid() {
        return cellGrid;
    }
}
