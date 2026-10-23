package fr.univartois.butinfo.fractalens.complex.sequence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;

/**
 * The ComplexSequenceTricornGeneralizedTest tests the implementation of
 * {@link IComplexSequence} for the generalized Tricorn's sequence, defined by
 * {@code t(n) = conj(t(n-1))^p + c}.
 *
 * @author Marie Tiers
 */
<<<<<<<< HEAD:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/ComplexSequenceTricornGeneralizedTest.java
class ComplexSequenceTricornGeneralizedTest {
========
class ComplexSequenceGeneralizedTricornTest {
>>>>>>>> 739edabb804610c40a6455d842ca099d11a2b566:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/ComplexSequenceGeneralizedTricornTest.java

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
     * Creates a new generalized Tricorn's sequence.
     *
     * @param first The first term of the sequence.
     * @param constant The constant number in the definition of the sequence.
     * @param nbTerms The maximum number of terms to compute.
     * @param power The power used in the definition of the sequence.
     *
     * @return The created sequence.
     */
    private static IComplexSequence createSequence(
            IComplex first, IComplex constant, int nbTerms, int power) {
<<<<<<<< HEAD:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/ComplexSequenceTricornGeneralizedTest.java
        return new ComplexSequenceTricornGeneralized(constant, nbTerms, first, power);
========
        return new ComplexSequenceGeneralizedTricorn(constant, nbTerms, first, power);
>>>>>>>> 739edabb804610c40a6455d842ca099d11a2b566:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/ComplexSequenceGeneralizedTricornTest.java
    }

    /**
     * Test checking that the generalized Tricorn's sequence is a particular case of
     * {@link ComplexSequenceLambda}, and gives the same terms as a
     * {@link ComplexSequenceLambda} built directly with the Tricorn's lambda expression.
     */
    @Test
    void testIsLambdaSequence() {
        IComplex z0 = createComplex(0.3, -0.2);
        IComplex c = createComplex(-0.4, 0.6);
        IComplexSequence tricorn = createSequence(z0, c, 64, 3);
        IComplexSequence lambda = new ComplexSequenceLambda(
                (first, z) -> z.conjugate().power(3).add(c), z0, 64);

        assertInstanceOf(ComplexSequenceLambda.class, tricorn);
        assertEquals(collect(lambda), collect(tricorn));
    }

    /**
     * Collects all the terms produced by the iterator of a sequence.
     *
     * @param sequence The sequence to iterate over.
     *
     * @return The terms of the sequence, in order.
     */
    private static List<IComplex> collect(IComplexSequence sequence) {
        List<IComplex> terms = new ArrayList<>();
        for (IComplex term : sequence) {
            terms.add(term);
        }
        return terms;
    }

    /**
     * Test method for {@link IComplexSequence#computeNext(IComplex)} with p = 2,
     * which must behave like the classical Tricorn's sequence.
     */
    @Test
    void testComputeNextPowerTwo() {
        IComplex zero = createComplex(0, 0);
        IComplex c = createComplex(0.5, -0.5);
        IComplexSequence generalized = createSequence(zero, c, 50, 2);
        IComplexSequence classical = new ComplexSequenceTricorn(c, 50, zero);

        // conj(3 + 4i)^2 + c = (-7 - 24i) + (0.5 - 0.5i)
        assertEquals(createComplex(-6.5, -24.5), generalized.computeNext(createComplex(3, 4)));
        assertEquals(classical.computeNext(createComplex(1, 1)),
                generalized.computeNext(createComplex(1, 1)));
    }

    /**
     * Test method for {@link IComplexSequence#computeNext(IComplex)} with p = 3.
     */
    @Test
    void testComputeNextPowerThree() {
        IComplex zero = createComplex(0, 0);

        // conj(1 + i)^3 = (1 - i)^3 = -2 - 2i
        assertEquals(createComplex(-2, -2),
                createSequence(zero, zero, 50, 3).computeNext(createComplex(1, 1)));

        // conj(3 + 4i)^3 = (3 - 4i)^3 = -117 - 44i, then + (1 + i)
        assertEquals(createComplex(-116, -43),
                createSequence(zero, createComplex(1, 1), 50, 3)
                        .computeNext(createComplex(3, 4)));
    }

    /**
     * Test method for {@link IComplexSequence#computeNext(IComplex)} with p = 4.
     */
    @Test
    void testComputeNextPowerFour() {
        IComplexSequence tricorn = createSequence(
                createComplex(0, 0), createComplex(0.5, 0), 50, 4);

        // conj(-1 + i)^4 = (-1 - i)^4 = (2i)^2 = -4, then + 0.5
        assertEquals(createComplex(-3.5, 0), tricorn.computeNext(createComplex(-1, 1)));
    }

    /**
     * Test method for {@link IComplexSequence#computeNext(IComplex)} with p = 1:
     * only the conjugate is taken, then c is added.
     */
    @Test
    void testComputeNextPowerOne() {
        IComplexSequence tricorn = createSequence(
                createComplex(0, 0), createComplex(0.5, 0.5), 50, 1);

        // conj(-2 - 3i) + c = (-2 + 3i) + (0.5 + 0.5i)
        assertEquals(createComplex(-1.5, 3.5), tricorn.computeNext(createComplex(-2, -3)));
    }

    /**
     * Test method for {@link IComplexSequence#computeNext(IComplex)} with p = 0:
     * the power is always 1, so the result is {@code 1 + c}.
     */
    @Test
    void testComputeNextPowerZero() {
        IComplexSequence tricorn = createSequence(
                createComplex(0, 0), createComplex(0.5, 0.25), 50, 0);

        assertEquals(createComplex(1.5, 0.25), tricorn.computeNext(createComplex(-2, 3)));
    }

    /**
     * Test method for {@link IComplexSequence#computeNext(IComplex)} with a negative
     * power, which is not supported by {@link IComplex#power(int)}.
     */
    @Test
    void testComputeNextNegativePower() {
        IComplexSequence tricorn = createSequence(
                createComplex(0, 0), createComplex(0, 0), 50, -2);
        IComplex previous = createComplex(1, 1);

        assertThrows(IllegalArgumentException.class, () -> tricorn.computeNext(previous));
    }

    /**
     * Test method for {@link IComplexSequence#computeNext(IComplex)} checking that,
     * when c = 0, the next term is the conjugate of the p-th power of the previous one
     * (since conj(z)^p = conj(z^p)), whatever the power.
     */
    @Test
    void testComputeNextIsConjugateOfPowerWithZeroConstant() {
        IComplex zero = createComplex(0, 0);
        IComplex z = createComplex(0.3, -0.7);
        for (int p = 1; p <= 6; p++) {
            IComplexSequence tricorn = createSequence(zero, zero, 50, p);

            assertEquals(z.power(p).conjugate(), tricorn.computeNext(z));
        }
    }

    /**
     * Test method for {@link IComplexSequence#computeNext(IComplex)} checking that the
     * previous term is not modified.
     */
    @Test
    void testComputeNextDoesNotModifyPrevious() {
        IComplexSequence tricorn = createSequence(
                createComplex(0, 0), createComplex(0.1, 0.1), 50, 3);
        IComplex previous = createComplex(-1.5, -0.5);

        tricorn.computeNext(previous);

        assertEquals(-1.5, previous.getRealPart(), 0.0);
        assertEquals(-0.5, previous.getImaginaryPart(), 0.0);
    }

    /**
     * Test method for {@link IComplexSequence#iterator()} checking that the generalized
     * sequence with p = 2 gives the same terms as the classical Tricorn's sequence.
     */
    @Test
    void testPowerTwoIsClassicalTricorn() {
        IComplex z0 = createComplex(0.3, -0.2);
        IComplex c = createComplex(-0.4, 0.6);

        List<IComplex> generalized = collect(createSequence(z0, c, 64, 2));
        List<IComplex> classical = collect(new ComplexSequenceTricorn(c, 64, z0));

        assertEquals(classical, generalized);
    }

    /**
     * Test method for {@link IComplexSequence#iterator()} when the sequence converges.
     */
    @Test
    void testIteratorOnConvergence() {
        int nbTerms = 50;
        IComplexSequence tricorn = createSequence(
                createComplex(0.5, 0), createComplex(0, 0), nbTerms, 3);

        assertEquals(nbTerms, collect(tricorn).size());
    }

    /**
     * Test method for {@link IComplexSequence#iterator()} when the sequence diverges.
     */
    @Test
    void testIteratorOnDivergence() {
        int nbTerms = 50;
        IComplexSequence tricorn = createSequence(
                createComplex(2, 0), createComplex(0.5, 0.5), nbTerms, 3);

        // z0 = 2 (modulus <= 2), then z1 = 8.5 + 0.5i, which exceeds the threshold.
        assertEquals(1, collect(tricorn).size());
    }

    /**
     * Test method for {@link IComplexSequence#iterator()} checking the first terms
     * of the sequence for p = 3, z0 = 0 and c = -0.4 + 0.6i.
     */
    @Test
    void testIteratorTerms() {
        IComplexSequence tricorn = createSequence(
                createComplex(0, 0), createComplex(-0.4, 0.6), 3, 3);

        List<IComplex> terms = collect(tricorn);

        // z0 = 0
        // z1 = c = -0.4 + 0.6i
        // z2 = (-0.4 - 0.6i)^3 + c = (0.368 - 0.072i) + c = -0.032 + 0.528i
        assertEquals(3, terms.size());
        assertEquals(createComplex(0, 0), terms.get(0));
        assertEquals(createComplex(-0.4, 0.6), terms.get(1));
        assertEquals(createComplex(-0.032, 0.528), terms.get(2));
    }

    /**
     * Test method for {@link IComplexSequence#iterator()} when no term is requested,
     * or when the first term is already beyond the divergence threshold.
     */
    @Test
    void testIteratorWithNoTerms() {
        IComplex zero = createComplex(0, 0);

        assertFalse(createSequence(zero, zero, 0, 3).iterator().hasNext());
        assertFalse(createSequence(createComplex(3, 3), zero, 50, 3).iterator().hasNext());
    }

    /**
     * Test method for {@link IComplexSequence#iterator()} checking that the iterator
     * starts with the first term, and can be requested several times.
     */
    @Test
    void testIteratorCanBeReused() {
        IComplex z0 = createComplex(0.3, -0.2);
        IComplexSequence tricorn = createSequence(z0, createComplex(-0.4, 0.6), 64, 3);

        assertTrue(tricorn.iterator().hasNext());
        assertEquals(z0, tricorn.iterator().next());
        assertEquals(collect(tricorn), collect(tricorn));
    }

}
