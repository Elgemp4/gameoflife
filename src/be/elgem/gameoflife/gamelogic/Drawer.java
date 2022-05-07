package be.elgem.gameoflife.gamelogic;

import be.elgem.gameoflife.render.Index;
import javafx.util.Pair;

public class Drawer {
    private Game game;

    public Drawer(Game game) {
        this.game = game;
    }

    /**
     * Dessine une ligne allant de startIndex jusqu'à endIndex
     * @param startIndex index de début de la ligne
     * @param endIndex index de fin de la ligne
     */
    protected void drawLine(Index startIndex, Index endIndex) {
        int xComposant = endIndex.getXIndex() - startIndex.getXIndex();
        int yComposant = endIndex.getYIndex() - startIndex.getYIndex();

        double slopeLength = Math.sqrt(Math.pow(xComposant, 2) + Math.pow(yComposant, 2)); //Pythagore

        if(slopeLength == 0.0) {
            game.getGameLogic().putCell(new Pair<>(startIndex.getXIndex(), startIndex.getYIndex()));
            return;
        }

        if(Math.abs(xComposant) > Math.abs(yComposant)){ //Horizontal
            processLine(true, xComposant, startIndex.getXIndex(), endIndex.getXIndex(), yComposant, startIndex.getYIndex());
        }
        else{
            processLine(false, yComposant, startIndex.getYIndex(), endIndex.getYIndex(), xComposant, startIndex.getXIndex());
        }


    }

    /**
     * Dessine une ligne "horizontale" ou "verticale" selon le paramètre isHorizontal, l'axe principal désigne l'axe
     * dominant soit x si l'axe est "horizontal" soit y s'il est "vertical"
     *
     * @param isHorizontal vrai si on dessine horizontalement
     * @param mainComposant composant de l'axe principal
     * @param startMainAxis index de début de l'axe principal
     * @param endMainAxis index de fin de l'axe principal
     * @param oppositeComposant composant oppose de l'axe principal
     * @param startOppositeAxis index de début de l'axe opposé à l'axe principal
     */
    private void processLine(boolean isHorizontal, int mainComposant, int startMainAxis, int endMainAxis, int oppositeComposant, int startOppositeAxis) {
        int minAxis = Math.min(startMainAxis, endMainAxis);
        int maxAxis = Math.max(startMainAxis, endMainAxis);

        for (double mainAxis = minAxis; mainAxis <= maxAxis; ++mainAxis) {
            /*
             * Passage de la formule paramétrique à l'équation de la droite
             * ============================================================
             * x = Ax + lambda * Ux
             * y = Ay + lambda * Uy
             *
             * (x - Ax) / Ux
             * (y - Ay) / Uy
             *
             * (x - Ax) / Ux = (y - Ay) / Uy
             * x - Ax = ((y - Ay) / Uy) * Ux
             * x = ((y - Ay) / Uy) * Ux + Ax
             * ============================================================
             * Le calcul en dessous est juste une équation de droite
             */

            double oppositeAxis = ((mainAxis - startMainAxis) / mainComposant) * oppositeComposant + startOppositeAxis;

            double x = (isHorizontal) ? mainAxis : oppositeAxis;
            double y = (isHorizontal) ? oppositeAxis : mainAxis;

            Pair<Integer, Integer> index = new Pair<>((int)x, (int)y);

            game.getGameLogic().putCell(index);
        }
    }


}
