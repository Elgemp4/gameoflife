package be.elgem.gameoflife.gui;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private ControlsPanel toolPanel;

    private GameCanvas gameCanvas;

    private MenuBar menuBar;

    private int width, height;

    final public static int OPTION_PANE_SIZE = 230;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.setProperty("sun.awt.noerasebackground", "true");

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



        gameCanvas = new GameCanvas(this, width, height);
        add(gameCanvas, BorderLayout.EAST);

        toolPanel = new ControlsPanel(this, gameCanvas.getGame());
        add(toolPanel, BorderLayout.SOUTH);

        menuBar = new MenuBar(gameCanvas);
        this.setJMenuBar(menuBar);

        setSize(width, height);
        setMinimumSize(new Dimension(350,250));

        pack();
        setLocationRelativeTo(null);

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        gameCanvas.postWindowCreationLoading();
    }

    public ControlsPanel getToolPanel() {
        return toolPanel;
    }

    public GameCanvas getGameCanvas() {
        return gameCanvas;
    }
}
