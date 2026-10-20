package fr.univartois.butinfo.fractalens.complex;

/**
 * The ComplexPlaneTranslate class extends the ComplexPlaneDecorator class and adds a translation
 * to the complex plane.
 * @author Theo Brureau
 */
public class ComplexPlaneTranslate extends ComplexPlaneDecorator {
    /**
     * The value to translate.
     */
    private IComplex valTranslate;

    /**
     * Constructor of the ComplexPlaneTranslate class.
     * @param valTranslate The value to translate.
     * @param complexPlane The complex plane to decorate.
     */
    public ComplexPlaneTranslate(IComplex valTranslate, IComplexPlane complexPlane) {
        super(complexPlane);
        this.valTranslate = valTranslate;
    }

    /**
     * Returns the complex number corresponding to the pixel at the given row and column.
     * @param row The row of the pixel in the image.
     * @param column The column of the pixel in the image.
     *
     * @return The complex number at the given position.
     */
    @Override
    public IComplex asComplex(int row, int column) {
        return super.asComplex(row, column).add(valTranslate);
    }
}
