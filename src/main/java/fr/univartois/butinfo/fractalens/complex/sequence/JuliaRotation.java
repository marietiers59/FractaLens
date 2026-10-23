package fr.univartois.butinfo.fractalens.complex.sequence;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;

/**
 * The JuliaRotation class provides the tools needed to animate a Julia fractal,
 * by rotating the constant {@code c} used in the Julia's sequence around the origin
 * of the complex plane.
 *
 * Each image of the animation corresponds to an angle {@code theta}, taken between
 * {@code 0} (included) and {@code 2 * PI} (excluded) with a regular step.
 * For each of these angles, the constant {@code c} is replaced by
 * {@code c * (cos(theta) + i sin(theta))}, always computed from the original value
 * of {@code c}.
 *
 * @author Marie Tiers
 *
 * @version 0.1.0
 */
public final class JuliaRotation {

    /**
     * The angle of a full turn, in radians.
     */
    private static final double FULL_TURN = 2 * Math.PI;

    /**
     * The tolerance used to exclude the angle {@code 2 * PI} despite rounding errors
     * (with a step of {@code 2 * PI / n}, {@code n * step} may be slightly lower
     * than {@code 2 * PI}).
     */
    private static final double EPSILON = 1e-9;

    /**
     * Prevents the instantiation of this utility class.
     */
    private JuliaRotation() {
        throw new AssertionError("No instances of JuliaRotation");
    }

    /**
     * Rotates a complex number around the origin of the complex plane.
     * The rotation is computed as {@code c * (cos(theta) + i sin(theta))}: the modulus
     * of {@code c} is kept, and {@code theta} is added to its argument.
     *
     * @param c The complex number to rotate.
     * @param theta The angle of the rotation, in radians.
     *
     * @return The rotated complex number.
     *
     * @throws NullPointerException If {@code c} is {@code null}.
     */
    public static IComplex rotate(IComplex c, double theta) {
        Objects.requireNonNull(c, "The complex number to rotate must not be null");
        IComplex rotation = new Complex(Math.cos(theta), Math.sin(theta));
        return c.multiply(rotation);
    }

    /**
     * Computes the angles of the animation, from {@code 0} (included) to
     * {@code 2 * PI} (excluded), separated by the given step.
     * The angle {@code 2 * PI} is excluded because it gives the same image as the
     * angle {@code 0}: keeping both would make the looping animation stutter.
     *
     * Each angle is computed as {@code k * step} (and not by adding the step to the
     * previous angle), so that rounding errors do not accumulate.
     *
     * @param step The difference between two successive angles, in radians.
     *
     * @return The angles of the animation, in increasing order.
     *
     * @throws IllegalArgumentException If the step is not a strictly positive finite
     *         number.
     */
    public static List<Double> angles(double step) {
        if (Double.isNaN(step) || step <= 0 || Double.isInfinite(step)) {
            throw new IllegalArgumentException("The step must be a strictly positive finite number");
        }

        List<Double> angles = new ArrayList<>();
        for (int k = 0; k * step < FULL_TURN - EPSILON; k++) {
            angles.add(k * step);
        }
        return List.copyOf(angles);
    }

    /**
     * Creates the factories of the Julia's sequences used by the images of a rotation
     * animation: one factory per angle given by {@link #angles(double)}.
     * Each factory takes the complex number associated with a pixel and the maximum
     * number of terms, and creates the Julia's sequence whose constant is {@code c}
     * rotated by the corresponding angle.
     * These factories can be passed directly to the {@code withSequence()} method of
     * the fractal generator builder.
     *
     * @param c The constant of the Julia's sequence, before any rotation.
     * @param step The difference between two successive angles, in radians.
     *
     * @return The factories of the sequences, one per image of the animation.
     *
     * @throws NullPointerException If {@code c} is {@code null}.
     * @throws IllegalArgumentException If the step is not a strictly positive finite
     *         number.
     */
    public static List<BiFunction<IComplex, Integer, IComplexSequence>> factories(
            IComplex c, double step) {
        Objects.requireNonNull(c, "The constant of the sequence must not be null");

        List<BiFunction<IComplex, Integer, IComplexSequence>> factories = new ArrayList<>();
        for (double theta : angles(step)) {
            // The rotation is computed once per image, and not once per pixel.
            IComplex rotatedC = rotate(c, theta);
            factories.add((z0, nbTerms) -> new ComplexSequenceJulia(z0, rotatedC, nbTerms));
        }
        return List.copyOf(factories);
    }

}
