package be.elgem.gameoflife.gamelogic;

import be.elgem.gameoflife.gui.GameDisplay;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionEvent;

public class Game {
    final private GameLoop gameLoop;

    final private GameDisplay gameDisplay;

    private CellGrid cellGrid;

    private int gameSpeed = 10;

    public Game(GameDisplay gameDisplay) {
        this.gameDisplay = gameDisplay;

        this.gameLoop = new GameLoop(gameSpeed, this);

        this.cellGrid = new CellGrid(4000, 4000);
    }

    /**
     * Active et désactive l'exécution du jeu
     * @param event
     */
    public void toggleExecution(ActionEvent event) {
        if (gameLoop.isRunning()) {
            stop();
        } else {
            start();
        }
    }

    public void stop() {
        if(gameLoop.isRunning()) {
            gameLoop.stop();
            gameDisplay.getWindow().getToolPanel().getStart().setText("Start");
        }
    }

    public void start() {
        if(!gameLoop.isRunning()) {
            gameLoop.start();
            gameDisplay.getWindow().getToolPanel().getStart().setText("Stop");
        }
    }

    /**
     * Passe un cycle du jeu
     * @param ignoredEvent
     */
    public void step(ActionEvent ignoredEvent) {
        cellGrid.checkCells();
        render();
    }

    /**
     * Gère le reset de la grille du jeu en l'arrêtant au préalable et en refraîchissant l'écran après
     * @param ignoredEvent
     */
    public void reset(ActionEvent ignoredEvent) {
        stop();

        cellGrid.reset();

        gameDisplay.repaint();

    }

    /**
     * Permet de redéfinir le taux d'exécution du jeu
     * @param changeEvent
     */
    public void setExecutionSpeed(ChangeEvent changeEvent) {
        gameSpeed = (int) ((JSpinner) changeEvent.getSource()).getValue();
        gameLoop.setUpdateRate(gameSpeed);
    }

    /**
     * Mets à jour tout ce qui a rapport au jeu
     */
    public void update() {
        cellGrid.checkCells();
    }

    /**
     * Appelle l'affichage du jeu
     */
    public void render() {
        gameDisplay.repaint();
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
