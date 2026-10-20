package fr.univartois.butinfo.fractalens.palette;

import java.awt.Color;

public class GreyscalePalette implements IPalette {

	/**
	 * Palette en niveaux de gris : les composantes rouge, verte et bleue
	 * sont égales au ratio entre le nombre d'itérations effectuées et le
	 * nombre maximal d'itérations.
	 */
    @Override
    public Color getColor(int nbIterations, int maxIterations) {
        float ratio = (float) nbIterations / maxIterations;
        return new Color(ratio, ratio, ratio); // constructeur float : valeurs entre 0 et 1
    }
}