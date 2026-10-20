package fr.univartois.butinfo.fractalens.palette;

import java.awt.Color;

/**
 * Palette decorator applying a primary color mask to the colors of the
 * decorated palette: only the component of the chosen primary color (red,
 * green, blue, yellow, cyan or magenta) is kept, the two others are set to 0.
 *
 * For example, decorating a {@link GreyscalePalette} with the
 * {@link Mask#RED} mask turns the black-to-white gradient into a
 * black-to-red gradient.
 */
public class ColorMaskPalette extends PaletteDecorator {

    /**
     * The primary color masks that can be applied.
     */
    public enum Mask {

        /**
         * Keeps only the red component.
         */
        RED,

        /**
         * Keeps only the green component.
         */
        GREEN,

        /**
         * Keeps only the blue component.
         */
        BLUE,
        
        /**
         * Keeps only the red and green components.
         */
        YELLOW,
        
        /**
         * Keeps only the green and blue components.
         */
        CYAN,
        
        /**
         * Keeps only the blue and red components.
         */
        MAGENTA
    }

    /**
     * The mask applied to the colors of the decorated palette.
     */
    private final Mask mask;

    /**
     * Creates a color mask palette.
     *
     * @param wrapped The palette to decorate.
     * @param mask The mask applied to the colors of the decorated palette.
     */
    public ColorMaskPalette(IPalette wrapped, Mask mask) {
        super(wrapped);
        this.mask = mask;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color getColor(int nbIterations) {
        Color original = wrapped.getColor(nbIterations);
        return switch (mask) {
            case RED -> new Color(original.getRed(), 0, 0);
            case GREEN -> new Color(0, original.getGreen(), 0);
            case BLUE -> new Color(0, 0, original.getBlue());
            case YELLOW -> new Color(original.getRed(), original.getGreen(), 0);
            case CYAN -> new Color(0, original.getGreen(), original.getBlue());
            case MAGENTA -> new Color(original.getRed(), 0, original.getBlue());
        };
    }
}
