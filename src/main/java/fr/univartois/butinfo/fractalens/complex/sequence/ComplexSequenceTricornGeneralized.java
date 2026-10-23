package fr.univartois.butinfo.fractalens.complex.sequence;

import fr.univartois.butinfo.fractalens.complex.IComplex;

/**
 * The ComplexSequenceTricornGeneralized class implements the generalized Tricorn's
 * sequence, which is a sequence of complex numbers defined by the following formula:
 * {@code t(n) = conj(t(n-1))^p + c}
 *
 * This sequence is a particular case of {@link ComplexSequenceLambda}, whose function
 * is given by the lambda expression {@code (first, z) -> conj(z)^p + c}.
 *
 * @author Marie Tiers
 */
public class ComplexSequenceTricornGeneralized extends ComplexSequenceLambda {

    /**
     * Creates a new generalized Tricorn's sequence using the given parameters.
     *
     * @param c The constant number in the definition of the considered Tricorn's sequence.
     * @param nbTerms The maximum number of terms to compute.
     * @param z0 The initial value of the sequence.
     * @param p The power used in the definition of the sequence.
     */
    public ComplexSequenceTricornGeneralized(IComplex c, int nbTerms, IComplex z0, int p) {
        super((first, z) -> z.conjugate().power(p).add(c), z0, nbTerms);
    }

}
