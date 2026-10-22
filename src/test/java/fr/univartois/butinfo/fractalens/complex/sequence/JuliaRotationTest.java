package fr.univartois.butinfo.fractalens.complex.sequence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import org.junit.jupiter.api.Test;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;

/**
 * The JuliaRotationTest tests the methods of {@link JuliaRotation}, used to animate a
 * Julia fractal by rotating the constant of the sequence.
 *
 * @author Marie Tiers
 */
class JuliaRotationTest {

    /**
     * The tolerance used to compare real numbers.
     */
    private static final double DELTA = 1e-9;

    /**
     * The constant of the Julia's sequence used in the tests.
     */
    private static final IComplex C = createComplex(-0.4, 0.6);

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
     * Checks that two complex numbers are equal, up to {@link #DELTA}.
     *
     * @param expected The expected complex number.
     * @param actual The actual complex number.
     */
    private static void assertComplexEquals(IComplex expected, IComplex actual) {
        assertEquals(expected.getRealPart(), actual.getRealPart(), DELTA);
        assertEquals(expected.getImaginaryPart(), actual.getImaginaryPart(), DELTA);
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

    // ----------------------------------------------------------------------- rotate

    /**
     * Test method for {@link JuliaRotation#rotate(IComplex, double)} with a rotation of
     * angle 0, which must not change the complex number.
     */
    @Test
    void testRotateNoTurn() {
        assertComplexEquals(C, JuliaRotation.rotate(C, 0));
    }

    /**
     * Test method for {@link JuliaRotation#rotate(IComplex, double)} with a quarter of
     * a turn: the real number 1 becomes i, and i becomes -1.
     */
    @Test
    void testRotateQuarterTurn() {
        assertComplexEquals(createComplex(0, 1),
                JuliaRotation.rotate(createComplex(1, 0), Math.PI / 2));
        assertComplexEquals(createComplex(-1, 0),
                JuliaRotation.rotate(createComplex(0, 1), Math.PI / 2));
    }

    /**
     * Test method for {@link JuliaRotation#rotate(IComplex, double)} with half a turn,
     * which gives the opposite of the complex number.
     */
    @Test
    void testRotateHalfTurn() {
        assertComplexEquals(C.negate(), JuliaRotation.rotate(C, Math.PI));
    }

    /**
     * Test method for {@link JuliaRotation#rotate(IComplex, double)} with a full turn,
     * which gives back the original complex number.
     */
    @Test
    void testRotateFullTurn() {
        assertComplexEquals(C, JuliaRotation.rotate(C, 2 * Math.PI));
    }

    /**
     * Test method for {@link JuliaRotation#rotate(IComplex, double)} checking that the
     * modulus of the complex number is kept, whatever the angle.
     */
    @Test
    void testRotateKeepsModulus() {
        for (double theta : new double[] { 0.1, 1.234, 3, -2.5, 10 }) {
            assertEquals(C.abs(), JuliaRotation.rotate(C, theta).abs(), DELTA);
        }
    }

    /**
     * Test method for {@link JuliaRotation#rotate(IComplex, double)} checking that two
     * successive rotations are equivalent to one rotation by the sum of the angles.
     */
    @Test
    void testRotateAnglesAddUp() {
        IComplex twice = JuliaRotation.rotate(JuliaRotation.rotate(C, 0.3), 0.5);
        assertComplexEquals(JuliaRotation.rotate(C, 0.8), twice);
    }

    /**
     * Test method for {@link JuliaRotation#rotate(IComplex, double)} checking that the
     * given complex number is not modified.
     */
    @Test
    void testRotateDoesNotModifyArgument() {
        IComplex c = createComplex(-0.4, 0.6);
        JuliaRotation.rotate(c, 1);
        assertEquals(-0.4, c.getRealPart(), 0.0);
        assertEquals(0.6, c.getImaginaryPart(), 0.0);
    }

    /**
     * Test method for {@link JuliaRotation#rotate(IComplex, double)} with a
     * {@code null} complex number.
     */
    @Test
    void testRotateNull() {
        assertThrows(NullPointerException.class, () -> JuliaRotation.rotate(null, 1));
    }

    // ----------------------------------------------------------------------- angles

    /**
     * Test method for {@link JuliaRotation#angles(double)} with a step dividing a full
     * turn into 60 parts: 2 * PI is excluded, so there are exactly 60 angles.
     */
    @Test
    void testAnglesCount() {
        assertEquals(60, JuliaRotation.angles(2 * Math.PI / 60).size());
        assertEquals(150, JuliaRotation.angles(2 * Math.PI / 150).size());
    }

    /**
     * Test method for {@link JuliaRotation#angles(double)} checking the bounds: the
     * first angle is 0, and every angle is strictly lower than 2 * PI.
     */
    @Test
    void testAnglesBounds() {
        List<Double> angles = JuliaRotation.angles(2 * Math.PI / 60);
        assertEquals(0, angles.get(0), 0.0);
        for (double angle : angles) {
            assertTrue(angle < 2 * Math.PI);
        }
    }

    /**
     * Test method for {@link JuliaRotation#angles(double)} checking that the angles are
     * regularly spaced by the step.
     */
    @Test
    void testAnglesRegularSpacing() {
        double step = 2 * Math.PI / 60;
        List<Double> angles = JuliaRotation.angles(step);
        for (int k = 0; k < angles.size(); k++) {
            assertEquals(k * step, angles.get(k), DELTA);
        }
    }

    /**
     * Test method for {@link JuliaRotation#angles(double)} with a step that does not
     * divide a full turn: the last angle is the greatest multiple of the step lower
     * than 2 * PI.
     */
    @Test
    void testAnglesWithNonDividingStep() {
        // 0, 1, 2, ..., 6 (since 6 < 2 * PI ≈ 6.283 < 7)
        assertEquals(7, JuliaRotation.angles(1).size());
    }

    /**
     * Test method for {@link JuliaRotation#angles(double)} with a step greater than a
     * full turn: only the angle 0 remains.
     */
    @Test
    void testAnglesWithHugeStep() {
        assertEquals(List.of(0.0), JuliaRotation.angles(10));
    }

    /**
     * Test method for {@link JuliaRotation#angles(double)} with invalid steps.
     */
    @Test
    void testAnglesInvalidStep() {
        assertThrows(IllegalArgumentException.class, () -> JuliaRotation.angles(0));
        assertThrows(IllegalArgumentException.class, () -> JuliaRotation.angles(-0.1));
        assertThrows(IllegalArgumentException.class, () -> JuliaRotation.angles(Double.NaN));
        assertThrows(IllegalArgumentException.class,
                () -> JuliaRotation.angles(Double.POSITIVE_INFINITY));
    }

    /**
     * Test method for {@link JuliaRotation#angles(double)} checking that the returned
     * list cannot be modified.
     */
    @Test
    void testAnglesUnmodifiable() {
        List<Double> angles = JuliaRotation.angles(1);
        assertThrows(UnsupportedOperationException.class, () -> angles.add(42.0));
    }

    // -------------------------------------------------------------------- factories

    /**
     * Test method for {@link JuliaRotation#factories(IComplex, double)} checking that
     * there is one factory per angle.
     */
    @Test
    void testFactoriesCount() {
        double step = 2 * Math.PI / 60;
        assertEquals(JuliaRotation.angles(step).size(),
                JuliaRotation.factories(C, step).size());
    }

    /**
     * Test method for {@link JuliaRotation#factories(IComplex, double)} checking that
     * the first factory (angle 0) creates the Julia's sequence with the original
     * constant.
     */
    @Test
    void testFirstFactoryKeepsConstant() {
        BiFunction<IComplex, Integer, IComplexSequence> first =
                JuliaRotation.factories(C, 2 * Math.PI / 60).get(0);
        IComplex z0 = createComplex(0, 0);

        IComplexSequence expected = new ComplexSequenceJulia(z0, C, 64);
        assertEquals(collect(expected), collect(first.apply(z0, 64)));
    }

    /**
     * Test method for {@link JuliaRotation#factories(IComplex, double)} checking that
     * each factory creates the Julia's sequence whose constant is rotated by the
     * corresponding angle: from 0, the next term is the constant itself.
     */
    @Test
    void testFactoriesUseRotatedConstant() {
        double step = 2 * Math.PI / 60;
        List<Double> angles = JuliaRotation.angles(step);
        List<BiFunction<IComplex, Integer, IComplexSequence>> factories =
                JuliaRotation.factories(C, step);
        IComplex zero = createComplex(0, 0);

        for (int k = 0; k < factories.size(); k++) {
            IComplexSequence sequence = factories.get(k).apply(zero, 64);
            assertComplexEquals(JuliaRotation.rotate(C, angles.get(k)),
                    sequence.computeNext(zero));
        }
    }

    /**
     * Test method for {@link JuliaRotation#factories(IComplex, double)} checking that
     * the factories pass the pixel and the maximum number of terms to the sequence.
     */
    @Test
    void testFactoriesUsePixelAndNbTerms() {
        IComplex z0 = createComplex(0.1, 0.1);
        IComplexSequence sequence = JuliaRotation.factories(C, 1).get(3).apply(z0, 5);

        List<IComplex> terms = collect(sequence);
        assertEquals(z0, terms.get(0));
        assertTrue(terms.size() <= 5);
    }

    /**
     * Test method for {@link JuliaRotation#factories(IComplex, double)} checking that
     * two different angles give two different sequences.
     */
    @Test
    void testFactoriesDifferFromEachOther() {
        List<BiFunction<IComplex, Integer, IComplexSequence>> factories =
                JuliaRotation.factories(C, 2 * Math.PI / 4);
        IComplex zero = createComplex(0, 0);

        assertNotEquals(factories.get(0).apply(zero, 64).computeNext(zero),
                factories.get(1).apply(zero, 64).computeNext(zero));
    }

    /**
     * Test method for {@link JuliaRotation#factories(IComplex, double)} with invalid
     * parameters.
     */
    @Test
    void testFactoriesInvalidParameters() {
        assertThrows(NullPointerException.class, () -> JuliaRotation.factories(null, 1));
        assertThrows(IllegalArgumentException.class, () -> JuliaRotation.factories(C, 0));
    }

}
