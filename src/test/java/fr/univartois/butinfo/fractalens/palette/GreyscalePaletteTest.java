package fr.univartois.butinfo.fractalens.palette;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link GreyscalePalette} class.
 */
class GreyscalePaletteTest {

    /**
     * The maximum number of iterations used in the tests.
     */
    private static final int MAX_ITERATIONS = 100;

    /**
     * The greyscale palette under test.
     */
    private final IPalette palette = new GreyscalePalette(MAX_ITERATIONS);

    /**
     * Checks that zero iterations (ratio 0) gives black.
     */
    @Test
    void testZeroIterationGivesBlack() {
        assertEquals(new Color(0, 0, 0), palette.getColor(0));
    }

    /**
     * Checks that the maximum number of iterations (ratio 1) gives white.
     */
    @Test
    void testMaxIterationsGivesWhite() {
        assertEquals(new Color(255, 255, 255), palette.getColor(MAX_ITERATIONS));
    }

    /**
     * Checks that half the maximum number of iterations (ratio 0.5) gives
     * a middle grey.
     */
    @Test
    void testHalfIterationsGivesMiddleGrey() {
        assertEquals(new Color(128, 128, 128), palette.getColor(50));
    }
}