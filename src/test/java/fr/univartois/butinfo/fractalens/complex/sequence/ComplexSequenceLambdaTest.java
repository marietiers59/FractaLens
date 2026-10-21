package fr.univartois.butinfo.fractalens.complex.sequence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;

import org.junit.jupiter.api.Test;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;

/**
 * The ComplexSequenceLambdaTest tests the implementation of {@link IComplexSequence}
 * defined by a function {@code f}, such that {@code z(n+1) = f(z0, z(n))}.
 */
class ComplexSequenceLambdaTest {

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
     * Creates a new sequence defined by a function.
     *
     * @param function The function computing the next term from z0 and the previous term.
     * @param first The first term of the sequence.
     * @param nbTerms The maximum number of terms to compute.
     *
     * @return The created sequence.
     */
    private static IComplexSequence createSequence(
            BinaryOperator<IComplex> function, IComplex first, int nbTerms) {
        return new ComplexSequenceLambda(function, first, nbTerms);
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
     * Test method for {@link IComplexSequence#computeNext(IComplex)} checking that the
     * function receives z0 as its first argument and the previous term as its second.
     */
    @Test
    void testFunctionReceivesZ0AndPrevious() {
        IComplex z0 = createComplex(1, 2);
        IComplex previous = createComplex(3, 4);
        List<IComplex> received = new ArrayList<>();

        IComplexSequence sequence = createSequence((first, z) -> {
            received.add(first);
            received.add(z);
            return first;
        }, z0, 10);
        sequence.computeNext(previous);

        assertEquals(2, received.size());
        assertEquals(z0, received.get(0));
        assertEquals(previous, received.get(1));
    }

    /**
     * Test method for {@link IComplexSequence#computeNext(IComplex)} checking that the
     * result is the one returned by the function.
     */
    @Test
    void testComputeNextReturnsFunctionResult() {
        IComplex result = createComplex(-7, 42);
        IComplexSequence sequence = createSequence((first, z) -> result, createComplex(0, 0), 10);

        assertEquals(result, sequence.computeNext(createComplex(1, 1)));
    }

    /**
     * Test method for {@link IComplexSequence#computeNext(IComplex)} with Julia's
     * expression {@code z^2 + c}.
     */
    @Test
    void testComputeNextJulia() {
        IComplex c = createComplex(0, 0);
        IComplexSequence julia = createSequence(
                (first, z) -> z.multiply(z).add(c), createComplex(0.5, 0), 50);

        IComplex z1 = createComplex(0.25, 0);
        assertEquals(z1, julia.computeNext(createComplex(0.5, 0)));

        IComplex z2 = createComplex(0.0625, 0);
        assertEquals(z2, julia.computeNext(z1));
    }

    /**
     * Test method for {@link IComplexSequence#computeNext(IComplex)} with Mandelbrot's
     * expression {@code z^2 + z0}, where z0 is the number tested.
     */
    @Test
    void testComputeNextMandelbrot() {
        IComplex z0 = createComplex(0.5, 0.5);
        IComplexSequence mandelbrot = createSequence(
                (first, z) -> z.multiply(z).add(first), z0, 50);

        // (0.5 + 0.5i)^2 + (0.5 + 0.5i) = 0.5i + 0.5 + 0.5i
        assertEquals(createComplex(0.5, 1), mandelbrot.computeNext(z0));
    }

    /**
     * Test method for {@link IComplexSequence#computeNext(IComplex)} with the
     * Multibrot's expression {@code z^3 + z0}.
     */
    @Test
    void testComputeNextMultibrot() {
        IComplexSequence multibrot = createSequence(
                (first, z) -> z.power(3).add(first), createComplex(1, 0), 50);

        // (1 + i)^3 + 1 = (-2 + 2i) + 1
        assertEquals(createComplex(-1, 2), multibrot.computeNext(createComplex(1, 1)));
    }

    /**
     * Test method for {@link IComplexSequence#computeNext(IComplex)} with Tricorn's
     * expression {@code conjugate(z)^2 + c}.
     */
    @Test
    void testComputeNextTricorn() {
        IComplex c = createComplex(0.5, 0);
        IComplexSequence tricorn = createSequence(
                (first, z) -> z.conjugate().power(2).add(c), createComplex(0, 0), 50);
        IComplexSequence julia = createSequence(
                (first, z) -> z.power(2).add(c), createComplex(0, 0), 50);

        // (1 - i)^2 + 0.5 = -2i + 0.5
        assertEquals(createComplex(0.5, -2), tricorn.computeNext(createComplex(1, 1)));
        assertNotEquals(julia.computeNext(createComplex(1, 1)),
                tricorn.computeNext(createComplex(1, 1)));
    }

    /**
     * Test method for {@link IComplexSequence#computeNext(IComplex)} with Julia's
     * expression, which must behave like {@link ComplexSequenceJulia}.
     */
    @Test
    void testJuliaAsLambda() {
        IComplex z0 = createComplex(0.1, 0.2);
        IComplex c = createComplex(-0.4, 0.6);

        IComplexSequence lambda = createSequence(
                (first, z) -> z.multiply(z).add(c), z0, 64);
        IComplexSequence julia = new ComplexSequenceJulia(z0, c, 64);

        assertEquals(collect(julia), collect(lambda));
    }

    /**
     * Test method for {@link IComplexSequence#computeNext(IComplex)} with an expression
     * using the exponential function.
     */
    @Test
    void testComputeNextExponential() {
        IComplexSequence exp = createSequence((first, z) -> {
            double e = Math.exp(z.getRealPart());
            return new Complex(e * Math.cos(z.getImaginaryPart()),
                    e * Math.sin(z.getImaginaryPart())).add(first);
        }, createComplex(1, 0), 50);

        // exp(0) + 1 = 2
        assertEquals(createComplex(2, 0), exp.computeNext(createComplex(0, 0)));

        // exp(i * pi) + 1 = 0
        assertEquals(createComplex(0, 0), exp.computeNext(createComplex(0, Math.PI)));
    }

    /**
     * Test method for {@link IComplexSequence#iterator()} checking the terms of a
     * sequence defined by {@code z^2 + i} starting from 0.
     */
    @Test
    void testIteratorTerms() {
        IComplex c = createComplex(0, 1);
        IComplexSequence sequence = createSequence(
                (first, z) -> z.multiply(z).add(c), createComplex(0, 0), 5);

        List<IComplex> terms = collect(sequence);

        // 0, i, -1 + i, -i, -1 + i
        assertEquals(5, terms.size());
        assertEquals(createComplex(0, 0), terms.get(0));
        assertEquals(createComplex(0, 1), terms.get(1));
        assertEquals(createComplex(-1, 1), terms.get(2));
        assertEquals(createComplex(0, -1), terms.get(3));
        assertEquals(createComplex(-1, 1), terms.get(4));
    }

    /**
     * Test method for {@link IComplexSequence#iterator()} when the sequence converges.
     */
    @Test
    void testIteratorOnConvergence() {
        int nbTerms = 50;
        IComplexSequence sequence = createSequence(
                (first, z) -> z.multiply(z), createComplex(0.5, 0), nbTerms);

        assertEquals(nbTerms, collect(sequence).size());
    }

    /**
     * Test method for {@link IComplexSequence#iterator()} when the sequence diverges.
     */
    @Test
    void testIteratorOnDivergence() {
        int nbTerms = 50;
        IComplex c = createComplex(0.5, 0.5);
        IComplexSequence sequence = createSequence(
                (first, z) -> z.multiply(z).add(c), createComplex(2, 0), nbTerms);

        // z0 = 2 (modulus <= 2), then z1 = 4.5 + 0.5i, which exceeds the threshold.
        assertEquals(1, collect(sequence).size());
    }

    /**
     * Test method for {@link IComplexSequence#iterator()} when no term is requested,
     * or when the first term is already beyond the divergence threshold.
     */
    @Test
    void testIteratorWithNoTerms() {
        IComplex zero = createComplex(0, 0);
        BinaryOperator<IComplex> function = (first, z) -> z.add(zero);

        assertFalse(createSequence(function, zero, 0).iterator().hasNext());
        assertFalse(createSequence(function, createComplex(3, 3), 50).iterator().hasNext());
    }

    /**
     * Test method for {@link IComplexSequence#iterator()} checking that the iterator
     * starts with the first term and that the sequence can be iterated several times.
     */
    @Test
    void testIteratorCanBeReused() {
        IComplex z0 = createComplex(0.3, -0.2);
        IComplex c = createComplex(-0.4, 0.6);
        IComplexSequence sequence = createSequence(
                (first, z) -> z.multiply(z).add(c), z0, 64);

        assertTrue(sequence.iterator().hasNext());
        assertEquals(z0, sequence.iterator().next());
        assertEquals(collect(sequence), collect(sequence));
    }

    /**
     * Test method for {@link IComplexSequence#computeNext(IComplex)} with the "dragon"
     * Julia's expression {@code z^2 + c}, where {@code c = -0.8 + 0.156i}.
     */
    @Test
    void testComputeNextDragon() {
        IComplex c = createComplex(-0.8, 0.156);
        IComplexSequence dragon = createSequence(
                (first, z) -> z.multiply(z).add(c), createComplex(0, 0), 150);

        // From 0, the next term is c itself.
        assertEquals(c, dragon.computeNext(createComplex(0, 0)));

        // 1^2 + c = 0.2 + 0.156i
        assertEquals(createComplex(0.2, 0.156), dragon.computeNext(createComplex(1, 0)));

        // i^2 + c = -1.8 + 0.156i
        assertEquals(createComplex(-1.8, 0.156), dragon.computeNext(createComplex(0, 1)));
    }

    /**
     * Test method for {@link IComplexSequence#computeNext(IComplex)} with the "galaxy"
     * Julia's expression {@code z^2 + c}, where {@code c = 0.285 + 0.01i}.
     */
    @Test
    void testComputeNextGalaxy() {
        IComplex c = createComplex(0.285, 0.01);
        IComplexSequence galaxy = createSequence(
                (first, z) -> z.multiply(z).add(c), createComplex(0, 0), 150);

        assertEquals(c, galaxy.computeNext(createComplex(0, 0)));
        assertEquals(createComplex(1.285, 0.01), galaxy.computeNext(createComplex(1, 0)));
    }

    /**
     * Test method for {@link IComplexSequence#computeNext(IComplex)} with the Multibrot's
     * expressions {@code z^3 + z0} and {@code z^5 + z0}.
     */
    @Test
    void testComputeNextMultibrotThreeAndFive() {
        IComplex z0 = createComplex(0.5, 0.5);
        IComplexSequence cubic = createSequence(
                (first, z) -> z.power(3).add(first), z0, 50);
        // (1 + i)^3 + z0 = (-2 + 2i) + (0.5 + 0.5i)
        assertEquals(createComplex(-1.5, 2.5), cubic.computeNext(createComplex(1, 1)));

        IComplexSequence quintic = createSequence(
                (first, z) -> z.power(5).add(first), createComplex(0.5, 0), 50);
        // (1 + i)^5 + 0.5 = (-4 - 4i) + 0.5
        assertEquals(createComplex(-3.5, -4), quintic.computeNext(createComplex(1, 1)));
    }

    /**
     * Test method for {@link IComplexSequence#iterator()} with the "dragon" expression:
     * the sequence starting from 0 must stay bounded, while one starting far from the
     * set must diverge immediately.
     */
    @Test
    void testIteratorDragon() {
        IComplex c = createComplex(-0.8, 0.156);
        BinaryOperator<IComplex> function = (first, z) -> z.multiply(z).add(c);

        assertEquals(150, collect(createSequence(function, createComplex(0, 0), 150)).size());
        assertEquals(1, collect(createSequence(function, createComplex(2, 0), 150)).size());
    }

}