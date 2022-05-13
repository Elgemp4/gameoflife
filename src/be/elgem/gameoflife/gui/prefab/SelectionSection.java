package be.elgem.gameoflife.gui.prefab;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

public class SelectionSection extends JScrollPane {
    private JPanel selectionPanel;

    private ArrayList<PrefabButton> prefabButtonArrayList;

    public SelectionSection(boolean isSystem) {
        super();

        selectionPanel = new JPanel();
        selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.Y_AXIS));

        prefabButtonArrayList = new ArrayList<>();

        File[] prefabFiles = getPrefabs(true);
        for (int i = 0; i < prefabFiles.length; i++) {
            PrefabButton prefabButton = new PrefabButton(prefabFiles[i], isSystem);

            prefabButtonArrayList.add(prefabButton);

            selectionPanel.add(prefabButton);

            this.setViewportView(selectionPanel);
        }
    }

    private File[] getPrefabs(boolean isSystem) {
        File prefabFolder;
        if(isSystem)
            prefabFolder = new File("src/be/elgem/gameoflife/resources/prefabs");
        else
            prefabFolder = new File("src/be/elgem/gameoflife/resources/prefabs");
        FileFilter filter = pathname -> pathname.getName().endsWith(".prefab");

        return prefabFolder.listFiles(filter);
    }
}
