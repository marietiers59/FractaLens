package fr.univartois.butinfo.fractalens.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * The BufferedImageAdapter class implements the IFractalImage interface and provides
 * methods to manipulate a BufferedImage object.
 *
 * @Author Théo Brureau
 *
 */
public class BufferedImageAdapter implements IFractalImage{
    /**
     * The BufferedImage object that stores the fractal image.
     */
    private final BufferedImage image;

    /**
     * Creates a new BufferedImageAdapter with the given width and height.
     * @param width The width of the image.
     * @param height The height of the image.
     */
    public BufferedImageAdapter(int width, int height){
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    /**
     * Creates a new BufferedImageAdapter with the given BufferedImage object.
     * @param image The BufferedImage object to wrap.
     */
    public BufferedImageAdapter(BufferedImage image){
        this.image = Objects.requireNonNull(image);
    }

    /**
     * Returns the BufferedImage object that stores the fractal image.
     * @return
     */
    public BufferedImage getImage() {
        return this.image;
    }

    /**
     * Returns the height of the image.
     * @return
     */
    @Override
    public int getHeight() {
        return image.getHeight();
    }

    /**
     * Returns the width of the image.
     * @return
     */
    @Override
    public int getWidth() {
        return image.getWidth();
    }

    /**
     * Updates the color of the pixel at the given position in the image.
     * @param row The row of the pixel to update.
     * @param column The column of the pixel to update.
     * @param color The new color for the pixel.
     */
    @Override
    public void setColor(int row, int column, Color color) {
        this.image.setRGB(column, row, color.getRGB());
    }

    /**
     * Saves this image in a file.
     * @param path The path of the file in which to save this image.
     *
     * @throws IOException
     */
    @Override
    public void saveAs(String path) throws IOException {
        ImageIO.write(this.image, "png", new java.io.File(path));
    }
}
