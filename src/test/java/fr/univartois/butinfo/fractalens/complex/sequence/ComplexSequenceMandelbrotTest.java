package fr.univartois.butinfo.fractalens.complex.sequence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fr.univartois.butinfo.fractalens.complex.Complex;
import org.junit.jupiter.api.Test;

import fr.univartois.butinfo.fractalens.complex.IComplex;

/**
 * The ComplexSequenceMandelbrotTest tests the implementation of {@link IComplexSequence}
 * for Mandelbrot's sequence.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
class ComplexSequenceMandelbrotTest {

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
     * Creates a new sequence implementing Mandelbrot's sequence.
     *
     * @param first The first term of the sequence (which is also the constant number
     *        in the definition of the considered Mandelbrot's sequence).
     * @param nbTerms The maximum number of terms to compute.
     *
     * @return The created Mandelbrot's sequence.
     */
    private static IComplexSequence createMandelbrotSequence(
            IComplex first, int nbTerms) {
        // Mandelbrot's sequence.
        return new ComplexSequenceMandelbrot(first, nbTerms);
    }

    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence#computeNext(fr.univartois.butinfo.fractalens.complex.IComplex)}.
     */
    @Test
    void testComputeNext() {
        IComplex z0 = createComplex(1, 0);
        IComplexSequence mandelbrot = createMandelbrotSequence(z0, 50);

        IComplex z1 = createComplex(2, 0);
        assertEquals(z1, mandelbrot.computeNext(z0));

        IComplex z2 = createComplex(5, 0);
        assertEquals(z2, mandelbrot.computeNext(z1));

        IComplex z3 = createComplex(26, 0);
        assertEquals(z3, mandelbrot.computeNext(z2));
    }

    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence#iterator()}
     * when the sequence converges.
     */
    @Test
    void testIteratorOnConvergence() {
        // With z0 = -1, the sequence alternates between -1 and 0 (it stays bounded).
        IComplex z0 = createComplex(-1, 0);
        int nbTerms = 50;

        IComplexSequence mandelbrot = createMandelbrotSequence(z0, nbTerms);
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
        // With z0 = 1 + i, the modulus exceeds 2 after a few terms.
        IComplex z0 = createComplex(1, 1);
        int nbTerms = 50;

        IComplexSequence mandelbrot = createMandelbrotSequence(z0, nbTerms);
        int nbIterations = 0;

        for (var _ : mandelbrot) {
            nbIterations++;
        }

        assertTrue(nbIterations < nbTerms);
    }

}