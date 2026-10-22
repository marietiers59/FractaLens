package fr.univartois.butinfo.fractalens.complex.sequence;

import java.util.Objects;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;

/**
 * The JuliaRotation class provides the tools needed to animate a Julia fractal,
 * by rotating the constant {@code c} used in the Julia's sequence around the origin
 * of the complex plane.
 *
 * @author Marie Tiers
 *
 * @version 0.1.0
 */
public final class JuliaRotation {

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

}
