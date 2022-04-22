package be.elgem.gameoflife.io;

import be.elgem.gameoflife.gamelogic.GameLogic;
import be.elgem.gameoflife.gui.GameDisplay;
import be.elgem.gameoflife.render.Camera;
import javafx.util.Pair;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class FileSaver extends JFileChooser {
    private GameLogic gameLogic;
    private Camera camera;
    private GameDisplay gameDisplay;

    public FileSaver(GameDisplay gameDisplay) {
        super();
        this.gameLogic = gameDisplay.getGame().getGameLogic();
        this.camera = gameDisplay.getCamera();
        this.gameDisplay = gameDisplay;

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Game Of Life Grid", "gold");

        setFileFilter(filter);
        setDialogTitle("Choisissez la zone de jeu :");
    }

    public void saveFile(ActionEvent ignoredEvent) {
        gameDisplay.getGame().stop();

        if(showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            HashMap<Pair<Integer, Integer>, Byte> cells = gameLogic.getActiveCellsMap();
            File file = new File(getSelectedFile().getPath() + ".gold");

            try {
                PrintWriter writer = new PrintWriter(file);
                writer.println(camera.getX()+":"+camera.getY()+":"+camera.getCellSize());
                writer.println();

                for (Pair<Integer, Integer> coordinates : cells.keySet()) {
                    Byte cellData = cells.get(coordinates);
                    writer.print("-"+coordinates.getKey() + "-" + coordinates.getValue() + "-" + cellData + "-:");
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
