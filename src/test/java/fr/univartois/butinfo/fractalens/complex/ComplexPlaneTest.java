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

package fr.univartois.butinfo.fractalens.complex;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * The ComplexPlaneTest tests the base implementation of {@link IComplexPlane}.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
class ComplexPlaneTest {

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
     * Creates a new complex plane.
     *
     * @param height The height of the complex plane.
     * @param width The width of the complex plane.
     *
     * @return The created complex plane.
     */
    private static IComplexPlane createPlane(int height, int width) {
        return new ComplexPlane(height, width);
    }

    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.complex.IComplexPlane#asComplex(int, int)}.
     */
    @Test
    void testAsComplex() {
        IComplexPlane plane = createPlane(600, 800);

        IComplex topLeft = createComplex(-400, 300);
        assertEquals(topLeft, plane.asComplex(0, 0));

        IComplex top = createComplex(0, 300);
        assertEquals(top, plane.asComplex(0, 400));

        IComplex topRight = createComplex(399, 300);
        assertEquals(topRight, plane.asComplex(0, 799));

        IComplex right = createComplex(-400, 0);
        assertEquals(right, plane.asComplex(300, 0));

        IComplex zero = createComplex(0, 0);
        assertEquals(zero, plane.asComplex(300, 400));

        IComplex left = createComplex(399, 0);
        assertEquals(left, plane.asComplex(300, 799));

        IComplex bottomLeft = createComplex(-400, -299);
        assertEquals(bottomLeft, plane.asComplex(599, 0));

        IComplex bottom = createComplex(0, -299);
        assertEquals(bottom, plane.asComplex(599, 400));

        IComplex bottomRight = createComplex(399, -299);
        assertEquals(bottomRight, plane.asComplex(599, 799));

    }

}
