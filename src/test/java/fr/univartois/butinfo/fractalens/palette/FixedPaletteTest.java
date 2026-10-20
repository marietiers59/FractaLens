package fr.univartois.butinfo.fractalens.palette;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link FixedPalette} class.
 *
 * @author Théo Brureau
 */
class FixedPaletteTest {

    /**
     * The colors of the palette under test, one per number of iterations.
     */
    private static final List<Color> COLORS = List.of(Color.BLACK, Color.WHITE, Color.RED, Color.PINK,
            Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.MAGENTA, Color.CYAN);

    /**
     * The fixed palette under test.
     */
    private final IPalette palette = new FixedPalette(COLORS);

    /**
     * Checks that the palette is usable as an {@link IPalette} strategy.
     */
    @Test
    void testIsAPalette() {
        assertInstanceOf(IPalette.class, palette);
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
        for (int i = 0; i < COLORS.size(); i++) {
            assertEquals(COLORS.get(i), palette.getColor(i), "Mauvaise couleur pour " + i + " itération(s)");
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

    /**
     * Checks that a negative number of iterations throws an exception.
     */
    @Test
    void testGetColorNegativeThrowsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> palette.getColor(-1));
    }

    /**
     * Checks that modifying the original list after creating the palette
     * does not change the colors of the palette.
     */
    @Test
    void testPaletteIsIndependentOfOriginalList() {
        List<Color> colors = new ArrayList<>(List.of(Color.RED, Color.GREEN));
        IPalette independentPalette = new FixedPalette(colors);

        colors.set(0, Color.BLACK);

        assertEquals(Color.RED, independentPalette.getColor(0));
    }
}
