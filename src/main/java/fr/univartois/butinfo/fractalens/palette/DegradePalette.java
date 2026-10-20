package fr.univartois.butinfo.fractalens.palette;

import java.awt.*;

/**
 * The DegradePalette class implements the IPalette interface and provides a color palette that gradually changes
 * from one color to another based on the number of iterations.
 *
 * @author Théo Brureau
 */
public class DegradePalette implements IPalette{
    /**
     * The maxIterations parameter is used to determine the ratio of the color change between the start and end colors.
     */
    private final int maxIterations;
    /**
     * The startColor and endColor parameters define the initial color of the color gradient.
     */
    private final Color startColor;
    /**
     * The startColor and endColor parameters define the final color of the color gradient.
     */
    private final Color endColor;

    /**
     * Creates a new DegradePalette with the specified parameters.
     * @param maxIterations The maxIterations parameter is used to determine the ratio of the color change between the start and end colors.
     * @param startColor The startColor and endColor parameters define the initial color of the color gradient.
     * @param endColor The startColor and endColor parameters define the final color of the color gradient.
     */
    public DegradePalette(int maxIterations, Color startColor, Color endColor) {
        if (maxIterations <= 0) {
            throw new IllegalArgumentException("maxIterations doit etre positif");
        }
        this.startColor = startColor;
        this.endColor = endColor;
        this.maxIterations = maxIterations;
    }

    /**
     * Returns the color associated with the given number of iterations.
     * @param nbIterations Le nombre d'itérations effectuées avant divergence.
     * @return The color associated with the given number of iterations.
     */
    @Override
    public Color getColor(int nbIterations) {
        float ratio = (float) nbIterations / maxIterations;
        int red = Math.round(startColor.getRed() + ratio * (endColor.getRed() - startColor.getRed()));
        int green = Math.round(startColor.getGreen() + ratio * (endColor.getGreen() - startColor.getGreen()));
        int blue = Math.round(startColor.getBlue() + ratio * (endColor.getBlue() - startColor.getBlue()));
        return new Color(red, green, blue);
    }

}
