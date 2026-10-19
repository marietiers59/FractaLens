package fr.univartois.butinfo.fractalens.complex;

/**
 * The ComplexPlane class handle how to link a position in an image to a number on
 * the complex plane.
 *
 * @author Thibault BREGUET
 *
 * @version 0.1.0
 */
public class ComplexPlane implements IComplexPlane{
	
	private int width;
	private int height;
	
	/**
     * Give an instance of ComplexPlane.
     *
     * @return An instance of ComplexPlane.
     */
	public ComplexPlane(int width, int height) {//On met 5 lignes pour une hauteur/largeur de 5 lignes
		this.height = height;
		this.width = width;
	}
	
	/**
     * Give the complex number of the pixel whose coordinates are entered.
     *
     * @return The complex number of the chosen pixel.
     */
	@Override
	public IComplex asComplex(int row, int column) {//On met 0 pour la première ligne
		if (row <= width && column <= height) {
			double a = column - width/2;
			double b = height/2 - row;
			IComplex z = new Complex(a, b);
			return z;
		}
		else {
			if (row > width && column > height){
				throw new IllegalArgumentException("La ligne et la colonne sont plus grand que la taille et la largeur de l'image");
			}
			else if (row > width) {
				throw new IllegalArgumentException("La ligne est plus grande que la largeur de l'image");
			}
			else {
				throw new IllegalArgumentException("La colonne est plus grande que la taille de l'image");
			}
		}
	}

}
