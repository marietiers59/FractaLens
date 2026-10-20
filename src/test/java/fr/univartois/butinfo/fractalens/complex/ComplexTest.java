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
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

/**
 * The IComplexTest tests the base implementation of {@link IComplex}.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
class ComplexTest {

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
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.complex.IComplex#getRealPart()}.
     */
    @Test
    void testGetRealPart() {
        IComplex zero = createComplex(0, 0);
        assertEquals(0, zero.getRealPart(), 0.001);

        IComplex one = createComplex(1, 0);
        assertEquals(1, one.getRealPart(), 0.001);

        IComplex i = createComplex(0, 1);
        assertEquals(0, i.getRealPart(), 0.001);

        IComplex z = createComplex(2, 2);
        assertEquals(2, z.getRealPart(), 0.001);
    }

    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.complex.IComplex#getImaginaryPart()}.
     */
    @Test
    void testGetImaginaryPart() {
        IComplex zero = createComplex(0, 0);
        assertEquals(0, zero.getImaginaryPart(), 0.001);

        IComplex one = createComplex(1, 0);
        assertEquals(0, one.getImaginaryPart(), 0.001);

        IComplex i = createComplex(0, 1);
        assertEquals(1, i.getImaginaryPart(), 0.001);

        IComplex z = createComplex(2, 2);
        assertEquals(2, z.getImaginaryPart(), 0.001);
    }

    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.complex.IComplex#abs()}.
     */
    @Test
    void testAbs() {
        IComplex zero = createComplex(0, 0);
        assertEquals(0, zero.abs(), 0.001);

        IComplex one = createComplex(1, 0);
        assertEquals(1, one.abs(), 0.001);

        IComplex i = createComplex(0, 1);
        assertEquals(1, i.abs(), 0.001);

        IComplex z = createComplex(2, 2);
        assertEquals(Math.sqrt(8), z.abs(), 0.001);
    }

    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.complex.IComplex#negate()}.
     */
    @Test
    void testNegate() {
        IComplex zero = createComplex(0, 0).negate();
        assertEquals(0, zero.getRealPart(), 0.001);
        assertEquals(0, zero.getImaginaryPart(), 0.001);

        IComplex minusOne = createComplex(1, 0).negate();
        assertEquals(-1, minusOne.getRealPart(), 0.001);
        assertEquals(0, minusOne.getImaginaryPart(), 0.001);

        IComplex minusI = createComplex(0, 1).negate();
        assertEquals(0, minusI.getRealPart(), 0.001);
        assertEquals(-1, minusI.getImaginaryPart(), 0.001);

        IComplex z = createComplex(2, 2).negate();
        assertEquals(-2, z.getRealPart(), 0.001);
        assertEquals(-2, z.getImaginaryPart(), 0.001);
    }

    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.complex.IComplex#conjugate()}.
     */
    @Test
    void testConjugate() {
        IComplex zeroConj = createComplex(0, 0).conjugate();
        assertEquals(0, zeroConj.getRealPart(), 0.001);
        assertEquals(0, zeroConj.getImaginaryPart(), 0.001);

        IComplex oneConj = createComplex(1, 0).conjugate();
        assertEquals(1, oneConj.getRealPart(), 0.001);
        assertEquals(0, oneConj.getImaginaryPart(), 0.001);

        IComplex iConj = createComplex(0, 1).conjugate();
        assertEquals(0, iConj.getRealPart(), 0.001);
        assertEquals(-1, iConj.getImaginaryPart(), 0.001);

        IComplex zConj = createComplex(2, 2).conjugate();
        assertEquals(2, zConj.getRealPart(), 0.001);
        assertEquals(-2, zConj.getImaginaryPart(), 0.001);
    }

    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.complex.IComplex#add(fr.univartois.butinfo.fractalens.complex.IComplex)}.
     */
    @Test
    void testAdd() {
        IComplex zero = createComplex(0, 0);
        IComplex one = createComplex(1, 0);
        IComplex i = createComplex(0, 1);
        IComplex z = createComplex(2, 2);

        IComplex sum = zero.add(one);
        assertEquals(createComplex(1, 0), sum);

        sum = sum.add(i);
        assertEquals(createComplex(1, 1), sum);

        sum = sum.add(z);
        assertEquals(createComplex(3, 3), sum);
    }

    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.complex.IComplex#subtract(fr.univartois.butinfo.fractalens.complex.IComplex)}.
     */
    @Test
    void testSubtract() {
        IComplex zero = createComplex(0, 0);
        IComplex one = createComplex(1, 0);
        IComplex i = createComplex(0, 1);
        IComplex z = createComplex(2, 2);

        IComplex sub = zero.subtract(one);
        assertEquals(createComplex(-1, 0), sub);

        sub = sub.subtract(i);
        assertEquals(createComplex(-1, -1), sub);

        sub = sub.subtract(z);
        assertEquals(createComplex(-3, -3), sub);
    }

    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.complex.IComplex#multiply(double)}.
     */
    @Test
    void testMultiplyDouble() {
        IComplex zero = createComplex(0, 0);
        assertEquals(createComplex(0, 0), zero.multiply(1.5));

        IComplex one = createComplex(1, 0);
        assertEquals(createComplex(1.5, 0), one.multiply(1.5));

        IComplex i = createComplex(0, 1);
        assertEquals(createComplex(0, 1.5), i.multiply(1.5));

        IComplex z = createComplex(2, 2);
        assertEquals(createComplex(3, 3), z.multiply(1.5));
    }

    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.complex.IComplex#multiply(fr.univartois.butinfo.fractalens.complex.IComplex)}.
     */
    @Test
    void testMultiplyIComplex() {
        IComplex zero = createComplex(0, 0);
        IComplex one = createComplex(1, 0);
        IComplex i = createComplex(0, 1);
        IComplex z = createComplex(2, 2);

        IComplex mult = z.multiply(i);
        assertEquals(createComplex(-2, 2), mult);

        mult = mult.multiply(one);
        assertEquals(createComplex(-2, 2), mult);

        mult = mult.multiply(zero);
        assertEquals(createComplex(0, 0), mult);
    }

    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.complex.IComplex#divide(fr.univartois.butinfo.fractalens.complex.IComplex)}.
     */
    @Test
    void testDivide() {
        IComplex zero = createComplex(0, 0);
        IComplex one = createComplex(1, 0);
        IComplex i = createComplex(0, 1);
        IComplex z = createComplex(2, 2);

        assertEquals(zero, zero.divide(one));
        assertEquals(zero, zero.divide(i));
        assertEquals(zero, zero.divide(z));

        assertEquals(createComplex(0, -1), one.divide(i));
        assertEquals(createComplex(0, 1), i.divide(one));

        assertEquals(createComplex(0.25, -0.25), one.divide(z));
        assertEquals(createComplex(2, 2), z.divide(one));

        assertEquals(createComplex(0.25, 0.25), i.divide(z));
        assertEquals(createComplex(2, -2), z.divide(i));
    }

    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.complex.IComplex#hashCode()}.
     */
    @Test
    void testHashCode() {
        IComplex zero1 = createComplex(0, 0);
        IComplex zero2 = createComplex(0, 0);
        assertEquals(zero1.hashCode(), zero2.hashCode());

        IComplex one1 = createComplex(1, 0);
        IComplex one2 = createComplex(1, 0);
        assertEquals(one1.hashCode(), one2.hashCode());

        IComplex i1 = createComplex(0, 1);
        IComplex i2 = createComplex(0, 1);
        assertEquals(i1.hashCode(), i2.hashCode());

        IComplex z1 = createComplex(2, 2);
        IComplex z2 = createComplex(2, 2);
        assertEquals(z1.hashCode(), z2.hashCode());
    }

    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.complex.IComplex#equals(java.lang.Object)}.
     */
    @Test
    void testEqualsObject() {
        IComplex zero1 = createComplex(0, 0);
        IComplex zero2 = createComplex(0, 0);
        assertEquals(zero1, zero2);

        IComplex one1 = createComplex(1, 0);
        IComplex one2 = createComplex(1, 0);
        assertEquals(one1, one2);

        IComplex i1 = createComplex(0, 1);
        IComplex i2 = createComplex(0, 1);
        assertEquals(i1, i2);

        IComplex z1 = createComplex(2, 2);
        IComplex z2 = createComplex(2, 2);
        assertEquals(z1, z2);

        assertNotEquals(zero1, one1);
        assertNotEquals(zero1, i1);
        assertNotEquals(zero1, z1);

        assertNotEquals(one2, i2);
        assertNotEquals(one2, z2);

        assertNotEquals(i1, z2);
    }

    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.complex.IComplex#toString()}.
     */
    @Test
    void testToString() {
        IComplex zero = createComplex(0, 0);
        assertEquals("0.0 + 0.0i", zero.toString());

        IComplex one = createComplex(1, 0);
        assertEquals("1.0 + 0.0i", one.toString());

        IComplex i = createComplex(0, 1);
        assertEquals("0.0 + 1.0i", i.toString());

        IComplex z = createComplex(2, 2);
        assertEquals("2.0 + 2.0i", z.toString());
    }

}
