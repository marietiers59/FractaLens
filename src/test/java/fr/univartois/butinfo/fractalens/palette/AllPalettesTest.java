package fr.univartois.butinfo.fractalens.palette;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.Color;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

/**
 * Unit tests of the {@link AllPalettes} enumeration.
 *
 * The parameterized test checks that every constant gives a usable palette.
 * The targeted tests check actual colors on representative constants: a plain
 * palette, an inverted one, a masked one and an inverted and masked one,
 * covering each of the three base palettes at least once.
 *
 * @author Marie TIERS
 */
public class AllPalettesTest {

    /**
     * The maximum number of iterations used in the tests.
     */
    private static final int MAX_ITERATIONS = 100;

    /**
     * The index of the orange color in the fixed palette.
     */
    private static final int FIXED_ORANGE_INDEX = 5;

    /**
     * Checks that every constant creates a palette giving a color for the
     * minimum and maximum numbers of iterations.
     *
     * @param constant The tested constant.
     */
    @ParameterizedTest
    @EnumSource(AllPalettes.class)
    void testEveryConstantGivesAPalette(AllPalettes constant) {
        IPalette palette = constant.getPalette(MAX_ITERATIONS);
        assertNotNull(palette);
        assertNotNull(palette.getColor(0));
        assertNotNull(palette.getColor(MAX_ITERATIONS));
    }

    /**
     * Checks the plain greyscale palette: black at 0, white at the maximum.
     */
    @Test
    void testGreyscale() {
        IPalette palette = AllPalettes.GREYSCALE.getPalette(MAX_ITERATIONS);
        assertEquals(Color.BLACK, palette.getColor(0));
        assertEquals(Color.WHITE, palette.getColor(MAX_ITERATIONS));
    }

    /**
     * Checks the inverted greyscale palette: white at 0, black at the maximum.
     */
    @Test
    void testGreyscaleInverted() {
        IPalette palette = AllPalettes.GREYSCALE_INVERTED.getPalette(MAX_ITERATIONS);
        assertEquals(Color.WHITE, palette.getColor(0));
        assertEquals(Color.BLACK, palette.getColor(MAX_ITERATIONS));
    }

    /**
     * Checks the inverted greyscale palette with a red mask.
     */
    @Test
    void testGreyscaleInvertedRed() {
        IPalette palette = AllPalettes.GREYSCALE_INVERTED_RED.getPalette(MAX_ITERATIONS);
        // grey at 0 = black -> inverted = white -> red mask = pure red
        assertEquals(new Color(255, 0, 0), palette.getColor(0));
        // grey at max = white -> inverted = black -> red mask = black
        assertEquals(Color.BLACK, palette.getColor(MAX_ITERATIONS));
    }

    /**
     * Checks the gradient palette with a red mask.
     */
    @Test
    void testDegradeRed() {
        IPalette palette = AllPalettes.DEGRADE_RED.getPalette(MAX_ITERATIONS);
        // blue (0, 0, 255) -> red mask = black
        assertEquals(Color.BLACK, palette.getColor(0));
        // orange (255, 200, 0) -> red mask = pure red
        assertEquals(new Color(255, 0, 0), palette.getColor(MAX_ITERATIONS));
    }

    /**
     * Checks the inverted gradient palette.
     */
    @Test
    void testDegradeInverted() {
        IPalette palette = AllPalettes.DEGRADE_INVERTED.getPalette(MAX_ITERATIONS);
        // blue (0, 0, 255) -> inverted = (255, 255, 0)
        assertEquals(new Color(255, 255, 0), palette.getColor(0));
        // orange (255, 200, 0) -> inverted = (0, 55, 255)
        assertEquals(new Color(0, 55, 255), palette.getColor(MAX_ITERATIONS));
    }

    /**
     * Checks the inverted gradient palette with a yellow mask.
     */
    @Test
    void testDegradeInvertedYellow() {
        IPalette palette = AllPalettes.DEGRADE_INVERTED_YELLOW.getPalette(MAX_ITERATIONS);
        // blue -> inverted = (255, 255, 0) -> yellow mask keeps red and green
        assertEquals(new Color(255, 255, 0), palette.getColor(0));
        // orange -> inverted = (0, 55, 255) -> yellow mask = (0, 55, 0)
        assertEquals(new Color(0, 55, 0), palette.getColor(MAX_ITERATIONS));
    }

    /**
     * Checks the plain fixed palette: one color per number of iterations, the
     * last color being reused beyond the end of the list.
     */
    @Test
    void testFixed() {
        IPalette palette = AllPalettes.FIXED.getPalette(MAX_ITERATIONS);
        assertEquals(Color.BLACK, palette.getColor(0));
        assertEquals(Color.BLUE, palette.getColor(1));
        assertEquals(Color.WHITE, palette.getColor(MAX_ITERATIONS));
    }

    /**
     * Checks the fixed palette with a cyan mask.
     */
    @Test
    void testFixedCyan() {
        IPalette palette = AllPalettes.FIXED_CYAN.getPalette(MAX_ITERATIONS);
        // orange (255, 200, 0) -> cyan mask keeps green and blue = (0, 200, 0)
        assertEquals(new Color(0, 200, 0), palette.getColor(FIXED_ORANGE_INDEX));
    }

    /**
     * Checks the inverted fixed palette with a magenta mask.
     */
    @Test
    void testFixedInvertedMagenta() {
        IPalette palette = AllPalettes.FIXED_INVERTED_MAGENTA.getPalette(MAX_ITERATIONS);
        // black -> inverted = white -> magenta mask = (255, 0, 255)
        assertEquals(new Color(255, 0, 255), palette.getColor(0));
        // white -> inverted = black -> magenta mask = black
        assertEquals(Color.BLACK, palette.getColor(MAX_ITERATIONS));
    }
}