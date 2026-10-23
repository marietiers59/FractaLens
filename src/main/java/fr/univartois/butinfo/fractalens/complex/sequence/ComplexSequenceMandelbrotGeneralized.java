package fr.univartois.butinfo.fractalens.complex.sequence;

import fr.univartois.butinfo.fractalens.complex.IComplex;

import java.util.Iterator;

/**
 * The ComplexSequenceMandelbrotGeneralized class implements the generalized Mandelbrot's
 * sequence, which is a sequence of complex numbers that is defined by the following formula:
 * {@code z(n) = z(n-1)^p + z0}
 */
<<<<<<<< HEAD:src/main/java/fr/univartois/butinfo/fractalens/complex/sequence/ComplexSequenceMandelbrotGeneralized.java
public class ComplexSequenceMandelbrotGeneralized implements IComplexSequence {
========
public class ComplexSequenceGeneralizedMandelbrot implements IComplexSequence {
>>>>>>>> 739edabb804610c40a6455d842ca099d11a2b566:src/main/java/fr/univartois/butinfo/fractalens/complex/sequence/ComplexSequenceGeneralizedMandelbrot.java

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
<<<<<<<< HEAD:src/main/java/fr/univartois/butinfo/fractalens/complex/sequence/ComplexSequenceMandelbrotGeneralized.java
    public ComplexSequenceMandelbrotGeneralized(IComplex z0, int p, int nbTerms) {
========
    public ComplexSequenceGeneralizedMandelbrot(IComplex z0, int p, int nbTerms) {
>>>>>>>> 739edabb804610c40a6455d842ca099d11a2b566:src/main/java/fr/univartois/butinfo/fractalens/complex/sequence/ComplexSequenceGeneralizedMandelbrot.java
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