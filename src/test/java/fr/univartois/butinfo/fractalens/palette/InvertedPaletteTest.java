package fr.univartois.butinfo.fractalens.palette;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

/**
 * The InvertedPaletteTest class contains the unit tests of {@link InvertedPalette}.
 */
class InvertedPaletteTest {

    /**
     * A number of iterations to use when its value does not matter for the test.
     */
    private static final int ANY_ITERATIONS = 10;

    @Test
    void getColorInvertsBlackIntoWhite() {
        IPalette palette = new InvertedPalette(nbIterations -> Color.BLACK);

        assertEquals(Color.WHITE, palette.getColor(ANY_ITERATIONS));
    }

    @Test
    void getColorInvertsWhiteIntoBlack() {
        IPalette palette = new InvertedPalette(nbIterations -> Color.WHITE);

        assertEquals(Color.BLACK, palette.getColor(ANY_ITERATIONS));
    }

    @Test
    void getColorInvertsAnArbitraryColor() {
        IPalette palette = new InvertedPalette(nbIterations -> new Color(10, 100, 200));

        assertEquals(new Color(245, 155, 55), palette.getColor(ANY_ITERATIONS));
    }

    @Test
    void getColorInvertsEachChannelIndependently() {
        assertEquals(new Color(0, 255, 255),
                new InvertedPalette(nbIterations -> Color.RED).getColor(ANY_ITERATIONS));
        assertEquals(new Color(255, 0, 255),
                new InvertedPalette(nbIterations -> Color.GREEN).getColor(ANY_ITERATIONS));
        assertEquals(new Color(255, 255, 0),
                new InvertedPalette(nbIterations -> Color.BLUE).getColor(ANY_ITERATIONS));
    }

    @Test
    void getColorForwardsTheNumberOfIterationsToTheWrappedPalette() {
        AtomicInteger received = new AtomicInteger(-1);
        IPalette wrapped = nbIterations -> {
            received.set(nbIterations);
            return Color.BLACK;
        };

        new InvertedPalette(wrapped).getColor(42);

        assertEquals(42, received.get());
    }

    @Test
    void getColorFollowsTheColorsOfTheWrappedPalette() {
        IPalette wrapped = nbIterations -> new Color(nbIterations, nbIterations, nbIterations);
        IPalette palette = new InvertedPalette(wrapped);

        assertEquals(Color.WHITE, palette.getColor(0));
        assertEquals(new Color(205, 205, 205), palette.getColor(50));
        assertEquals(Color.BLACK, palette.getColor(255));
    }

    @Test
    void getColorOfADoubleInversionGivesTheOriginalColor() {
        Color original = new Color(12, 34, 56);
        IPalette wrapped = nbIterations -> original;
        IPalette palette = new InvertedPalette(new InvertedPalette(wrapped));

        assertEquals(original, palette.getColor(ANY_ITERATIONS));
    }

}