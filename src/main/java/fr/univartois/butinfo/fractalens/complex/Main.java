package fr.univartois.butinfo.fractalens.complex;

import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

import fr.univartois.butinfo.fractalens.complex.sequence.AllSequences;
import fr.univartois.butinfo.fractalens.complex.sequence.ComplexSequenceJulia;
import fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence;
import fr.univartois.butinfo.fractalens.complex.sequence.JuliaRotation;
import fr.univartois.butinfo.fractalens.generator.FractalGenerator;
import fr.univartois.butinfo.fractalens.generator.FractalGeneratorAnimated;
import fr.univartois.butinfo.fractalens.generator.FractalGeneratorBuilder;
import fr.univartois.butinfo.fractalens.generator.IFractalGenerator;
import fr.univartois.butinfo.fractalens.palette.AllPalettes;

/**
 * The Main class is the entry point of the application.
 * It generates the four images of the demonstration: the Mandelbrot
 * fractal, the Burning Ship fractal, Julia, and Tricorn.
 */
public final class Main {

    public static void main(String[] args) {
        // 1 : Mandelbrot
        int maxIterations1 = 250;
        IFractalGenerator generator1 = FractalGeneratorBuilder.newInstance()
                .withSize(600, 800)
                .withMaxIterations(maxIterations1)
                .withScale(0.005)
                .withCenter(new Complex(-0.75, 0))
                .withPalette(AllPalettes.DEGRADE_RED.getPalette(maxIterations1))
                .withSequence(AllSequences.MANDELBROT.getSequenceFactory())
                .withOutputPath("generator1.png")
                .build();
        generator1.generateImage();
        generator1.saveImage();


        // 2 : Burning Ship
        int maxIterations2 = 64;
        IFractalGenerator generator2 = FractalGeneratorBuilder.newInstance()
                .withSize(600, 800)
                .withMaxIterations(maxIterations2)
                .withScale(0.00005) // Vue beaucoup plus large pour voir l'extérieur
                .withCenter(new Complex(-1.755, -0.03))
                .withPalette(AllPalettes.DEGRADE.getPalette(maxIterations2))
                .withSequence(AllSequences.BURNING_SHIP.getSequenceFactory())
                .withOutputPath("generator2.png")
                .build();
        generator2.generateImage();
        generator2.saveImage();

        // 3 : Julia
        int maxIterations3 = 250;
        IFractalGenerator generator3 = FractalGeneratorBuilder.newInstance()
                .withSize(600, 800)
                .withMaxIterations(maxIterations3)
                .withScale(0.001)
                .withCenter(new Complex(0.9, -0.1))
                .withPalette(AllPalettes.DEGRADE_GREEN.getPalette(maxIterations3))
                .withSequence(AllSequences.JULIA.getSequenceFactory())
                .build();
        FractalGeneratorAnimated animatedGenerator = new FractalGeneratorAnimated("testGif.gif", 100);
        zoomAnimated(generator3, animatedGenerator, 25, 0.1,0.8);
        animatedGenerator.generateImage();
        animatedGenerator.saveImage();



        // 4 : Tricorn
        int maxIterations4 = 250;
        IFractalGenerator generator4 = FractalGeneratorBuilder.newInstance()
                .withSize(800, 800)
                .withMaxIterations(maxIterations4)
                .withScale(0.002)
                .withCenter(new Complex(0, 0))
                .withPalette(AllPalettes.GREYSCALE.getPalette(maxIterations4))
                .withPalette(AllPalettes.FIXED.getPalette(maxIterations4))
                .withOutputPath("generator4.png")
                .build();
        generator4.generateImage();
        generator4.saveImage();


        // 5 : Rotation of the Julia's fractal (60 frames, 40 ms per frame = 25 frames per second)
        int maxIterations5 = 250;
        FractalGeneratorBuilder builder5 = FractalGeneratorBuilder.newInstance()
                .withSize(400, 400)
                .withMaxIterations(maxIterations5)
                .withScale(0.008)
                .withCenter(new Complex(0, 0))
                .withPalette(AllPalettes.DEGRADE_INVERTED_MAGENTA.getPalette(maxIterations5));
        FractalGeneratorAnimated rotationGenerator = new FractalGeneratorAnimated("julia-rotation.gif", 40);
        sequenceChangeAnimated(builder5, rotationGenerator, 60, new Complex(-0.4, 0.6));
        rotationGenerator.generateImage();
        rotationGenerator.saveImage();

    }

    /**
     *
     * Put generators containing zoomed images into a list whose type is generator
     *
     * @param generator
     * @param animation
     * @param amount
     * @param scale
     * @param constantZoom
     */
    public static void zoomAnimated(IFractalGenerator generator, FractalGeneratorAnimated animation, int amount, double scale, double constantZoom) {
    	//le contenu essentiel est une boucle pour faire varier la valeur du zoom
    	//Plus la valeur du zoom est petite, plus le zoom est important.
    	//Il faut donc diminuer petit à petit cette valeur
    	//public void addFrame(IFractalGenerator generator) {
        //	generatorList.add(Objects.requireNonNull(generator));
        //	imageList.clear();
    	for(int i = 0 ; i < amount; i++) {
    		scale = scale * constantZoom;
    		FractalGeneratorBuilder zoomed = new FractalGeneratorBuilder(generator).withScale(scale);
    		animation.addFrame(zoomed.build());
    	}


    }

    /**
     * Puts into the animation the generators of a rotation of the Julia's fractal:
     * the constant {@code c} of the sequence is rotated by {@code 2 * PI / amount}
     * between two successive frames.
     *
     * The builder is reused for every frame: only the sequence is changed before each
     * call to {@code build()}, so that all the frames share the configuration of the
     * builder (size, scale, center, palette, number of iterations).
     *
     * @param builder The builder configured for the frames of the animation.
     * @param animation The animation in which the frames are added.
     * @param amount The number of frames of a full turn (at least 2).
     * @param c The constant of the Julia's sequence for the first frame.
     *
     * @throws NullPointerException If {@code builder}, {@code animation} or {@code c}
     *         is {@code null}.
     * @throws IllegalArgumentException If {@code amount} is lower than 2.
     *
     * @author Marie Tiers
     */
    public static void sequenceChangeAnimated(FractalGeneratorBuilder builder,
            FractalGeneratorAnimated animation, int amount, IComplex c) {
        Objects.requireNonNull(builder, "The builder must not be null");
        Objects.requireNonNull(animation, "The animation must not be null");
        Objects.requireNonNull(c, "The constant of the sequence must not be null");
        if (amount < 2) {
            throw new IllegalArgumentException("An animation needs at least two frames");
        }

        double step = 2 * Math.PI / amount;
        for (int i = 0; i < amount; i++) {
            // New complex number: c rotated by i * step.
            IComplex rotatedC = JuliaRotation.rotate(c, i * step);
            // New Julia's sequence using this complex number.
            BiFunction<IComplex, Integer, IComplexSequence> julia =
                    (z0, nbTerms) -> new ComplexSequenceJulia(z0, rotatedC, nbTerms);
            // New frame, built with the configuration of the builder.
            animation.addFrame(builder.withSequence(julia).build());
        }
    }
}
