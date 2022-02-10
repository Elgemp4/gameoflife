package be.elgem.gameoflife.gui.prefab;

import javax.swing.*;

public class SelectionPanels extends JTabbedPane {
    private SelectionPanel systemPrefab;
    private SelectionPanel userPrefab;

    public SelectionPanels() {
        systemPrefab = new SelectionPanel(true);
        userPrefab = new SelectionPanel(false);

        addTab("Par défaut", systemPrefab);
        addTab("Utilisateur", userPrefab);
    }
}
