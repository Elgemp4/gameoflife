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
import java.util.StringJoiner;

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

                writeCameraInfo(writer);

                writeCells(writer, gameLogic.getActiveCellsMap());
                writeCells(writer, gameLogic.getAliveCellsMap());

                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeCameraInfo(PrintWriter writer) {
        String coordinates = String.join(":", Integer.toString(camera.getX()),
                Integer.toString(camera.getY()), Integer.toString(camera.getCellSize()));

        writer.println(coordinates);
    }

    private void writeCells(PrintWriter writer, HashMap<Pair<Integer, Integer>, Byte> cellMap) {
        String cellList = "";

        boolean firstIteration = true; //Pour éviter d'avoir un : seul au début des coordonées

        for (Pair<Integer, Integer> cell : cellMap.keySet()) {
            Byte cellData = cellMap.get(cell);
            String cellString = String.join("_", Integer.toString(cell.getKey()),
                    Integer.toString(cell.getValue()), Byte.toString(cellData));
            if(firstIteration){
                cellList = cellString;
                firstIteration = false;
            }
            else{
                cellList = String.join(":", cellList, cellString);
            }

        }

        writer.println(cellList);
    }
}
