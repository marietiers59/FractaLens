package fr.univartois.butinfo.fractalens.complex.sequence;

import fr.univartois.butinfo.fractalens.complex.IComplex;

import java.util.Iterator;

/**
 * The ComplexSequenceMandelbrot class implements the Mandelbrot's sequence, which is a
 * sequence of complex numbers that is defined by the following formula:
 * {@code z(n) = z(n-1)² + c}
 */
public class ComplexSequenceMandelbrot implements IComplexSequence {

    /**
     * The constant number in the definition of the considered Mandelbrot's sequence.
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
     * Creates a new Mandelbrot's sequence using the given parameters.
     *
     * @param c The constant number in the definition of the considered Mandelbrot's sequence.
     * @param nbTerms The maximum number of terms to compute.
     * @param z0 The initial value of the sequence.
     */
    public ComplexSequenceMandelbrot(IComplex c, int nbTerms, IComplex z0) {
        this.c = c;
        this.nbTerms = nbTerms;
        this.z0 = z0;
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
        return previous.power(2).add(c);
    }

    /**
     * @return an Iterator over the elements of this sequence.
     */
    @Override
    public Iterator<IComplex> iterator() {
        return new ComplexSequenceIterator(this, z0, nbTerms);
    }

}