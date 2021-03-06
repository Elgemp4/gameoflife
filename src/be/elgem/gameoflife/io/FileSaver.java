package be.elgem.gameoflife.io;

import be.elgem.gameoflife.gamelogic.GameLogic;
import be.elgem.gameoflife.gui.GameDisplay;
import be.elgem.gameoflife.render.Camera;
import be.elgem.gameoflife.render.Index;
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

    public FileSaver() {
        super();
        this.gameLogic = GameLogic.getGameLogicClass();
        this.camera = Camera.getCameraClass();
        this.gameDisplay = GameDisplay.getGameDisplayClass();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Game Of Life Grid", "gold");

        setFileFilter(filter);
        setDialogTitle("Choisissez la zone de jeu :");
    }

    public void saveFile(ActionEvent ignoredEvent) {
        gameDisplay.getGame().stop();

        if(showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            HashMap<Index, Byte> cells = gameLogic.getActiveCellsMap();
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

    private void writeCells(PrintWriter writer, HashMap<Index, Byte> cellMap) {
        String cellList = "";

        boolean firstIteration = true; //Pour ??viter d'avoir un : seul au d??but des coordon??es

        for (Index cell : cellMap.keySet()) {
            Byte cellData = cellMap.get(cell);
            String cellString = String.join("_", Integer.toString(cell.getXIndex()),
                    Integer.toString(cell.getYIndex()), Byte.toString(cellData));
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
