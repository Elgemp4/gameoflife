package be.elgem.gameoflife.gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.HashMap;

import be.elgem.gameoflife.gamelogic.Game;
import be.elgem.gameoflife.render.EGridVisibility;

/**
 * ToolPanel est une class JPanel qui contient tous les composants "paramètres" du jeu de la vie"
 */
public class ToolPanel extends JPanel {
    final private Game game;
    final private MainWindow window;

    final private GameCanvas gameCanvas;

    public ToolPanel(MainWindow window, Game game, int width, int height) {
        super();
        this.setLayout(new SpringLayout());

        this.window = window;

        this.game = game;

        this.gameCanvas = window.getGameCanvas();

        initializePanel(width, height);

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(MainWindow.OPTION_PANE_SIZE, window.getHeight());
    }

    /**
     * Créer un JPanel d'option de la taille spécifiée
     * @param width
     * @param height
     */
    private void initializePanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalGlue());

        JPanel gamePanel = createGameCategory();
        this.add(gamePanel);

        JPanel optionPanel = createOptionCategory();

        this.add(optionPanel);

        add(Box.createVerticalGlue());
    }



    private JPanel createGameCategory() {
        JPanel gamePanel = new JPanel();

        TitledBorder gamePanelBorder = BorderFactory.createTitledBorder("Game Controls");
        gamePanelBorder.setTitleJustification(TitledBorder.CENTER);

        gamePanel.setBorder(gamePanelBorder);

        JButton start = createButton(gamePanel, "Start", game::toggleExecution);

        JButton step = createButton(gamePanel, "Step", game::step);

        JButton reset = createButton(gamePanel, "Reset", ignoredEvent -> game.reset(ignoredEvent, start));

        createSpeedSection(gamePanel);

        return gamePanel;
    }

    private JPanel createOptionCategory() {
        JPanel optionPanel = new JPanel();

        TitledBorder gamePanelBorder = BorderFactory.createTitledBorder("Options");
        gamePanelBorder.setTitleJustification(TitledBorder.CENTER);

        optionPanel.setBorder(gamePanelBorder);

        createGridVisibilitySection(optionPanel);

        return optionPanel;
    }

    private void createGridVisibilitySection(JPanel parentPanel) {
        JPanel gridVisibilityPanel = new JPanel();

        JLabel visibilityLabel = new JLabel("Grid Visibility : ");
        gridVisibilityPanel.add(visibilityLabel);

        JComboBox<String> gridVisibilityCB = new JComboBox<String>();

        gridVisibilityCB.addItem("Always shown");
        gridVisibilityCB.addItem("Hybrid");
        gridVisibilityCB.addItem("Always hide");
        gridVisibilityCB.setSelectedItem("Hybrid");

        gridVisibilityCB.addItemListener(this::changeGridVisibility);

        gridVisibilityPanel.add(gridVisibilityCB);

        parentPanel.add(gridVisibilityPanel);
    }

    private void changeGridVisibility(ItemEvent event){
        HashMap<String, EGridVisibility> correspondingMap = new HashMap<>();
        correspondingMap.put("Always shown", EGridVisibility.ALWAYS_SHOWN);
        correspondingMap.put("Hybrid", EGridVisibility.HYBRID);
        correspondingMap.put("Always hide", EGridVisibility.ALWAYS_HIDE);

        gameCanvas.getRenderer().setGridVisibility(correspondingMap.get((String)event.getItem()));
        gameCanvas.render();
    }

    /**
     * Crée un JPanel contenant un label et un slider, servant à définir le taux de rafraichissement
     */
    private void createSpeedSection(JPanel panel) {
        JPanel speedSliderPanel = new JPanel(new FlowLayout());
        speedSliderPanel.setSize(new Dimension(getWidth(), 0));
        speedSliderPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel speedText = new JLabel("Speed (1)");

        JSlider sldSpeed = new JSlider(1,10, game.getGameSpeed());
        sldSpeed.setPreferredSize(new Dimension(100,20));
        sldSpeed.addChangeListener(changeEvent -> game.setExecutionSpeed(changeEvent, speedText));

        speedSliderPanel.add(speedText);
        speedSliderPanel.add(sldSpeed);

        panel.add(speedSliderPanel);
    }

    /**
     * Crée un bouton avec le nom et l'action listener fournit
     * @param panel
     * @param name
     * @param actionListener
     */
    private JButton createButton(JPanel panel, String name, ActionListener actionListener){
        JButton button = new JButton(name);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(actionListener);

        panel.add(button);

        return button;
    }
}
