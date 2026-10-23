package fr.univartois.butinfo.fractalens.palette;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;

import org.junit.jupiter.api.Test;

/**
 * The DegradePaletteTest class contains the unit tests of the {@link DegradePalette} class.
 *
 * @author Théo Brureau
 */
class DegradePaletteTest {

    /**
     * The tolerance (on each RGB component) accepted because of the rounding of the gradient.
     */
    private static final int TOLERANCE = 1;

    /**
     * Checks that two colors are equal, up to a small rounding tolerance on each component.
     *
     * @param expected The expected color.
     * @param actual The actual color.
     */
    private static void assertColorEquals(Color expected, Color actual) {
        assertEquals((float) expected.getRed(), (float) actual.getRed(), TOLERANCE, "red component");
        assertEquals((float) expected.getGreen(), (float) actual.getGreen(), TOLERANCE, "green component");
        assertEquals((float) expected.getBlue(), (float) actual.getBlue(), TOLERANCE, "blue component");
    }

    @Test
    void testZeroIterationGivesStartColor() {
        IPalette palette = new DegradePalette(100, Color.RED, Color.BLUE);
        assertEquals(Color.RED, palette.getColor(0));
    }

    @Test
    void testMaxIterationsGivesEndColor() {
        IPalette palette = new DegradePalette(100, Color.RED, Color.BLUE);
        assertEquals(Color.BLUE, palette.getColor(100));
    }

    @Test
    void testHalfIterationsGivesMiddleColor() {
        IPalette palette = new DegradePalette(100, Color.BLACK, Color.WHITE);
        assertColorEquals(new Color(128, 128, 128), palette.getColor(50));
    }

    @Test
    void testQuarterIterationsOnEachComponent() {
        IPalette palette = new DegradePalette(100, new Color(0, 0, 0), new Color(200, 100, 40));
        assertColorEquals(new Color(50, 25, 10), palette.getColor(25));
    }

    @Test
    void testIntermediateColorWithNonTrivialStartColor() {
        IPalette palette = new DegradePalette(10, new Color(10, 20, 30), new Color(110, 220, 130));
        assertColorEquals(new Color(40, 80, 60), palette.getColor(3));
    }

    @Test
    void testDecreasingComponents() {
        IPalette palette = new DegradePalette(100, Color.YELLOW, Color.RED);
        assertColorEquals(new Color(255, 128, 0), palette.getColor(50));
    }

    @Test
    void testSameStartAndEndColorsGiveConstantColor() {
        IPalette palette = new DegradePalette(50, Color.GREEN, Color.GREEN);
        for (int i = 0; i <= 50; i++) {
            assertEquals(Color.GREEN, palette.getColor(i));
        }
    }

    @Test
    void testGradientIsMonotonic() {
        IPalette palette = new DegradePalette(100, Color.BLACK, Color.WHITE);
        int previous = palette.getColor(0).getRed();
        for (int i = 1; i <= 100; i++) {
            int current = palette.getColor(i).getRed();
            assertTrue(current >= previous, "The gradient must not go backwards at iteration " + i);
            previous = current;
        }
    }

    @Test
    void testGradientIsNotConstantBetweenBounds() {
        IPalette palette = new DegradePalette(100, Color.BLACK, Color.WHITE);
        assertTrue(palette.getColor(50).getRed() > palette.getColor(0).getRed());
        assertTrue(palette.getColor(50).getRed() < palette.getColor(100).getRed());
    }

    @Test
    void testAllIterationsGiveAValidColor() {
        IPalette palette = new DegradePalette(250, Color.RED, Color.BLUE);
        for (int i = 0; i <= 250; i++) {
            int nbIterations = i;
            assertDoesNotThrow(() -> palette.getColor(nbIterations));
        }
    }

    @Test
    void testMaxIterationsOfOne() {
        IPalette palette = new DegradePalette(1, Color.BLACK, Color.WHITE);
        assertEquals(Color.BLACK, palette.getColor(0));
        assertEquals(Color.WHITE, palette.getColor(1));
    }

    @Test
    void testZeroMaxIterationsIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new DegradePalette(0, Color.RED, Color.BLUE));
    }

    @Test
    void testNegativeMaxIterationsIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new DegradePalette(-10, Color.RED, Color.BLUE));
    }
}