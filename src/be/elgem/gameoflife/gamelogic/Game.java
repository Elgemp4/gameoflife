package be.elgem.gameoflife.gamelogic;

import be.elgem.gameoflife.gui.GameDisplay;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionEvent;

public class Game {
    final private GameLoop gameLoop;

    final private GameDisplay gameDisplay;

    private GameLogic cellGrid;

    private int gameSpeed = 10;

    public Game(GameDisplay gameDisplay) {
        this.gameDisplay = gameDisplay;

        this.gameLoop = new GameLoop(gameSpeed, this);

        this.cellGrid = new GameLogic();
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
    public void changeGameSpeed(ChangeEvent changeEvent) {
        gameSpeed = (int) ((JSpinner) changeEvent.getSource()).getValue();
        gameLoop.setUpdateRate(gameSpeed);
    }

    public void changeGameSpeed(ActionEvent actionEvent) {
        JComboBox source = (JComboBox)(actionEvent.getSource());
        String entry = source.getSelectedItem().toString();

        if(entry.equals("Très lent")) {
            gameLoop.setUpdateRate(1);
        }
        else if(entry.equals("Lent")) {
            gameLoop.setUpdateRate(10);
        }
        else if(entry.equals("Normal")) {
            gameLoop.setUpdateRate(25);
        }
        else if(entry.equals("Rapide")) {
            gameLoop.setUpdateRate(100);
        }
        else if(entry.equals("Super rapide")) {
            gameLoop.setUpdateRate(1000);
        }
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
    public GameLogic getGameLogic() {
        return cellGrid;
    }

    public GameLoop getGameLoop() {
        return gameLoop;
    }

    public GameDisplay getGameDisplay() {
        return gameDisplay;
    }
}
