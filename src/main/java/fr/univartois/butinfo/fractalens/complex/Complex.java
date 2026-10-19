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
public class Complex implements IComplex {
	
	private double a,b;
	//private double column, row, height, width; //pas besoin de ça dans cette classe.
	
	public Complex(double a, double b) {
		this.a = a;
		this.b = b;
	}
	
	Complex z = new Complex(a,b);
	IComplex resA = getRealPart();
	IComplex resB = getImaginaryPart();

    /**
     * Gives the real part of this complex number.
     *
     * @return The real part of this complex number.
     */
    public double getRealPart() {
    	return a;
    }

    /**
     * Gives the imaginary part of this complex number.
     *
     * @return The imaginary part of this complex number.
     */
    public double getImaginaryPart() {
    	return b;
    }

    /**
     * Gives the modulus of this complex number.
     *
     * @return The modulus of this complex number.
     */
    public double abs() {
    	//TODO
    }

    /**
     * Gives the opposite of this complex number.
     *
     * @return The opposite of this complex number.
     */
    public IComplex negate() {
    	//TODO
    }

    /**
     * Gives the conjugate of this complex number.
     *
     * @return The conjugate of this complex number.
     */
    public IComplex conjugate() {
    	zbarre = //TODO
    	return zbarre;
    }

    /**
     * Adds this complex number to an other one.
     *
     * @param other The complex number to add to this one.
     *
     * @return The result of {@code this + other}.
     */
    public IComplex add(IComplex other) {
    	IComplex resA = getRealPart()+value;
    	IComplex resB = getImaginaryPart()+value;
    	return resA+resB; //z = a+i*b
    }

    /**
     * Subtracts a complex number from this one.
     *
     * @param other The complex number to subtract from this one.
     *
     * @return The result of {@code this - other}.
     */
    public IComplex subtract(IComplex other) {
    	IComplex resA = getRealPart()-value;
    	IComplex resB = getImaginaryPart()-value;
    	return resA+resB; //z = a+i*b
    }

    /**
     * Multiplies this complex number by a real value.
     *
     * @param value The value by which to multiply this number.
     *
     * @return The result of {@code this * value}.
     */
    public IComplex multiply(double value) {
    	IComplex resA = getRealPart()*value;
    	IComplex resB = getImaginaryPart()*value;
    	return resA+resB; //z = a+i*b
    }

    /**
     * Multiplies this complex number by an other one.
     *
     * @param other The complex number to multiply this one with.
     *
     * @return The result of {@code this * other}.
     */
    public IComplex multiply(IComplex other) {
    	return z*other;
    }

    /**
     * Divides this complex number by an other one.
     *
     * @param other The complex number to divide this one by.
     *
     * @return The result of {@code this / other}.
     */
    public IComplex divide(IComplex other) {
    	return z/other;
    }

}
