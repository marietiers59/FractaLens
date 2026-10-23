
package fr.univartois.butinfo.fractalens.image;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * The BufferedImageAdapterTest class tests the functionality of the BufferedImageAdapter
 *
 * @author Théo Brureau
 */
class BufferedImageAdapterTest {

    /**
     * The width of the image.
     */
    private static final int WIDTH = 8;
    /**
     * The height of the image.
     */
    private static final int HEIGHT = 6;

    /**
     * The temporary directory used for testing.
     */
    @TempDir
    Path tempDir;


    /**
     * Creates a new BufferedImageAdapter with the given size.
     * @return The created BufferedImageAdapter.
     */
    private static BufferedImageAdapter createAdapter() {
        return new BufferedImageAdapter(WIDTH, HEIGHT);
    }


    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.image.BufferedImageAdapter#BufferedImageAdapter(int, int)}.
     *
     */
    @Test
    void testSizeConstructor() {
        IFractalImage image = createAdapter();

        assertEquals(WIDTH, image.getWidth());
        assertEquals(HEIGHT, image.getHeight());
    }


    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.image.BufferedImageAdapter#BufferedImageAdapter(java.awt.image.BufferedImage)}.
     *
     */
    @Test
    void testImageConstructorUsesImageSize() {
        BufferedImage bufferedImage = new BufferedImage(10, 4, BufferedImage.TYPE_INT_RGB);
        IFractalImage image = new BufferedImageAdapter(bufferedImage);

        assertEquals(10, image.getWidth());
        assertEquals(4, image.getHeight());
    }

    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.image.BufferedImageAdapter#BufferedImageAdapter(java.awt.image.BufferedImage)}.
     */
    @Test
    void testImageConstructorRejectsNull() {
        assertThrows(NullPointerException.class, () -> new BufferedImageAdapter(null));
    }

    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.image.BufferedImageAdapter#getImage()}.
     */
    @Test
    void testGetBufferedImageReturnsAdaptedImage() {
        BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

        assertSame(bufferedImage, new BufferedImageAdapter(bufferedImage).getImage());
    }


    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.image.BufferedImageAdapter#setColor(int, int, java.awt.Color)}.
     */
    @Test
    void testSetColorUsesRowAndColumnInTheRightOrder() {
        BufferedImageAdapter image = createAdapter();

        image.setColor(5, 7, Color.RED);

        BufferedImage raw = image.getImage();
        assertEquals(Color.RED.getRGB(), raw.getRGB(7, 5));
    }

    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.image.BufferedImageAdapter#setColor(int, int, java.awt.Color)}.
     */
    @Test
    void testSetColorOnlyChangesOnePixel() {
        BufferedImageAdapter image = createAdapter();
        int black = Color.BLACK.getRGB();

        image.setColor(2, 3, Color.GREEN);

        BufferedImage raw = image.getImage();
        for (int row = 0; row < HEIGHT; row++) {
            for (int column = 0; column < WIDTH; column++) {
                int expected = (row == 2 && column == 3) ? Color.GREEN.getRGB() : black;
                assertEquals(expected, raw.getRGB(column, row));
            }
        }
    }


    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.image.BufferedImageAdapter#setColor(int, int, java.awt.Color)}.
     */
    @Test
    void testSetColorOverwritesPreviousColor() {
        BufferedImageAdapter image = createAdapter();

        image.setColor(0, 0, Color.RED);
        image.setColor(0, 0, Color.BLUE);

        assertEquals(Color.BLUE.getRGB(), image.getImage().getRGB(0, 0));
    }

    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.image.BufferedImageAdapter#setColor(int, int, java.awt.Color)}.
     */
    @Test
    void testSetColorOutOfBounds() {
        BufferedImageAdapter image = createAdapter();

        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> image.setColor(HEIGHT, 0, Color.RED));
        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> image.setColor(0, WIDTH, Color.RED));
        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> image.setColor(-1, 0, Color.RED));
    }

    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.image.BufferedImageAdapter#saveAs(java.lang.String)}.
     * @throws IOException if an I/O error occurs.
     */
    @Test
    void testSaveAsPng() throws IOException {
        BufferedImageAdapter image = createAdapter();
        image.setColor(5, 7, Color.RED);
        File file = tempDir.resolve("fractal.png").toFile();

        image.saveAs(file.getPath());

        BufferedImage saved = ImageIO.read(file);
        assertNotNull(saved);
        assertEquals(WIDTH, saved.getWidth());
        assertEquals(HEIGHT, saved.getHeight());
        assertEquals(Color.RED.getRGB(), saved.getRGB(7, 5));
        assertEquals(Color.BLACK.getRGB(), saved.getRGB(0, 0));
    }

    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.image.BufferedImageAdapter#saveAs(java.lang.String)}.
     * @throws IOException if an I/O error occurs.
     */
    @Test
    void testSaveAsJpg() throws IOException {
        BufferedImageAdapter image = createAdapter();
        File file = tempDir.resolve("fractal.jpg").toFile();

        image.saveAs(file.getPath());

        assertTrue(file.length() > 0);
        BufferedImage saved = ImageIO.read(file);
        assertNotNull(saved);
        assertEquals(WIDTH, saved.getWidth());
        assertEquals(HEIGHT, saved.getHeight());
    }

    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.image.BufferedImageAdapter#saveAs(java.lang.String)}.
     * @throws IOException if an I/O error occurs.
     */
    @Test
    void testSaveAsUpperCaseExtension() throws IOException {
        File file = tempDir.resolve("FRACTAL.PNG").toFile();

        createAdapter().saveAs(file.getPath());

        assertNotNull(ImageIO.read(file));
    }


    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.image.BufferedImageAdapter#saveAs(java.lang.String)}.
     * @throws IOException if an I/O error occurs.
     */
    @Test
    void testSaveAsWithoutExtensionUsesPng() throws IOException {
        File file = tempDir.resolve("fractal").toFile();

        createAdapter().saveAs(file.getPath());

        assertPng(file);
    }

    /**
     * Test method for
     * {@link fr.univartois.butinfo.fractalens.image.BufferedImageAdapter#saveAs(java.lang.String)}.
     *
     */
    @Test
    void testSaveAsInMissingDirectory() {
        BufferedImageAdapter image = createAdapter();
        String path = tempDir.resolve("missing").resolve("fractal.png").toString();

        assertThrows(IOException.class, () -> image.saveAs(path));
    }

    /**
     * Asserts that the given file is a PNG file.
     * @param file The file to check.
     * @throws IOException if an I/O error occurs.
     */
    private static void assertPng(File file) throws IOException {
        byte[] header = Files.readAllBytes(file.toPath());
        assertEquals((byte) 0x89, header[0]);
        assertEquals('P', header[1]);
        assertEquals('N', header[2]);
        assertEquals('G', header[3]);
        assertNotNull(ImageIO.read(file));
    }

}