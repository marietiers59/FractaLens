package fr.univartois.butinfo.fractalens.complex.sequence;

import java.util.function.BiFunction;

import fr.univartois.butinfo.fractalens.complex.IComplex;
import fr.univartois.butinfo.fractalens.complex.Complex;

/**
 * The AllSequences class is an enumeration containing all the possibles sequences and the instructions for them
 *
 * @author Thibault BREGUET
 *
 * @version 0.1.0
 */

public enum AllSequences {
	
	Julia ((z0, nbTerms) -> new ComplexSequenceLambda((first, z) -> z.multiply(z).add(new Complex(-0.4, 06)), z0, nbTerms)),
	JuliaGeneralisee ((z0, nbTerms) -> new ComplexSequenceLambda((first, z) -> z.power(3).add(new Complex(0.285, 0.01)), z0, nbTerms)),
	Mandelbrot ((z0, nbTerms) -> new ComplexSequenceLambda((first, z) -> z.multiply(z).add(z0), z0, nbTerms)),
	MandelBrotGeneralisee((z0, nbTerms) -> new ComplexSequenceLambda((first, z) -> z.power(3).add(z0), z0, nbTerms)),
	//IComplex z = new Complex(Math.abs(previous.getRealPart()), Math.abs(previous.getImaginaryPart()));
    //return z.multiply(z).add(c);
	BurningShip((z0, nbTerms) -> new ComplexSequenceLambda((first, z) -> {
		IComplex z = new Complex(Math.abs(previous.getRealPart()), Math.abs(previous.getImaginaryPart()));
		return z.multiply(z).add(c);
	}, z0, nbTerms)),
	//IComplex z = new Complex(Math.abs(previous.getRealPart()), Math.abs(previous.getImaginaryPart()));
    //return z.power(p).add(c);
	BurningShipGeneralisee(z0, nbTerms) -> new ComplexSequenceLambda((first, z) -> {
		IComplex z = new Complex(Math.abs(previous.getRealPart()), Math.abs(previous.getImaginaryPart()));
	    return z.power(p).add(c);
	}, z0, nbTerms)(),
	Tricorn((z0, nbTerms) -> new ComplexSequenceLambda((first, z) -> z.conjugate().power(2).add(new Complex(-0.4, 06)), z0, nbTerms)),
	TricornGeneralisee((z0, nbTerms) -> new ComplexSequenceLambda((first, z) -> z.conjugate().power(3).add(new Complex(0.285, 0.01)), z0, nbTerms));
	
	/**
	 * 
	 */
	private BiFunction<IComplex, Integer, IComplexSequence> sequenceFactory;
	
	/**
	 * 
	 * @param sequenceFactory
	 */
	AllSequences(BiFunction<IComplex, Integer, IComplexSequence> sequenceFactory){
		this.sequenceFactory = sequenceFactory;
	}
}
