package fr.univartois.butinfo.fractalens.complex.sequence;

import fr.univartois.butinfo.fractalens.complex.IComplex;

import java.util.Iterator;

/**
 * The ComplexSequenceTricorn class implements the Tricorn's sequence (also known as
 * Mandelbar), which is a sequence of complex numbers defined by the following formula:
 * {@code t(n) = conj(t(n-1))² + c}
 *
 * @author Marie Tiers
 */
public class ComplexSequenceTricorn implements IComplexSequence {

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
     * Creates a new Tricorn's sequence using the given parameters.
     *
     * @param c The constant number in the definition of the considered Tricorn's sequence.
     * @param nbTerms The maximum number of terms to compute.
     * @param z0 The initial value of the sequence.
     */
    public ComplexSequenceTricorn(IComplex c, int nbTerms, IComplex z0) {
        this.c = c;
        this.nbTerms = nbTerms;
        this.z0 = z0;
    }

    /**
     * Computes the next term of the sequence, based on the previous one, by squaring
     * the conjugate of the previous term and adding the constant {@code c}.
     *
     * @param previous The previous value in the sequence.
     *
     * @return The next value in the sequence.
     */
    @Override
    public IComplex computeNext(IComplex previous) {
        return previous.conjugate().power(2).add(c);
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
