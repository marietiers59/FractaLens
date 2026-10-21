package fr.univartois.butinfo.fractalens.complex.sequence;

import java.util.Iterator;
import java.util.function.BinaryOperator;

import fr.univartois.butinfo.fractalens.complex.IComplex;

/**
 * A complex sequence defined by an arbitrary function f.
 */
public class ComplexSequenceLambda implements IComplexSequence {

    /** The function computing the next term from z0 and the previous term. */
    private final BinaryOperator<IComplex> function;

    /** The first term of the sequence. */
    private final IComplex z0;

    /** The maximum number of terms to compute. */
    private final int nbTerms;

    public ComplexSequenceLambda(BinaryOperator<IComplex> function, IComplex z0, int nbTerms) {
        this.function = function;
        this.z0 = z0;
        this.nbTerms = nbTerms;
    }

    /**
     * Computes the next term of the sequence, based on the previous one.
     * @param previous The previous value in the sequence.
     *
     * @return The next value in the sequence.
     */
    @Override
    public IComplex computeNext(IComplex previous) {
        return function.apply(z0, previous);
    }

    /**
     * Returns an iterator over the elements of this sequence.
     * @return an Iterator over the elements of this sequence.
     */
    @Override
    public Iterator<IComplex> iterator() {
        return new ComplexSequenceIterator(this, z0, nbTerms);
    }

}