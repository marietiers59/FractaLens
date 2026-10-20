package fr.univartois.butinfo.fractalens.palette;

import java.awt.Color; //AWT : Abstract Window Toolkit, première bibliothèque graphique de Java (javafx.scene.paint.Color pas compatible avec BufferedImage d'où d'utilisation de AWT à la place)

public interface IPalette {

    /**
     * @param iterations Le nombre d'itérations effectuées avant divergence.
     * @param maxIterations Le nombre maximal d'itérations.
     * @return La couleur associée (noir si iterations == maxIterations).
     */
    Color getColor(int iterations, int maxIterations);
}