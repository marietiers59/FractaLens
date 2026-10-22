package fr.univartois.butinfo.fractalens.complex;

import fr.univartois.butinfo.fractalens.complex.sequence.AllSequences;
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
                .withScale(0.005)
                .withCenter(new Complex(0, 0))
                .withPalette(AllPalettes.DEGRADE_INVERTED_MAGENTA.getPalette(maxIterations3))
                .withSequence(AllSequences.JULIA.getSequenceFactory())
                .withOutputPath("generator3.png")
                .build();
        generator3.generateImage();
        generator3.saveImage();


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
     */
    public static void zoomAnimated() {
    	//le contenu essentiel est une boucle pour faire varier la valeur du zoom
    	//Plus la valeur du zoom est petite, plus le zoom est important.
    	//Il faut donc diminuer petit à petit cette valeur
    }
}