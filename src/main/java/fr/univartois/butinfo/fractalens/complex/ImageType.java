package fr.univartois.butinfo.fractalens.complex;

/**
 * The ImageType enumeration lists the kinds of images that can be generated from the
 * command line.
 *
 * @author FractaLens - Groupe 5
 *
 * @version 0.1.0
 */
public enum ImageType {

    /**
     * A single image of a fractal.
     */
    SINGLE,

    /**
     * An animated image zooming progressively on the center of the fractal.
     */
    ZOOM,

    /**
     * An animated image in which the constant of a Julia's sequence is rotated.
     */
    ROTATION
}