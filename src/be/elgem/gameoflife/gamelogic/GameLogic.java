package be.elgem.gameoflife.gamelogic;

import javafx.util.Pair;

import java.util.HashMap;

/**
 * La class CellGrid est une classe représentant un grille de cellule d'un jeu de la vie, chaque cellule est un byte
 * dont les trois premiers bits sont inutilisés, les quatres suivants encodent le nombre de cellules adjacentes et
 * le dernier encode si la cellule est vivante.
 */
public class GameLogic {
    private HashMap<Pair<Integer, Integer>, Byte> activeCellsMap;
    private HashMap<Pair<Integer, Integer>, Byte> aliveCellsMap;

    public GameLogic() {
        activeCellsMap = new HashMap<>();
        aliveCellsMap = new HashMap<>();
    }

    /**
     * Mets une cellule en x y
     */
    public void putCell(Pair<Integer, Integer> index) {
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
    public void removeCell(Pair<Integer, Integer> index) {
        if(isAlive(index)) {
            aliveCellsMap.remove(index);

            informRemovalSurroundingCells(index);
        }
    }

    private void informAdditionSurroundingCells(Pair<Integer, Integer> index) {
        for (int yOffset = -1; yOffset <= 1; yOffset++) {
            for (int xOffset = -1; xOffset <= 1; xOffset++) {
                if (yOffset != 0 || xOffset != 0) {
                    Pair<Integer, Integer> searchIndex = new Pair<>(index.getKey() + xOffset, index.getValue() + yOffset);

                    if (activeCellsMap.containsKey(searchIndex)) {
                        activeCellsMap.replace(searchIndex, (byte) (activeCellsMap.get(searchIndex) + 1));
                    } else {
                        activeCellsMap.put(searchIndex, (byte) 1);
                    }
                }
            }
        }
    }

    public void informRemovalSurroundingCells(Pair<Integer, Integer> index) {
        for (int yOffset = -1; yOffset <= 1; yOffset++) {
            for (int xOffset = -1; xOffset <= 1; xOffset++) {
                if (yOffset != 0 || xOffset != 0) {
                    Pair<Integer, Integer> searchindex = new Pair<>(index.getKey() + xOffset, index.getValue() + yOffset);
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

    public boolean isAlive(Pair<Integer, Integer> index) {
        return aliveCellsMap.containsKey(index);
    }

    /**
     * Vérifie toute les cellules du tableaux en regardant si elles doivent vivre ou mourir
     */
    public void checkCells() {
        HashMap<Pair<Integer, Integer>, Byte> activeCellsMapCopy = (HashMap<Pair<Integer, Integer>, Byte>) activeCellsMap.clone();
        HashMap<Pair<Integer, Integer>, Byte> aliveCellsMapCopy = (HashMap<Pair<Integer, Integer>, Byte>) aliveCellsMap.clone();

        System.out.println(activeCellsMapCopy.size());

        for (Pair<Integer, Integer> cellIndex : activeCellsMapCopy.keySet()) {
            Byte surroundCells = activeCellsMapCopy.get(cellIndex);
            if (surroundCells != 2 && surroundCells != 3) {
                this.removeCell(cellIndex);
            }
            else if (surroundCells == 3) {
                this.putCell(cellIndex);
            }

        }
    }

    /**
     * Renvoie vrai si jamais la cellule en x y est vivante
     */
    public boolean isAlive(int x, int y) {
        return aliveCellsMap.containsKey(new Pair<>(x, y));
    }

    private void debugAliveCells() {
        System.out.println("oui");
        for (Pair<Integer, Integer> cell: aliveCellsMap.keySet()) {
            System.out.println(cell.getKey() + " " + cell.getValue());
        }
    }

    /**
     * Compte le nombre de cellules vivantes autour d'une autre cellule
     * @param x
     * @param y
     * @return
     */
    public int getSurroundingCells(int x, int y) {
        Pair index = new Pair<>(x, y);

        if(activeCellsMap.containsKey(index)){
            return activeCellsMap.get(index);
        }
        else {
            return 0;
        }
    }

    public HashMap<Pair<Integer, Integer>, Byte> getActiveCellsMap() {
        return activeCellsMap;
    }
}
