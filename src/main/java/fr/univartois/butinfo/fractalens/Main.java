package fr.univartois.butinfo.fractalens;

import fr.univartois.butinfo.fractalens.commandline.FractalCommand;
import picocli.CommandLine;

/**
 * The Main class is the entry point of the application.
 * It generates the four images of the demonstration: the Mandelbrot
 * fractal, the Burning Ship fractal, Julia, and Tricorn.
 */
public final class Main {

    /**
     * Runs the application with the given command line.
     *
     * @param args The arguments of the command line.
     */
    public static void main(String[] args) {
        int exitCode = new CommandLine(new FractalCommand()).execute(args);
        System.exit(exitCode);
    }
}