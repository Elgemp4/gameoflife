package be.elgem.gameoflife.io;

import be.elgem.gameoflife.gamelogic.GameLogic;
import be.elgem.gameoflife.gui.GameDisplay;
import be.elgem.gameoflife.render.Camera;
import javafx.util.Pair;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class FileLoader extends JFileChooser {
    private GameDisplay gameDisplay;
    private GameLogic cellGrid;
    private Camera camera;

    public FileLoader(GameDisplay gameDisplay) {
        super();

        this.gameDisplay = gameDisplay;

        this.cellGrid = gameDisplay.getGame().getGameLogic();

        this.camera = gameDisplay.getCamera();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Game Of Life Grid", "gold");

        setFileFilter(filter);
        setDialogTitle("Choisissez la zone de jeu :");
    }

    public void openFile(ActionEvent event) {


        gameDisplay.getGame().stop();

        if(showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File openedFile = getSelectedFile();
            try {
                Scanner sc = new Scanner(openedFile);

                parseCameraSettings(sc);

                parseCells(sc);


                gameDisplay.repaint();
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("Une erreur a eu lieu lors du chargement");
            }
        }
    }


    private void parseCameraSettings(Scanner sc) {
        String[] cameraSettings = sc.nextLine().split(":");

        this.camera.setX(Integer.parseInt(cameraSettings[0]));
        this.camera.setY(Integer.parseInt(cameraSettings[1]));
        this.camera.setCellSize(Integer.parseInt(cameraSettings[2]));
        this.camera.actualizeDisplayedCells();

    }

    private void parseCells(Scanner sc) {
        HashMap<Pair<Integer, Integer>, Byte> openedCellMap = new HashMap<>();

        for (String cellData : sc.nextLine().split(":")) {
            if(cellData!="") {
                String[] cellInfo = sc.nextLine().split("-");
                openedCellMap.put(new Pair<>(Integer.parseInt(cellInfo[1]), Integer.parseInt(cellInfo[2])), Byte.parseByte(cellInfo[3]));
            }
        }

//        gameDisplay.getGame().getGameLogic().set
    }
}
