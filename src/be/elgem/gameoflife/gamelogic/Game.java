package be.elgem.gameoflife.gamelogic;

import be.elgem.gameoflife.gui.GamePanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionEvent;

public class Game {
    final private GameLoop gameLoop;

    final private GamePanel gamePanel;

    private CellGrid cellGrid;

    private int gameSpeed = 10;

    public Game(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

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
        cellGrid.checkCells();
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

        gamePanel.repaint();

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
        gamePanel.repaint();
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
