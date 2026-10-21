package fr.univartois.butinfo.fractalens.complex;

import java.awt.Color;
import java.util.function.BiFunction;

import fr.univartois.butinfo.fractalens.complex.sequence.ComplexSequenceBurningShip;
import fr.univartois.butinfo.fractalens.complex.sequence.ComplexSequenceLambda;
import fr.univartois.butinfo.fractalens.complex.sequence.ComplexSequenceTricorn;
import fr.univartois.butinfo.fractalens.complex.sequence.ComplexSequenceTricornGeneralisee;
import fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence;
import fr.univartois.butinfo.fractalens.generator.FractalGenerator;
import fr.univartois.butinfo.fractalens.generator.IFractalGenerator;
import fr.univartois.butinfo.fractalens.image.BufferedImageAdapter;
import fr.univartois.butinfo.fractalens.image.IFractalImage;
import fr.univartois.butinfo.fractalens.palette.DegradePalette;
import fr.univartois.butinfo.fractalens.palette.IPalette;

/**
 * The Main class is the entry point of the application.
 * It generates the three images of the demonstration of sprint 4: the Mandelbrot
 * fractal, the Burning Ship fractal, and a fractal defined by a lambda expression.
 * It also generates the Tricorn fractal and its generalization.
 */
public final class Main {

    /** The name of the file for the Mandelbrot fractal, when none is given. */
    private static final String DEFAULT_MANDELBROT = "mandelbrot.png";

    /** The name of the file for the Burning Ship fractal, when none is given. */
    private static final String DEFAULT_BURNING_SHIP = "burning-ship.png";

    /** The name of the file for the lambda fractal, when none is given. */
    private static final String DEFAULT_LAMBDA = "lambda.png";

    /** The name of the file for the Tricorn fractal. */
    private static final String DEFAULT_TRICORN = "tricorn.png";

    /** The name of the file for the generalized Tricorn fractal. */
    private static final String DEFAULT_TRICORN_GENERALISEE = "tricorn3.png";

    /**
     * Prevents the instantiation of this class.
     */
    private Main() {
        throw new AssertionError("No instance");
    }

    /**
     * Generates the fractal images and saves them in files.
     *
     * @param args The (optional) names of the three output files, in the order:
     *        Mandelbrot, Burning Ship, lambda.
     */
    public static void main(String[] args) {
        generateMandelbrot((args.length > 0) ? args[0] : DEFAULT_MANDELBROT);
        generateBurningShip((args.length > 1) ? args[1] : DEFAULT_BURNING_SHIP);
        generateLambda((args.length > 2) ? args[2] : DEFAULT_LAMBDA);
        generateTricorn(DEFAULT_TRICORN);
        generateTricornGeneralisee(DEFAULT_TRICORN_GENERALISEE);
    }

    /**
     * Generates the Mandelbrot fractal: 600x800 pixels, centered in -0.75 + 0i,
     * with at most 250 iterations and a zoom of 0.004.
     *
     * @param outputPath The file in which to save the image.
     */
    private static void generateMandelbrot(String outputPath) {
        int width = 600;
        int height = 800;
        int maxIterations = 250;

        // m(n) = m(n-1)^2 + z, where z is the number associated with the pixel.
        BiFunction<IComplex, Integer, IComplexSequence> sequence = (z0, nbTerms) ->
                new ComplexSequenceLambda((first, z) -> z.multiply(z).add(first), z0, nbTerms);

        generate(width, height, maxIterations, 0.004, new Complex(-0.75, 0),
                new DegradePalette(maxIterations, Color.BLACK, Color.ORANGE),
                sequence, outputPath);
    }

    /**
     * Generates the Burning Ship fractal: 800x800 pixels, centered in -1.755 - 0.03i,
     * with at most 64 iterations and a zoom of 0.0001.
     * As for Mandelbrot's sequence, the constant c is the number associated with the
     * pixel, and the sequence starts from 0. (With a fixed c = -0.4 + 0.6i, every point
     * of the plane diverges within a few iterations, which gives a uniformly black
     * image, whatever the center and the zoom.)
     *
     * @param outputPath The file in which to save the image.
     */
    private static void generateBurningShip(String outputPath) {
        int width = 800;
        int height = 800;
        int maxIterations = 64;

        // b(n) = (|Re(b(n-1))| + i|Im(b(n-1))|)^2 + c, where c is the pixel.
        BiFunction<IComplex, Integer, IComplexSequence> sequence = (pixel, nbTerms) ->
                new ComplexSequenceBurningShip(pixel, nbTerms, new Complex(0, 0));

        generate(width, height, maxIterations, 0.0001, new Complex(-1.755, -0.03),
                new DegradePalette(maxIterations, Color.BLACK, Color.YELLOW),
                sequence, outputPath);
    }

    /**
     * Generates a fractal defined by a lambda expression: the Julia set for
     * c = -0.8 + 0.156i, on 800x800 pixels, centered in 0, with at most 150 iterations
     * and a zoom of 0.004.
     *
     * @param outputPath The file in which to save the image.
     */
    private static void generateLambda(String outputPath) {
        int width = 800;
        int height = 800;
        int maxIterations = 150;
        IComplex c = new Complex(-0.8, 0.156);

        BiFunction<IComplex, Integer, IComplexSequence> sequence = (z0, nbTerms) ->
                new ComplexSequenceLambda((first, z) -> z.multiply(z).add(c), z0, nbTerms);

        generate(width, height, maxIterations, 0.004, new Complex(0, 0),
                new DegradePalette(maxIterations, Color.BLUE, Color.WHITE),
                sequence, outputPath);
    }

    /**
     * Generates the Tricorn fractal: 800x800 pixels, centered in 0, with at most
     * 250 iterations and a zoom of 0.005.
     * As for Mandelbrot's sequence, the constant c is the number associated with the
     * pixel, and the sequence starts from 0.
     *
     * @param outputPath The file in which to save the image.
     */
    private static void generateTricorn(String outputPath) {
        int width = 800;
        int height = 800;
        int maxIterations = 250;

        // t(n) = conj(t(n-1))^2 + c, where c is the pixel.
        BiFunction<IComplex, Integer, IComplexSequence> sequence = (pixel, nbTerms) ->
                new ComplexSequenceTricorn(pixel, nbTerms, new Complex(0, 0));

        generate(width, height, maxIterations, 0.005, new Complex(0, 0),
                new DegradePalette(maxIterations, Color.BLACK, Color.CYAN),
                sequence, outputPath);
    }

    /**
     * Generates the generalized Tricorn fractal with p = 3: 800x800 pixels, centered
     * in 0, with at most 250 iterations and a zoom of 0.005.
     * As for Mandelbrot's sequence, the constant c is the number associated with the
     * pixel, and the sequence starts from 0.
     *
     * @param outputPath The file in which to save the image.
     */
    private static void generateTricornGeneralisee(String outputPath) {
        int width = 800;
        int height = 800;
        int maxIterations = 250;

        // t(n) = conj(t(n-1))^3 + c, where c is the pixel.
        BiFunction<IComplex, Integer, IComplexSequence> sequence = (pixel, nbTerms) ->
                new ComplexSequenceTricornGeneralisee(pixel, nbTerms, new Complex(0, 0), 3);

        generate(width, height, maxIterations, 0.005, new Complex(0, 0),
                new DegradePalette(maxIterations, Color.BLACK, Color.MAGENTA),
                sequence, outputPath);
    }

    /**
     * Generates a fractal image and saves it in a file.
     *
     * @param width The width of the image, in pixels.
     * @param height The height of the image, in pixels.
     * @param maxIterations The maximum number of iterations for each pixel.
     * @param scale The scale applied to the complex plane.
     * @param center The complex number on which the image is centered.
     * @param palette The palette used to color the image.
     * @param sequence How to build the sequence to study for each pixel.
     * @param outputPath The file in which to save the image.
     */
    private static void generate(int width, int height, int maxIterations, double scale,
                                 IComplex center, IPalette palette,
                                 BiFunction<IComplex, Integer, IComplexSequence> sequence, String outputPath) {
        IComplexPlane plane = new ComplexPlaneZoom(scale, new ComplexPlane(height, width));
        plane = new ComplexPlaneTranslate(center, plane);

        IFractalImage image = new BufferedImageAdapter(width, height);
        IFractalGenerator generator = new FractalGenerator(image, plane, palette,
                outputPath, sequence, maxIterations);
        generator.generateImage();
        generator.saveImage();
    }

}
