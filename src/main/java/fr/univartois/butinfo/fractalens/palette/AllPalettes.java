package fr.univartois.butinfo.fractalens.palette;

import java.awt.Color;
import java.util.List;
import java.util.function.IntFunction;

/**
 * Enumeration of every color palette available in FractaLens.
 *
 * Each constant combines one of the three base palettes ({@link GreyscalePalette},
 * {@link DegradePalette}, {@link FixedPalette}) with the decorators implemented in
 * the project: {@link InvertedPalette} and {@link ColorMaskPalette}.
 * When both decorators are used, the colors are first inverted, then masked, so
 * that only the chosen color components remain visible.
 *
 * @author Marie TIERS
 */
public enum AllPalettes {

    // ------------------------------------------------------------------ Greyscale

    /** Black-to-white gradient. */
    GREYSCALE(max -> new GreyscalePalette(max), false, null),

    /** White-to-black gradient. */
    GREYSCALE_INVERTED(max -> new GreyscalePalette(max), true, null),

    /** Greyscale palette keeping only the red component. */
    GREYSCALE_RED(max -> new GreyscalePalette(max), false, ColorMaskPalette.Mask.RED),

    /** Greyscale palette keeping only the green component. */
    GREYSCALE_GREEN(max -> new GreyscalePalette(max), false, ColorMaskPalette.Mask.GREEN),

    /** Greyscale palette keeping only the blue component. */
    GREYSCALE_BLUE(max -> new GreyscalePalette(max), false, ColorMaskPalette.Mask.BLUE),

    /** Greyscale palette keeping only the red and green components. */
    GREYSCALE_YELLOW(max -> new GreyscalePalette(max), false, ColorMaskPalette.Mask.YELLOW),

    /** Greyscale palette keeping only the green and blue components. */
    GREYSCALE_CYAN(max -> new GreyscalePalette(max), false, ColorMaskPalette.Mask.CYAN),

    /** Greyscale palette keeping only the blue and red components. */
    GREYSCALE_MAGENTA(max -> new GreyscalePalette(max), false, ColorMaskPalette.Mask.MAGENTA),

    /** Inverted greyscale palette keeping only the red component. */
    GREYSCALE_INVERTED_RED(max -> new GreyscalePalette(max), true, ColorMaskPalette.Mask.RED),

    /** Inverted greyscale palette keeping only the green component. */
    GREYSCALE_INVERTED_GREEN(max -> new GreyscalePalette(max), true, ColorMaskPalette.Mask.GREEN),

    /** Inverted greyscale palette keeping only the blue component. */
    GREYSCALE_INVERTED_BLUE(max -> new GreyscalePalette(max), true, ColorMaskPalette.Mask.BLUE),

    /** Inverted greyscale palette keeping only the red and green components. */
    GREYSCALE_INVERTED_YELLOW(max -> new GreyscalePalette(max), true, ColorMaskPalette.Mask.YELLOW),

    /** Inverted greyscale palette keeping only the green and blue components. */
    GREYSCALE_INVERTED_CYAN(max -> new GreyscalePalette(max), true, ColorMaskPalette.Mask.CYAN),

    /** Inverted greyscale palette keeping only the blue and red components. */
    GREYSCALE_INVERTED_MAGENTA(max -> new GreyscalePalette(max), true, ColorMaskPalette.Mask.MAGENTA),

    // -------------------------------------------------------------------- Degrade

    /** Gradient from {@link Defaults#DEGRADE_START_COLOR} to {@link Defaults#DEGRADE_END_COLOR}. */
    DEGRADE(max -> new DegradePalette(max, Defaults.DEGRADE_START_COLOR, Defaults.DEGRADE_END_COLOR), false, null),

    /** Inverted gradient palette. */
    DEGRADE_INVERTED(max -> new DegradePalette(max, Defaults.DEGRADE_START_COLOR, Defaults.DEGRADE_END_COLOR), true, null),

    /** Gradient palette keeping only the red component. */
    DEGRADE_RED(max -> new DegradePalette(max, Defaults.DEGRADE_START_COLOR, Defaults.DEGRADE_END_COLOR), false, ColorMaskPalette.Mask.RED),

    /** Gradient palette keeping only the green component. */
    DEGRADE_GREEN(max -> new DegradePalette(max, Defaults.DEGRADE_START_COLOR, Defaults.DEGRADE_END_COLOR), false, ColorMaskPalette.Mask.GREEN),

    /** Gradient palette keeping only the blue component. */
    DEGRADE_BLUE(max -> new DegradePalette(max, Defaults.DEGRADE_START_COLOR, Defaults.DEGRADE_END_COLOR), false, ColorMaskPalette.Mask.BLUE),

    /** Gradient palette keeping only the red and green components. */
    DEGRADE_YELLOW(max -> new DegradePalette(max, Defaults.DEGRADE_START_COLOR, Defaults.DEGRADE_END_COLOR), false, ColorMaskPalette.Mask.YELLOW),

    /** Gradient palette keeping only the green and blue components. */
    DEGRADE_CYAN(max -> new DegradePalette(max, Defaults.DEGRADE_START_COLOR, Defaults.DEGRADE_END_COLOR), false, ColorMaskPalette.Mask.CYAN),

    /** Gradient palette keeping only the blue and red components. */
    DEGRADE_MAGENTA(max -> new DegradePalette(max, Defaults.DEGRADE_START_COLOR, Defaults.DEGRADE_END_COLOR), false, ColorMaskPalette.Mask.MAGENTA),

    /** Inverted gradient palette keeping only the red component. */
    DEGRADE_INVERTED_RED(max -> new DegradePalette(max, Defaults.DEGRADE_START_COLOR, Defaults.DEGRADE_END_COLOR), true, ColorMaskPalette.Mask.RED),

    /** Inverted gradient palette keeping only the green component. */
    DEGRADE_INVERTED_GREEN(max -> new DegradePalette(max, Defaults.DEGRADE_START_COLOR, Defaults.DEGRADE_END_COLOR), true, ColorMaskPalette.Mask.GREEN),

    /** Inverted gradient palette keeping only the blue component. */
    DEGRADE_INVERTED_BLUE(max -> new DegradePalette(max, Defaults.DEGRADE_START_COLOR, Defaults.DEGRADE_END_COLOR), true, ColorMaskPalette.Mask.BLUE),

    /** Inverted gradient palette keeping only the red and green components. */
    DEGRADE_INVERTED_YELLOW(max -> new DegradePalette(max, Defaults.DEGRADE_START_COLOR, Defaults.DEGRADE_END_COLOR), true, ColorMaskPalette.Mask.YELLOW),

    /** Inverted gradient palette keeping only the green and blue components. */
    DEGRADE_INVERTED_CYAN(max -> new DegradePalette(max, Defaults.DEGRADE_START_COLOR, Defaults.DEGRADE_END_COLOR), true, ColorMaskPalette.Mask.CYAN),

    /** Inverted gradient palette keeping only the blue and red components. */
    DEGRADE_INVERTED_MAGENTA(max -> new DegradePalette(max, Defaults.DEGRADE_START_COLOR, Defaults.DEGRADE_END_COLOR), true, ColorMaskPalette.Mask.MAGENTA),

    // ---------------------------------------------------------------------- Fixed

    /** Fixed palette using the colors of {@link Defaults#FIXED_COLORS}. */
    FIXED(_ -> new FixedPalette(Defaults.FIXED_COLORS), false, null),

    /** Inverted fixed palette. */
    FIXED_INVERTED(_ -> new FixedPalette(Defaults.FIXED_COLORS), true, null),

    /** Fixed palette keeping only the red component. */
    FIXED_RED(_ -> new FixedPalette(Defaults.FIXED_COLORS), false, ColorMaskPalette.Mask.RED),

    /** Fixed palette keeping only the green component. */
    FIXED_GREEN(_ -> new FixedPalette(Defaults.FIXED_COLORS), false, ColorMaskPalette.Mask.GREEN),

    /** Fixed palette keeping only the blue component. */
    FIXED_BLUE(_ -> new FixedPalette(Defaults.FIXED_COLORS), false, ColorMaskPalette.Mask.BLUE),

    /** Fixed palette keeping only the red and green components. */
    FIXED_YELLOW(_ -> new FixedPalette(Defaults.FIXED_COLORS), false, ColorMaskPalette.Mask.YELLOW),

    /** Fixed palette keeping only the green and blue components. */
    FIXED_CYAN(_ -> new FixedPalette(Defaults.FIXED_COLORS), false, ColorMaskPalette.Mask.CYAN),

    /** Fixed palette keeping only the blue and red components. */
    FIXED_MAGENTA(_ -> new FixedPalette(Defaults.FIXED_COLORS), false, ColorMaskPalette.Mask.MAGENTA),

    /** Inverted fixed palette keeping only the red component. */
    FIXED_INVERTED_RED(_ -> new FixedPalette(Defaults.FIXED_COLORS), true, ColorMaskPalette.Mask.RED),

    /** Inverted fixed palette keeping only the green component. */
    FIXED_INVERTED_GREEN(_ -> new FixedPalette(Defaults.FIXED_COLORS), true, ColorMaskPalette.Mask.GREEN),

    /** Inverted fixed palette keeping only the blue component. */
    FIXED_INVERTED_BLUE(_ -> new FixedPalette(Defaults.FIXED_COLORS), true, ColorMaskPalette.Mask.BLUE),

    /** Inverted fixed palette keeping only the red and green components. */
    FIXED_INVERTED_YELLOW(_ -> new FixedPalette(Defaults.FIXED_COLORS), true, ColorMaskPalette.Mask.YELLOW),

    /** Inverted fixed palette keeping only the green and blue components. */
    FIXED_INVERTED_CYAN(_ -> new FixedPalette(Defaults.FIXED_COLORS), true, ColorMaskPalette.Mask.CYAN),

    /** Inverted fixed palette keeping only the blue and red components. */
    FIXED_INVERTED_MAGENTA(_ -> new FixedPalette(Defaults.FIXED_COLORS), true, ColorMaskPalette.Mask.MAGENTA);

    /**
     * The factory creating the base palette from the maximum number of iterations.
     */
    private final IntFunction<IPalette> baseFactory;

    /**
     * Whether the colors of the base palette are inverted.
     */
    private final boolean inverted;

    /**
     * The color mask to apply, or {@code null} if no mask is applied.
     */
    private final ColorMaskPalette.Mask mask;

    /**
     * Creates a palette type.
     *
     * @param baseFactory The factory creating the base palette.
     * @param inverted Whether the colors of the base palette are inverted.
     * @param mask The color mask to apply, or {@code null} for no mask.
     */
    AllPalettes(IntFunction<IPalette> baseFactory, boolean inverted, ColorMaskPalette.Mask mask) {
        this.baseFactory = baseFactory;
        this.inverted = inverted;
        this.mask = mask;
    }

    /**
     * Creates the color palette corresponding to this constant.
     *
     * @param maxIterations The maximum number of iterations (the size of the
     *        palette), needed by the greyscale and gradient palettes.
     * @return The palette, decorated as described by this constant.
     */
    public IPalette getPalette(int maxIterations) {
        IPalette palette = baseFactory.apply(maxIterations);
        if (inverted) {
            palette = new InvertedPalette(palette);
        }
        if (mask != null) {
            palette = new ColorMaskPalette(palette, mask);
        }
        return palette;
    }

    /**
     * Holder of the default colors used by the palettes.
     * These constants live in a nested class because an enum constant cannot
     * refer to a static field of its own enum declared after it.
     */
    private static final class Defaults {

        /**
         * The first color of the gradient palette.
         */
        static final Color DEGRADE_START_COLOR = Color.BLUE;

        /**
         * The last color of the gradient palette.
         */
        static final Color DEGRADE_END_COLOR = Color.ORANGE;

        /**
         * The colors of the fixed palette (the index is the number of iterations;
         * the last color is reused beyond the end of the list).
         */
        static final List<Color> FIXED_COLORS = List.of(
                Color.BLACK, Color.BLUE, Color.CYAN, Color.GREEN,
                Color.YELLOW, Color.ORANGE, Color.RED, Color.MAGENTA, Color.WHITE);

        /**
         * Prevents instantiation.
         */
        private Defaults() {
        }
    }
}