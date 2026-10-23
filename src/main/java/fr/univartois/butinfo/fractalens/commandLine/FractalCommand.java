package fr.univartois.butinfo.fractalens.commandLine;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.ImageType;
import fr.univartois.butinfo.fractalens.complex.sequence.AllSequences;
import fr.univartois.butinfo.fractalens.generator.FractalGeneratorAnimated;
import fr.univartois.butinfo.fractalens.generator.FractalGeneratorBuilder;
import fr.univartois.butinfo.fractalens.generator.IFractalGenerator;
import fr.univartois.butinfo.fractalens.palette.AllPalettes;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;

/**
 * The FractalCommand class is the command-line interface of the application.
 * It reads the options given by the user, configures a fractal generator builder
 * accordingly, and generates and saves the requested image.
 *
 * @author Joan Pesson
 *
 * @version 0.1.0
 */

@Command(name= "fractalens",
        description = "Generates fractal images",
        sortOptions = false )
public class FractalCommand {
    /**
     * The fractal to generate.
     */
    @Option(names = {"-f", "--fractal-name"}, paramLabel = "<name>", required = true,
            description = "The fractal to generate. Values: ${COMPLETION-CANDIDATES}.")
    private AllSequences fractalName;

    /**
     * The maximum number of iterations for each pixel.
     */
    @Option(names = {"-n", "--nb-iterations"}, paramLabel = "<number>", defaultValue = "250",
            description = "The maximum number of iterations (default: ${DEFAULT-VALUE}).")
    private int nbIterations;

    /**
     * The height of the image, in pixels.
     */
    @Option(names = {"-h", "--height"}, paramLabel = "<pixels>", defaultValue = "600",
            description = "The height of the image, in pixels (default: ${DEFAULT-VALUE}).")
    private int height;

    /**
     * The width of the image, in pixels.
     */
    @Option(names = {"-w", "--width"}, paramLabel = "<pixels>", defaultValue = "800",
            description = "The width of the image, in pixels (default: ${DEFAULT-VALUE}).")
    private int width;

    /**
     * The scale applied to the image.
     */
    @Option(names = {"-s", "--scale"}, paramLabel = "<zoom>", defaultValue = "0.004",
            description = "The scale applied to the image (default: ${DEFAULT-VALUE}).")
    private double scale;

    /**
     * The real part of the center of the image.
     */
    @Option(names = {"-x", "--focus-x"}, paramLabel = "<number>", defaultValue = "0",
            description = "The real part of the center of the image (default: ${DEFAULT-VALUE}).")
    private double focusX;

    /**
     * The imaginary part of the center of the image.
     */
    @Option(names = {"-y", "--focus-y"}, paramLabel = "<number>", defaultValue = "0",
            description = "The imaginary part of the center of the image (default: ${DEFAULT-VALUE}).")
    private double focusY;

    ///////////



    /////////

    /**
     * The number of images of an animation.
     */
    @Option(names = {"-i", "--image-count"}, paramLabel = "<number>", defaultValue = "30",
            description = "The number of images of an animation (default: ${DEFAULT-VALUE}).")
    private int imageCount;

    /**
     * The file in which the fractal is saved.
     */
    @Option(names = {"-o", "--output"}, paramLabel = "<file>", required = true,
            description = "The file in which the fractal is saved (a GIF file for animations).")
    private String output;

    /**
     * Creates the generator corresponding to the requested type of image.
     *
     * @return The generator of the image (or of the animation).
     */
    private IFractalGenerator createGenerator() {
        IFractalGenerator generator = FractalGeneratorBuilder.newInstance()
                .withSize(width, height)
                .withMaxIterations(nbIterations)
                .withScale()
                .withCenter(new Complex(-0.74364388703715, 0.131825904205330))
                .withPalette(AllPalettes.DEGRADE_RED.getPalette(maxIterations1))
                .withSequence(AllSequences.MANDELBROT.getSequenceFactory())
                .build();
        FractalGeneratorAnimated animatedGenerator = new FractalGeneratorAnimated("MandleBrotZoom.gif", 70);
        zoomAnimated(generator1, animatedGenerator, 70, 0.1, 0.8);
        animatedGenerator.generateImage();
        animatedGenerator.saveImage();
    }





    }
