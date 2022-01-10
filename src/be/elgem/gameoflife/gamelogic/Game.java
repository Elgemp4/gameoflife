package be.elgem.gameoflife.gamelogic;

import be.elgem.gameoflife.gui.GameCanvas;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionEvent;

public class Game {
    final private GameLoop gameLoop;

    final private GameCanvas gameCanvas;

    private CellGrid cellGrid;

    private int gameSpeed = 1;

    public Game(GameCanvas gameCanvas) {
        this.gameCanvas = gameCanvas;

        this.gameLoop = new GameLoop(gameSpeed, this);

        cellGrid = new CellGrid(2000, 2000);
    }

    /**
     * Active et désactive l'exécution du jeu
     * @param event
     */
    public void toggleExecution(ActionEvent event) {
        if (gameLoop.isRunning()) {
            gameLoop.stop();
            ((JButton) event.getSource()).setText("Start");
        } else {
            gameLoop.start();
            ((JButton) event.getSource()).setText("Stop");
        }
    }

    /**
     * Passe un cycle du jeu
     * @param ignoredEvent
     */
    public void step(ActionEvent ignoredEvent) {
        checkCells();
        render();
    }

    /**
     * Gère le reset de la grille du jeu en l'arrêtant au préalable et en refraîchissant l'écran après
     * @param ignoredEvent
     */
    public void reset(ActionEvent ignoredEvent, JButton btnToggle) {
        if (gameLoop.isRunning()) {
            gameLoop.stop();
            btnToggle.setText("Start");
        }

        cellGrid.reset();

        gameCanvas.render();

    }

    /**
     * Permet de redéfinir le taux d'exécution du jeu
     * @param changeEvent
     */
    public void setExecutionSpeed(ChangeEvent changeEvent, JLabel textLabel) {
        gameSpeed = ((JSlider) changeEvent.getSource()).getValue();
        gameLoop.setUpdateRate(gameSpeed);

        textLabel.setText(String.format("Speed (%d) : ",gameSpeed));
    }

    /**
     * Mets à jour tout ce qui a rapport au jeu
     */
    public void update() {
        checkCells();
    }

    /**
     * Appelle l'affichage du jeu
     */
    public void render() {
        gameCanvas.render();
    }

    /**
     * Vérifie toute les cellules du tableaux en regardant si elles doivent vivre ou mourir
     */
    public void checkCells() {
        CellGrid cellGridCopy = cellGrid.cloneCellGrid();
        byte[][] cellGridCopyByteCellMatrices = cellGridCopy.getCellMatrix();

        for (int y = 0; y < cellGridCopyByteCellMatrices.length; y++) {
            for (int x = 0; x < cellGridCopyByteCellMatrices[0].length; x++) {
                int numberCells = cellGridCopy.getSurroundingCells(x, y);

                if(cellGridCopyByteCellMatrices[y][x]==0) {continue;}

                if (cellGrid.isAlive(x, y)) {
                    if (numberCells != 2 && numberCells != 3) {
                        cellGrid.removeCell(x, y);
                    }
                }
                else {
                    if (numberCells == 3) {
                        cellGrid.putCell(x, y);
                    }
                }
            }
        }
    }

    /**
     * retourne la vitesse actuelle de mise à jour du jeu
     * @return
     */
    public int getGameSpeed() {
        return gameSpeed;
    }

    /**
     * Retourne la grille du jeu
     * @return
     */
    public CellGrid getCellGrid() {
        return cellGrid;
    }

    public GameLoop getGameLoop() {
        return gameLoop;
    }
}
