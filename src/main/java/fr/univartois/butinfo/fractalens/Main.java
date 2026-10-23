package fr.univartois.butinfo.fractalens;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;
import fr.univartois.butinfo.fractalens.complex.sequence.JuliaRotation;
import fr.univartois.butinfo.fractalens.complex.sequence.ComplexSequenceJulia;

import java.util.function.BiFunction;

import fr.univartois.butinfo.fractalens.complex.sequence.AllSequences;
import fr.univartois.butinfo.fractalens.complex.sequence.ComplexSequenceLambda;
import fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence;
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
        // Zoom Mandelbrot
        int maxIterations1 = 250;
        IFractalGenerator generator1 = FractalGeneratorBuilder.newInstance()
                .withSize(600, 800)
                .withMaxIterations(maxIterations1)
                .withScale(0.001)
                .withCenter(new Complex(-0.74364388703715, 0.131825904205330))
                .withPalette(AllPalettes.DEGRADE_RED.getPalette(maxIterations1))
                .withSequence(AllSequences.MANDELBROT.getSequenceFactory())
                .build();
        FractalGeneratorAnimated animatedGenerator = new FractalGeneratorAnimated("MandleBrotZoom.gif", 70);
        zoomAnimated(generator1, animatedGenerator, 70, 0.1, 0.8);
        animatedGenerator.generateImage();
        animatedGenerator.saveImage();


        // Rotation Julia
        int maxIterations3 = 250;
        BiFunction<IComplex, Integer, IComplexSequence> juliaRota = (z0, nbTerms) -> new ComplexSequenceLambda((first, z) -> z.multiply(z).add(new Complex(0.7885, 0)), z0, nbTerms);
        IFractalGenerator generator2 = FractalGeneratorBuilder.newInstance()
                .withSize(600, 800)
                .withMaxIterations(maxIterations3)
                .withScale(0.004)
                .withCenter(new Complex(0, 0))
                .withPalette(AllPalettes.DEGRADE_YELLOW.getPalette(maxIterations3))
                .withSequence(juliaRota)
                .build();
        FractalGeneratorAnimated animatedGenerator2 = new FractalGeneratorAnimated("JuliaRotation.gif", 42);
        rotaAnimated(generator2, animatedGenerator2, 100, new Complex(0.7885, 0), 2 * Math.PI / 100);
        animatedGenerator2.generateImage();
        animatedGenerator2.saveImage();


    }

    /**
     *
     * Put generators containing zoomed images into a list whose type is generator
     *
     * @param generator The generator.
     * @param animation The animation.
     * @param amount The number of frames.
     * @param scale The scale.
     * @param constantZoom The constant zoom.
     */
    public static void zoomAnimated(IFractalGenerator generator, FractalGeneratorAnimated animation,
                                    int amount, double scale, double constantZoom) {
        for (int i = 0; i < amount; i++) {
            FractalGeneratorBuilder builder = new FractalGeneratorBuilder(generator);
            animation.addFrame(builder.withScale(scale).build());
            scale *= constantZoom; // zoomFactor < 1: the smaller the scale, the stronger the zoom
        }
    }

    /**
     * Puts into the animation the generators of a rotation of the Julia's fractal:
     * theta rotates the constant c between two successive frames.
     *
     * @param generator The generator whose configuration is used for every frame.
     * @param animation The animation in which the frames are added.
     * @param amount The number of frames.
     * @param c The constant of the Julia's sequence for the first frame.
     * @param theta The angle between two successive frames, in radians.
     */
    public static void rotaAnimated(IFractalGenerator generator, FractalGeneratorAnimated animation,
                                    int amount, IComplex c, double theta) {
        for (int i = 0; i < amount; i++) {
            IComplex rotatedC = JuliaRotation.rotate(c, i * theta);
            FractalGeneratorBuilder builder = new FractalGeneratorBuilder(generator);
            builder.withSequence((z0, nbTerms) -> new ComplexSequenceJulia(z0, rotatedC, nbTerms));
            animation.addFrame(builder.build());
        }
    }
}