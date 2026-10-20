package fr.univartois.butinfo.fractalens.palette;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;

import org.junit.jupiter.api.Test;

class GreyscalePaletteTest {

    private final IPalette palette = new GreyscalePalette(100);

    @Test
    void testZeroIterationGivesBlack() {
        assertEquals(new Color(0, 0, 0), palette.getColor(0));
    }

    @Test
    void testMaxIterationsGivesWhite() {
        assertEquals(new Color(255, 255, 255), palette.getColor(100));
    }

    @Test
    void testHalfIterationsGivesMiddleGrey() {
        assertEquals(new Color(128, 128, 128), palette.getColor(50));
    }
}