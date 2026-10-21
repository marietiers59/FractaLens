package fr.univartois.butinfo.fractalens.complex.sequence;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;

import java.util.Iterator;

public class ComplexSequenceBurningShip implements IComplexSequence{
    private final IComplex c;
    private final int nbTerms;
    private IComplex z0;

    public ComplexSequenceBurningShip(IComplex c, int nbTerms, IComplex z0) {
        this.c = c;
        this.nbTerms = nbTerms;
        this.z0 = z0;
    }

    @Override
    public IComplex computeNext(IComplex previous) {
        IComplex z = new Complex(Math.abs(previous.getRealPart()), Math.abs(previous.getImaginaryPart()));
        return z.multiply(z).add(c);
    }

    @Override
    public Iterator<IComplex> iterator() {
        return new ComplexSequenceIterator(this, z0, nbTerms);
    }
}
