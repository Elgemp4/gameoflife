package be.elgem.gameoflife.gui.prefab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

public class PreviewPanel extends JPanel {
    private static PreviewPanel previewPanel;

    private JLabel previewImage;
    private JLabel prefabName;

    private JPanel buttonPanel;

    private JButton loadButton;
    private JButton deleteButton;

    public PreviewPanel() {
        super();

        PreviewPanel.previewPanel = this;

        System.out.println("ici");

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.previewImage = new JLabel("");
        this.previewImage.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.prefabName = new JLabel("");
        this.prefabName.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.prefabName.setFont(new Font("Arial", Font.PLAIN, 20));

        this.buttonPanel = new JPanel();

        Font buttonFont = new Font("Arial", Font.PLAIN, 20);
        this.loadButton = new JButton("Charger");
        this.loadButton.setFont(buttonFont);

        this.deleteButton = new JButton("Supprimer");
        this.deleteButton.setFont(buttonFont);

        buttonPanel.add(loadButton);
        buttonPanel.add(deleteButton);
        buttonPanel.setVisible(false);

        FlowLayout layout = new FlowLayout();
        layout.setAlignment(FlowLayout.CENTER);

        buttonPanel.setLayout(layout);

        add(Box.createVerticalGlue());
        add(previewImage);
        add(Box.createVerticalGlue());
        add(prefabName);
        add(Box.createVerticalGlue());
//        add(Box.createRigidArea(new Dimension(getWidth(), 100)));
        add(buttonPanel);
        add(Box.createVerticalGlue());
    }


    public void changeSelectedPrefab(ActionEvent actionEvent) {
        PrefabButton button = (PrefabButton)actionEvent.getSource();

        this.prefabName.setText(button.getPrefabName().getText());
        this.previewImage.setIcon(new ImageIcon(new BufferedImage(150, 150, BufferedImage.TYPE_INT_RGB)));
        this.previewImage.setIconTextGap(0);


        deleteButton.setEnabled(button.isDeletable());

        buttonPanel.setVisible(true);

        repaint();
    }

    public static PreviewPanel getPreviewPanel() {
        return previewPanel;
    }
}
