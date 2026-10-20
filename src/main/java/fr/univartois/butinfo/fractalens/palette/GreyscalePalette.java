package fr.univartois.butinfo.fractalens.palette;

import java.awt.Color;

public class GreyscalePalette implements IPalette {

    private final int maxIterations;

    public GreyscalePalette(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    @Override
    public Color getColor(int nbIterations) {
        float ratio = (float) nbIterations / maxIterations;
        return new Color(ratio, ratio, ratio); // constructeur float : valeurs entre 0 et 1
    }
}