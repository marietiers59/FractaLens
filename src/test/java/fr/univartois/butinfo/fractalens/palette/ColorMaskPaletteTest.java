package fr.univartois.butinfo.fractalens.palette;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link ColorMaskPalette} class.
 */
class ColorMaskPaletteTest {

    /**
     * The maximum number of iterations used in the tests.
     */
    private static final int MAX_ITERATIONS = 100;

    /**
     * The greyscale palette decorated in the tests.
     */
    private final IPalette greyscale = new GreyscalePalette(MAX_ITERATIONS);

    /**
     * Checks that black stays black after applying a mask.
     */
    @Test
    void testBlackStaysBlack() {
        IPalette palette = new ColorMaskPalette(greyscale, ColorMaskPalette.Mask.RED);
        assertEquals(new Color(0, 0, 0), palette.getColor(0));
    }

    /**
     * Checks that the red mask keeps only the red component.
     */
    @Test
    void testRedMaskKeepsOnlyRed() {
        IPalette palette = new ColorMaskPalette(greyscale, ColorMaskPalette.Mask.RED);
        assertEquals(new Color(255, 0, 0), palette.getColor(MAX_ITERATIONS));
        assertEquals(new Color(128, 0, 0), palette.getColor(50));
    }

    /**
     * Checks that the green mask keeps only the green component.
     */
    @Test
    void testGreenMaskKeepsOnlyGreen() {
        IPalette palette = new ColorMaskPalette(greyscale, ColorMaskPalette.Mask.GREEN);
        assertEquals(new Color(0, 255, 0), palette.getColor(MAX_ITERATIONS));
        assertEquals(new Color(0, 128, 0), palette.getColor(50));
    }

    /**
     * Checks that the blue mask keeps only the blue component.
     */
    @Test
    void testBlueMaskKeepsOnlyBlue() {
        IPalette palette = new ColorMaskPalette(greyscale, ColorMaskPalette.Mask.BLUE);
        assertEquals(new Color(0, 0, 255), palette.getColor(MAX_ITERATIONS));
        assertEquals(new Color(0, 0, 128), palette.getColor(50));
    }
}
