package fr.univartois.butinfo.fractalens.complex.sequence;

import fr.univartois.butinfo.fractalens.complex.IComplex;

import java.util.Iterator;

/**
 * The ComplexSequenceJulia class implements the Julia's sequence, which is a sequence of complex numbers that is defined by the following formula:
 * jn(z) = jn−1(z)2 + c
 */
public class ComplexSequenceJulia implements IComplexSequence{
    /**
     * The initial value of the sequence.
     */
    private final IComplex z0;
    /**
     * The constant number in the definition of the considered Julia's sequence.
     */
    private final IComplex c;
    /**
     * The maximum number of terms to compute.
     */
    private final int nbTerms;

    /**
     * Creates a new Julia's sequence using the given parameters.
     *
     * @param z0 The initial value of the sequence.
     * @param c The constant number in the definition of the considered Julia's sequence.
     * @param nbTerms The maximum number of terms to compute.
     */
    public ComplexSequenceJulia(IComplex z0, IComplex c, int nbTerms) {
        this.z0 = z0;
        this.c = c;
        this.nbTerms = nbTerms;
    }

    /**
     * Computes the next term of the sequence, based on the previous one.
     *
     * @param previous The previous value in the sequence.
     *
     * @return The next value in the sequence.
     */
    @Override
    public IComplex computeNext(IComplex previous) {
        return previous.multiply(previous).add(c);
    }

    /**
     * @return an Iterator over the elements of this sequence.
     */
    @Override
    public Iterator<IComplex> iterator() {
        return newComplexSequenceIterator(this, z0, nbTerms);
    }
}
