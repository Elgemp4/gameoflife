package be.elgem.gameoflife.gamelogic;

public class ByteCell {
    public static byte setAlive(byte cell, boolean isAlive) {
        if (isAlive)
            return (byte) (cell | 0b00000001); //Utilisation de l'opérateur bitwise OR pour "forcer" le dernier bits à devenir 1
        else
            return (byte) (cell & 0b11111110);//Utilisation de l'opérateur bitwise AND pour "forcer" le dernier bits à devenir 0
    }

    public static boolean isAlive(Byte cell) {
        if ((cell & 0b00000001) == 1) //Utilisation de l'opérateur bitwise AND pour appliquer un masque et isoler le dernier bit
            return true;
        else
            return false;
    }

    public static Byte incrementAdjacentCellCount(Byte cell) {
        cell = (byte) ((cell & 0b11100001) | ((cell >> 1) + 1) << 1);   // On isole d'abord les 4 bits représentant le
                                                                        // nombres de cellules adjacente et puis on
                                                                        // remplace ces 4 bits grace à l'opérateur OR
        return cell;
    }

    public static Byte decrementAdjacentCellCount(Byte cell) {
        cell = (byte) ((cell & 0b11100001) | ((cell >> 1) - 1) << 1);

        return cell;
    }

    public static byte getAdjacentCellCount(Byte cell) {
        return (byte) ((cell & 0b00011110) >> 1);   //On décalle les bits à droite pour pouvoir lire les valeurs de bits
                                                    //enregistrant le nombre de cellules adjacente
    }
}
