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

package fr.univartois.butinfo.fractalens.complex.sequence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;

/**
 * The AllSequencesTest tests the {@link AllSequences} enumeration.
 *
 * @author Thibault BREGUET
 *
 * @version 0.1.0
 */
class AllSequencesTest {

    /**
     * The tolerance used when comparing the parts of complex numbers.
     */
    private static final double DELTA = 1e-9;

    /**
     * Creates a new complex number.
     *
     * @param realPart The real part of the complex number.
     * @param imaginaryPart The imaginary part of the complex number.
     *
     * @return The created complex number.
     */
    private static IComplex createComplex(double realPart, double imaginaryPart) {
        return new Complex(realPart, imaginaryPart);
    }

    /**
     * Creates the sequence associated with a constant of the enumeration.
     *
     * @param type The constant of the enumeration.
     * @param z0 The complex number associated with the pixel.
     *
     * @return The created sequence.
     */
    private static IComplexSequence createSequence(AllSequences type, IComplex z0) {
        return type.getSequenceFactory().apply(z0, 10);
    }

    /**
     * Checks that two complex numbers are equal, up to a small tolerance.
     *
     * @param expected The expected complex number.
     * @param actual The actual complex number.
     */
    private static void assertComplexEquals(IComplex expected, IComplex actual) {
        assertEquals(expected.getRealPart(), actual.getRealPart(), DELTA);
        assertEquals(expected.getImaginaryPart(), actual.getImaginaryPart(), DELTA);
    }

    /**
     * Test method for {@link AllSequences#getSequenceFactory()}.
     */
    @Test
    void testGetSequenceFactory() {
        for (AllSequences type : AllSequences.values()) {
            assertNotNull(type.getSequenceFactory());
        }
    }

    /**
     * Test method for the creation of a sequence from each constant.
     */
    @Test
    void testCreateSequence() {
        for (AllSequences type : AllSequences.values()) {
            IComplexSequence sequence = createSequence(type, createComplex(1, 1));
            assertNotNull(sequence);
            assertNotNull(sequence.iterator());
        }
    }

    /**
     * Test method for the Julia's sequence: {@code z² + (-0.4 + 0.6i)}.
     */
    @Test
    void testJulia() {
        IComplexSequence sequence = createSequence(AllSequences.JULIA, createComplex(1, 1));
        assertComplexEquals(createComplex(0.6, 0.6), sequence.computeNext(createComplex(1, 0)));
    }

    /**
     * Test method for the generalized Julia's sequence: {@code z³ + (0.285 + 0.01i)}.
     */
    @Test
    void testJuliaGeneralized() {
        IComplexSequence sequence = createSequence(AllSequences.JULIA_GENERALIZED,
                createComplex(1, 1));
        assertComplexEquals(createComplex(1.285, 0.01),
                sequence.computeNext(createComplex(1, 0)));
    }

    /**
     * Test method for the Mandelbrot's sequence: {@code z² + z0}.
     */
    @Test
    void testMandelbrot() {
        IComplexSequence sequence = createSequence(AllSequences.MANDELBROT,
                createComplex(1, 1));
        assertComplexEquals(createComplex(1, 3), sequence.computeNext(createComplex(1, 1)));
    }

    /**
     * Test method for the generalized Mandelbrot's sequence: {@code z³ + z0}.
     */
    @Test
    void testMandelbrotGeneralized() {
        IComplexSequence sequence = createSequence(AllSequences.MANDELBROT_GENERALIZED,
                createComplex(1, 1));
        assertComplexEquals(createComplex(-1, 3), sequence.computeNext(createComplex(1, 1)));
    }


    /**
     * Test method for the generalized Burning Ship's sequence, with a power of 3.
     */
    @Test
    void testBurningShipGeneralized() {
        IComplexSequence sequence = createSequence(AllSequences.BURNING_SHIP_GENERALIZED,
                createComplex(1, 1));
        assertComplexEquals(createComplex(-1, 3), sequence.computeNext(createComplex(-1, 1)));
    }

    /**
     * Test method for the Tricorn's sequence: {@code conj(z)² + (-0.4 + 0.6i)}.
     */
    @Test
    void testTricorn() {
        IComplexSequence sequence = createSequence(AllSequences.TRICORN, createComplex(1, 1));
        assertComplexEquals(createComplex(-0.4, -1.4),
                sequence.computeNext(createComplex(1, 1)));
    }

    /**
     * Test method for the generalized Tricorn's sequence: {@code conj(z)³ + (0.285 + 0.01i)}.
     */
    @Test
    void testTricornGeneralized() {
        IComplexSequence sequence = createSequence(AllSequences.TRICORN_GENERALIZED,
                createComplex(1, 1));
        assertComplexEquals(createComplex(1.285, 0.01),
                sequence.computeNext(createComplex(1, 0)));
    }

}