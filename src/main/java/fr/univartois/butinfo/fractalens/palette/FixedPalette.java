package fr.univartois.butinfo.fractalens.palette;

import java.awt.Color;
import java.util.List;

/**
 * Fixed color palette: associates each number of iterations with the
 * color at that index in a list.
 *
 * @author Théo Brureau
 */
public class FixedPalette implements IPalette {

    /**
     * The colors of the palette (the index is the number of iterations).
     */
    private final List<Color> colors;

    /**
     * Creates a fixed color palette.
     *
     * @param colors The colors of the palette; the list must contain at
     *        least (maximum number of iterations + 1) colors.
     */
    public FixedPalette(List<Color> colors) {
        this.colors = List.copyOf(colors);
    }

    /**
     * {@inheritDoc}
     *
     * @throws IndexOutOfBoundsException If the list contains no color for
     *         this number of iterations.
     */
    @Override
    public Color getColor(int nbIterations) {
        if (nbIterations >= colors.size()) {
            return colors.get(colors.size() - 1);
        }
        return colors.get(nbIterations);
    }
}
