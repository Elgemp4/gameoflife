package be.elgem.gameoflife.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import be.elgem.gameoflife.gamelogic.Game;

/**
 * OptionPanel est une class JPanel qui contient tous les composants "paramètres" du jeu de la vie"
 */
public class OptionPanel extends JPanel {
    final private Game game;
    final private MainWindow window;

    public OptionPanel(MainWindow window, Game game, int width, int height) {
        super();
        setLayout(new SpringLayout());

        this.window = window;

        this.game = game;

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
        JButton start = createButtonWithSpacing(this, "Start", game::toggleExecution);

        JButton step = createButtonWithSpacing(this, "Step", game::step);

        JButton reset = createButtonWithSpacing(this, "Reset", ignoredEvent -> game.reset(ignoredEvent, start));

        createSpeedPanel(this);
    }

    /**
     * Crée un JPanel contenant un label et un slider, servant à définir le taux de rafraichissement
     */
    private void createSpeedPanel(JPanel panel) {
        //Speed slider
        JPanel speedSliderPanel = new JPanel(new FlowLayout());
        speedSliderPanel.setSize(new Dimension(getWidth(), 0));
        speedSliderPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JSlider sldSpeed = new JSlider(1,10, game.getGameSpeed());
        sldSpeed.setPreferredSize(new Dimension(100,20));
        sldSpeed.addChangeListener(game::setExecutionSpeed);
        JLabel speedText = new JLabel(String.format("Speed (%d) : ",sldSpeed.getValue()));

        speedSliderPanel.add(speedText);
        speedSliderPanel.add(sldSpeed);

        panel.add(speedSliderPanel);
        panel.add(Box.createVerticalStrut(20));
    }


    private void createButton(JPanel panel, String name, ActionListener actionListener){
        JButton button = new JButton(name);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(actionListener);

        panel.add(button);
    }

    /**
     * Crée un bouton avec le nom et l'action listener fournit
     * @param panel
     * @param name
     * @param actionListener
     */
    private JButton createButtonWithSpacing(JPanel panel, String name, ActionListener actionListener){
        JButton button = new JButton(name);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(actionListener);

        panel.add(button);
        //panel.add(Box.createVerticalStrut(20));

        return button;
    }
}
