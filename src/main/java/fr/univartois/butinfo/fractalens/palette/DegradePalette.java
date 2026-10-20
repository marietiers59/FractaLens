package fr.univartois.butinfo.fractalens.palette;

import java.awt.*;

public class DegradePalette implements IPalette{
    private final int maxIterations;
    private final Color startColor;
    private final Color endColor;

    public DegradePalette(int maxIterations, Color startColor, Color endColor) {
        if (maxIterations <= 0) {
            throw new IllegalArgumentException("maxIterations doit etre positif");
        }
        this.startColor = startColor;
        this.endColor = endColor;
        this.maxIterations = maxIterations;
    }
    @Override
    public Color getColor(int nbIterations) {
        double p = Math.clamp((double) nbIterations / maxIterations, 0.0, 1.0);
        return new Color(interpolate(startColor.getRed(), endColor.getRed(),p), interpolate(startColor.getGreen(), endColor.getGreen(),p), interpolate(startColor.getBlue(), endColor.getBlue(),p));
    }

    private static int interpolate(int from, int to, double p) {
        return (int) Math.round(from + p * (to - from));
    }
}
