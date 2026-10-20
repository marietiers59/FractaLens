package fr.univartois.butinfo.fractalens.palette;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Color;

import org.junit.jupiter.api.Test;

/**
 * The FixedPaletteTest class contains the unit tests of the {@link FixedPalette} class.
 *
 * @author Théo Brureau
 */
class FixedPaletteTest {

    private final IPalette palette = new FixedPalette(100);

    @Test
    void testZeroIterationGivesFirstColor() {
        assertEquals(Color.BLACK, palette.getColor(0));
    }

    @Test
    void testIterationsBelowMaxGiveFirstColor() {
        assertEquals(Color.BLACK, palette.getColor(1));
        assertEquals(Color.BLACK, palette.getColor(50));
        assertEquals(Color.BLACK, palette.getColor(99));
    }

    @Test
    void testMaxIterationsGivesSecondColor() {
        assertEquals(Color.WHITE, palette.getColor(100));
    }

    @Test
    void testIterationsJustAboveMaxStayOnSecondColor() {
        assertEquals(Color.WHITE, palette.getColor(101));
        assertEquals(Color.WHITE, palette.getColor(199));
    }

    @Test
    void testEachMultipleOfMaxIterationsGivesNextColor() {
        assertEquals(Color.RED, palette.getColor(200));
        assertEquals(Color.PINK, palette.getColor(300));
        assertEquals(Color.BLUE, palette.getColor(400));
    }

    @Test
    void testPaletteWrapsAroundAfterLastColor() {
        assertEquals(Color.BLACK, palette.getColor(500));
        assertEquals(Color.WHITE, palette.getColor(600));
    }

    @Test
    void testAllIterationsBelowMaxShareTheSameColor() {
        for (int i = 0; i < 100; i++) {
            assertEquals(Color.BLACK, palette.getColor(i), "iteration " + i);
        }
    }

    @Test
    void testMaxIterationsOfOne() {
        IPalette small = new FixedPalette(1);
        assertEquals(Color.BLACK, small.getColor(0));
        assertEquals(Color.WHITE, small.getColor(1));
        assertEquals(Color.RED, small.getColor(2));
    }

    @Test
    void testZeroMaxIterationsIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new FixedPalette(0));
    }

    @Test
    void testNegativeMaxIterationsIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new FixedPalette(-5));
    }
}