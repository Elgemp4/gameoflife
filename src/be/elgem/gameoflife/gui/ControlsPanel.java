package be.elgem.gameoflife.gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.HashMap;

import be.elgem.gameoflife.gamelogic.Game;
import be.elgem.gameoflife.render.EGridVisibility;
import be.elgem.gameoflife.externallibrary.WrapLayout;

/**
 * ToolPanel est une class JPanel qui contient tous les composants "paramètres" du jeu de la vie"
 */
public class ControlsPanel extends JPanel {
    final private Game game;
    final private MainWindow window;

    final private GameCanvas gameCanvas;

    private JButton start, step, reset;
    private JPanel speedSection;

    public ControlsPanel(MainWindow window, Game game) {
        super();

        this.window = window;

        this.game = game;

        this.gameCanvas = window.getGameCanvas();

        initializePanel();

    }

//    @Override
//    public Dimension getPreferredSize() {
//        return new Dimension(MainWindow.OPTION_PANE_SIZE, window.getHeight());
//    }

    /**
     * Créer un JPanel d'option
     */
    private void initializePanel() {
        setLayout(new WrapLayout(FlowLayout.CENTER, 25, 10));

        start = createButton("Start", game::toggleExecution);

        step = createButton( "Step", game::step);

        reset = createButton( "Reset", ignoredEvent -> game.reset(ignoredEvent, start));

        speedSection = createSpeedSection();
    }



    /**
     * Crée un JPanel contenant un label et un slider, servant à définir le taux de rafraichissement
     */
    private JPanel createSpeedSection() {
        JPanel speedSliderPanel = new JPanel(new FlowLayout());
        speedSliderPanel.setSize(new Dimension(getWidth(), 0));
        speedSliderPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel speedText = new JLabel("Speed : ");

        SpinnerNumberModel model = new SpinnerNumberModel(10,0,1000,10);

        JSpinner spinnerSpeed = new JSpinner(model);
        spinnerSpeed.setPreferredSize(new Dimension(50,20));
        spinnerSpeed.addChangeListener(changeEvent -> game.setExecutionSpeed(changeEvent));

        speedSliderPanel.add(speedText);
        speedSliderPanel.add(spinnerSpeed);

        this.add(speedSliderPanel);

        return speedSliderPanel;
    }

    /**
     * Crée un bouton avec le nom et l'action listener fournit
     * @param name
     * @param actionListener
     */
    private JButton createButton(String name, ActionListener actionListener){
        JButton button = new JButton(name);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(actionListener);

        this.add(button);

        return button;
    }
}
