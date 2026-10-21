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

/**
 * The IComplex interface defines the methods for making operations on complex numbers.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public interface IComplex {

    /**
     * Gives the real part of this complex number.
     *
     * @return The real part of this complex number.
     */
    double getRealPart();

    /**
     * Gives the imaginary part of this complex number.
     *
     * @return The imaginary part of this complex number.
     */
    double getImaginaryPart();

    /**
     * Gives the modulus of this complex number.
     *
     * @return The modulus of this complex number.
     */
    double abs();

    /**
     * Gives the opposite of this complex number.
     *
     * @return The opposite of this complex number.
     */
    IComplex negate();

    /**
     * Gives the conjugate of this complex number.
     *
     * @return The conjugate of this complex number.
     */
    IComplex conjugate();

    /**
     * Adds this complex number to an other one.
     *
     * @param other The complex number to add to this one.
     *
     * @return The result of {@code this + other}.
     */
    IComplex add(IComplex other);

    /**
     * Subtracts a complex number from this one.
     *
     * @param other The complex number to subtract from this one.
     *
     * @return The result of {@code this - other}.
     */
    IComplex subtract(IComplex other);

    /**
     * Multiplies this complex number by a real value.
     *
     * @param value The value by which to multiply this number.
     *
     * @return The result of {@code this * value}.
     */
    IComplex multiply(double value);

    /**
     * Multiplies this complex number by an other one.
     *
     * @param other The complex number to multiply this one with.
     *
     * @return The result of {@code this * other}.
     */
    IComplex multiply(IComplex other);

    /**
     * Divides this complex number by an other one.
     *
     * @param other The complex number to divide this one by.
     *
     * @return The result of {@code this / other}.
     */
    IComplex divide(IComplex other);

    /**
     * Computes the power of this complex number.
     * @param power the power
     * @return the result of the power
     */
    IComplex power(int power);

}
