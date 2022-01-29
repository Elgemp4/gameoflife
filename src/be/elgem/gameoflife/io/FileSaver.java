package be.elgem.gameoflife.io;

import be.elgem.gameoflife.gamelogic.CellGrid;
import be.elgem.gameoflife.gui.GameDisplay;
import be.elgem.gameoflife.render.Camera;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class FileSaver extends JFileChooser {
    private CellGrid cellGrid;
    private Camera camera;
    private GameDisplay gameDisplay;

    public FileSaver(GameDisplay gameDisplay) {
        super();
        this.cellGrid = gameDisplay.getGame().getCellGrid();
        this.camera = gameDisplay.getCamera();
        this.gameDisplay = gameDisplay;

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Game Of Life Grid", "gold");

        setFileFilter(filter);
        setDialogTitle("Choisissez la zone de jeu :");
    }

    public void saveFile(ActionEvent ignoredEvent) {
        gameDisplay.getGame().stop();

        if(showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            byte[][] byteGrid = cellGrid.getCellMatrix();
            File file = new File(getSelectedFile().getPath() + ".gold");

            try {
                PrintWriter writer = new PrintWriter(file);
                writer.println(byteGrid.length+":"+byteGrid[0].length);
                writer.println(camera.getX()+":"+camera.getY()+":"+camera.getCellSize());

                for (int y = 0; y < byteGrid.length; y++) {
                    for (int x = 0; x < byteGrid[0].length; x++) {
                        writer.print(byteGrid[y][x]);
                        if(x != byteGrid[0].length)
                            writer.print(",");
                    }
                    writer.println();
                }
                writer.close();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
