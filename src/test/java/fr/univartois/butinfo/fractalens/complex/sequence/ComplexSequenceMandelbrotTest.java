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
     * @param constant The constant number in the definition of the considered
     *        Mandelbrot's sequence.
     * @param nbTerms The maximum number of terms to compute.
     * @param first The first term of the sequence.
     *
     * @return The created Mandelbrot's sequence.
     */
    private static IComplexSequence createMandelbrotSequence(
            IComplex constant, int nbTerms, IComplex first) {
        // Mandelbrot's sequence.
        return new ComplexSequenceMandelbrot(constant, nbTerms, first);
    }

    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence#computeNext(fr.univartois.butinfo.fractalens.complex.IComplex)}.
     */
    @Test
    void testComputeNext() {
        IComplex z0 = createComplex(0, 0);
        IComplex c = createComplex(1, 0);
        IComplexSequence mandelbrot = createMandelbrotSequence(c, 50, z0);

        IComplex z1 = createComplex(1, 0);
        assertEquals(z1, mandelbrot.computeNext(z0));

        IComplex z2 = createComplex(2, 0);
        assertEquals(z2, mandelbrot.computeNext(z1));

        IComplex z3 = createComplex(5, 0);
        assertEquals(z3, mandelbrot.computeNext(z2));

        IComplex z4 = createComplex(26, 0);
        assertEquals(z4, mandelbrot.computeNext(z3));
    }

    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence#iterator()}
     * when the sequence converges.
     */
    @Test
    void testIteratorOnConvergence() {
        IComplex z0 = createComplex(0, 0);
        IComplex c = createComplex(-1, 0);
        int nbTerms = 50;

        // With c = -1, the sequence alternates between 0 and -1 (it stays bounded).
        IComplexSequence mandelbrot = createMandelbrotSequence(c, nbTerms, z0);
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
        IComplex z0 = createComplex(0, 0);
        IComplex c = createComplex(1, 1);
        int nbTerms = 50;

        // With c = 1 + i, the modulus exceeds 2 after a few terms.
        IComplexSequence mandelbrot = createMandelbrotSequence(c, nbTerms, z0);
        int nbIterations = 0;

        for (var _ : mandelbrot) {
            nbIterations++;
        }

        assertTrue(nbIterations < nbTerms);
    }

}