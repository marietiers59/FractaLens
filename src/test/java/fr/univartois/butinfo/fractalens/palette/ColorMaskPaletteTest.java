package fr.univartois.butinfo.fractalens.palette;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;
import java.util.List;

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
     * A palette giving the color (10, 20, 30) for 0 iterations. As its three
     * components are all different, any mix-up between them becomes visible
     * (which is not the case with a grey color, where R = G = B).
     */
    private final IPalette distinctComponents = new FixedPalette(List.of(new Color(10, 20, 30)));

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

    /**
     * Checks that the yellow mask keeps only the red and green components.
     */
    @Test
    void testYellowMaskKeepsRedAndGreen() {
        IPalette palette = new ColorMaskPalette(greyscale, ColorMaskPalette.Mask.YELLOW);
        assertEquals(new Color(255, 255, 0), palette.getColor(MAX_ITERATIONS));
        assertEquals(new Color(128, 128, 0), palette.getColor(50));
    }

    /**
     * Checks that the cyan mask keeps only the green and blue components.
     */
    @Test
    void testCyanMaskKeepsGreenAndBlue() {
        IPalette palette = new ColorMaskPalette(greyscale, ColorMaskPalette.Mask.CYAN);
        assertEquals(new Color(0, 255, 255), palette.getColor(MAX_ITERATIONS));
        assertEquals(new Color(0, 128, 128), palette.getColor(50));
    }

    /**
     * Checks that the magenta mask keeps only the red and blue components.
     */
    @Test
    void testMagentaMaskKeepsRedAndBlue() {
        IPalette palette = new ColorMaskPalette(greyscale, ColorMaskPalette.Mask.MAGENTA);
        assertEquals(new Color(255, 0, 255), palette.getColor(MAX_ITERATIONS));
        assertEquals(new Color(128, 0, 128), palette.getColor(50));
    }

    /**
     * Checks that each mask keeps exactly the right components of a color
     * whose red, green and blue components are all different.
     */
    @Test
    void testEachMaskKeepsTheRightComponents() {
        assertEquals(new Color(10, 0, 0), masked(ColorMaskPalette.Mask.RED));
        assertEquals(new Color(0, 20, 0), masked(ColorMaskPalette.Mask.GREEN));
        assertEquals(new Color(0, 0, 30), masked(ColorMaskPalette.Mask.BLUE));
        assertEquals(new Color(10, 20, 0), masked(ColorMaskPalette.Mask.YELLOW));
        assertEquals(new Color(0, 20, 30), masked(ColorMaskPalette.Mask.CYAN));
        assertEquals(new Color(10, 0, 30), masked(ColorMaskPalette.Mask.MAGENTA));
    }

    /**
     * Applies the given mask to the palette with distinct components and
     * returns the color obtained for 0 iterations.
     *
     * @param mask The mask to apply.
     *
     * @return The masked color.
     */
    private Color masked(ColorMaskPalette.Mask mask) {
        return new ColorMaskPalette(distinctComponents, mask).getColor(0);
    }
}
