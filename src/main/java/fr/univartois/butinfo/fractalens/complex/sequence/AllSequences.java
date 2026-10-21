package fr.univartois.butinfo.fractalens.complex.sequence;

import java.util.function.BiFunction;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;

/**
 * The AllSequences enumeration contains all the available complex sequences, along with
 * the way to create each of them for a given pixel.
 *
 * @author Thibault BREGUET
 *
 * @version 0.1.0
 */
public enum AllSequences {

	/**
	 * The Julia's sequence: {@code j(n) = j(n-1)² + c}.
	 */
	JULIA((z0, nbTerms) -> new ComplexSequenceLambda(
			(first, z) -> z.multiply(z).add(new Complex(-0.4, 0.6)), z0, nbTerms)),

	/**
	 * The generalized Julia's sequence: {@code j(n) = j(n-1)³ + c}.
	 */
	JULIA_GENERALIZED((z0, nbTerms) -> new ComplexSequenceLambda(
			(first, z) -> z.power(3).add(new Complex(0.285, 0.01)), z0, nbTerms)),

	/**
	 * The Mandelbrot's sequence: {@code m(n) = m(n-1)² + z0}.
	 */
	MANDELBROT((z0, nbTerms) -> new ComplexSequenceLambda(
			(first, z) -> z.multiply(z).add(z0), z0, nbTerms)),

	/**
	 * The generalized Mandelbrot's sequence: {@code m(n) = m(n-1)³ + z0}.
	 */
	MANDELBROT_GENERALIZED((z0, nbTerms) -> new ComplexSequenceLambda(
			(first, z) -> z.power(3).add(z0), z0, nbTerms)),

	/**
	 * The Burning Ship's sequence: {@code b(n) = (|Re(b(n-1))| + i|Im(b(n-1))|)² + c}.
	 */
	BURNING_SHIP((c, nbTerms) -> new ComplexSequenceBurningShip(
			c, nbTerms, new Complex(0, 0))),

	/**
	 * The generalized Burning Ship's sequence, with a power of 3.
	 */
	BURNING_SHIP_GENERALIZED((c, nbTerms) -> new ComplexSequenceGeneralizedBurningShip(
			c, nbTerms, new Complex(0, 0), 3)),

	/**
	 * The Tricorn's sequence: {@code t(n) = conj(t(n-1))² + c}.
	 */
	TRICORN((z0, nbTerms) -> new ComplexSequenceLambda(
			(first, z) -> z.conjugate().power(2).add(new Complex(-0.4, 0.6)), z0, nbTerms)),

	/**
	 * The generalized Tricorn's sequence: {@code t(n) = conj(t(n-1))³ + c}.
	 */
	TRICORN_GENERALIZED((z0, nbTerms) -> new ComplexSequenceLambda(
			(first, z) -> z.conjugate().power(3).add(new Complex(0.285, 0.01)), z0, nbTerms));

	/**
	 * The function creating the sequence to study for a given pixel.
	 */
	private final BiFunction<IComplex, Integer, IComplexSequence> sequenceFactory;

	/**
	 * Creates a new AllSequences.
	 *
	 * @param sequenceFactory The function creating the sequence for a given pixel.
	 */
	AllSequences(BiFunction<IComplex, Integer, IComplexSequence> sequenceFactory) {
		this.sequenceFactory = sequenceFactory;
	}

	/**
	 * Gives the function creating the sequence associated with this constant.
	 * The function takes the complex number of the pixel and the number of terms to
	 * compute.
	 *
	 * @return The function creating the sequence.
	 */
	public BiFunction<IComplex, Integer, IComplexSequence> getSequenceFactory() {
		return sequenceFactory;
	}

}