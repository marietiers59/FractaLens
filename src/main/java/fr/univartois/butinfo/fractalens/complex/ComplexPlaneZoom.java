package fr.univartois.butinfo.fractalens.complex;

public class ComplexPlaneZoom extends ComplexPlaneDecorator{
	
	private double valeurZoom;
	
	//Insérer ici le constructeur (param = valeurzoom et le plan complex du decorator on fait un super)
	public ComplexPlaneZoom(double valeurZoom, IComplexPlane complexPlane) {
		super(complexPlane);
		this.valeurZoom = valeurZoom;
	}
	
	//Je réimplémente asComplex
	@Override
	public IComplex asComplex(int row, int column) {
		return super.asComplex(row, column).multiply(valeurZoom);
	}
}
