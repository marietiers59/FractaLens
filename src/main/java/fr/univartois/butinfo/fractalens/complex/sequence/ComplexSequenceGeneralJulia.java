package fr.univartois.butinfo.fractalens.complex.sequence;

import java.util.Iterator;

import fr.univartois.butinfo.fractalens.complex.IComplex;

public class ComplexSequenceGeneralJulia implements IComplexSequence{

	/**
    * The initial value of the sequence.
    */
   private final IComplex z0;
   /**
    * The constant added number in the definition of the considered Julia's sequence.
    */
   private final IComplex c;
   /**
    * The maximum number of terms to compute.
    */
   private final int nbTerms;
   
   /**
    * The constant power number in the definition of the considered Julia's sequence.
    */
   private final int p;
   
   /**
    * Creates a new Julia's sequence using the given parameters.
    *
    * @param z0 The initial value of the sequence.
    * @param c The constant added number in the definition of the considered Julia's sequence.
    * @param nbTerms The maximum number of terms to compute.
    * @param p The constant power number in the definition of the considered Julia's sequence.
    */
   public ComplexSequenceGeneralJulia(IComplex z0, IComplex c, int nbTerms, int p) {
       this.z0 = z0;
       this.c = c;
       this.nbTerms = nbTerms;
       this.p = p;
   }
   
   /**
    * @return an Iterator over the elements of this sequence.
    */
   @Override
   public Iterator<IComplex> iterator() {
	   return new ComplexSequenceIterator(this, z0, nbTerms);
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
	   return previous.power(p).add(c);
   }

}
