package fr.univartois.butinfo.fractalens.complex.sequence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;

/**
 * The ComplexSequenceTest tests the implementation of {@link IComplexSequence} for
 * Julia's sequence.
 *
 * @author Thibault BREGUET
 *
 * @version 1.0
 */
public class ComplexSequenceGeneralJuliaTest {
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
     * Creates a new sequence implementing Julia's sequence.
     *
     * @param first The first term of the sequence.
     * @param constant The constant number in the definition of the considered Julia's
     *        sequence.
     * @param nbTerms The maximum number of terms to compute.
     * @param p The constant power number in the definition of the considered Julia's sequence.
     *
     * @return The created Julia's sequence.
     */
    private static IComplexSequence createGeneralJuliaSequence(
            IComplex first, IComplex constant, int nbTerms, int p) {
        // Julia's sequence.
        return new ComplexSequenceGeneralJulia(first, constant, nbTerms, p);
    }
    
    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence#computeNext(fr.univartois.butinfo.fractalens.complex.IComplex)}.
     */
    @Test
    void testComputeNext() {
        IComplex z0 = createComplex(0.5, 0);
        IComplex c = createComplex(0, 0);
        int power = 2;
        
        IComplexSequence julia = createGeneralJuliaSequence(z0, c, 50, power);
        
        		
        IComplex z1 = createComplex(0.25, 0);
        assertEquals(z1, julia.computeNext(z0));

        IComplex z2 = createComplex(0.0625, 0);
        assertEquals(z2, julia.computeNext(z1));

        IComplex z3 = createComplex(0.00390625, 0);
        assertEquals(z3, julia.computeNext(z2));
    }
    
    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence#iterator()}
     * when the sequence converges (tend toward a peculiar number).
     */
    @Test
    void testIteratorOnConvergence() {
        IComplex z0 = createComplex(0.5, 0);
        IComplex c = createComplex(0, 0);
        
        /**
        * The sequence converge if the distance between our number and the
        * point 0,0 on our complex plan stay under 2 for our first 50 terms. 
        */
        int nbTerms = 50;
        int power = 2;

        IComplexSequence julia = createGeneralJuliaSequence(z0, c, nbTerms, power);
        int nbIterations = 0;

        for (var _ : julia) {
            nbIterations++;
        }

        assertEquals(nbTerms, nbIterations);
    }
    
    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence#iterator()}
     * when the sequence diverges (tend toward infinite).
     */
    @Test
    void testIteratorOnDivergence() {
        IComplex z0 = createComplex(2, 0);
        IComplex c = createComplex(0.5, 0.5);
        int nbTerms = 50;
        int power = 2;

        IComplexSequence julia = createGeneralJuliaSequence(z0, c, nbTerms, power);
        int nbIterations = 0;

        for (var _ : julia) {
            nbIterations++;
        }

        /**
         * If our sequence go beyond 2 before reaching term N°50 it has diverge (tend toward infinite)
         */
        assertTrue(nbIterations < nbTerms);
    }
}
