package fr.univartois.butinfo.fractalens.complex.sequence;

import fr.univartois.butinfo.fractalens.complex.IComplex;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The ComplexSequenceIterator class allows iterating over the terms of a complex number sequence
 * up to a maximum number of iterations or until the divergence criterion is met.
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
     *  @return {@code true} if the iterator has more terms and the modulus of {@code actual} is <= 2, {@code false} otherwise.
     */
    public boolean hasNext() {
        return count < maxIterations && actual.abs() <=2;
    }

    /**
     * the therm moves to the next one
     * @return the term before next()
     */
    public IComplex next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more terms");
        }
        IComplex previous = actual;
        actual = sequence.computeNext(actual);
        count++;
        return previous;

    }

}
