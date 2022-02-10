package be.elgem.gameoflife.gui.prefab;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class PrefabButton extends JButton{
    JLabel preview;
    JLabel name;

    public PrefabButton(File file) {
        super();

        this.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));

        parseData(file);

        this.add(preview);

        this.add(name);
    }

    public void parseData(File file) {
        this.preview = new JLabel(new ImageIcon(new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB)));

        this.name = new JLabel("testfghreoifoij");
    }
}
