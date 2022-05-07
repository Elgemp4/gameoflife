package be.elgem.gameoflife.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import be.elgem.gameoflife.gamelogic.Game;
import be.elgem.gameoflife.externallibrary.WrapLayout;
import be.elgem.gameoflife.gui.prefab.PrefabSelector;

/**
 * ToolPanel est une class JPanel qui contient tous les composants "paramètres" du jeu de la vie"
 */
public class ControlsPanel extends JPanel {
    final private Game game;
    final private PrefabSelector prefabSelector;

    final private MainWindow window;

    final private GameDisplay gameDisplay;

    private JButton start, step, reset, prefab;
    private JPanel speedSection;

    public ControlsPanel() {
        super();

        this.window = MainWindow.getMainWindowClass();

        this.game = Game.getGameClass();

        this.gameDisplay = window.getGameCanvas();

        this.prefabSelector = PrefabSelector.getPrefabSelectorClass();

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

        reset = createButton( "Reset", game::reset);

        prefab = createButton("Prefab", prefabSelector::setVisible);

        speedSection = createSpeedSection();
    }



    /**
     * Crée un JPanel contenant un label et un slider, servant à définir le taux de rafraichissement
     */
    private JPanel createSpeedSection() {
        JPanel speedPanel = new JPanel(new FlowLayout());
        speedPanel.setSize(new Dimension(getWidth(), 0));
        speedPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel speedText = new JLabel("Speed : ");

        JComboBox<String> choice = new JComboBox<>();
        choice.addItem("Très lent");
        choice.addItem("Lent");
        choice.addItem("Normal");
        choice.addItem("Rapide");
        choice.addItem("Super rapide");
        choice.addActionListener(game::changeGameSpeed);
        choice.setSelectedItem("Lent");

        speedPanel.add(speedText);
        speedPanel.add(choice);

        this.add(speedPanel);

        return speedPanel;
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

    public JButton getStart() {
        return start;
    }

}
