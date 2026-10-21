package fr.univartois.butinfo.fractalens.complex;

import java.awt.*;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

import fr.univartois.butinfo.fractalens.complex.sequence.ComplexSequenceLambda;
import fr.univartois.butinfo.fractalens.palette.*;
import fr.univartois.butinfo.fractalens.complex.sequence.ComplexSequenceJulia;
import fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence;
import fr.univartois.butinfo.fractalens.generator.FractalGenerator;
import fr.univartois.butinfo.fractalens.generator.IFractalGenerator;
import fr.univartois.butinfo.fractalens.image.BufferedImageAdapter;
import fr.univartois.butinfo.fractalens.image.IFractalImage;

/**
 * The Main class is the entry point of the application.
 * For now, it generates the Julia fractal for c = -0.4 + 0.6i in grayscale.
 */
public final class  Main {

    /** The width of the generated image, in pixels. */
    private static final int WIDTH = 800;

    /** The height of the generated image, in pixels. */
    private static final int HEIGHT = 800;

    /** The maximum number of iterations to compute for each pixel. */
    private static final int MAX_ITERATIONS = 250;

    /** The scale applied to the complex plane (0.004 zooms in 250 times). */
    private static final double SCALE = 0.001;

    private static final double TRANSLATE = 0.6;

    /** The constant c of the Julia sequence. */
    private static IComplex julia = new Complex(-0.4, 0.6);

    /** The name of the output file, when none is given on the command line. */
    private static final String DEFAULT_OUTPUT = "julia.png";


    /**
     * Prevents the instantiation of this class.
     */
    private Main() {
        throw new AssertionError("No instance");
    }

    /**
     * Generates the fractal image and saves it in a file.
     *
     * @param args The (optional) name of the output file.
     */
    static void main(String[] args) {
        String outputPath = (args.length > 0) ? args[0] : DEFAULT_OUTPUT;

        // The complex plane, zoomed in around 0.
        IComplexPlane plane = new ComplexPlaneZoom(SCALE, new ComplexPlane(HEIGHT, WIDTH));
        plane = new ComplexPlaneTranslate(new Complex(0.1, 0.6), plane);

        // The image to draw, and the way to color it.
        IFractalImage image = new BufferedImageAdapter(WIDTH, HEIGHT);
        IPalette palette = new DegradePalette(MAX_ITERATIONS, Color.BLUE,Color.RED);

        // How to build the sequence to study for each pixel.
        BiFunction<IComplex, Integer, IComplexSequence> sequence =
                (z0, nbTerms) -> new ComplexSequenceJulia(z0, julia, nbTerms);

        IFractalGenerator generator = new FractalGenerator(image, plane, palette, outputPath, sequence,
                MAX_ITERATIONS);
        generator.generateImage();
        generator.saveImage();

        julia = new Complex(0.285, 0.01);

        plane = new ComplexPlaneZoom(SCALE, new ComplexPlane(HEIGHT, WIDTH));
        plane = new ComplexPlaneTranslate(new Complex(-0.47, -0.2), plane);
        palette = new ColorMaskPalette(new GreyscalePalette(MAX_ITERATIONS), ColorMaskPalette.Mask.YELLOW);
        generator = new FractalGenerator(image, plane, palette, "julia2.png", sequence,
                MAX_ITERATIONS);
        generator.generateImage();
        generator.saveImage();

    }

}
