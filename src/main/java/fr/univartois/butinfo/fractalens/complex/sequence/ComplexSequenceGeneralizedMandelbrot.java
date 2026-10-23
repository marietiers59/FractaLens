package fr.univartois.butinfo.fractalens.complex.sequence;

import fr.univartois.butinfo.fractalens.complex.IComplex;

import java.util.Iterator;

/**
 * The ComplexSequenceMandelbrotGeneralisee class implements the generalized Mandelbrot's
 * sequence, which is a sequence of complex numbers that is defined by the following formula:
 * {@code z(n) = z(n-1)^p + z0}
 */
public class ComplexSequenceGeneralizedMandelbrot implements IComplexSequence {

    /**
     * The initial value of the sequence (also the constant added at each step).
     */
    private final IComplex z0;

    /**
     * The power of the sequence.
     */
    private final int p;

    /**
     * The maximum number of terms to compute.
     */
    private final int nbTerms;

    /**
     * Creates a new generalized Mandelbrot's sequence using the given parameters.
     *
     * @param z0 The initial value of the sequence.
     * @param p The power of the sequence.
     * @param nbTerms The maximum number of terms to compute.
     */
    public ComplexSequenceGeneralizedMandelbrot(IComplex z0, int p, int nbTerms) {
        this.z0 = z0;
        this.p = p;
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
        return previous.power(p).add(z0);
    }

    /**
     * @return an Iterator over the elements of this sequence.
     */
    @Override
    public Iterator<IComplex> iterator() {
        return new ComplexSequenceIterator(this, z0, nbTerms);
    }

}