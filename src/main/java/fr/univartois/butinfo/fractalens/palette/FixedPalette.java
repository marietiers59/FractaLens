package fr.univartois.butinfo.fractalens.palette;

import java.awt.Color;

/**
 * Class FixedPalette implements the IPalette interface and provides a fixed palette of colors.
 *
 * @author Théo Brureau
 */
public class FixedPalette implements IPalette {
    /**
     * The array of colors used in the palette.
     */
    private static final Color[] colors = { Color.BLACK, Color.WHITE, Color.RED, Color.PINK, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.MAGENTA, Color.CYAN};

    /**
     *  Method that returns the color corresponding to the given number of iterations.
     * @param nbIterations Le nombre d'itérations effectuées avant divergence.
     * @return the color corresponding to the given number of iterations.
     */
    @Override
    public Color getColor(int nbIterations) {
        if (nbIterations >= colors.length) {
            return colors[colors.length - 1];
        }
        return colors[nbIterations];
    }
}
