package fr.univartois.butinfo.fractalens.commandLine;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;
import fr.univartois.butinfo.fractalens.complex.ImageType;
import fr.univartois.butinfo.fractalens.complex.sequence.AllSequences;
import fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence;
import fr.univartois.butinfo.fractalens.complex.sequence.JuliaRotationGeneralisee;
import fr.univartois.butinfo.fractalens.generator.FractalGeneratorAnimated;
import fr.univartois.butinfo.fractalens.generator.FractalGeneratorBuilder;
import fr.univartois.butinfo.fractalens.generator.IFractalGenerator;
import fr.univartois.butinfo.fractalens.palette.AllPalettes;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Spec;
import picocli.CommandLine.Model.CommandSpec;

/**
 * The FractalCommand class is the command-line interface of the application.
 * It reads the options given by the user, configures a fractal generator builder
 * accordingly, and generates and saves the requested image.
 *
 *
 * @version 0.1.0
 */
@Command(name = "fractalens",
        description = "Generates fractal images (single image, zoom or rotation).",
        sortOptions = false)
public class FractalCommand {


    /**
     * The delay between two images of an animation, in milliseconds.
     */
    private static final int FRAME_DELAY = 70;

    /**
     * The factor by which the scale is multiplied between two images of a zoom.
     */
    private static final double ZOOM_FACTOR = 0.8;

    /**
     * The minimal number of images of an animation.
     */
    private static final int MIN_IMAGE_COUNT = 2;

    /**
     * The constant of the Julia's sequence used for a rotation.
     */
    private static final IComplex JULIA_CONSTANT = new Complex(0.7885, 0);

    /**
     * The constant of the generalized Julia's sequence used for a rotation.
     */
    private static final IComplex JULIA_GENERALIZED_CONSTANT = new Complex(0.285, 0.01);



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


    /**
     * The name of the color palette used to color the fractal.
     */
    @Option(names = {"-p", "--palette-name"}, paramLabel = "<name>", defaultValue = "GREYSCALE",
            description = "The color palette used to color the fractal (default: ${DEFAULT-VALUE}). "
                    + "Values: ${COMPLETION-CANDIDATES}.")
    private AllPalettes paletteName;

    /**
     * The type of image to generate.
     */
    @Option(names = {"-t", "--type"}, paramLabel = "<type>", defaultValue = "SINGLE",
            description = "The type of image to generate (default: ${DEFAULT-VALUE}). "
                    + "Values: ${COMPLETION-CANDIDATES}.")
    private ImageType type;


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
     * Whether the help message must be displayed.
     * The option {@code -h} is not used here, as it stands for the height of the image.
     */
    @Option(names = "--help", usageHelp = true,
            description = "Shows this help message and exits.")
    private boolean help;

    /**
     * Generates and saves the image described by the options of the command line.
     *
     * @return The exit code of the application.
     *
     * @throws ParameterException If the options are invalid.
     */


    /**
     * Creates the generator corresponding to the requested type of image.
     *
     * @return The generator of the image (or of the animation).
     */
    private IFractalGenerator createGenerator() {
        FractalGeneratorBuilder builder = FractalGeneratorBuilder.newInstance()
                .withSize(width, height)
                .withMaxIterations(nbIterations) // Must be called before the palette.
                .withScale(scale)
                .withCenter(new Complex(focusX, focusY))
                .withPalette(paletteName)
                .withSequence(fractalName)
                .withOutputPath(output);

        return switch (type) {
            case SINGLE -> builder.build();
            case ZOOM -> createZoom(builder);
            case ROTATION -> createRotation(builder);
        };
    }

    /**
     * Creates an animation zooming on the center of the image.
     * The scale of each image is the scale of the previous one multiplied by
     * a constant factor between 0 and 1.
     *
     * @param builder The builder configured with the options of the command line.
     *
     * @return The animation.
     */
    private IFractalGenerator createZoom(FractalGeneratorBuilder builder) {
        checkImageCount();
        FractalGeneratorAnimated animation = new FractalGeneratorAnimated(output, FRAME_DELAY);
        double currentScale = scale;
        for (int i = 0; i < imageCount; i++) {
            animation.addFrame(builder.withScale(currentScale).build());
            currentScale *= ZOOM_FACTOR;
        }
        return animation;
    }

    /**
     * Creates an animation in which the constant of a Julia's sequence makes a full turn.
     *
     * @param builder The builder configured with the options of the command line.
     *
     * @return The animation.
     */
    private IFractalGenerator createRotation(FractalGeneratorBuilder builder) {
        checkImageCount();
         // "JULIA"or  "JULIA_GENERALIZED"
        if (!JuliaRotationGeneralisee.isSupported(fractalName)) {
            throw new UnsupportedOperationException(
                    "The rotation is only available for JULIA and JULIA_GENERALIZED, not for "
                            + fractalName);
        }

        IComplex constant;
        if (fractalName == AllSequences.JULIA) {
            constant = JULIA_CONSTANT;
        } else {
            constant = JULIA_GENERALIZED_CONSTANT;
        }

        List<BiFunction<IComplex, Integer, IComplexSequence>> factories =
                JuliaRotationGeneralisee.factories(fractalName, constant, 2 * Math.PI / imageCount);

        FractalGeneratorAnimated animation = new FractalGeneratorAnimated(output, FRAME_DELAY);
        for (BiFunction<IComplex, Integer, IComplexSequence> factory : factories) {
            animation.addFrame(builder.withSequence(factory).build());
        }
        return animation;
    }

    /**
     * Checks that there are enough images to make an animation.
     *
     * @throws IllegalArgumentException If there are too few images.
     */
    private void checkImageCount() {
        if (imageCount < MIN_IMAGE_COUNT) {
            throw new IllegalArgumentException(
                    "An animation needs at least " + MIN_IMAGE_COUNT + " images");
        }
    }
}