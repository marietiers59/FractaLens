package fr.univartois.butinfo.fractalens.generator;

import fr.univartois.butinfo.fractalens.complex.ComplexPlane;
import fr.univartois.butinfo.fractalens.complex.IComplex;
import fr.univartois.butinfo.fractalens.complex.IComplexPlane;
import fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence;
import fr.univartois.butinfo.fractalens.image.IFractalImage;
import fr.univartois.butinfo.fractalens.palette.IPalette;

import java.awt.Color;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.BiFunction;

/**
 * FractalGenerator generates the image of a fractal.
 */
public class FractalGenerator implements IFractalGenerator {

    /**
     * The IFractalImage interface defines how to handle the image on which the fractal is drawn.
     */
    private IFractalImage image;

    /**
     * The ComplexPlane class handle how to link a position in an image to a number on the complex plane.
     */
    private IComplexPlane plane;

    /**
     * The palette used to color the fractal.
     */
    private IPalette palette;

    /**
     * The path of the file in which the generated image is saved.
     */
    private String outputPath;

    /**
     * The function that associates a complex sequence to each starting complex number
     * and a maximum number of terms.
     */
    BiFunction<IComplex, Integer, IComplexSequence> sequenceFunction;

    /**
     * The maximum number of terms/iterations used to compute the fractal sequence.
     */
    private int nbTherm;

    /**
     * Creates a new FractalGenerator.
     *
     * @param image            The image on which to draw the fractal.
     * @param plane            The complex plane linking the pixels of the image to complex numbers.
     * @param palette          The palette used to color the fractal.
     * @param outputPath       The path of the file in which to save the generated image.
     * @param sequenceFunction The BiFunction generating the complex sequence for each point based on a maximum number of terms.
     * @param nbTherm          The maximum number of terms for the sequence generation.
     */
    public FractalGenerator(IFractalImage image, IComplexPlane plane, IPalette palette, String outputPath, BiFunction<IComplex, Integer, IComplexSequence> sequenceFunction, int nbTherm) {
        this.image = image;
        this.plane = plane;
        this.palette = palette;
        this.outputPath = outputPath;
        this.sequenceFunction = sequenceFunction;
        this.nbTherm = nbTherm;
    }

    /**
     * Generates the full fractal image.
     * Every pixel of the image is visited, row by row, and its color is computed with
     * {@link #computePixelColor(int, int)} before being set in the image.
     *
     * @return The generated image, with all its pixels colored.
     */
    @Override
    public IFractalImage generateImage() {
        for (int row = 0; row < image.getHeight(); row++) {
            for (int column = 0; column < image.getWidth(); column++) {
                image.setColor(row, column, computePixelColor(row, column));
            }
        }
        return image;
    }

    /**
     * Computes the color of the pixel at the given position.
     * The complex number associated with this pixel by the complex plane and the maximum
     * number of terms are passed to the sequence function to generate the terms.
     * The terms of this sequence are then counted to determine the color of the pixel.
     *
     * @param row    The row of the pixel.
     * @param column The column of the pixel.
     * @return The computed color for the pixel.
     */
    @Override
    public Color computePixelColor(int row, int column) {
        IComplex z0 = plane.asComplex(row, column);
        IComplexSequence sequence = sequenceFunction.apply(z0, nbTherm);

        int nbIterations = 0;
        for (var temp : sequence) {
            nbIterations++;
        }

        return palette.getColor(nbIterations);
    }

    /**
     * Saves the generated image in the output file.
     *
     * @throws UncheckedIOException If an I/O error occurs while writing the file.
     */
    @Override
    public void saveImage() {
        try {
            image.saveAs(outputPath);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}