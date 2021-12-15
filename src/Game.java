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
    private int yOffset;

    public Game(GameCanvas gameCanvas) {
        this.gameCanvas = gameCanvas;

        this.gameLoop = new GameLoop(gameSpeed, this);

        cellGrid = new boolean[NUMBER_ROW][NUMBER_COL];
        cellGrid[5][5] = true;
        cellGrid[5][6] = true;
        cellGrid[5][7] = true;
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
        //checkCells();
        System.out.println(countSurroundingCells(6, 4));
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

    private void checkCells() {
        for (int y = 0; y < cellGrid.length; y++) {
            for (int x = 0; x < cellGrid[0].length; x++) {
                int numberCells = countSurroundingCells(x, y);
                System.out.println(numberCells);
                if (isAlive(x, y)) {
                    if (numberCells != 2 && numberCells != 3) {
                        cellGrid[y][x] = false;
                    }
                } else {
                    if (numberCells == 3) {
                        cellGrid[y][x] = true;
                    }
                }
            }

        }
    }

    private int countSurroundingCells(int x, int y) {
        int aliveCellsCounter = 0;

        for (int yOffset = -1; yOffset <= 1; yOffset++) {
            for (int xOffset = -1; xOffset <= 1; xOffset++) {

                if (xOffset != 0 && yOffset != 0) {
                    int searchX = x + xOffset;
                    int searchY = y + yOffset;

                    if (isInGrid(searchX, searchY)) {
                        if (isAlive(searchX, searchY)) {
                            aliveCellsCounter++;
                        }
                    }
                }

            }
        }

        return aliveCellsCounter;

    }

    private boolean isInGrid(int x, int y) {
        boolean yCorrect = x >= 0 && x < cellGrid.length;
        boolean xCorrect = y >= 0 && y < cellGrid[0].length;

        return xCorrect && yCorrect;
    }

    private boolean isAlive(int x, int y) {
        return cellGrid[y][x];
    }
}
