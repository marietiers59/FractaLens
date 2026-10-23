package fr.univartois.butinfo.fractalens.complex.sequence;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;

import java.util.Iterator;

/**
 * The ComplexSequenceJulia class implements the Julia's sequence, which is a sequence of complex numbers that is defined by the following formula:
 * bn(z) = (|Re(bn−1(z))| + i|Im(bn−1(z))|)² + z0
 * @author Théo Brureau
 */
public class ComplexSequenceBurningShip implements IComplexSequence{
    /**
     * The maximum number of terms to compute.
     */
    private final int nbTerms;
    /**
     * The initial value of the sequence.
     */
    private final IComplex z0;

    /**
     * Constructor of the ComplexSequenceBurningShip class.
     * @param nbTerms The maximum number of terms to compute.
     * @param z0 The initial value of the sequence.
     */
    public ComplexSequenceBurningShip(int nbTerms, IComplex z0) {
        this.nbTerms = nbTerms;
        this.z0 = z0;
    }

    /**
     * Computes the next term of the sequence, based on the previous one.
     * @param previous The previous value in the sequence.
     *
     * @return The next value in the sequence.
     */
    @Override
    public IComplex computeNext(IComplex previous) {
        IComplex z = new Complex(Math.abs(previous.getRealPart()), Math.abs(previous.getImaginaryPart()));
        return z.multiply(z).add(z0);
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
