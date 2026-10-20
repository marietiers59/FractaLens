package fr.univartois.butinfo.fractalens.palette;

import java.awt.Color; //AWT : Abstract Window Toolkit, première bibliothèque graphique de Java (javafx.scene.paint.Color pas compatible avec BufferedImage d'où d'utilisation de AWT à la place)


/**
 * 
 */
public interface IPalette {

    /**
     * Donne la couleur associée à un nombre d'itérations.
     *
     * @param nbIterations Le nombre d'itérations effectuées avant divergence.
     * @return La couleur associée.
     */
    Color getColor(int nbIterations);
}