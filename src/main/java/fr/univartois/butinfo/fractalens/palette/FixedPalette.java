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
    private static final Color[] colors = { Color.BLACK, Color.WHITE, Color.RED, Color.PINK, Color.BLUE};
    /**
     * The maximum number of iterations allowed in the palette.
     */
    private final int maxIterations;

    /**
     * Creates a new FixedPalette with the specified maximum number of iterations.
     * @param maxIterations The maximum number of iterations allowed in the palette.
     */
    public FixedPalette(int maxIterations) {
        if (maxIterations <= 0) {
            throw new IllegalArgumentException("maxIterations doit etre positif");
        }
        this.maxIterations = maxIterations;
    }

    /**
     *  Method that returns the color corresponding to the given number of iterations.
     * @param nbIterations Le nombre d'itérations effectuées avant divergence.
     * @return the color corresponding to the given number of iterations.
     */
    @Override
    public Color getColor(int nbIterations) {
        int ratio = nbIterations / maxIterations;
        return colors[ratio % colors.length];
    }
}
