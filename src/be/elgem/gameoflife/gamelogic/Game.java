package be.elgem.gameoflife.gamelogic;

import be.elgem.gameoflife.gui.GameCanvas;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionEvent;

public class Game {
    final private GameLoop gameLoop;

    final private GameCanvas gameCanvas;

    final private CellGrid cellGrid;

    private int gameSpeed = 1;

    public Game(GameCanvas gameCanvas) {
        this.gameCanvas = gameCanvas;

        this.gameLoop = new GameLoop(gameSpeed, this);

        cellGrid = new CellGrid(1000, 1000);
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

    public void step(ActionEvent ignoredEvent) {
        update();
        render();
    }

    public void reset(ActionEvent ignoredEvent) {
        if (gameLoop.isRunning()) {
            gameLoop.stop();
        }

        cellGrid.reset();

        gameCanvas.render();

    }

    public void setExecutionSpeed(ChangeEvent changeEvent) {
        gameSpeed = ((JSlider) changeEvent.getSource()).getValue();
        gameLoop.setUpdateRate(gameSpeed);
    }

    public void update() {
        cellGrid.checkCells();
    }

    public void render() {
        gameCanvas.render();
    }

    public int getGameSpeed() {
        return gameSpeed;
    }

    public CellGrid getCellGrid() {
        return cellGrid;
    }



}
