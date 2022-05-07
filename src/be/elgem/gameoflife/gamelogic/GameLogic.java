package be.elgem.gameoflife.gamelogic;

import be.elgem.gameoflife.render.Index;
import javafx.util.Pair;

import java.lang.management.MemoryUsage;
import java.util.HashMap;

/**
 * La class CellGrid est une classe représentant un grille de cellule d'un jeu de la vie, chaque cellule est un byte
 * dont les trois premiers bits sont inutilisés, les quatres suivants encodent le nombre de cellules adjacentes et
 * le dernier encode si la cellule est vivante.
 */
public class GameLogic {
    private static GameLogic GameLogicClass;

    private HashMap<Index, Byte> activeCellsMap;
    private HashMap<Index, Byte> aliveCellsMap;


    public GameLogic() {
        activeCellsMap = new HashMap<>();
        aliveCellsMap = new HashMap<>();
    }

    /**
     * Mets une cellule en x y
     */
    public void putCell(Index index) {
        if (!isAlive(index)) {
            if(activeCellsMap.containsKey(index)){
                aliveCellsMap.put(index, activeCellsMap.get(index));
            }
            else{
                aliveCellsMap.put(index, (byte) 0);
                activeCellsMap.put(index, (byte) 0);
            }

            informAdditionSurroundingCells(index);

//            debugAliveCells();
        }
    }

    /**
     * Enlève un cellule en x y
     */
    public void removeCell(Index index) {
        if(isAlive(index)) {
            aliveCellsMap.remove(index);

            informRemovalSurroundingCells(index);
        }
    }

    private void informAdditionSurroundingCells(Index index) {
        for (int yOffset = -1; yOffset <= 1; yOffset++) {
            for (int xOffset = -1; xOffset <= 1; xOffset++) {
                if (yOffset != 0 || xOffset != 0) {
                    Index searchIndex = new Index(index.getXIndex() + xOffset, index.getYIndex() + yOffset);

                    if (activeCellsMap.containsKey(searchIndex)) {
                        activeCellsMap.replace(searchIndex, (byte) (activeCellsMap.get(searchIndex) + 1));
                    } else {
                        activeCellsMap.put(searchIndex, (byte) 1);
                    }
                }
            }
        }
    }

    public void informRemovalSurroundingCells(Index index) {
        for (int yOffset = -1; yOffset <= 1; yOffset++) {
            for (int xOffset = -1; xOffset <= 1; xOffset++) {
                if (yOffset != 0 || xOffset != 0) {
                    Index searchindex = new Index(index.getXIndex() + xOffset, index.getYIndex() + yOffset);
                    if(activeCellsMap.containsKey(searchindex)) {
                        if (activeCellsMap.get(searchindex) > 0) {
                            activeCellsMap.replace(searchindex, (byte) (activeCellsMap.get(searchindex) - 1));
                        }
                        if(activeCellsMap.get(searchindex) == 0 && !aliveCellsMap.containsKey(searchindex)){
                            activeCellsMap.remove(searchindex);
                        }
                    }
                }
            }
        }
    }

    /**
     * Reset la grille de boolean en une nouvelle grille
     */
    public void reset() {
        aliveCellsMap = new HashMap<>();
        activeCellsMap = new HashMap<>();
    }

    public boolean isAlive(Index index) {
        return aliveCellsMap.containsKey(index);
    }

    /**
     * Vérifie toute les cellules du tableaux en regardant si elles doivent vivre ou mourir
     */
    public void checkCells() {
        HashMap<Index, Byte> activeCellsMapCopy = (HashMap<Index, Byte>) activeCellsMap.clone();
        HashMap<Index, Byte> aliveCellsMapCopy = (HashMap<Index, Byte>) aliveCellsMap.clone();

        for (Index cellIndex : activeCellsMapCopy.keySet()) {
            Byte surroundCells = activeCellsMapCopy.get(cellIndex);

            if (aliveCellsMapCopy.containsKey(cellIndex)) {
                if (surroundCells != 2 && surroundCells != 3) {
                    this.removeCell(cellIndex);
                }
            }
            else {
                if (surroundCells == 3) {
                    this.putCell(cellIndex);
                }
            }

        }
    }

    /**
     * Renvoie vrai si jamais la cellule en x y est vivante
     */
    public boolean isAlive(int x, int y) {
        return aliveCellsMap.containsKey(new Index(x, y));
    }

    private void debugAliveCells() {
        for (Index cell: aliveCellsMap.keySet()) {
            System.out.println(cell.getXIndex() + " " + cell.getYIndex());
        }
    }

    /**
     * Compte le nombre de cellules vivantes autour d'une autre cellule
     * @param x
     * @param y
     * @return
     */
    public int getSurroundingCells(int x, int y) {
        Index index = new Index(x, y);

        if(activeCellsMap.containsKey(index)){
            return activeCellsMap.get(index);
        }
        else {
            return 0;
        }
    }

    public HashMap<Index, Byte> getActiveCellsMap() {
        return activeCellsMap;
    }

    public HashMap<Index, Byte> getAliveCellsMap() {
        return aliveCellsMap;
    }

    public void setActiveCellsMap(HashMap<Index, Byte> activeCellsMap) {
        this.activeCellsMap = activeCellsMap;
    }

    public void setAliveCellsMap(HashMap<Index, Byte> aliveCellsMap) {
        this.aliveCellsMap = aliveCellsMap;
    }

    public static GameLogic getGameLogicClass() {
        return GameLogicClass;
    }
}
