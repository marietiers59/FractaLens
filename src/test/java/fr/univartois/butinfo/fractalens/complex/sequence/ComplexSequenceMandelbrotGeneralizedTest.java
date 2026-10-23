package fr.univartois.butinfo.fractalens.complex.sequence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fr.univartois.butinfo.fractalens.complex.Complex;
import org.junit.jupiter.api.Test;

import fr.univartois.butinfo.fractalens.complex.IComplex;

/**
 * The ComplexSequenceMandelbrotGeneralizedTest tests the implementation of
 * {@link IComplexSequence} for the generalized Mandelbrot's sequence.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
<<<<<<<< HEAD:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/ComplexSequenceMandelbrotGeneralizedTest.java
class ComplexSequenceMandelbrotGeneralizedTest {
========
class ComplexSequenceGeneralizedMandelbrotTest {
>>>>>>>> 739edabb804610c40a6455d842ca099d11a2b566:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/ComplexSequenceGeneralizedMandelbrotTest.java

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
     * Creates a new sequence implementing the generalized Mandelbrot's sequence.
     *
     * @param first The first term of the sequence (which is also the constant number
     *        in the definition of the considered Mandelbrot's sequence).
     * @param power The power of the sequence.
     * @param nbTerms The maximum number of terms to compute.
     *
     * @return The created generalized Mandelbrot's sequence.
     */
    private static IComplexSequence createMandelbrotSequence(
            IComplex first, int power, int nbTerms) {
        // Generalized Mandelbrot's sequence.
<<<<<<<< HEAD:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/ComplexSequenceMandelbrotGeneralizedTest.java
        return new ComplexSequenceMandelbrotGeneralized(first, power, nbTerms);
========
        return new ComplexSequenceGeneralizedMandelbrot(first, power, nbTerms);
>>>>>>>> 739edabb804610c40a6455d842ca099d11a2b566:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/ComplexSequenceGeneralizedMandelbrotTest.java
    }

    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence#computeNext(fr.univartois.butinfo.fractalens.complex.IComplex)}.
     */
    @Test
    void testComputeNext() {
        IComplex z0 = createComplex(1, 0);
        IComplexSequence mandelbrot = createMandelbrotSequence(z0, 3, 50);

        IComplex z1 = createComplex(2, 0);
        assertEquals(z1, mandelbrot.computeNext(z0));

        IComplex z2 = createComplex(9, 0);
        assertEquals(z2, mandelbrot.computeNext(z1));

        IComplex z3 = createComplex(730, 0);
        assertEquals(z3, mandelbrot.computeNext(z2));
    }

    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence#computeNext(fr.univartois.butinfo.fractalens.complex.IComplex)}
     * when the power is 2 (which gives the classical Mandelbrot's sequence).
     */
    @Test
    void testComputeNextWithPowerTwo() {
        IComplex z0 = createComplex(1, 0);
        IComplexSequence mandelbrot = createMandelbrotSequence(z0, 2, 50);

        assertEquals(createComplex(2, 0), mandelbrot.computeNext(z0));
        assertEquals(createComplex(5, 0), mandelbrot.computeNext(createComplex(2, 0)));
        assertEquals(createComplex(26, 0), mandelbrot.computeNext(createComplex(5, 0)));
    }

    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence#iterator()}
     * when the sequence converges.
     */
    @Test
    void testIteratorOnConvergence() {
        // With z0 = 0.3 and p = 3, the sequence stabilizes around 0.336 (it stays bounded).
        IComplex z0 = createComplex(0.3, 0);
        int nbTerms = 50;

        IComplexSequence mandelbrot = createMandelbrotSequence(z0, 3, nbTerms);
        int nbIterations = 0;

        for (var _ : mandelbrot) {
            nbIterations++;
        }

        assertEquals(nbTerms, nbIterations);
    }

    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence#iterator()}
     * when the sequence diverges.
     */
    @Test
    void testIteratorOnDivergence() {
        // With z0 = 1 + i and p = 3, z1 = -1 + 3i, whose modulus exceeds 2.
        IComplex z0 = createComplex(1, 1);
        int nbTerms = 50;

        IComplexSequence mandelbrot = createMandelbrotSequence(z0, 3, nbTerms);
        int nbIterations = 0;

        for (var _ : mandelbrot) {
            nbIterations++;
        }

        assertTrue(nbIterations < nbTerms);
    }

}