package fr.univartois.butinfo.fractalens.palette;

import java.awt.Color; //AWT : Abstract Window Toolkit, première bibliothèque graphique de Java (javafx.scene.paint.Color pas compatible avec BufferedImage d'où d'utilisation de AWT à la place)


/**
 * Stratégie de sélection d'une couleur à partir d'un nombre d'itérations.
 */
public interface IPalette {

    /**
     * @param nbIterations Le nombre d'itérations effectuées avant divergence.
     * @param maxIterations Le nombre maximal d'itérations.
     * @return La couleur associée.
     */
    Color getColor(int nbIterations, int maxIterations);
}