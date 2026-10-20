package fr.univartois.butinfo.fractalens.complex.sequence;

import fr.univartois.butinfo.fractalens.complex.IComplex;

import java.util.Iterator;

/**
 *
 */
public class ComplexSequenceIterator implements Iterator<IComplex>{

    /**
    * the sequence
     */
    private final IComplexSequence sequence;

    /**
     *  the therm that will be returned by the next
     */
    private IComplex actual;

    /**
     *The maximum number of terms
     */
    private final int maxIterations;

    /**
     *The number of terms
     */
    private int count;

    /**
     * construccts a new ComplexSequenceIterator
     * @param sequence      the sequence
     * @param actual        the actual term
     * @param maxIterations The maximum number of terms
     */
    public ComplexSequenceIterator(IComplexSequence sequence,IComplex actual, int maxIterations) {
        this.sequence = sequence;
        this.maxIterations = maxIterations;
        this.actual = actual;
        this.count = 0;
    }

    /**
     *  Checks if the iterator has more terms.
     *  @return {@code true} if the iterator has more terms and the modulus of {@code actual} is < 2, {@code false} otherwise.
     */
    public boolean hasNext() {
        return count < maxIterations && actual.abs() <2;
    }

    /**
     * the therm moves to the next one
     * @return the term before next()
     */
    public IComplex next() {
        IComplex previous = actual;
        actual = sequence.computeNext(actual);
        count++;
        return previous;

    }

}
