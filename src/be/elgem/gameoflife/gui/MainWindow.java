package be.elgem.gameoflife.gui;

import javax.swing.*;
import java.awt.*;
import be.elgem.gameoflife.gui.GameCanvas;
import be.elgem.gameoflife.gamelogic.Game;

public class MainWindow extends JFrame {
    private OptionPanel optionPanel;

    private GameCanvas gameCanvas;

    private int width, height;


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


        gameCanvas = new GameCanvas(width * 6/8, height);
        add(gameCanvas, BorderLayout.EAST);

        optionPanel = new OptionPanel(gameCanvas.getGame(), width * 2/8, height);
        add(optionPanel);

        pack();
        setLocationRelativeTo(null);

        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        gameCanvas.postWindowCreationLoading();
    }




}
