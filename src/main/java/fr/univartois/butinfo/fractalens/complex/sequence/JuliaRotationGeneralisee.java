package fr.univartois.butinfo.fractalens.complex.sequence;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

import fr.univartois.butinfo.fractalens.complex.IComplex;

/**
 * The JuliaRotationGeneralisee class generalizes the rotation of {@link JuliaRotation}
 * to every sequence of {@link AllSequences}.
 *
 * The rotation only makes sense for the sequences whose constant {@code c} can be
 * chosen independently of the pixel, i.e. Julia's sequences: it is supported for
 * {@link AllSequences#JULIA} and {@link AllSequences#JULIA_GENERALIZED}.
 * For every other sequence (Mandelbrot, Burning Ship, Tricorn and their generalized
 * versions), an {@link UnsupportedOperationException} is thrown.
 *
 * Since the switches of this class are exhaustive, adding a new constant to
 * {@link AllSequences} requires deciding here whether it supports the rotation:
 * otherwise, the code does not compile.
 *
 * @author Marie Tiers
 *
 * @version 0.1.0
 */
public final class JuliaRotationGeneralisee {

    /**
     * The power used by {@link AllSequences#JULIA_GENERALIZED}.
     */
    private static final int GENERALIZED_JULIA_POWER = 3;

    /**
     * Prevents the instantiation of this utility class.
     */
    private JuliaRotationGeneralisee() {
        throw new AssertionError("No instances of JuliaRotationGeneralisee");
    }

    /**
     * Checks whether the rotation is supported for the given sequence.
     *
     * @param sequence The type of sequence.
     *
     * @return {@code true} if the sequence can be rotated, {@code false} otherwise.
     *
     * @throws NullPointerException If {@code sequence} is {@code null}.
     */
    public static boolean isSupported(AllSequences sequence) {
        Objects.requireNonNull(sequence, "The type of sequence must not be null");
        return switch (sequence) {
            case JULIA, JULIA_GENERALIZED -> true;
            case MANDELBROT, MANDELBROT_GENERALIZED,
                    BURNING_SHIP, BURNING_SHIP_GENERALIZED,
                    TRICORN, TRICORN_GENERALIZED -> false;
        };
    }

    /**
     * Creates the factories of the sequences used by the images of a rotation
     * animation, for the given type of sequence: one factory per angle given by
     * {@link JuliaRotation#angles(double)}.
     * Each factory creates the sequence whose constant is {@code c} rotated by the
     * corresponding angle.
     * These factories can be passed directly to the {@code withSequence()} method of
     * the fractal generator builder.
     *
     * @param sequence The type of sequence to rotate.
     * @param c The constant of the sequence, before any rotation.
     * @param step The difference between two successive angles, in radians.
     *
     * @return The factories of the rotated sequences, one per image of the animation.
     *
     * @throws NullPointerException If {@code sequence} or {@code c} is {@code null}.
     * @throws UnsupportedOperationException If the rotation is not supported for this
     *         type of sequence.
     * @throws IllegalArgumentException If the step is not a strictly positive finite
     *         number.
     */
    public static List<BiFunction<IComplex, Integer, IComplexSequence>> factories(
            AllSequences sequence, IComplex c, double step) {
        Objects.requireNonNull(sequence, "The type of sequence must not be null");
        Objects.requireNonNull(c, "The constant of the sequence must not be null");

        return switch (sequence) {
            case JULIA -> JuliaRotation.factories(c, step);
            case JULIA_GENERALIZED -> generalizedJuliaFactories(c, step);
            case MANDELBROT, MANDELBROT_GENERALIZED,
                    BURNING_SHIP, BURNING_SHIP_GENERALIZED,
                    TRICORN, TRICORN_GENERALIZED -> throw new UnsupportedOperationException(
                            "The rotation is not supported for " + sequence
                                    + ": only Julia's sequences can be rotated");
        };
    }

    /**
     * Creates the factories of the generalized Julia's sequences used by the images of
     * a rotation animation, with the power of {@link AllSequences#JULIA_GENERALIZED}.
     *
     * @param c The constant of the sequence, before any rotation.
     * @param step The difference between two successive angles, in radians.
     *
     * @return The factories of the rotated sequences, one per image of the animation.
     */
    private static List<BiFunction<IComplex, Integer, IComplexSequence>> generalizedJuliaFactories(
            IComplex c, double step) {
        List<BiFunction<IComplex, Integer, IComplexSequence>> factories = new ArrayList<>();
        for (double theta : JuliaRotation.angles(step)) {
            // The rotation is computed once per image, and not once per pixel.
            IComplex rotatedC = JuliaRotation.rotate(c, theta);
            factories.add((z0, nbTerms) -> new ComplexSequenceGeneralJulia(
                    z0, rotatedC, nbTerms, GENERALIZED_JULIA_POWER));
        }
        return List.copyOf(factories);
    }

}
