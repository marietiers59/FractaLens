package fr.univartois.butinfo.fractalens.palette;

import java.awt.Color; // AWT (Abstract Window Toolkit): Java's first graphics library; javafx.scene.paint.Color is not compatible with BufferedImage, hence the use of AWT

/**
 * Strategy for selecting a color from a number of iterations.
 */
public interface IPalette {

    /**
     * Gives the color associated with a number of iterations.
     *
     * @param nbIterations The number of iterations performed before divergence.
     * @return The associated color.
     */
    Color getColor(int nbIterations);
}