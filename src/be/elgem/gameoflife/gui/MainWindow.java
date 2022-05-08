package be.elgem.gameoflife.gui;

import be.elgem.gameoflife.gui.prefab.PrefabSelector;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainWindow extends JFrame {
    static private MainWindow mainWindowClass;

    private ControlsPanel toolPanel;

    private GameDisplay gameDisplay;

    private PrefabSelector prefabSelector;

    private MenuBar menuBar;

    private int width, height;

    final public static int OPTION_PANE_SIZE = 230;

    private Image icon;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.setProperty("sun.awt.noerasebackground", "true"); //Empêche l'effacement de ce qui est dessiné
        System.setProperty("sun.java2d.opengl", "true"); //Activation de l'accéleration graphique

        new MainWindow(800, 600);
    }

    public MainWindow(int width, int height){
        super("Conway's GameLogic.Game Of Life");

        MainWindow.mainWindowClass = this;

        try {
            this.icon = ImageIO.read(new File("src/be/elgem/gameoflife/resources/images/icon.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

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

        gameDisplay = new GameDisplay(width, height);
        add(gameDisplay, BorderLayout.EAST);

        prefabSelector = new PrefabSelector(this);

        toolPanel = new ControlsPanel();
        add(toolPanel, BorderLayout.SOUTH);



        setJMenuBar(new MenuBar());

        setIconImage(this.icon);
        setSize(width, height);
        setMinimumSize(new Dimension(520,250));

        pack();
        setLocationRelativeTo(null);

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        gameDisplay.postWindowCreationLoading();
    }

    public ControlsPanel getToolPanel() {
        return toolPanel;
    }

    public GameDisplay getGameCanvas() {
        return gameDisplay;
    }

    public PrefabSelector getPrefabSelector() {
        return prefabSelector;
    }

    public static MainWindow getMainWindowClass() {
        return mainWindowClass;
    }
}
