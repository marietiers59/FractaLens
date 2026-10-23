
package fr.univartois.butinfo.fractalens.generator;

import fr.univartois.butinfo.fractalens.image.BufferedImageAdapter;
import fr.univartois.butinfo.fractalens.image.IFractalImage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The FractalGeneratorAnimatedTest tests the generator of animated fractals.
 * <p>
 * The frames are produced by simple generators drawing a single color, and the
 * animated GIF written is read again to check its content.
 */
class FractalGeneratorAnimatedTest {

    /** The width of the frames used in these tests. */
    private static final int WIDTH = 8;

    /** The height of the frames used in these tests (different from the width). */
    private static final int HEIGHT = 6;

    /** The delay between two frames used by default, in milliseconds. */
    private static final int DELAY = 100;

    /** The temporary directory in which animations are saved. */
    @TempDir
    Path tempDir;

    /**
     * The StubGenerator class is a generator drawing a single color, which records how it is used.
     */
    private static final class StubGenerator implements IFractalGenerator {

        /** The image returned by this generator. */
        private final IFractalImage image;

        /** The name of this generator, written in the log when its image is generated. */
        private final String name;

        /** The log shared by all the generators of a test. */
        private final List<String> log;

        /** The number of images generated. */
        private int nbGenerated;

        /** The number of times this generator was asked to save its image. */
        private int nbSaved;

        /**
         * Creates a new StubGenerator.
         *
         * @param name The name of the generator.
         * @param color The color of the image.
         * @param log The log in which generations are written.
         */
        StubGenerator(String name, Color color, List<String> log) {
            this.name = name;
            this.image = solidImage(color);
            this.log = log;
        }

        @Override
        public IFractalImage generateImage() {
            nbGenerated++;
            log.add(name);
            return image;
        }

        @Override
        public Color computePixelColor(int row, int column) {
            throw new AssertionError("The pixels must not be computed by the animation");
        }

        @Override
        public void saveImage() {
            nbSaved++;
        }
    }

    /**
     * Creates an image filled with a single color.
     *
     * @param color The color of all the pixels.
     *
     * @return The image created.
     */
    private static IFractalImage solidImage(Color color) {
        BufferedImageAdapter image = new BufferedImageAdapter(WIDTH, HEIGHT);
        for (int row = 0; row < HEIGHT; row++) {
            for (int column = 0; column < WIDTH; column++) {
                image.setColor(row, column, color);
            }
        }
        return image;
    }

    /**
     * Gives the path of a GIF in the temporary directory.
     *
     * @return The path of the file (which does not exist yet).
     */
    private File gifFile() {
        return tempDir.resolve("animation.gif").toFile();
    }

    /**
     * Creates an animation with the given generators.
     *
     * @param output The GIF to write.
     * @param generators The generators producing the frames (their number is the number of frames).
     *
     * @return The animation created.
     */
    private static FractalGeneratorAnimated createAnimation(File output, IFractalGenerator... generators) {
        FractalGeneratorAnimated animation = new FractalGeneratorAnimated(output.getPath(), DELAY);
        for (IFractalGenerator generator : generators) {
            animation.addFrame(generator);
        }
        return animation;
    }

    /**
     * Reads all the frames of a GIF.
     *
     * @param file The GIF to read.
     *
     * @return The frames, in order.
     *
     * @throws IOException If the file cannot be read.
     */
    private static List<BufferedImage> readFrames(File file) throws IOException {
        try (ImageInputStream input = ImageIO.createImageInputStream(file)) {
            ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
            try {
                reader.setInput(input);
                List<BufferedImage> frames = new ArrayList<>();
                for (int i = 0; i < reader.getNumImages(true); i++) {
                    frames.add(reader.read(i));
                }
                return frames;
            } finally {
                reader.dispose();
            }
        }
    }

    /**
     * Reads the delay stored in the first frame of a GIF.
     *
     * @param file The GIF to read.
     *
     * @return The delay, in hundredths of a second.
     *
     * @throws IOException If the file cannot be read.
     */
    private static int readDelay(File file) throws IOException {
        try (ImageInputStream input = ImageIO.createImageInputStream(file)) {
            ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
            try {
                reader.setInput(input);
                IIOMetadataNode root = (IIOMetadataNode) reader.getImageMetadata(0)
                        .getAsTree("javax_imageio_gif_image_1.0");
                IIOMetadataNode extension = (IIOMetadataNode) root
                        .getElementsByTagName("GraphicControlExtension").item(0);
                return Integer.parseInt(extension.getAttribute("delayTime"));
            } finally {
                reader.dispose();
            }
        }
    }

    /**
     * Checks that a pixel is mostly of the expected color
     * (GIF images are quantized, so colors are only approximately preserved).
     *
     * @param expected The expected color.
     * @param frame The frame to check.
     */
    private static void assertMostlyColor(Color expected, BufferedImage frame) {
        Color actual = new Color(frame.getRGB(WIDTH / 2, HEIGHT / 2));
        int[] expectedComponents = { expected.getRed(), expected.getGreen(), expected.getBlue() };
        int[] actualComponents = { actual.getRed(), actual.getGreen(), actual.getBlue() };
        for (int i = 0; i < 3; i++) {
            assertTrue(Math.abs(expectedComponents[i] - actualComponents[i]) <= 40,
                    "Expected " + expected + " but was " + actual);
        }
    }

    /**
     * Test method for {@link FractalGeneratorAnimated#FractalGeneratorAnimated(String, int)}.
     */
    @Test
    void testConstructorRejectsNullPath() {
        assertThrows(NullPointerException.class, () -> new FractalGeneratorAnimated(null, DELAY));
    }

    /**
     * Test method for {@link FractalGeneratorAnimated#addFrame(IFractalGenerator)}.
     */
    @Test
    void testAddFrameRejectsNull() {
        FractalGeneratorAnimated animation = new FractalGeneratorAnimated(gifFile().getPath(), DELAY);

        assertThrows(NullPointerException.class, () -> animation.addFrame(null));
    }

    /**
     * Test method for {@link FractalGeneratorAnimated#generateImage()}.
     */
    @Test
    void testGenerateImageWithoutFrameIsRefused() {
        FractalGeneratorAnimated animation = createAnimation(gifFile());

        assertThrows(IllegalStateException.class, animation::generateImage);
    }

    /**
     * Test method for {@link FractalGeneratorAnimated#generateImage()}.
     * An animation needs at least two frames.
     */
    @Test
    void testGenerateImageWithOneFrameIsRefused() {
        List<String> log = new ArrayList<>();
        StubGenerator only = new StubGenerator("only", Color.RED, log);
        FractalGeneratorAnimated animation = createAnimation(gifFile(), only);

        assertThrows(IllegalStateException.class, animation::generateImage);
        assertEquals(0, only.nbGenerated);
    }

    /**
     * Test method for {@link FractalGeneratorAnimated#saveImage()}.
     */
    @Test
    void testSaveImageWithLessThanTwoFramesIsRefused() {
        File output = gifFile();
        List<String> log = new ArrayList<>();

        assertThrows(IllegalStateException.class, () -> createAnimation(output).saveImage());
        assertThrows(IllegalStateException.class,
                () -> createAnimation(output, new StubGenerator("only", Color.RED, log)).saveImage());
        assertFalse(output.exists());
    }

    /**
     * Test method for {@link FractalGeneratorAnimated#generateImage()}.
     * Every generator must be used exactly once, in the order of the animation.
     */
    @Test
    void testGenerateImageGeneratesEveryFrameInOrder() {
        List<String> log = new ArrayList<>();
        StubGenerator first = new StubGenerator("first", Color.RED, log);
        StubGenerator second = new StubGenerator("second", Color.GREEN, log);
        StubGenerator third = new StubGenerator("third", Color.BLUE, log);

        createAnimation(gifFile(), first, second, third).generateImage();

        assertEquals(List.of("first", "second", "third"), log);
        assertEquals(1, first.nbGenerated);
        assertEquals(1, second.nbGenerated);
        assertEquals(1, third.nbGenerated);
    }

    /**
     * Test method for {@link FractalGeneratorAnimated#generateImage()}.
     */
    @Test
    void testGenerateImageReturnsTheLastFrame() {
        List<String> log = new ArrayList<>();
        StubGenerator first = new StubGenerator("first", Color.RED, log);
        StubGenerator last = new StubGenerator("last", Color.BLUE, log);

        IFractalImage image = createAnimation(gifFile(), first, last).generateImage();

        assertSame(last.image, image);
    }

    /**
     * Test method for {@link FractalGeneratorAnimated#computePixelColor(int, int)}.
     */
    @Test
    void testComputePixelColorIsNotSupported() {
        List<String> log = new ArrayList<>();
        FractalGeneratorAnimated animation = createAnimation(gifFile(),
                new StubGenerator("first", Color.RED, log), new StubGenerator("second", Color.BLUE, log));

        assertThrows(UnsupportedOperationException.class, () -> animation.computePixelColor(0, 0));
    }

    /**
     * Test method for {@link FractalGeneratorAnimated#saveImage()}.
     * The result must be a GIF file with one image for each frame.
     */
    @Test
    void testSaveImageWritesAnAnimatedGifWithAllFrames() throws IOException {
        File output = gifFile();
        List<String> log = new ArrayList<>();
        FractalGeneratorAnimated animation = createAnimation(output,
                new StubGenerator("first", Color.RED, log),
                new StubGenerator("second", Color.GREEN, log),
                new StubGenerator("third", Color.BLUE, log));

        animation.saveImage();

        byte[] content = Files.readAllBytes(output.toPath());
        assertEquals("GIF89a", new String(content, 0, 6, StandardCharsets.US_ASCII));
        assertEquals(3, readFrames(output).size());
    }

    /**
     * Test method for {@link FractalGeneratorAnimated#saveImage()}.
     * The frames must keep their size and their order.
     */
    @Test
    void testSaveImageKeepsTheFramesInOrder() throws IOException {
        File output = gifFile();
        List<String> log = new ArrayList<>();
        FractalGeneratorAnimated animation = createAnimation(output,
                new StubGenerator("first", Color.RED, log),
                new StubGenerator("second", Color.GREEN, log),
                new StubGenerator("third", Color.BLUE, log));

        animation.saveImage();

        List<BufferedImage> frames = readFrames(output);
        assertEquals(3, frames.size());
        for (BufferedImage frame : frames) {
            assertEquals(WIDTH, frame.getWidth());
            assertEquals(HEIGHT, frame.getHeight());
        }
        assertMostlyColor(Color.RED, frames.get(0));
        assertMostlyColor(Color.GREEN, frames.get(1));
        assertMostlyColor(Color.BLUE, frames.get(2));
    }

    /**
     * Test method for {@link FractalGeneratorAnimated#saveImage()}.
     * The order of the animation is the order in which frames are added.
     */
    @Test
    void testSaveImageFollowsTheOrderOfAddition() throws IOException {
        File output = gifFile();
        List<String> log = new ArrayList<>();
        FractalGeneratorAnimated animation = createAnimation(output,
                new StubGenerator("blue", Color.BLUE, log), new StubGenerator("red", Color.RED, log));

        animation.saveImage();

        List<BufferedImage> frames = readFrames(output);
        assertMostlyColor(Color.BLUE, frames.get(0));
        assertMostlyColor(Color.RED, frames.get(1));
    }

    /**
     * Test method for {@link FractalGeneratorAnimated#saveImage()}.
     * The delay must be written in the GIF (in hundredths of a second).
     */
    @Test
    void testSaveImageWritesTheDelay() throws IOException {
        File output = gifFile();
        List<String> log = new ArrayList<>();
        FractalGeneratorAnimated animation = new FractalGeneratorAnimated(output.getPath(), 250);
        animation.addFrame(new StubGenerator("first", Color.RED, log));
        animation.addFrame(new StubGenerator("second", Color.BLUE, log));

        animation.saveImage();

        assertEquals(25, readDelay(output));
    }

    /**
     * Test method for {@link FractalGeneratorAnimated#saveImage()}.
     * The animation must loop forever.
     */
    @Test
    void testSaveImageLoopsForever() throws IOException {
        File output = gifFile();
        List<String> log = new ArrayList<>();
        createAnimation(output, new StubGenerator("first", Color.RED, log),
                new StubGenerator("second", Color.BLUE, log)).saveImage();

        byte[] content = Files.readAllBytes(output.toPath());
        byte[] marker = "NETSCAPE2.0".getBytes(StandardCharsets.US_ASCII);
        int position = indexOf(content, marker);

        assertTrue(position >= 0, "The looping extension is missing");
        // After the marker: a sub-block of 3 bytes (1, then the number of loops on 2 bytes), 0 = forever.
        byte[] expected = { 3, 1, 0, 0, 0 };
        int start = position + marker.length;
        assertEquals(Arrays.toString(expected), Arrays.toString(Arrays.copyOfRange(content, start, start + 5)));
    }

    /**
     * Finds a sequence of bytes in an array.
     *
     * @param content The array to search in.
     * @param marker The sequence to find.
     *
     * @return The index of the first occurrence, or -1.
     */
    private static int indexOf(byte[] content, byte[] marker) {
        for (int i = 0; i <= content.length - marker.length; i++) {
            if (Arrays.equals(Arrays.copyOfRange(content, i, i + marker.length), marker)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Test method for {@link FractalGeneratorAnimated#saveImage()}.
     * Saving without generating first must generate every frame, once.
     */
    @Test
    void testSaveImageGeneratesFramesIfNeeded() {
        List<String> log = new ArrayList<>();
        StubGenerator first = new StubGenerator("first", Color.RED, log);
        StubGenerator second = new StubGenerator("second", Color.BLUE, log);

        createAnimation(gifFile(), first, second).saveImage();

        assertEquals(1, first.nbGenerated);
        assertEquals(1, second.nbGenerated);
    }

    /**
     * Test method for {@link FractalGeneratorAnimated#saveImage()}.
     * Frames already generated must not be computed again.
     */
    @Test
    void testSaveImageDoesNotGenerateFramesAgain() {
        List<String> log = new ArrayList<>();
        StubGenerator first = new StubGenerator("first", Color.RED, log);
        StubGenerator second = new StubGenerator("second", Color.BLUE, log);
        FractalGeneratorAnimated animation = createAnimation(gifFile(), first, second);

        animation.generateImage();
        animation.saveImage();

        assertEquals(1, first.nbGenerated);
        assertEquals(1, second.nbGenerated);
    }

    /**
     * Test method for {@link FractalGeneratorAnimated#generateImage()}.
     * Generating several times must not accumulate frames.
     */
    @Test
    void testGenerateImageTwiceDoesNotDuplicateFrames() throws IOException {
        File output = gifFile();
        List<String> log = new ArrayList<>();
        FractalGeneratorAnimated animation = createAnimation(output,
                new StubGenerator("first", Color.RED, log), new StubGenerator("second", Color.BLUE, log));

        animation.generateImage();
        animation.generateImage();
        animation.saveImage();

        assertEquals(2, readFrames(output).size());
    }

    /**
     * Test method for {@link FractalGeneratorAnimated#addFrame(IFractalGenerator)}.
     * A frame added after the generation must be part of the saved animation.
     */
    @Test
    void testAddFrameAfterGenerationIsTakenIntoAccount() throws IOException {
        File output = gifFile();
        List<String> log = new ArrayList<>();
        FractalGeneratorAnimated animation = createAnimation(output,
                new StubGenerator("first", Color.RED, log), new StubGenerator("second", Color.GREEN, log));

        animation.generateImage();
        animation.addFrame(new StubGenerator("third", Color.BLUE, log));
        animation.saveImage();

        List<BufferedImage> frames = readFrames(output);
        assertEquals(3, frames.size());
        assertMostlyColor(Color.BLUE, frames.get(2));
    }

    /**
     * Test method for {@link FractalGeneratorAnimated#saveImage()}.
     * The generators of the frames must not write anything: only the animation does.
     */
    @Test
    void testFramesAreNotSavedIndividually() {
        List<String> log = new ArrayList<>();
        StubGenerator first = new StubGenerator("first", Color.RED, log);
        StubGenerator second = new StubGenerator("second", Color.BLUE, log);

        createAnimation(gifFile(), first, second).saveImage();

        assertEquals(0, first.nbSaved);
        assertEquals(0, second.nbSaved);
    }

    /**
     * Test method for {@link FractalGeneratorAnimated#saveImage()}.
     * Saving twice must give the same animation.
     */
    @Test
    void testSaveImageTwiceGivesTheSameFile() throws IOException {
        File output = gifFile();
        List<String> log = new ArrayList<>();
        FractalGeneratorAnimated animation = createAnimation(output,
                new StubGenerator("first", Color.RED, log), new StubGenerator("second", Color.BLUE, log));

        animation.saveImage();
        byte[] firstSave = Files.readAllBytes(output.toPath());
        animation.saveImage();
        byte[] secondSave = Files.readAllBytes(output.toPath());

        assertEquals(Arrays.toString(firstSave), Arrays.toString(secondSave));
    }

}