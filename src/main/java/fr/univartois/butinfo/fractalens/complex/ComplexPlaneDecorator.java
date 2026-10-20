package fr.univartois.butinfo.fractalens.complex;

//Je transforme ici ComplexPlane en un plan décoré pour pouvoir après le transformer soit en plan zoomé, soit en plan translaté (le centre a changé)
abstract class ComplexPlaneDecorator implements IComplexPlane{

	private IComplexPlane complexPlane;
	
	//Insérer ici le constructeur qui prend en paramètre un plan complex
	public ComplexPlaneDecorator(IComplexPlane complexPlane) {
		this.complexPlane = complexPlane;
	}
	
	//Délège à l'objet décoré l'appel de méthode
	@Override
	public IComplex asComplex(int row, int column) {
		return complexPlane.asComplex(row, column);
	}

}
