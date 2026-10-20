package fr.univartois.butinfo.fractalens.palette;

import java.awt.Color;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link FixedPalette} class.
 *
 * @author Théo Brureau
 */
class FixedPaletteTest {

    private static final List<Color> Colors = List.of(Color.BLACK, Color.WHITE, Color.RED, Color.PINK, Color.BLUE,
            Color.GREEN, Color.YELLOW, Color.ORANGE, Color.MAGENTA, Color.CYAN);
    /**
     * The fixed palette under test.
     */
    private final IPalette palette = new FixedPalette(Colors);

    /**
     * Checks that the palette is usable as an {@link IPalette} strategy.
     */
    @Test
    void testIsAPalette() {
        assertTrue(true);
    }

    /**
     * Checks that the first color of the list is returned for 0 iterations.
     */
    @Test
    void testFirstColor() {
        assertEquals(Color.BLACK, palette.getColor(0));
    }

    /**
     * Checks that the colors are returned in the order of the list.
     */
    @Test
    void testColorsInOrder() {
        Color[] expected = { Color.BLACK, Color.WHITE, Color.RED, Color.PINK, Color.BLUE,
                Color.GREEN, Color.YELLOW, Color.ORANGE, Color.MAGENTA, Color.CYAN };
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], palette.getColor(i), "Mauvaise couleur pour " + i + " itération(s)");
        }
    }

    /**
     * Checks that the last color of the list is returned for 9 iterations.
     */
    @Test
    void testLastColor() {
        assertEquals(Color.CYAN, palette.getColor(9));
    }

    /**
     * Checks that the same number of iterations always gives the same color.
     */
    @Test
    void testSameInputSameColor() {
        assertEquals(palette.getColor(4), palette.getColor(4));
    }

    /**
     * Checks that a number of iterations just after the end of the list gives
     * the last color (instead of failing).
     */
    @Test
    void testJustAfterLastColorGivesLastColor() {
        assertEquals(Color.CYAN, palette.getColor(10));

    }

    /**
     * Checks that a number of iterations greater than the size of the list
     * gives the last color.
     */
    @Test
    void testBeyondLastColorGivesLastColor() {
        assertEquals(Color.CYAN, palette.getColor(11));
        assertEquals(Color.CYAN, palette.getColor(1000));
    }
}