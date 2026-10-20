package fr.univartois.butinfo.fractalens.palette;

import java.awt.Color;

/**
 * Greyscale palette: the red, green and blue components are equal to
 * the ratio between the number of iterations performed and the maximum
 * number of iterations.
 */
public class GreyscalePalette implements IPalette {

    /**
     * The maximum number of iterations.
     */
    private final int maxIterations;

    /**
     * Creates a greyscale palette.
     *
     * @param maxIterations The maximum number of iterations.
     */
    public GreyscalePalette(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color getColor(int nbIterations) {
        float ratio = (float) nbIterations / maxIterations;
        return new Color(ratio, ratio, ratio); // float constructor: values between 0 and 1
    }
}