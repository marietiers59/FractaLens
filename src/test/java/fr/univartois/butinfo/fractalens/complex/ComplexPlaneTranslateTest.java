package fr.univartois.butinfo.fractalens.complex;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link ComplexPlaneTranslate} class.
 *
 * @author Théo Brureau
 */
class ComplexPlaneTranslateTest {

    /**
     * The height of the plane used in the tests.
     */
    private static final int HEIGHT = 600;

    /**
     * The width of the plane used in the tests.
     */
    private static final int WIDTH = 800;

    /**
     * Creates a new complex number.
     *
     * @param re The real part.
     * @param im The imaginary part.
     *
     * @return The created complex number.
     */
    private static IComplex complex(double re, double im) {
        return new Complex(re, im);
    }

    /**
     * Creates a base (non-decorated) complex plane.
     *
     * @return The base plane.
     */
    private static IComplexPlane basePlane() {
        return new ComplexPlane(HEIGHT, WIDTH);
    }

    /**
     * Checks that the translated plane is a complex plane.
     */
    @Test
    void testIsAComplexPlane() {
        assertEquals(true, new ComplexPlaneTranslate(complex(1, 1), basePlane()) instanceof IComplexPlane);
    }

    /**
     * Checks that translating by 0 does not change anything.
     */
    @Test
    void testTranslationByZero() {
        IComplexPlane base = basePlane();
        IComplexPlane plane = new ComplexPlaneTranslate(complex(0, 0), base);
        assertEquals(base.asComplex(0, 0), plane.asComplex(0, 0));
        assertEquals(base.asComplex(300, 400), plane.asComplex(300, 400));
        assertEquals(base.asComplex(599, 799), plane.asComplex(599, 799));
    }

    /**
     * Checks that the translation is added to the top-left pixel.
     */
    @Test
    void testTopLeft() {
        IComplexPlane plane = new ComplexPlaneTranslate(complex(1, 2), basePlane());
        assertEquals(complex(-399, 302), plane.asComplex(0, 0));
    }

    /**
     * Checks that the centre of the image is moved onto the translation value.
     */
    @Test
    void testCentreIsMovedToTranslation() {
        IComplex centre = complex(-0.47, -0.21);
        IComplexPlane plane = new ComplexPlaneTranslate(centre, basePlane());
        assertEquals(centre, plane.asComplex(HEIGHT / 2, WIDTH / 2));
    }

    /**
     * Checks a translation with negative real and imaginary parts.
     */
    @Test
    void testNegativeTranslation() {
        IComplexPlane plane = new ComplexPlaneTranslate(complex(-10, -20), basePlane());
        assertEquals(complex(-410, 280), plane.asComplex(0, 0));
    }

    /**
     * Checks a translation with non-integer values.
     */
    @Test
    void testDecimalTranslation() {
        IComplexPlane plane = new ComplexPlaneTranslate(complex(0.1, 0.6), basePlane());
        assertEquals(complex(-399.9, 300.6), plane.asComplex(0, 0));
    }

    /**
     * Checks that the translation is the same for every pixel.
     */
    @Test
    void testSameShiftForEveryPixel() {
        IComplex shift = complex(3, -4);
        IComplexPlane base = basePlane();
        IComplexPlane plane = new ComplexPlaneTranslate(shift, base);
        for (int row = 0; row < HEIGHT; row += 150) {
            for (int column = 0; column < WIDTH; column += 200) {
                assertEquals(base.asComplex(row, column).add(shift), plane.asComplex(row, column));
            }
        }
    }

    /**
     * Checks that the zoom is applied before the translation, so that the zoom
     * factor does not alter the new centre of the image.
     */
    @Test
    void testZoomThenTranslate() {
        IComplexPlane zoomed = new ComplexPlaneZoom(0.1, basePlane());
        IComplexPlane plane = new ComplexPlaneTranslate(complex(0.1, 0.6), zoomed);
        assertEquals(complex(-39.9, 30.6), plane.asComplex(0, 0));
        assertEquals(complex(0.1, 0.6), plane.asComplex(HEIGHT / 2, WIDTH / 2));
    }

    /**
     * Checks that two translations are cumulated.
     */
    @Test
    void testTwoTranslations() {
        IComplexPlane plane = new ComplexPlaneTranslate(complex(1, 1),
                new ComplexPlaneTranslate(complex(2, 3), basePlane()));
        assertEquals(complex(-397, 304), plane.asComplex(0, 0));
    }

    /**
     * Checks that the errors of the decorated plane (pixel outside the image)
     * are propagated.
     */
    @Test
    void testPixelOutsideImage() {
        IComplexPlane plane = new ComplexPlaneTranslate(complex(1, 1), basePlane());
        assertThrows(IllegalArgumentException.class, () -> plane.asComplex(HEIGHT, 0));
        assertThrows(IllegalArgumentException.class, () -> plane.asComplex(0, WIDTH));
        assertThrows(IllegalArgumentException.class, () -> plane.asComplex(HEIGHT, WIDTH));
    }
}