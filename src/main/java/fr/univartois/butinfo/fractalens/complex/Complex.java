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

import java.util.Objects;

/**
 * The Complex class is an implementation of {@link IComplex} representing a
 * complex number {@code z = a + ib}, where {@code a} is its real part,
 * {@code b} is its imaginary part, and {@code i} is the imaginary unit such
 * that {@code i² = -1}.
 *
 * Instances of this class are immutable: every operation returns a new
 * complex number, and never modifies the complex numbers it is applied to.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public class Complex implements IComplex {

    /**
     * The real part {@code a} of this complex number.
     */
    private final double realPart; //a

    /**
     * The imaginary part {@code b} of this complex number.
     */
    private final double imaginaryPart; //b

    /**
     * Creates a new Complex representing the number {@code a + ib}.
     *
     * @param realPart The real part {@code a} of the complex number.
     * @param imaginaryPart The imaginary part {@code b} of the complex number.
     */
    public Complex(double realPart, double imaginaryPart) {
        this.realPart = realPart; //a
        this.imaginaryPart = imaginaryPart; //b
    }

    /**
     * Gives the real part of this complex number.
     *
     * @return The real part {@code a} of this complex number {@code a + ib}.
     */
    @Override
    public double getRealPart() {
        return realPart; //a
    }

    /**
     * Gives the imaginary part of this complex number.
     *
     * @return The imaginary part {@code b} of this complex number {@code a + ib}.
     */
    @Override
    public double getImaginaryPart() {
        return imaginaryPart; //b
    }

    /**
     * Gives the modulus of this complex number, i.e., its distance to 0 in the
     * complex plane, computed as {@code |z| = sqrt(a² + b²)}.
     *
     * @return The modulus of this complex number.
     */
    @Override
    public double abs() {
        return Math.sqrt(realPart * realPart + imaginaryPart * imaginaryPart); //i = a² + b²
    }

    /**
     * Gives the opposite of this complex number, computed as
     * {@code -z = -a - ib}.
     *
     * @return The opposite of this complex number.
     */
    @Override
    public IComplex negate() {
        return new Complex(-realPart, -imaginaryPart); //swap the sign everywhere: every "+" turns into "-"
    }

    /**
     * Gives the conjugate of this complex number, computed as
     * {@code a - ib}.
     *
     * @return The conjugate of this complex number.
     */
    @Override
    public IComplex conjugate() {
        return new Complex(realPart, -imaginaryPart); //zbar = a - i*b
    }

    /**
     * Adds this complex number to an other one, using the formula
     * {@code z1 + z2 = (a1 + a2) + i(b1 + b2)}.
     *
     * @param other The complex number to add to this one.
     *
     * @return The result of {@code this + other}.
     */
    @Override
    public IComplex add(IComplex other) {
        return new Complex(
                realPart + other.getRealPart(), //a of z + a of other
                imaginaryPart + other.getImaginaryPart()); //b of z + b of other
    }

    /**
     * Subtracts a complex number from this one, using the formula
     * {@code z1 - z2 = (a1 - a2) + i(b1 - b2)}.
     *
     * @param other The complex number to subtract from this one.
     *
     * @return The result of {@code this - other}.
     */
    @Override
    public IComplex subtract(IComplex other) {
        return new Complex(
                realPart - other.getRealPart(),
                imaginaryPart - other.getImaginaryPart());
    }

    /**
     * Multiplies this complex number by a real value, using the formula
     * {@code r * z = (r * a) + i(r * b)}.
     *
     * @param value The real value by which to multiply this number.
     *
     * @return The result of {@code this * value}.
     */
    @Override
    public IComplex multiply(double value) {
        return new Complex(realPart * value, imaginaryPart * value); //a*value + b*value
    }

    /**
     * Multiplies this complex number by an other one, using the formula
     * {@code z1 * z2 = (a1 * a2 - b1 * b2) + i(a1 * b2 + a2 * b1)}.
     *
     * @param other The complex number to multiply this one with.
     *
     * @return The result of {@code this * other}.
     */
    @Override
    public IComplex multiply(IComplex other) {
        double a2 = other.getRealPart();
        double b2 = other.getImaginaryPart();
        return new Complex(
                realPart * a2 - imaginaryPart * b2,
                realPart * b2 + a2 * imaginaryPart);
    }

    /**
     * Divides this complex number by an other one, using the formula
     * {@code z1 / z2 = ((a1 * a2 + b1 * b2) + i(a2 * b1 - a1 * b2)) / (a2² + b2²)}.
     *
     * If {@code other} is equal to 0, the parts of the result are not finite
     * (they are either infinite or NaN), following the IEEE 754 rules applied
     * to {@code double} values.
     *
     * @param other The complex number to divide this one by.
     *
     * @return The result of {@code this / other}.
     */
    @Override
    public IComplex divide(IComplex other) {
        double a2 = other.getRealPart();
        double b2 = other.getImaginaryPart();
        double denominator = a2 * a2 + b2 * b2;
        return new Complex(
                (realPart * a2 + imaginaryPart * b2) / denominator,
                (a2 * imaginaryPart - realPart * b2) / denominator);
    }

    /**
     * Checks whether this complex number is equal to the given object.
     * Two complex numbers are considered equal when they have the same real
     * part and the same imaginary part.
     *
     * @param obj The object to compare with this complex number.
     *
     * @return {@code true} if {@code obj} is an {@link IComplex} with the same
     *         real and imaginary parts as this one, {@code false} otherwise.
     *
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IComplex)) {
            return false;
        }
        IComplex other = (IComplex) obj;
        return Math.abs(realPart - other.getRealPart()) <= 1e-5
                && Math.abs(imaginaryPart - other.getImaginaryPart()) <= 1e-5;
    }


    /**
     * Computes the power of this complex number to the given power.
     * @param power the power
     * @return the result of the power
     */
    @Override
    public IComplex power(int power) {
        if (power < 0) {
            throw new IllegalArgumentException("power must be >= 0");
        }
        IComplex result = new Complex(1, 0);
        for (int i = 0; i < power; i++) {
            result = result.multiply(this);
        }
        return result;
    }

    /**
     * Computes a hash code for this complex number, consistent with
     * {@link #equals(Object)}.
     *
     * @return The hash code of this complex number.
     *
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(realPart, imaginaryPart);
    }

    /**
     * Gives a string representation of this complex number, in the form
     * {@code a + bi}.
     *
     * @return The string representation of this complex number.
     *
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return realPart + " + " + imaginaryPart + "i";
    }

}
