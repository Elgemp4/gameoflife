package be.elgem.gameoflife.gui.prefab;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class PrefabButton extends JButton{
    private File file;

    private JLabel previewImage;
    private JLabel prefabName;

    private boolean isDeletable;

    public PrefabButton(File file, boolean isSystem) {
        super();

        this.file = file;

        this.isDeletable = !isSystem;
        this.parseData();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //Flow panel pour mettre les éléments l'un à côté de l'autre
        JPanel flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        flowPanel.setOpaque(false);

        flowPanel.add(previewImage);
        flowPanel.add(prefabName);

        //Centrage vertical
        this.add(Box.createVerticalGlue());
        this.add(flowPanel);
        this.add(Box.createVerticalGlue());

        this.addActionListener(PreviewPanel.getPreviewPanel()::changeSelectedPrefab);

        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
    }

    public void parseData() {
        this.previewImage = new JLabel(new ImageIcon(new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB)));

        this.prefabName = new JLabel(file.getName().replace(".prefab", ""));
    }

    public JLabel getPreviewImage() {
        return previewImage;
    }

    public JLabel getPrefabName() {
        return prefabName;
    }

    public boolean isDeletable() {
        return isDeletable;
    }
}
