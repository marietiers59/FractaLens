package fr.univartois.butinfo.fractalens.palette;

import java.awt.Color;

public class GreyscalePalette implements IPalette {

    /**
     *
     */
    @Override
    public Color getColor(int nbIterations, int maxIterations) {
        float ratio = (float) nbIterations / maxIterations;
        return new Color(ratio, ratio, ratio); // constructeur float : valeurs entre 0 et 1
    }
}