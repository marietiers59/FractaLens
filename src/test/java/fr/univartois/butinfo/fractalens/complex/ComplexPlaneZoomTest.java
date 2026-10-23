package fr.univartois.butinfo.fractalens.complex;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * The IComplexTest tests the base implementation of {@link ComplexPlaneZoom}.
 *
 * @author Thibault BREGUET
 *
 * @version 0.1.0
 */

class ComplexPlaneZoomTest {
	
	/**
     * Creates a new complex number.
     *
     * @param realPart The real part of the complex number.
     * @param imaginaryPart The imaginary part of the complex number.
     *
     * @return The created complex number.
     */
	
	private static IComplex createComplex(double realPart, double imaginaryPart) {
        return new Complex(realPart, imaginaryPart);
    }
	
	/**
     * Creates a new zoomed complex plane.
     *
     * @param valeurZoom The value of the zoom.
     *
     * @return The zoomed complex plane.
     */
	
	 private static ComplexPlaneZoom createComplexZoom(double valeurZoom) {
		 IComplexPlane complexPlane = new ComplexPlane(600,800);
			  
	        return new ComplexPlaneZoom(valeurZoom, complexPlane);
	 }
	
	 /**
	  * Test method for
	  * {@link fr.univartois.butinfo.fractalens.complex.IComplexPlane#asComplex(int, int)}.
	  * */
	@Test
	void testAsComplex() {
		ComplexPlaneZoom plane = createComplexZoom(0.1);
		
        IComplex topLeft = createComplex(-40, 30);
        assertEquals(topLeft, plane.asComplex(0, 0));
      
	}

}
