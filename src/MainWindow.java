import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;

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
        super("Conway's Game Of Life");

        initializeWindow(width, height);
    }

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
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        gameCanvas.render();
    }




}
