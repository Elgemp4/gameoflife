package be.elgem.gameoflife.gui;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private OptionPanel optionPanel;

    private GameCanvas gameCanvas;

    private int width, height;

    final public static int OPTION_PANE_SIZE = 200;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new MainWindow(800, 600);
    }

    public MainWindow(int width, int height){
        super("Conway's GameLogic.Game Of Life");

        initializeWindow(width, height);
    }

    /**
     * Initialise la fenêtre
     * @param width
     * @param height
     */
    private void initializeWindow(int width, int height) {
        this.width = width;
        this.height = height;

        gameCanvas = new GameCanvas(this, width - OPTION_PANE_SIZE, height);
        add(gameCanvas, BorderLayout.EAST);

        optionPanel = new OptionPanel(this, gameCanvas.getGame(), OPTION_PANE_SIZE, height);
        add(optionPanel);

        setSize(width, height);

        pack();
        setLocationRelativeTo(null);

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        gameCanvas.postWindowCreationLoading();
    }




}
