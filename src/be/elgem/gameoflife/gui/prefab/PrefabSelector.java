package be.elgem.gameoflife.gui.prefab;

import be.elgem.gameoflife.gui.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PrefabSelector extends JDialog {
    private static PrefabSelector prefabSelectorClass;

    private MainWindow mainWindow;

    private PreviewPanel previewPanel;
    private SelectionPanels selectionPanels;

    public PrefabSelector(MainWindow mainWindow) {
        super(mainWindow, "Prefabriqué");

        PrefabSelector.prefabSelectorClass = this;

        this.createRootPane();

        this.mainWindow = mainWindow;

        GridLayout layout = new GridLayout(1,2);

        this.setLayout(layout);
        this.setSize(new Dimension(600,450));

        previewPanel = new PreviewPanel();
        this.add(previewPanel);

        selectionPanels = new SelectionPanels();
        this.add(selectionPanels);


        this.setLocationRelativeTo(mainWindow);
        this.setResizable(false);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void setVisible(ActionEvent actionEvent) {
        this.setVisible(true);
    }

    public static PrefabSelector getPrefabSelectorClass() {
        return prefabSelectorClass;
    }
}
