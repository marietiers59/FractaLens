package fr.univartois.butinfo.fractalens.generator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.function.BiFunction;

import javax.imageio.ImageIO;

import fr.univartois.butinfo.fractalens.complex.sequence.AllSequences;
import fr.univartois.butinfo.fractalens.palette.AllPalettes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;
import fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence;
import fr.univartois.butinfo.fractalens.palette.IPalette;

/**
 * Unit tests for {@link FractalGeneratorBuilder} (sprint 5).
 *
 * <p>Pixels are checked through {@link IFractalGenerator#computePixelColor(int, int)}, and the
 * size of the image through {@link IFractalGenerator#generateImage()}.
 */
class FractalGeneratorBuilderTest {

    private static final int WIDTH = 20;
    private static final int HEIGHT = 10;
    private static final int MAX_ITERATIONS = 50;

    /** Black if the sequence (almost) never diverged, white otherwise. */
    private static final IPalette BLACK_OR_WHITE =
            iterations -> iterations > MAX_ITERATIONS / 2 ? Color.BLACK : Color.WHITE;

    @TempDir
    Path tempDir;

    // ------------------------------------------------------------------ helpers

    private FractalGeneratorBuilder baseBuilder() {
        return FractalGeneratorBuilder.newInstance()
                .withSequence(AllSequences.MANDELBROT.getSequenceFactory())
                .withSize(WIDTH, HEIGHT)
                .withScale(0.001)
                .withCenter(new Complex(0, 0))
                .withMaxIterations(MAX_ITERATIONS)
                .withPalette(BLACK_OR_WHITE)
                .withOutputPath(tempDir.resolve("out.png").toString());
    }

    private static Color centerColor(IFractalGenerator generator) {
        return generator.computePixelColor(HEIGHT / 2, WIDTH / 2);
    }

    private static void assertSamePixels(IFractalGenerator expected, IFractalGenerator actual,
                                         int width, int height) {
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                assertEquals(expected.computePixelColor(row, column),
                        actual.computePixelColor(row, column),
                        "Different pixel at (" + row + ", " + column + ")");
            }
        }
    }

    // ------------------------------------------------------------ fluent interface

    @Test
    void newInstanceCreatesDistinctBuilders() {
        assertNotSame(FractalGeneratorBuilder.newInstance(), FractalGeneratorBuilder.newInstance());
    }

    @Test
    void everySetterReturnsTheBuilderItself() {
        FractalGeneratorBuilder builder = FractalGeneratorBuilder.newInstance();

        assertSame(builder, builder.withSize(WIDTH, HEIGHT));
        assertSame(builder, builder.withScale(0.001));
        assertSame(builder, builder.withCenter(new Complex(0, 0)));
        assertSame(builder, builder.withMaxIterations(MAX_ITERATIONS));
        assertSame(builder, builder.withPalette(AllPalettes.values()[0]));
        assertSame(builder, builder.withPalette(BLACK_OR_WHITE));
        assertSame(builder, builder.withSequence(AllSequences.values()[0]));
        assertSame(builder, builder.withSequence(AllSequences.MANDELBROT.getSequenceFactory()));
        assertSame(builder, builder.withOutputPath("out.png"));
    }

    // ---------------------------------------------------------------- defaults

    @Test
    void buildWithoutAnyConfigurationReturnsAGenerator() {
        assertNotNull(FractalGeneratorBuilder.newInstance().build());
    }

    @Test
    void buildCanBeCalledSeveralTimes() {
        FractalGeneratorBuilder builder = baseBuilder();

        IFractalGenerator first = builder.build();
        IFractalGenerator second = builder.build();

        assertNotSame(first, second);
        assertSamePixels(first, second, WIDTH, HEIGHT);
    }

    // ------------------------------------------------------------------- size

    @Test
    void imageHasTheConfiguredSize() {
        IFractalImageSize size = sizeOf(baseBuilder());

        assertEquals(WIDTH, size.width());
        assertEquals(HEIGHT, size.height());
    }

    @Test
    void lastSizeWins() {
        IFractalImageSize size = sizeOf(baseBuilder().withSize(8, 6).withSize(30, 12));

        assertEquals(30, size.width());
        assertEquals(12, size.height());
    }

    private record IFractalImageSize(int width, int height) { }

    private static IFractalImageSize sizeOf(FractalGeneratorBuilder builder) {
        var image = builder.build().generateImage();
        return new IFractalImageSize(image.getWidth(), image.getHeight());
    }


    @Test
    void changingIterationsAfterwardsOverridesThePreviousValue() {
        IFractalGenerator expected = baseBuilder()
                .withPalette(AllPalettes.GREYSCALE).withScale(0.5).withMaxIterations(10).build();
        IFractalGenerator actual = baseBuilder()
                .withPalette(AllPalettes.GREYSCALE).withScale(0.5)
                .withMaxIterations(200).withMaxIterations(10).build();

        assertSamePixels(expected, actual, WIDTH, HEIGHT);
    }



    // ----------------------------------------------------------- center and scale

    @Test
    void centerPixelIsTheConfiguredCenter() {
        // 0 belongs to the MANDELBROT set -> black
        IFractalGenerator inside = baseBuilder().withCenter(new Complex(0, 0)).build();
        // 10 + 10i diverges immediately -> white
        IFractalGenerator outside = baseBuilder().withCenter(new Complex(10, 10)).build();

        assertEquals(Color.BLACK, centerColor(inside));
        assertEquals(Color.WHITE, centerColor(outside));
    }

    @Test
    void translationDoesNotDependOnTheScale() {
        // The center must stay the same whatever the zoom (zoom first, then translation).
        IFractalGenerator zoomed = baseBuilder().withCenter(new Complex(10, 10)).withScale(0.001).build();
        IFractalGenerator wide = baseBuilder().withCenter(new Complex(10, 10)).withScale(0.1).build();

        assertEquals(Color.WHITE, centerColor(zoomed));
        assertEquals(Color.WHITE, centerColor(wide));
    }

    @Test
    void scaleControlsTheVisiblePortionOfThePlane() {
        // Tiny scale: the corner pixel stays close to 0 -> inside the set
        IFractalGenerator zoomed = baseBuilder().withScale(0.001).build();
        // Scale 1: the corner pixel is far from 0 -> diverges
        IFractalGenerator wide = baseBuilder().withScale(1).build();

        assertEquals(Color.BLACK, zoomed.computePixelColor(0, 0));
        assertEquals(Color.WHITE, wide.computePixelColor(0, 0));
    }

    // ----------------------------------------------------------------- sequence

    @ParameterizedTest
    @EnumSource(AllSequences.class)
    void everySequenceOfTheEnumCanBeUsed(AllSequences type) {
        IFractalGenerator generator = FractalGeneratorBuilder.newInstance()
                .withSequence(type).withSize(4, 3).withMaxIterations(20).build();

        var image = generator.generateImage();

        assertEquals(4, image.getWidth());
        assertEquals(3, image.getHeight());
    }

    @Test
    void enumSequenceAndRawSequenceGiveTheSameImage() {
        BiFunction<IComplex, Integer, IComplexSequence> raw =
                (z0, n) -> AllSequences.MANDELBROT.getSequenceFactory().apply(z0, n);

        IFractalGenerator fromEnum = baseBuilder().withScale(0.05)
                .withSequence(AllSequences.MANDELBROT).build();
        IFractalGenerator fromRaw = baseBuilder().withScale(0.05).withSequence(raw).build();

        assertSamePixels(fromEnum, fromRaw, WIDTH, HEIGHT);
    }

    @Test
    void rawSequenceIsUsedByTheGenerator() {
        // Whatever the pixel, the sequence is studied from 10 + 10i: it always diverges.
        BiFunction<IComplex, Integer, IComplexSequence> alwaysDiverging =
                (z0, n) -> AllSequences.MANDELBROT.getSequenceFactory().apply(new Complex(10, 10), n);

        IFractalGenerator generator = baseBuilder().withSequence(alwaysDiverging).build();

        assertEquals(Color.WHITE, centerColor(generator));
    }

    @Test
    void lastSequenceWins() {
        BiFunction<IComplex, Integer, IComplexSequence> alwaysDiverging =
                (z0, n) -> AllSequences.MANDELBROT.getSequenceFactory().apply(new Complex(10, 10), n);

        IFractalGenerator generator = baseBuilder()
                .withSequence(alwaysDiverging)
                .withSequence(AllSequences.MANDELBROT)
                .build();

        assertEquals(Color.BLACK, centerColor(generator));
    }

    // ------------------------------------------------------------------ palette

    @ParameterizedTest
    @EnumSource(AllPalettes.class)
    void everyPaletteOfTheEnumCanBeUsed(AllPalettes type) {
        IFractalGenerator generator = FractalGeneratorBuilder.newInstance()
                .withPalette(type).withSize(4, 3).withMaxIterations(20).build();

        var image = generator.generateImage();

        assertEquals(4, image.getWidth());
        assertEquals(3, image.getHeight());
        assertNotNull(generator.computePixelColor(0, 0));
    }

    @Test
    void rawPaletteIsUsedByTheGenerator() {
        IFractalGenerator generator = baseBuilder().withPalette(iterations -> Color.RED).build();

        for (int row = 0; row < HEIGHT; row++) {
            for (int column = 0; column < WIDTH; column++) {
                assertEquals(Color.RED, generator.computePixelColor(row, column));
            }
        }
    }

    @Test
    void lastPaletteWins() {
        IFractalGenerator generator = baseBuilder()
                .withPalette(AllPalettes.values()[0])
                .withPalette(iterations -> Color.RED)
                .build();

        assertEquals(Color.RED, centerColor(generator));
    }

    // -------------------------------------------------------------- output file

    @Test
    void generatedImageIsSavedInTheConfiguredFile() throws IOException {
        File output = tempDir.resolve("fractal.png").toFile();
        IFractalGenerator generator = baseBuilder().withOutputPath(output.getAbsolutePath()).build();

        generator.generateImage();
        generator.saveImage();

        assertTrue(output.exists());
        BufferedImage saved = ImageIO.read(output);
        assertNotNull(saved);
        assertEquals(WIDTH, saved.getWidth());
        assertEquals(HEIGHT, saved.getHeight());
    }

    @Test
    void lastOutputPathWins() {
        File first = tempDir.resolve("first.png").toFile();
        File second = tempDir.resolve("second.png").toFile();
        IFractalGenerator generator = baseBuilder()
                .withOutputPath(first.getAbsolutePath())
                .withOutputPath(second.getAbsolutePath())
                .build();

        generator.generateImage();
        generator.saveImage();

        assertTrue(second.exists());
        assertTrue(!first.exists());
    }

    // --------------------------------------------------------------- validation

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -100})
    void nonPositiveWidthIsRejected(int invalid) {
        FractalGeneratorBuilder builder = FractalGeneratorBuilder.newInstance();

        assertThrows(IllegalArgumentException.class, () -> builder.withSize(invalid, HEIGHT));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -100})
    void nonPositiveHeightIsRejected(int invalid) {
        FractalGeneratorBuilder builder = FractalGeneratorBuilder.newInstance();

        assertThrows(IllegalArgumentException.class, () -> builder.withSize(WIDTH, invalid));
    }

    @Test
    void rejectedSizeLeavesThePreviousSizeUntouched() {
        FractalGeneratorBuilder builder = baseBuilder();

        assertThrows(IllegalArgumentException.class, () -> builder.withSize(50, 0));

        IFractalImageSize size = sizeOf(builder);
        assertEquals(WIDTH, size.width());
        assertEquals(HEIGHT, size.height());
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, -0.001, -1})
    void nonPositiveScaleIsRejected(double invalid) {
        FractalGeneratorBuilder builder = FractalGeneratorBuilder.newInstance();

        assertThrows(IllegalArgumentException.class, () -> builder.withScale(invalid));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -100})
    void nonPositiveIterationsAreRejected(int invalid) {
        FractalGeneratorBuilder builder = FractalGeneratorBuilder.newInstance();

        assertThrows(IllegalArgumentException.class, () -> builder.withMaxIterations(invalid));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    void blankOutputPathIsRejected(String invalid) {
        FractalGeneratorBuilder builder = FractalGeneratorBuilder.newInstance();

        assertThrows(IllegalArgumentException.class, () -> builder.withOutputPath(invalid));
    }

    @Test
    void nullOutputPathIsRejected() {
        FractalGeneratorBuilder builder = FractalGeneratorBuilder.newInstance();

        assertThrows(IllegalArgumentException.class, () -> builder.withOutputPath(null));
    }

    @Test
    void nullArgumentsAreRejected() {
        FractalGeneratorBuilder builder = FractalGeneratorBuilder.newInstance();

        assertThrows(NullPointerException.class, () -> builder.withCenter(null));
        assertThrows(NullPointerException.class, () -> builder.withPalette((AllPalettes) null));
        assertThrows(NullPointerException.class, () -> builder.withPalette((IPalette) null));
        assertThrows(NullPointerException.class, () -> builder.withSequence((BiFunction<IComplex, Integer, IComplexSequence>) null));
        assertThrows(NullPointerException.class,
                () -> builder.withSequence((BiFunction<IComplex, Integer, IComplexSequence>) null));
    }

    // ------------------------------------------------------- readable chained call

    @Test
    void fullChainedConfigurationBuildsAGenerator() {
        IFractalGenerator generator = FractalGeneratorBuilder.newInstance()
                .withSequence(AllSequences.MANDELBROT.getSequenceFactory())
                .withSize(WIDTH, HEIGHT)
                .withScale(0.004)
                .withCenter(new Complex(-0.75, 0))
                .withMaxIterations(250)
                .withPalette(AllPalettes.GREYSCALE)
                .withOutputPath(tempDir.resolve("chain.png").toString())
                .build();

        assertNotNull(generator);
        assertNotNull(generator.generateImage());
    }
}