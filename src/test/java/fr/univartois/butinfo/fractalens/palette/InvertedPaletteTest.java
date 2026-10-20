/*
 * FractaLens - A Fractal Generator Library Developed in Lens.
 * Copyright (c) 2022-2026 Romain Wallon - Université d'Artois.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package fr.univartois.butinfo.fractalens.palette;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;

import org.junit.jupiter.api.Test;

/**
 * The InvertedPaletteTest tests the base implementation of {@link InvertedPalette}.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
class InvertedPaletteTest {

    /**
     * Creates a new palette that always returns the given color,
     * whatever the number of iterations.
     *
     * @param color The color returned by the palette.
     *
     * @return The created palette.
     */
    private static IPalette createConstantPalette(Color color) {
        return nbIterations -> color;
    }

    /**
     * Creates a new inverted palette.
     *
     * @param wrapped The palette to invert.
     *
     * @return The created inverted palette.
     */
    private static IPalette createInvertedPalette(IPalette wrapped) {
        return new InvertedPalette(wrapped);
    }

    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.palette.InvertedPalette#getColor(int)}.
     */
    @Test
    void testGetColorBlack() {
        IPalette palette = createInvertedPalette(createConstantPalette(Color.BLACK));

        assertEquals(Color.WHITE, palette.getColor(10));
    }

    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.palette.InvertedPalette#getColor(int)}.
     */
    @Test
    void testGetColorWhite() {
        IPalette palette = createInvertedPalette(createConstantPalette(Color.WHITE));

        assertEquals(Color.BLACK, palette.getColor(10));
    }

    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.palette.InvertedPalette#getColor(int)}.
     */
    @Test
    void testGetColorPrimaryColors() {
        assertEquals(new Color(0, 255, 255),
                createInvertedPalette(createConstantPalette(Color.RED)).getColor(10));

        assertEquals(new Color(255, 0, 255),
                createInvertedPalette(createConstantPalette(Color.GREEN)).getColor(10));

        assertEquals(new Color(255, 255, 0),
                createInvertedPalette(createConstantPalette(Color.BLUE)).getColor(10));
    }

    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.palette.InvertedPalette#getColor(int)}.
     */
    @Test
    void testGetColorArbitraryColor() {
        IPalette palette = createInvertedPalette(
                createConstantPalette(new Color(12, 100, 200)));

        assertEquals(new Color(243, 155, 55), palette.getColor(10));
    }

    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.palette.InvertedPalette#getColor(int)}.
     */
    @Test
    void testGetColorDoubleInversion() {
        Color original = new Color(12, 100, 200);
        IPalette palette = createInvertedPalette(
                createInvertedPalette(createConstantPalette(original)));

        assertEquals(original, palette.getColor(10));
    }

    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.palette.InvertedPalette#getColor(int)}.
     */
    @Test
    void testGetColorDelegatesIterations() {
        // The wrapped palette returns a gray level depending on the iterations.
        IPalette wrapped = nbIterations -> new Color(nbIterations, nbIterations, nbIterations);
        IPalette palette = createInvertedPalette(wrapped);

        assertEquals(new Color(255, 255, 255), palette.getColor(0));
        assertEquals(new Color(155, 155, 155), palette.getColor(100));
        assertEquals(new Color(0, 0, 0), palette.getColor(255));
    }

}