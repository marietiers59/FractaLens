package fr.univartois.butinfo.fractalens.palette;

import java.awt.Color;
import java.util.List;

/**
 * Palette de couleur fixe : associe à chaque nombre d'itérations
 * la couleur située à cet indice dans une liste.
 */
public class CustomPalette implements IPalette {

    /**
     * Les couleurs de la palette (l'indice correspond au nombre d'itérations).
     */
    private final List<Color> colors;

    /**
     * @param colors Les couleurs de la palette ; la liste doit contenir
     *        au moins (maxIterations + 1) couleurs.
     */
    public CustomPalette(List<Color> colors) {
        this.colors = List.copyOf(colors);
    }

    /**
     *
     */
    @Override
    public Color getColor(int nbIterations, int maxIterations) {
        return colors.get(nbIterations);
    }
}