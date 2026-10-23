package fr.univartois.butinfo.fractalens.complex;

/**
 * The ComplexPlaneDecorator class change a ComplexPlane into a decorated plan
 * in order to turn it into either a zoomed plan or a translated one
 *
 * @author Thibault BREGUET
 *
 * @version 0.1.0
 */

abstract class ComplexPlaneDecorator implements IComplexPlane {

	private IComplexPlane complexPlane;
	
	/**
     * Give an instance of ComplexPlaneDecorator.
     * 
     * @param complexPlane A complex plane.
     * 
     * @return An instance of ComplexPlaneDecorator.
     */

	protected ComplexPlaneDecorator(IComplexPlane complexPlane) {
		this.complexPlane = complexPlane;
	}
	
	/**
     * Delegate the calling of the function asComplex to the class complexPlane.
     * The function asComplex give the complex number of the pixel whose coordinates are entered.
     * 
     * @param row The pixel row.
     * @param column The pixel column.
     * 
     * @return The complex number of the chosen pixel.
     */

	@Override
	public IComplex asComplex(int row, int column) {
		return complexPlane.asComplex(row, column);
	}
}
