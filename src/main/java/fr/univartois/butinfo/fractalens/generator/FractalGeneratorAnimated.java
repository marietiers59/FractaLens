package fr.univartois.butinfo.fractalens.generator;


import com.madgag.gif.fmsware.AnimatedGifEncoder;
import fr.univartois.butinfo.fractalens.image.BufferedImageAdapter;
import fr.univartois.butinfo.fractalens.image.IFractalImage;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The FractalGeneratorAnimated class is a class that generates an animated fractal.
 * @author Théo Brureau
 */
public class FractalGeneratorAnimated implements IFractalGenerator {
    private final List<IFractalGenerator> generatorList = new ArrayList<>();
    private final List<BufferedImageAdapter> imageList = new ArrayList<>();
    private final String outputPath;
    private final int frameDelay;

    public FractalGeneratorAnimated(String outputPath, int frameDelay) {
        this.outputPath = Objects.requireNonNull(outputPath);
        this.frameDelay = frameDelay;
    }


    /**
     * Add a frame to the animation.
     * @param generator The generator to add to the animation.
     */
    public void addFrame(IFractalGenerator generator) {
        generatorList.add(Objects.requireNonNull(generator));
        imageList.clear();
    }


    @Override
    public IFractalImage generateImage() {
        if (generatorList.size() <= 1) {
            throw new IllegalStateException("An animation needs at least 2 frames.");
        }
        imageList.clear();
        for (IFractalGenerator iFractalGenerator : generatorList) {
            imageList.add((BufferedImageAdapter) iFractalGenerator.generateImage());
        }
        return imageList.getLast();
    }

    @Override
    public Color computePixelColor(int row, int column) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void saveImage() {
        if (imageList.isEmpty()) {
            generateImage();
        }

        AnimatedGifEncoder encoder = new AnimatedGifEncoder();
        encoder.start(outputPath);
        encoder.setDelay(frameDelay);
        encoder.setRepeat(0);
        encoder.setQuality(10);

        for(BufferedImageAdapter image : imageList) {
            encoder.addFrame(image.getImage());
        }
        encoder.finish();

    }
}
