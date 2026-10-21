package fr.univartois.butinfo.fractalens.complex.sequence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;

/**
 * The ComplexSequenceBurningShipTest tests the implementation of {@link IComplexSequence}
 * for the Burning Ship's sequence, defined by
 * {@code b(n) = (|Re(b(n-1))| + i|Im(b(n-1))|)^2 + c}.
 */
class ComplexSequenceBurningShipTest {

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
     * Creates a new sequence implementing Burning Ship's sequence.
     *
     * @param first The first term of the sequence.
     * @param constant The constant number in the definition of the sequence.
     * @param nbTerms The maximum number of terms to compute.
     *
     * @return The created Burning Ship's sequence.
     */
    private static IComplexSequence createBurningShipSequence(
            IComplex first, IComplex constant, int nbTerms) {
        return new ComplexSequenceBurningShip(constant, nbTerms, first);
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
     * Test method for
     * {@link IComplexSequence#computeNext(IComplex)} with c = 0
     * (only the squaring of the absolute values is applied).
     */
    @Test
    void testComputeNextWithZeroConstant() {
        IComplexSequence ship = createBurningShipSequence(
                createComplex(0, 0), createComplex(0, 0), 50);

        // (0.5 + 0i)^2 = 0.25
        assertEquals(createComplex(0.25, 0), ship.computeNext(createComplex(0.5, 0)));

        // (|-1| + i|-1|)^2 = (1 + i)^2 = 2i
        assertEquals(createComplex(0, 2), ship.computeNext(createComplex(-1, -1)));

        // (|0.5| + i|-0.5|)^2 = (0.5 + 0.5i)^2 = 0.5i
        assertEquals(createComplex(0, 0.5), ship.computeNext(createComplex(0.5, -0.5)));

        // 0 is a fixed point when c = 0.
        assertEquals(createComplex(0, 0), ship.computeNext(createComplex(0, 0)));
    }

    /**
     * Test method for
     * {@link IComplexSequence#computeNext(IComplex)} with a non-zero constant.
     */
    @Test
    void testComputeNextWithConstant() {
        IComplexSequence ship = createBurningShipSequence(
                createComplex(0, 0), createComplex(0.5, -0.5), 50);

        // (|3| + i|-4|)^2 + c = (3 + 4i)^2 + c = (-7 + 24i) + (0.5 - 0.5i)
        assertEquals(createComplex(-6.5, 23.5), ship.computeNext(createComplex(3, -4)));

        // From 0, the next term is c itself.
        assertEquals(createComplex(0.5, -0.5), ship.computeNext(createComplex(0, 0)));
    }

    /**
     * Test method for
     * {@link IComplexSequence#computeNext(IComplex)} checking that the signs of the
     * previous term do not matter (this is what differs from Julia's sequence).
     */
    @Test
    void testComputeNextIgnoresSigns() {
        IComplex c = createComplex(-0.4, 0.6);
        IComplexSequence ship = createBurningShipSequence(createComplex(0, 0), c, 50);

        IComplex expected = ship.computeNext(createComplex(1, 2));
        assertEquals(expected, ship.computeNext(createComplex(-1, 2)));
        assertEquals(expected, ship.computeNext(createComplex(1, -2)));
        assertEquals(expected, ship.computeNext(createComplex(-1, -2)));
    }

    /**
     * Test method for
     * {@link IComplexSequence#computeNext(IComplex)} checking the difference with
     * Julia's sequence when the real and imaginary parts have the same sign or not.
     */
    @Test
    void testComputeNextDiffersFromJulia() {
        IComplex c = createComplex(0, 0);
        IComplexSequence ship = createBurningShipSequence(createComplex(0, 0), c, 50);
        IComplexSequence julia = new ComplexSequenceJulia(createComplex(0, 0), c, 50);

        // Same signs: both sequences agree ((1 + i)^2 = 2i).
        assertEquals(julia.computeNext(createComplex(1, 1)),
                ship.computeNext(createComplex(1, 1)));

        // Opposite signs: Julia gives (1 - i)^2 = -2i, Burning Ship gives 2i.
        assertEquals(createComplex(0, -2), julia.computeNext(createComplex(1, -1)));
        assertEquals(createComplex(0, 2), ship.computeNext(createComplex(1, -1)));
        assertNotEquals(julia.computeNext(createComplex(1, -1)),
                ship.computeNext(createComplex(1, -1)));
    }

    /**
     * Test method for
     * {@link IComplexSequence#computeNext(IComplex)} checking that the previous term
     * is not modified.
     */
    @Test
    void testComputeNextDoesNotModifyPrevious() {
        IComplexSequence ship = createBurningShipSequence(
                createComplex(0, 0), createComplex(0.1, 0.1), 50);
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
        IComplexSequence ship = createBurningShipSequence(
                createComplex(0.5, 0), createComplex(0, 0), nbTerms);

        assertEquals(nbTerms, collect(ship).size());
    }

    /**
     * Test method for {@link IComplexSequence#iterator()} when the sequence diverges.
     */
    @Test
    void testIteratorOnDivergence() {
        int nbTerms = 50;
        IComplexSequence ship = createBurningShipSequence(
                createComplex(2, 0), createComplex(0.5, 0.5), nbTerms);

        // z0 = 2 (modulus <= 2), then z1 = 4.5 + 0.5i, which exceeds the threshold.
        assertTrue(collect(ship).size() < nbTerms);
        assertEquals(1, collect(ship).size());
    }

    /**
     * Test method for {@link IComplexSequence#iterator()} checking the terms of the
     * sequence used in the demonstration of the sprint (c = -0.4 + 0.6i, z0 = 0).
     */
    @Test
    void testIteratorTerms() {
        IComplexSequence ship = createBurningShipSequence(
                createComplex(0, 0), createComplex(-0.4, 0.6), 64);

        List<IComplex> terms = collect(ship);

        // z0 = 0
        // z1 = c = -0.4 + 0.6i
        // z2 = (0.4 + 0.6i)^2 + c = -0.6 + 1.08i
        // z3 = (0.6 + 1.08i)^2 + c = -1.2064 + 1.896i, whose modulus is > 2.
        assertEquals(3, terms.size());
        assertEquals(createComplex(0, 0), terms.get(0));
        assertEquals(createComplex(-0.4, 0.6), terms.get(1));
        assertEquals(createComplex(-0.6, 1.08), terms.get(2));
    }

    /**
     * Test method for {@link IComplexSequence#iterator()} checking that the first term
     * returned is the first term given at the creation of the sequence.
     */
    @Test
    void testIteratorStartsWithFirstTerm() {
        IComplex z0 = createComplex(0.3, -0.2);
        IComplexSequence ship = createBurningShipSequence(z0, createComplex(0, 0), 10);

        Iterator<IComplex> iterator = ship.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(z0, iterator.next());
    }

    /**
     * Test method for {@link IComplexSequence#iterator()} when no term is requested.
     */
    @Test
    void testIteratorWithNoTerms() {
        IComplexSequence ship = createBurningShipSequence(
                createComplex(0, 0), createComplex(0, 0), 0);

        assertFalse(ship.iterator().hasNext());
        assertEquals(0, collect(ship).size());
    }

    /**
     * Test method for {@link IComplexSequence#iterator()} when the first term is
     * already beyond the divergence threshold.
     */
    @Test
    void testIteratorWithDivergingFirstTerm() {
        IComplexSequence ship = createBurningShipSequence(
                createComplex(3, 3), createComplex(0, 0), 50);

        assertFalse(ship.iterator().hasNext());
    }

    /**
     * Test method for {@link IComplexSequence#iterator()} checking that the sequence
     * can be iterated over several times, always giving the same terms.
     */
    @Test
    void testIteratorCanBeReused() {
        IComplexSequence ship = createBurningShipSequence(
                createComplex(0, 0), createComplex(-0.4, 0.6), 64);

        assertEquals(collect(ship), collect(ship));
    }

    /**
     * Test method for {@link IComplexSequence#iterator()} checking that the number of
     * terms never exceeds the requested maximum.
     */
    @Test
    void testIteratorRespectsMaxIterations() {
        IComplexSequence ship = createBurningShipSequence(
                createComplex(0, 0), createComplex(0, 0), 7);

        assertEquals(7, collect(ship).size());
    }

}