package fr.univartois.butinfo.fractalens.complex.sequence;

import fr.univartois.butinfo.fractalens.complex.IComplex;

import java.util.Iterator;
import java.util.function.BinaryOperator;

/**
 * The ComplexSequenceTricornGeneralisee class implements the generalized Tricorn's
 * sequence, which is a sequence of complex numbers defined by the following formula:
 * {@code t(n) = conj(t(n-1))^p + c}
 *
 * The formula of the sequence is represented by a lambda expression of type
 * {@link BinaryOperator}, built once at the creation of the sequence.
 *
 * @author Marie Tiers
 */
public class ComplexSequenceTricornGeneralisee implements IComplexSequence {

    /**
     * The constant number in the definition of the considered Tricorn's sequence.
     */
    private final IComplex c;

    /**
     * The maximum number of terms to compute.
     */
    private final int nbTerms;

    /**
     * The initial value of the sequence.
     */
    private final IComplex z0;

    /**
     * The formula defining the sequence, taking the previous term and the constant
     * {@code c} as parameters, and returning the next term.
     */
    private final BinaryOperator<IComplex> formula;

    /**
     * Creates a new generalized Tricorn's sequence using the given parameters.
     *
     * @param c The constant number in the definition of the considered Tricorn's sequence.
     * @param nbTerms The maximum number of terms to compute.
     * @param z0 The initial value of the sequence.
     * @param p The power used in the definition of the sequence.
     */
    public ComplexSequenceTricornGeneralisee(IComplex c, int nbTerms, IComplex z0, int p) {
        this.c = c;
        this.nbTerms = nbTerms;
        this.z0 = z0;
        this.formula = (z, constant) -> z.conjugate().power(p).add(constant);
    }

    /**
     * Computes the next term of the sequence, based on the previous one, by applying
     * the formula of the sequence.
     *
     * @param previous The previous value in the sequence.
     *
     * @return The next value in the sequence.
     *
     * @throws IllegalArgumentException If the power of the sequence is negative.
     */
    @Override
    public IComplex computeNext(IComplex previous) {
        return formula.apply(previous, c);
    }

    /**
     * Returns an iterator over the terms of this sequence.
     *
     * @return An iterator over the terms of this sequence.
     */
    @Override
    public Iterator<IComplex> iterator() {
        return new ComplexSequenceIterator(this, z0, nbTerms);
    }

}
