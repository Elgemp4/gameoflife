package be.elgem.gameoflife.io;

import be.elgem.gameoflife.gamelogic.CellGrid;
import jdk.nashorn.internal.ir.debug.JSONWriter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;

public class FileLoader extends JFileChooser {
    private CellGrid cellGrid;

    public FileLoader(CellGrid cellGrid) {
        super();

        this.cellGrid = cellGrid;

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Game Of Life Grid", "gold");

        setFileFilter(filter);
        setDialogTitle("Choisissez la zone de jeu :");
    }

    public void openFile(ActionEvent event) {
        if(showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File openedFile = getSelectedFile();
        }
    }
}
