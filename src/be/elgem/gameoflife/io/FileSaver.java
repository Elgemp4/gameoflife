package be.elgem.gameoflife.io;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class FileSaver extends JFileChooser {
    public FileSaver() {
        super();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Game Of Life Grid", "gold");

        setFileFilter(filter);
        setDialogTitle("Choisissez la zone de jeu :");
    }

    public void saveFile() {
        if(showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = getSelectedFile();
        }
    }
}
