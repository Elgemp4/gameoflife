import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionListener;

public class OptionPanel extends JPanel {
    private Game game;

    public OptionPanel(Game game, int width, int height) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.game = game;

        initializePanel(width, height);

    }

    private void initializePanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        add(Box.createVerticalGlue());
        createButtonWithSpacing(this, "Start", game::toggleExecution);

        createButtonWithSpacing(this, "Step", game::step);

        createButtonWithSpacing(this, "Reset", game::reset);

        createSpeedPanel();



    }

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

    private void createButtonWithSpacing(JPanel panel, String name, ActionListener actionListener){
        JButton button = new JButton(name);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(actionListener);

        panel.add(button);
        panel.add(Box.createVerticalStrut(20));
    }
}
