package fr.univartois.butinfo.fractalens.palette;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link CustomPalette} class.
 */
class CustomPaletteTest {

    /**
     * The custom palette under test, with one color per number of iterations.
     */
    private final IPalette palette = new CustomPalette(List.of(Color.RED, Color.GREEN, Color.BLUE));

    /**
     * Checks that each number of iterations gives the color at the same
     * index in the list.
     */
    @Test
    void testGetColorReturnsColorAtIndex() {
        assertEquals(Color.RED, palette.getColor(0));
        assertEquals(Color.GREEN, palette.getColor(1));
        assertEquals(Color.BLUE, palette.getColor(2));
    }

    /**
     * Checks that a number of iterations beyond the end of the list throws
     * an exception.
     */
    @Test
    void testGetColorBeyondListThrowsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> palette.getColor(3));
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
        IPalette independentPalette = new CustomPalette(colors);

        colors.set(0, Color.BLACK);

        assertEquals(Color.RED, independentPalette.getColor(0));
    }
}