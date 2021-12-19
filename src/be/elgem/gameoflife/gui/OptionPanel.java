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

    public OptionPanel(Game game, int width, int height) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.game = game;

        initializePanel(width, height);

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

        createSpeedPanel();
    }

    /**
     * Crée un JPanel contenant un label et un slider, servant à définir le taux de rafraichissement
     */
    private void createSpeedPanel() {
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

        add(speedSliderPanel);
        add(Box.createVerticalStrut(20));
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
        panel.add(Box.createVerticalStrut(20));

        return button;
    }
}
