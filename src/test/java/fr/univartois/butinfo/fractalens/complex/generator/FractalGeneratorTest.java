/*
 * FractaLens - A Fractal Generator Library Developed in Lens.
 *
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

package fr.univartois.butinfo.fractalens.complex.generator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Color;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;

import fr.univartois.butinfo.fractalens.generator.FractalGenerator;
import fr.univartois.butinfo.fractalens.palette.IPalette;
import org.junit.jupiter.api.Test;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.ComplexPlane;
import fr.univartois.butinfo.fractalens.complex.IComplex;
import fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence;
import fr.univartois.butinfo.fractalens.image.IFractalImage;

/**
 * The FractalGeneratorTest tests the implementation of {@link FractalGenerator}.
 *
 * @author Romain Wallon
 * @version 0.1.0
 */
class FractalGeneratorTest {

    /**
     * A stub implementation of IFractalImage for testing purposes without mocking frameworks.
     */
    private static class DummyFractalImage implements IFractalImage {
        private final int height;
        private final int width;
        private boolean saveThrowsError = false;

        public DummyFractalImage(int height, int width) {
            this.height = height;
            this.width = width;
        }

        public void setSaveThrowsError(boolean saveThrowsError) {
            this.saveThrowsError = saveThrowsError;
        }

        @Override
        public int getHeight() {
            return height;
        }

        @Override
        public int getWidth() {
            return width;
        }

        @Override
        public void setColor(int row, int column, Color color) {
            // Nothing to do for the test
        }

        @Override
        public void saveAs(String path) throws IOException {
            if (saveThrowsError) {
                throw new IOException("Simulated I/O error");
            }
        }
    }

    /**
     * A stub implementation of IPalette for testing purposes.
     */
    private static class DummyPalette implements IPalette {
        @Override
        public Color getColor(int index) {
            return new Color(index % 256, 0, 0);
        }
    }

    /**
     * Creates a new complex number.
     *
     * @param realPart      The real part of the complex number.
     * @param imaginaryPart The imaginary part of the complex number.
     * @return The created complex number.
     */
    private static IComplex createComplex(double realPart, double imaginaryPart) {
        return new Complex(realPart, imaginaryPart);
    }

    /**
     * Creates a new fractal generator for testing.
     *
     * @param image      The image on which to draw the fractal.
     * @param plane      The complex plane.
     * @param palette    The palette used to color the fractal.
     * @param outputPath The path of the file in which to save the image.
     * @param function   The function generating the sequence.
     * @param nbTherm    The maximum number of terms.
     * @return The created FractalGenerator.
     */
    private static FractalGenerator createGenerator(
            IFractalImage image, ComplexPlane plane, IPalette palette,
            String outputPath, BiFunction<IComplex, Integer, IComplexSequence> function, int nbTherm) {
        return new FractalGenerator(image, plane, palette, outputPath, function, nbTherm);
    }

    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.generator.FractalGenerator#computePixelColor(int, int)}.
     */
    @Test
    void testComputePixelColor() {
        IFractalImage image = new DummyFractalImage(10, 10);
        ComplexPlane plane = new ComplexPlane(10, 10);
        IPalette palette = new DummyPalette();
        String outputPath = "test.png";
        int nbTherm = 10;

        BiFunction<IComplex, Integer, IComplexSequence> sequenceFunction = (z0, n) -> new IComplexSequence() {
            @Override
            public IComplex computeNext(IComplex previous) {
                return previous;
            }

            @Override
            public Iterator<IComplex> iterator() {
                return List.of(z0, z0).iterator();
            }
        };

        FractalGenerator generator = createGenerator(image, plane, palette, outputPath, sequenceFunction, nbTherm);

        Color color = generator.computePixelColor(0, 0);
        assertNotNull(color);
        assertEquals(palette.getColor(2), color);
    }

    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.generator.FractalGenerator#generateImage()}.
     */
    @Test
    void testGenerateImage() {
        IFractalImage image = new DummyFractalImage(2, 2);
        ComplexPlane plane = new ComplexPlane(2, 2);
        IPalette palette = new DummyPalette();
        String outputPath = "test.png";
        int nbTherm = 5;

        BiFunction<IComplex, Integer, IComplexSequence> sequenceFunction = (z0, n) -> new IComplexSequence() {
            @Override
            public IComplex computeNext(IComplex previous) {
                return previous;
            }

            @Override
            public Iterator<IComplex> iterator() {
                return List.of(z0).iterator();
            }
        };

        FractalGenerator generator = createGenerator(image, plane, palette, outputPath, sequenceFunction, nbTherm);

        IFractalImage result = generator.generateImage();
        assertNotNull(result);
        assertEquals(image, result);
    }

    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.generator.FractalGenerator#saveImage()}.
     */
    @Test
    void testSaveImage() {
        DummyFractalImage image = new DummyFractalImage(2, 2);
        ComplexPlane plane = new ComplexPlane(2, 2);
        IPalette palette = new DummyPalette();
        String outputPath = "test.png";
        int nbTherm = 5;

        BiFunction<IComplex, Integer, IComplexSequence> sequenceFunction = (z0, n) -> null;

        FractalGenerator generator = createGenerator(image, plane, palette, outputPath, sequenceFunction, nbTherm);

        // Should not throw any exception
        generator.saveImage();
    }

    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.generator.FractalGenerator#saveImage()} when an I/O error occurs.
     */
    @Test
    void testSaveImageWithException() {
        DummyFractalImage image = new DummyFractalImage(2, 2);
        image.setSaveThrowsError(true);
        ComplexPlane plane = new ComplexPlane(2, 2);
        IPalette palette = new DummyPalette();
        String outputPath = "test.png";
        int nbTherm = 5;

        BiFunction<IComplex, Integer, IComplexSequence> sequenceFunction = (z0, n) -> null;

        FractalGenerator generator = createGenerator(image, plane, palette, outputPath, sequenceFunction, nbTherm);

        assertThrows(UncheckedIOException.class, () -> generator.saveImage());
    }

}