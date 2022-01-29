package be.elgem.gameoflife.io;

import be.elgem.gameoflife.gamelogic.CellGrid;
import be.elgem.gameoflife.gui.GameDisplay;
import be.elgem.gameoflife.render.Camera;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileLoader extends JFileChooser {
    private GameDisplay gameDisplay;
    private CellGrid cellGrid;
    private Camera camera;

    public FileLoader(GameDisplay gameDisplay) {
        super();

        this.gameDisplay = gameDisplay;

        this.cellGrid = gameDisplay.getGame().getCellGrid();

        this.camera = gameDisplay.getCamera();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Game Of Life Grid", "gold");

        setFileFilter(filter);
        setDialogTitle("Choisissez la zone de jeu :");
    }

    public void openFile(ActionEvent event) {
        byte[][] openedCellGrid;

        gameDisplay.getGame().stop();

        if(showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File openedFile = getSelectedFile();
            try {
                Scanner sc = new Scanner(openedFile);

                openedCellGrid = parseCellGridSize(sc);

                parseCameraSettings(sc);

                parseCells(sc, openedCellGrid);

                cellGrid.setByteCellGrid(openedCellGrid);
                cellGrid.recreateChunks();

                gameDisplay.repaint();
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("Une erreur a eu lieu lors du chargement");
            }
        }
    }

    private byte[][] parseCellGridSize(Scanner sc) {
        byte[][] returnGrid;

        String[] gridSize = sc.nextLine().split(":");
        returnGrid = new byte[Integer.parseInt(gridSize[0])][Integer.parseInt(gridSize[1])];
        
        return returnGrid;
    }

    private void parseCameraSettings(Scanner sc) {
        String[] cameraSettings = sc.nextLine().split(":");

        this.camera.setX(Integer.parseInt(cameraSettings[0]));
        this.camera.setY(Integer.parseInt(cameraSettings[1]));
        this.camera.setCellSize(Double.parseDouble(cameraSettings[2]));
        this.camera.actualizeDisplayedCells();

    }

    private void parseCells(Scanner sc, byte[][] openedCellGrid) {
        for (int y = 0; y < openedCellGrid.length; y++) {
            String[] currentLine = sc.nextLine().split(",");
            for (int x = 0; x < openedCellGrid[0].length; x++) {
                openedCellGrid[y][x] = Byte.parseByte(currentLine[x]);
            }
        }
    }
}
