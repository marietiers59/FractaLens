package fr.univartois.butinfo.fractalens.complex.sequence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;

/**
<<<<<<<< HEAD:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/JuliaRotationGeneralizedTest.java
 * The JuliaRotationGeneralizedTest tests the methods of {@link JuliaRotationGeneralized},
========
 * The JuliaRotationGeneraliseeTest tests the methods of {@link GeneralizedJuliaRotation},
>>>>>>>> 739edabb804610c40a6455d842ca099d11a2b566:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/GeneralizedJuliaRotationTest.java
 * which generalizes the rotation of the constant to every sequence of
 * {@link AllSequences}.
 *
 * @author Marie Tiers
 */
<<<<<<<< HEAD:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/JuliaRotationGeneralizedTest.java
class JuliaRotationGeneralizedTest {
========
class GeneralizedJuliaRotationTest {
>>>>>>>> 739edabb804610c40a6455d842ca099d11a2b566:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/GeneralizedJuliaRotationTest.java

    /**
     * The tolerance used to compare real numbers.
     */
    private static final double DELTA = 1e-9;

    /**
     * The step used in the tests (60 images per turn).
     */
    private static final double STEP = 2 * Math.PI / 60;

    /**
     * The constant of the sequences used in the tests.
     */
    private static final IComplex C = createComplex(-0.4, 0.6);

    /**
     * The complex number 0.
     */
    private static final IComplex ZERO = createComplex(0, 0);

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

    // ------------------------------------------------------------------ isSupported

    /**
<<<<<<<< HEAD:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/JuliaRotationGeneralizedTest.java
     * Test method for {@link JuliaRotationGeneralized#isSupported(AllSequences)} with
========
     * Test method for {@link GeneralizedJuliaRotation#isSupported(AllSequences)} with
>>>>>>>> 739edabb804610c40a6455d842ca099d11a2b566:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/GeneralizedJuliaRotationTest.java
     * Julia's sequences, which can be rotated.
     */
    @ParameterizedTest
    @EnumSource(value = AllSequences.class, names = { "JULIA", "JULIA_GENERALIZED" })
    void testIsSupportedForJulia(AllSequences sequence) {
<<<<<<<< HEAD:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/JuliaRotationGeneralizedTest.java
        assertTrue(JuliaRotationGeneralized.isSupported(sequence));
    }

    /**
     * Test method for {@link JuliaRotationGeneralized#isSupported(AllSequences)} with
========
        assertTrue(GeneralizedJuliaRotation.isSupported(sequence));
    }

    /**
     * Test method for {@link GeneralizedJuliaRotation#isSupported(AllSequences)} with
>>>>>>>> 739edabb804610c40a6455d842ca099d11a2b566:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/GeneralizedJuliaRotationTest.java
     * the other sequences, which cannot be rotated.
     */
    @ParameterizedTest
    @EnumSource(value = AllSequences.class, mode = EnumSource.Mode.EXCLUDE,
            names = { "JULIA", "JULIA_GENERALIZED" })
    void testIsNotSupportedForOthers(AllSequences sequence) {
<<<<<<<< HEAD:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/JuliaRotationGeneralizedTest.java
        assertFalse(JuliaRotationGeneralized.isSupported(sequence));
    }

    /**
     * Test method for {@link JuliaRotationGeneralized#isSupported(AllSequences)} with
========
        assertFalse(GeneralizedJuliaRotation.isSupported(sequence));
    }

    /**
     * Test method for {@link GeneralizedJuliaRotation#isSupported(AllSequences)} with
>>>>>>>> 739edabb804610c40a6455d842ca099d11a2b566:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/GeneralizedJuliaRotationTest.java
     * a {@code null} sequence.
     */
    @Test
    void testIsSupportedNull() {
        assertThrows(NullPointerException.class,
<<<<<<<< HEAD:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/JuliaRotationGeneralizedTest.java
                () -> JuliaRotationGeneralized.isSupported(null));
========
                () -> GeneralizedJuliaRotation.isSupported(null));
>>>>>>>> 739edabb804610c40a6455d842ca099d11a2b566:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/GeneralizedJuliaRotationTest.java
    }

    // ---------------------------------------------------------------- factories: Julia

    /**
<<<<<<<< HEAD:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/JuliaRotationGeneralizedTest.java
     * Test method for {@link JuliaRotationGeneralized#factories(AllSequences, IComplex, double)}
========
     * Test method for {@link GeneralizedJuliaRotation#factories(AllSequences, IComplex, double)}
>>>>>>>> 739edabb804610c40a6455d842ca099d11a2b566:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/GeneralizedJuliaRotationTest.java
     * with Julia's sequence: it gives the same sequences as
     * {@link JuliaRotation#factories(IComplex, double)}.
     */
    @Test
    void testFactoriesJuliaMatchesJuliaRotation() {
        List<BiFunction<IComplex, Integer, IComplexSequence>> expected =
                JuliaRotation.factories(C, STEP);
        List<BiFunction<IComplex, Integer, IComplexSequence>> actual =
<<<<<<<< HEAD:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/JuliaRotationGeneralizedTest.java
                JuliaRotationGeneralized.factories(AllSequences.JULIA, C, STEP);
========
                GeneralizedJuliaRotation.factories(AllSequences.JULIA, C, STEP);
>>>>>>>> 739edabb804610c40a6455d842ca099d11a2b566:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/GeneralizedJuliaRotationTest.java
        IComplex z0 = createComplex(0.2, 0.3);

        assertEquals(expected.size(), actual.size());
        for (int k = 0; k < expected.size(); k++) {
            assertEquals(collect(expected.get(k).apply(z0, 64)),
                    collect(actual.get(k).apply(z0, 64)));
        }
    }

    // ---------------------------------------------------- factories: generalized Julia

    /**
<<<<<<<< HEAD:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/JuliaRotationGeneralizedTest.java
     * Test method for {@link JuliaRotationGeneralized#factories(AllSequences, IComplex, double)}
========
     * Test method for {@link GeneralizedJuliaRotation#factories(AllSequences, IComplex, double)}
>>>>>>>> 739edabb804610c40a6455d842ca099d11a2b566:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/GeneralizedJuliaRotationTest.java
     * with the generalized Julia's sequence: there is one factory per angle.
     */
    @Test
    void testFactoriesGeneralizedJuliaCount() {
        assertEquals(60,
<<<<<<<< HEAD:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/JuliaRotationGeneralizedTest.java
                JuliaRotationGeneralized.factories(AllSequences.JULIA_GENERALIZED, C, STEP).size());
    }

    /**
     * Test method for {@link JuliaRotationGeneralized#factories(AllSequences, IComplex, double)}
========
                GeneralizedJuliaRotation.factories(AllSequences.JULIA_GENERALIZED, C, STEP).size());
    }

    /**
     * Test method for {@link GeneralizedJuliaRotation#factories(AllSequences, IComplex, double)}
>>>>>>>> 739edabb804610c40a6455d842ca099d11a2b566:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/GeneralizedJuliaRotationTest.java
     * with the generalized Julia's sequence: each sequence uses the constant rotated by
     * the corresponding angle (from 0, the next term is the constant itself).
     */
    @Test
    void testFactoriesGeneralizedJuliaUsesRotatedConstant() {
        List<Double> angles = JuliaRotation.angles(STEP);
        List<BiFunction<IComplex, Integer, IComplexSequence>> factories =
<<<<<<<< HEAD:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/JuliaRotationGeneralizedTest.java
                JuliaRotationGeneralized.factories(AllSequences.JULIA_GENERALIZED, C, STEP);
========
                GeneralizedJuliaRotation.factories(AllSequences.JULIA_GENERALIZED, C, STEP);
>>>>>>>> 739edabb804610c40a6455d842ca099d11a2b566:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/GeneralizedJuliaRotationTest.java

        for (int k = 0; k < factories.size(); k++) {
            IComplexSequence sequence = factories.get(k).apply(ZERO, 64);
            assertComplexEquals(JuliaRotation.rotate(C, angles.get(k)),
                    sequence.computeNext(ZERO));
        }
    }

    /**
<<<<<<<< HEAD:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/JuliaRotationGeneralizedTest.java
     * Test method for {@link JuliaRotationGeneralized#factories(AllSequences, IComplex, double)}
========
     * Test method for {@link GeneralizedJuliaRotation#factories(AllSequences, IComplex, double)}
>>>>>>>> 739edabb804610c40a6455d842ca099d11a2b566:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/GeneralizedJuliaRotationTest.java
     * with the generalized Julia's sequence: the power is 3, as in
     * {@link AllSequences#JULIA_GENERALIZED}.
     */
    @Test
    void testFactoriesGeneralizedJuliaUsesPowerThree() {
        IComplexSequence first =
<<<<<<<< HEAD:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/JuliaRotationGeneralizedTest.java
                JuliaRotationGeneralized.factories(AllSequences.JULIA_GENERALIZED, ZERO, STEP)
========
                GeneralizedJuliaRotation.factories(AllSequences.JULIA_GENERALIZED, ZERO, STEP)
>>>>>>>> 739edabb804610c40a6455d842ca099d11a2b566:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/GeneralizedJuliaRotationTest.java
                        .get(0).apply(ZERO, 64);

        // With c = 0: (1 + i)^3 = -2 + 2i
        assertComplexEquals(createComplex(-2, 2), first.computeNext(createComplex(1, 1)));
    }

    /**
<<<<<<<< HEAD:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/JuliaRotationGeneralizedTest.java
     * Test method for {@link JuliaRotationGeneralized#factories(AllSequences, IComplex, double)}
========
     * Test method for {@link GeneralizedJuliaRotation#factories(AllSequences, IComplex, double)}
>>>>>>>> 739edabb804610c40a6455d842ca099d11a2b566:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/GeneralizedJuliaRotationTest.java
     * with the generalized Julia's sequence: the first factory (angle 0) keeps the
     * constant, and the pixel and the number of terms are passed to the sequence.
     */
    @Test
    void testFactoriesGeneralizedJuliaFirstFactory() {
        IComplex z0 = createComplex(0.1, 0.1);
        IComplexSequence expected = new ComplexSequenceGeneralizedJulia(z0, C, 10, 3);
        IComplexSequence actual =
<<<<<<<< HEAD:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/JuliaRotationGeneralizedTest.java
                JuliaRotationGeneralized.factories(AllSequences.JULIA_GENERALIZED, C, STEP)
========
                GeneralizedJuliaRotation.factories(AllSequences.JULIA_GENERALIZED, C, STEP)
>>>>>>>> 739edabb804610c40a6455d842ca099d11a2b566:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/GeneralizedJuliaRotationTest.java
                        .get(0).apply(z0, 10);

        assertEquals(collect(expected), collect(actual));
    }

    // ------------------------------------------------------ factories: unsupported

    /**
<<<<<<<< HEAD:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/JuliaRotationGeneralizedTest.java
     * Test method for {@link JuliaRotationGeneralized#factories(AllSequences, IComplex, double)}
========
     * Test method for {@link GeneralizedJuliaRotation#factories(AllSequences, IComplex, double)}
>>>>>>>> 739edabb804610c40a6455d842ca099d11a2b566:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/GeneralizedJuliaRotationTest.java
     * with the sequences that cannot be rotated: Mandelbrot, Burning Ship, Tricorn and
     * their generalized versions.
     */
    @ParameterizedTest
    @EnumSource(value = AllSequences.class, mode = EnumSource.Mode.EXCLUDE,
            names = { "JULIA", "JULIA_GENERALIZED" })
    void testFactoriesUnsupported(AllSequences sequence) {
        UnsupportedOperationException exception = assertThrows(
                UnsupportedOperationException.class,
<<<<<<<< HEAD:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/JuliaRotationGeneralizedTest.java
                () -> JuliaRotationGeneralized.factories(sequence, C, STEP));
========
                () -> GeneralizedJuliaRotation.factories(sequence, C, STEP));
>>>>>>>> 739edabb804610c40a6455d842ca099d11a2b566:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/GeneralizedJuliaRotationTest.java
        assertTrue(exception.getMessage().contains(sequence.name()));
    }

    // ------------------------------------------------- factories: invalid parameters

    /**
<<<<<<<< HEAD:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/JuliaRotationGeneralizedTest.java
     * Test method for {@link JuliaRotationGeneralized#factories(AllSequences, IComplex, double)}
========
     * Test method for {@link GeneralizedJuliaRotation#factories(AllSequences, IComplex, double)}
>>>>>>>> 739edabb804610c40a6455d842ca099d11a2b566:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/GeneralizedJuliaRotationTest.java
     * with invalid parameters.
     */
    @Test
    void testFactoriesInvalidParameters() {
        assertThrows(NullPointerException.class,
<<<<<<<< HEAD:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/JuliaRotationGeneralizedTest.java
                () -> JuliaRotationGeneralized.factories(null, C, STEP));
        assertThrows(NullPointerException.class,
                () -> JuliaRotationGeneralized.factories(AllSequences.JULIA, null, STEP));
        assertThrows(IllegalArgumentException.class,
                () -> JuliaRotationGeneralized.factories(AllSequences.JULIA, C, 0));
        assertThrows(IllegalArgumentException.class,
                () -> JuliaRotationGeneralized.factories(AllSequences.JULIA_GENERALIZED, C, -1));
========
                () -> GeneralizedJuliaRotation.factories(null, C, STEP));
        assertThrows(NullPointerException.class,
                () -> GeneralizedJuliaRotation.factories(AllSequences.JULIA, null, STEP));
        assertThrows(IllegalArgumentException.class,
                () -> GeneralizedJuliaRotation.factories(AllSequences.JULIA, C, 0));
        assertThrows(IllegalArgumentException.class,
                () -> GeneralizedJuliaRotation.factories(AllSequences.JULIA_GENERALIZED, C, -1));
>>>>>>>> 739edabb804610c40a6455d842ca099d11a2b566:src/test/java/fr/univartois/butinfo/fractalens/complex/sequence/GeneralizedJuliaRotationTest.java
    }

}
