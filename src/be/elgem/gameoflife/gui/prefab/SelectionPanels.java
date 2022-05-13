package be.elgem.gameoflife.gui.prefab;

import javax.swing.*;

public class SelectionPanels extends JTabbedPane {
    private SelectionSection systemPrefab;
    private SelectionSection userPrefab;

    public SelectionPanels() {
        systemPrefab = new SelectionSection(true);
        userPrefab = new SelectionSection(false);

        addTab("Par défaut", systemPrefab);
        addTab("Utilisateur", userPrefab);
    }
}
