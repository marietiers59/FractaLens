package fr.univartois.butinfo.fractalens.palette;

import java.awt.*;
/**
 - The InvertedPalette class is a decorator that inverts the colors of a wrapped palette
 */
public class InvertedPalette extends PaletteDecorator {

    /**
    create a new inverted palette
    @param wrapped The palette to decorate.
     */
    public InvertedPalette(IPalette wrapped) {
        super(wrapped);
    }

    /**
     * Inverts the colors of the wrapped palette.
     * @param maxIterations
     * @return
     */
    @Override
    public Color getColor(int maxIterations) {
        Color imageColor = wrapped.getColor(maxIterations);
        int r = 255 - imageColor.getRed();
        int g = 255 - imageColor.getGreen();
        int b = 255 - imageColor.getBlue();
        return new Color(r, g, b);
    }
}
