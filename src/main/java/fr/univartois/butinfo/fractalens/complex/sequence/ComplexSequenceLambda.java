package fr.univartois.butinfo.fractalens.complex.sequence;

import java.util.Iterator;
import java.util.function.BinaryOperator;

import fr.univartois.butinfo.fractalens.complex.IComplex;

/**
 * The ComplexSequenceLambda class implements a complex sequence defined by an arbitrary
 * function {@code f}, such that {@code z(n+1) = f(z0, z(n))}.
 * It allows to define many different sequences (Julia, Mandelbrot, Multibrot, Tricorn,
 * etc.) with a lambda expression, without creating a new class for each of them.
 */
public class ComplexSequenceLambda implements IComplexSequence {

    /**
     * The function computing the next term from z0 and the previous term.
     */
    private final BinaryOperator<IComplex> function;

    /**
     * The first term of the sequence.
     */
    private final IComplex z0;

    /**
     * The maximum number of terms to compute.
     */
    private final int nbTerms;

    /**
     * Creates a new ComplexSequenceLambda.
     *
     * @param function The function computing the next term from z0 and the previous term.
     * @param z0 The first term of the sequence.
     * @param nbTerms The maximum number of terms to compute.
     */
    public ComplexSequenceLambda(BinaryOperator<IComplex> function, IComplex z0, int nbTerms) {
        this.function = function;
        this.z0 = z0;
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
        return function.apply(z0, previous);
    }

    /**
     * Gives an iterator over the terms of this sequence.
     *
     * @return An iterator over the terms of this sequence.
     */
    @Override
    public Iterator<IComplex> iterator() {
        return new ComplexSequenceIterator(this, z0, nbTerms);
    }
}