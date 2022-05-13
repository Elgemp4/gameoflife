package be.elgem.gameoflife.gui.prefab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

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

        this.previewImage = new JLabel();
        this.prefabName = new JLabel();

        this.buttonPanel = new JPanel();

        this.loadButton = new JButton("Charger");
        this.deleteButton = new JButton("Supprimer");

        buttonPanel.add(loadButton);
        buttonPanel.add(deleteButton);
        buttonPanel.setVisible(false);

        FlowLayout layout = new FlowLayout();
        layout.setAlignment(FlowLayout.CENTER);

        buttonPanel.setLayout(layout);

        add(previewImage);
        add(prefabName);
        add(buttonPanel);
    }


    public void changeSelectedPrefab(ActionEvent actionEvent) {
        PrefabButton button = (PrefabButton)actionEvent.getSource();

        this.prefabName.setText(button.getPrefabName().getText());
        this.previewImage.setIcon(button.getPreviewImage().getIcon());


        deleteButton.setEnabled(button.isDeletable());

        buttonPanel.setVisible(true);

        repaint();
    }

    public static PreviewPanel getPreviewPanel() {
        return previewPanel;
    }
}
