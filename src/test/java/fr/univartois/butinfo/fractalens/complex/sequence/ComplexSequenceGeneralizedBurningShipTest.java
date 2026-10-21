package fr.univartois.butinfo.fractalens.complex.sequence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;

/**
 * The ComplexSequenceGeneralizedBurningShipTest tests the implementation of
 * {@link IComplexSequence} for the generalized Burning Ship's sequence, defined by
 * {@code b(n) = (|Re(b(n-1))| + i|Im(b(n-1))|)^p + c}.
 */
class ComplexSequenceGeneralizedBurningShipTest {

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
     * Creates a new generalized Burning Ship's sequence.
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
        return new ComplexSequenceGeneralizedBurningShip(constant, nbTerms, first, power);
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
     * which must behave like the classical Burning Ship's sequence.
     */
    @Test
    void testComputeNextPowerTwo() {
        IComplex zero = createComplex(0, 0);
        IComplexSequence ship = createSequence(zero, createComplex(0.5, -0.5), 50, 2);

        // (3 + 4i)^2 + c = (-7 + 24i) + (0.5 - 0.5i)
        assertEquals(createComplex(-6.5, 23.5), ship.computeNext(createComplex(3, -4)));

        IComplexSequence noConstant = createSequence(zero, zero, 50, 2);
        assertEquals(createComplex(0, 2), noConstant.computeNext(createComplex(-1, -1)));
        assertEquals(createComplex(0, 2), noConstant.computeNext(createComplex(1, -1)));
    }

    /**
     * Test method for {@link IComplexSequence#computeNext(IComplex)} with p = 3.
     */
    @Test
    void testComputeNextPowerThree() {
        IComplex zero = createComplex(0, 0);

        // (|-1| + i|-1|)^3 = (1 + i)^3 = -2 + 2i
        assertEquals(createComplex(-2, 2),
                createSequence(zero, zero, 50, 3).computeNext(createComplex(-1, -1)));

        // (3 + 4i)^3 = -117 + 44i, then + (1 + i)
        assertEquals(createComplex(-116, 45),
                createSequence(zero, createComplex(1, 1), 50, 3)
                        .computeNext(createComplex(3, -4)));
    }

    /**
     * Test method for {@link IComplexSequence#computeNext(IComplex)} with p = 4.
     */
    @Test
    void testComputeNextPowerFour() {
        IComplexSequence ship = createSequence(
                createComplex(0, 0), createComplex(0.5, 0), 50, 4);

        // (|-1| + i|1|)^4 = (1 + i)^4 = -4, then + 0.5
        assertEquals(createComplex(-3.5, 0), ship.computeNext(createComplex(-1, 1)));
    }

    /**
     * Test method for {@link IComplexSequence#computeNext(IComplex)} with p = 1:
     * only the absolute values are taken, then c is added.
     */
    @Test
    void testComputeNextPowerOne() {
        IComplexSequence ship = createSequence(
                createComplex(0, 0), createComplex(0.5, 0.5), 50, 1);

        assertEquals(createComplex(2.5, 3.5), ship.computeNext(createComplex(-2, -3)));
    }

    /**
     * Test method for {@link IComplexSequence#computeNext(IComplex)} with p = 0:
     * the power is always 1, so the result is {@code 1 + c}.
     */
    @Test
    void testComputeNextPowerZero() {
        IComplexSequence ship = createSequence(
                createComplex(0, 0), createComplex(0.5, 0.25), 50, 0);

        assertEquals(createComplex(1.5, 0.25), ship.computeNext(createComplex(-2, 3)));
    }

    /**
     * Test method for {@link IComplexSequence#computeNext(IComplex)} with a negative
     * power, which is not supported by {@link IComplex#power(int)}.
     */
    @Test
    void testComputeNextNegativePower() {
        IComplexSequence ship = createSequence(
                createComplex(0, 0), createComplex(0, 0), 50, -2);
        IComplex previous = createComplex(1, 1);

        assertThrows(IllegalArgumentException.class, () -> ship.computeNext(previous));
    }

    /**
     * Test method for {@link IComplexSequence#computeNext(IComplex)} checking that
     * the signs of the previous term never matter, whatever the power.
     */
    @Test
    void testComputeNextIgnoresSigns() {
        IComplex c = createComplex(-0.4, 0.6);
        for (int p = 1; p <= 6; p++) {
            IComplexSequence ship = createSequence(createComplex(0, 0), c, 50, p);

            IComplex expected = ship.computeNext(createComplex(1, 2));
            assertEquals(expected, ship.computeNext(createComplex(-1, 2)));
            assertEquals(expected, ship.computeNext(createComplex(1, -2)));
            assertEquals(expected, ship.computeNext(createComplex(-1, -2)));
        }
    }


    /**
     * Test method for {@link IComplexSequence#computeNext(IComplex)} checking that
     * the previous term is not modified.
     */
    @Test
    void testComputeNextDoesNotModifyPrevious() {
        IComplexSequence ship = createSequence(
                createComplex(0, 0), createComplex(0.1, 0.1), 50, 3);
        IComplex previous = createComplex(-1.5, -0.5);

        ship.computeNext(previous);

        assertEquals(-1.5, previous.getRealPart(), 0.0);
        assertEquals(-0.5, previous.getImaginaryPart(), 0.0);
    }

    /**
     * Test method for {@link IComplexSequence#iterator()} when the sequence converges.
     */
    @Test
    void testIteratorOnConvergence() {
        int nbTerms = 50;
        IComplexSequence ship = createSequence(
                createComplex(0.5, 0), createComplex(0, 0), nbTerms, 3);

        assertEquals(nbTerms, collect(ship).size());
    }

    /**
     * Test method for {@link IComplexSequence#iterator()} when the sequence diverges.
     */
    @Test
    void testIteratorOnDivergence() {
        int nbTerms = 50;
        IComplexSequence ship = createSequence(
                createComplex(2, 0), createComplex(0.5, 0.5), nbTerms, 3);

        // z0 = 2 (modulus <= 2), then z1 = 8.5 + 0.5i, which exceeds the threshold.
        assertEquals(1, collect(ship).size());
    }

    /**
     * Test method for {@link IComplexSequence#iterator()} checking the first terms
     * of the sequence for p = 3, z0 = 0 and c = -0.4 + 0.6i.
     */
    @Test
    void testIteratorTerms() {
        IComplexSequence ship = createSequence(
                createComplex(0, 0), createComplex(-0.4, 0.6), 3, 3);

        List<IComplex> terms = collect(ship);

        // z0 = 0
        // z1 = c = -0.4 + 0.6i
        // z2 = (0.4 + 0.6i)^3 + c = (-0.368 + 0.072i) + c = -0.768 + 0.672i
        assertEquals(3, terms.size());
        assertEquals(createComplex(0, 0), terms.get(0));
        assertEquals(createComplex(-0.4, 0.6), terms.get(1));
        assertEquals(createComplex(-0.768, 0.672), terms.get(2));
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
     * Test method for {@link IComplexSequence#iterator()} checking that the iterator is
     * never {@code null}, starts with the first term, and can be requested several times.
     */
    @Test
    void testIteratorCanBeReused() {
        IComplex z0 = createComplex(0.3, -0.2);
        IComplexSequence ship = createSequence(z0, createComplex(-0.4, 0.6), 64, 3);

        assertTrue(ship.iterator().hasNext());
        assertEquals(z0, ship.iterator().next());
        assertEquals(collect(ship), collect(ship));
    }

}