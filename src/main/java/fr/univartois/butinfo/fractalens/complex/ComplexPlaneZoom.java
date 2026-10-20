package fr.univartois.butinfo.fractalens.complex;

/**
 * The ComplexPlaneZoom class change a decorated plan into a zoomed plan.
 *
 * @author Thibault BREGUET
 *
 * @version 0.1.0
 */

public class ComplexPlaneZoom extends ComplexPlaneDecorator {
	
	private final double valeurZoom;
	
	/**
     * Give an instance of ComplexPlaneZoom.
     * 
     * @param valeurZoom Indicate the value of the zoom.
     * @param complexPlane A complex plane from the decorator.
     *
     */

	public ComplexPlaneZoom(double valeurZoom, IComplexPlane complexPlane) {
		super(complexPlane);
		this.valeurZoom = valeurZoom;
	}
	
	/**
     * .
     * The function asComplex give the complex number of the pixel whose coordinates are entered.
     * 
     * @param row The pixel row.
     * @param column The pixel column.
     * 
     * @return The complex number of the chosen pixel.
     */

	@Override
	public IComplex asComplex(int row, int column) {
		if (valeurZoom >= 0 && valeurZoom <= 1) {
			IComplex z = super.asComplex(row, column);
			z = z.multiply(valeurZoom);
			return z;
		}
		else {
			throw new IllegalArgumentException("La valeur du zoom doit être compris entre 0 et 1.");
		}
	}
}
