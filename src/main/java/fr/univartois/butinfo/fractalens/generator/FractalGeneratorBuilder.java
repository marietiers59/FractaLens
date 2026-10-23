package fr.univartois.butinfo.fractalens.generator;

import fr.univartois.butinfo.fractalens.complex.*;
import fr.univartois.butinfo.fractalens.complex.sequence.AllSequences;
import fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence;
import fr.univartois.butinfo.fractalens.image.BufferedImageAdapter;
import fr.univartois.butinfo.fractalens.image.IFractalImage;
import fr.univartois.butinfo.fractalens.palette.AllPalettes;
import fr.univartois.butinfo.fractalens.palette.IPalette;

import java.util.Objects;
import java.util.function.BiFunction;

/**
 * The FractalGeneratorBuilder class is a builder used to configure and create a fractal generator.
 * Each parameter has a default value, so that a valid generator can be built without any configuration.
 *
 * @author Théo Brureau
 */
public class FractalGeneratorBuilder {

    /**
     * The height of the image, in pixels.
     */
    private int height = 800;

    /**
     * The width of the image, in pixels.
     */
    private int width = 800;

    /**
     * The maximum number of iterations for each pixel.
     */
    private int maxIterations = 250;

    /**
     * The scale applied to the complex plane.
     */
    private double scale = 0.001;

    /**
     * The complex number on which the image is centered.
     */
    private IComplex center = new Complex(0, 0);

    /**
     * How to create the palette, once the maximum number of iterations is known.
     * It is kept as a function so that the order of the calls to the "with" methods does not matter.
     */
    private IPalette paletteFactory = AllPalettes.GREYSCALE.getPalette(maxIterations);

    /**
     * How to build the sequence to study for each pixel.
     */
    private BiFunction<IComplex, Integer, IComplexSequence> sequence =
            AllSequences.MANDELBROT.getSequenceFactory();

    /**
     * The file in which the image is saved.
     */
    private String outputPath = "fractal.png";

    /**
     * Creates a new FractalGeneratorBuilder.
     * The constructor is private: use {@link #newInstance()} instead.
     */
    private FractalGeneratorBuilder() {}

    /**
     * Creates a new FractalGeneratorBuilder.
     * The constructor is private: use {@link #newInstance()} instead.
     */
    private FractalGeneratorBuilder(FractalGeneratorBuilder other) {
        this.height = other.height;
        this.width = other.width;
        this.maxIterations = other.maxIterations;
        this.scale = other.scale;
        this.center = other.center;
        this.paletteFactory = other.paletteFactory;
        this.sequence = other.sequence;
        this.outputPath = other.outputPath;
    }

    public FractalGeneratorBuilder(IFractalGenerator generator) {
        this(configurationOf(generator));
    }



    /**
     * Creates a new builder, configured with the default values.
     *
     * @return A new builder.
     */
    public static FractalGeneratorBuilder newInstance() {
        return new FractalGeneratorBuilder();
    }


    /**
     * Gives the configuration that was used to build the given generator.
     *
     * @param generator The generator.
     * @return The configuration of the generator.
     */
    private static FractalGeneratorBuilder configurationOf(IFractalGenerator generator) {
        Objects.requireNonNull(generator, "The generator must not be null");
        if (generator instanceof FractalGenerator fractalGenerator
                && fractalGenerator.getConfiguration() != null) {
            return fractalGenerator.getConfiguration();
        }
        throw new IllegalArgumentException("The generator was not created by a builder");
    }

    /**
     * Sets the size of the image.
     *
     * @param width The width of the image, in pixels.
     * @param height The height of the image, in pixels.
     * @return This builder.
     * @throws IllegalArgumentException If the width or the height is not positive.
     */
    public FractalGeneratorBuilder withSize(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("The size of the image must be positive");
        }
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * Sets the scale applied to the complex plane.
     *
     * @param scale The scale (a pixel corresponds to this value in the complex plane).
     * @return This builder.
     * @throws IllegalArgumentException If the scale is not positive.
     */
    public FractalGeneratorBuilder withScale(double scale) {
        if (scale <= 0) {
            throw new IllegalArgumentException("The scale must be positive");
        }
        this.scale = scale;
        return this;
    }

    /**
     * Sets the complex number on which the image is centered.
     *
     * @param center The center of the image.
     * @return This builder.
     * @throws NullPointerException If the center is null.
     */
    public FractalGeneratorBuilder withCenter(IComplex center) {
        this.center = Objects.requireNonNull(center, "The center must not be null");
        return this;
    }

    /**
     * Sets the maximum number of iterations for each pixel.
     *
     * @param maxIterations The maximum number of iterations.
     * @return This builder.
     * @throws IllegalArgumentException If the number of iterations is not positive.
     */
    public FractalGeneratorBuilder withMaxIterations(int maxIterations) {
        if (maxIterations <= 0) {
            throw new IllegalArgumentException("The number of iterations must be positive");
        }
        this.maxIterations = maxIterations;
        return this;
    }

    /**
     * Sets the palette to use, from the enumeration of the available palettes.
     *
     * @param paletteType The type of palette.
     * @return This builder.
     * @throws NullPointerException If the type of palette is null.
     */
    public FractalGeneratorBuilder withPalette(AllPalettes paletteType) {
        Objects.requireNonNull(paletteType, "The type of palette must not be null");
        this.paletteFactory = paletteType.getPalette(maxIterations);
        return this;
    }

    /**
     * Sets the palette to use, directly from an instance.
     *
     * @param palette The palette.
     * @return This builder.
     * @throws NullPointerException If the palette is null.
     */
    public FractalGeneratorBuilder withPalette(IPalette palette) {
        Objects.requireNonNull(palette, "The palette must not be null");
        this.paletteFactory = palette;
        return this;
    }

    /**
     * Sets the sequence to study, from the enumeration of the available sequences.
     *
     * @param sequenceType The type of sequence.
     * @return This builder.
     * @throws NullPointerException If the type of sequence is null.
     */
    public FractalGeneratorBuilder withSequence(AllSequences sequenceType) {
        Objects.requireNonNull(sequenceType, "The type of sequence must not be null");
        this.sequence = sequenceType.getSequenceFactory();
        return this;
    }

    /**
     * Sets the sequence to study, directly from a function creating it.
     *
     * @param sequence How to build the sequence to study for each pixel.
     * @return This builder.
     * @throws NullPointerException If the function is null.
     */
    public FractalGeneratorBuilder withSequence(BiFunction<IComplex, Integer, IComplexSequence> sequence) {
        this.sequence = Objects.requireNonNull(sequence, "The sequence must not be null");
        return this;
    }

    /**
     * Sets the file in which the image is saved.
     *
     * @param outputPath The path of the file.
     * @return This builder.
     * @throws IllegalArgumentException If the path is null or blank.
     */
    public FractalGeneratorBuilder withOutputPath(String outputPath) {
        if (outputPath == null || outputPath.isBlank()) {
            throw new IllegalArgumentException("The output path must not be blank");
        }
        this.outputPath = outputPath;
        return this;
    }

    /**
     * Creates the fractal generator from the current configuration.
     *
     * @return The configured fractal generator.
     */
    public IFractalGenerator build() {
        IComplexPlane plane = new ComplexPlaneZoom(scale, new ComplexPlane(height, width));
        plane = new ComplexPlaneTranslate(center, plane);

        IFractalImage image = new BufferedImageAdapter(width, height);
        return new FractalGenerator(image, plane, paletteFactory, outputPath, sequence, maxIterations, new FractalGeneratorBuilder(this));
    }

}