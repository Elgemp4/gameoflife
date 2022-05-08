package be.elgem.gameoflife.gamelogic;

import be.elgem.gameoflife.gui.GameDisplay;
import be.elgem.gameoflife.gui.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Game {
    private static Game gameClass;

    final private GameLoop GAME_LOOP;

    final private GameDisplay GAME_DISPLAY;

    final private GameLogic GAME_LOGIC;

    public Game() {
        Game.gameClass = this;

        this.GAME_DISPLAY = GameDisplay.getGameDisplayClass();

        this.GAME_LOOP = new GameLoop(10);

        this.GAME_LOGIC = new GameLogic();
    }

    /**
     * Active et désactive l'exécution du jeu
     */
    public void toggleExecution() {
        if (GAME_LOOP.isRunning()) {
            stop();
        } else {
            start();
        }
    }

    public void stop() {
        if(GAME_LOOP.isRunning()) {
            GAME_LOOP.stop();
            MainWindow.getMainWindowClass().getToolPanel().getStart().setText("Start");
        }
    }

    public void start() {
        if(!GAME_LOOP.isRunning()) {
            GAME_LOOP.start();
            MainWindow.getMainWindowClass().getToolPanel().getStart().setText("Stop");
        }
    }

    /**
     * Passe un cycle du jeu
     */
    public void step(ActionEvent ignoredEvent) {
        GAME_LOGIC.checkCells();
        render();
    }

    /**
     * Gère le reset de la grille du jeu en l'arrêtant au préalable et en refraîchissant l'écran après
     */
    public void reset(ActionEvent ignoredEvent) {
        stop();

        GAME_LOGIC.reset();

        GAME_DISPLAY.repaint();

    }

    /**
     * Permet de redéfinir le taux d'exécution du jeu
     */
    public void changeGameSpeed(ActionEvent actionEvent) {
        JComboBox source = (JComboBox)(actionEvent.getSource());
        String entry = source.getSelectedItem().toString();

        switch (entry) {
            case "Très lent":
                GAME_LOOP.setUpdateRate(1);
                break;
            case "Lent":
                GAME_LOOP.setUpdateRate(10);
                break;
            case "Normal":
                GAME_LOOP.setUpdateRate(25);
                break;
            case "Rapide":
                GAME_LOOP.setUpdateRate(100);
                break;
            case "Super rapide":
                GAME_LOOP.setUpdateRate(1000);
                break;
        }
    }

    /**
     * Mets à jour tout ce qui a rapport au jeu
     */
    public void update() {
        GAME_LOGIC.checkCells();
    }

    /**
     * Appelle l'affichage du jeu
     */
    public void render() {
        GAME_DISPLAY.repaint();
    }

    /**
     * Retourne la grille du jeu
     */
    public GameLogic getGAME_LOGIC() {
        return GAME_LOGIC;
    }

    public GameLoop getGAME_LOOP() {
        return GAME_LOOP;
    }

    public static Game getGameClass() {
        return gameClass;
    }
}
