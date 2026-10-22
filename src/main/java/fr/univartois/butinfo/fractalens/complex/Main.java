package fr.univartois.butinfo.fractalens.complex;

import java.util.List;
import java.util.Objects;

import fr.univartois.butinfo.fractalens.complex.sequence.AllSequences;
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
}