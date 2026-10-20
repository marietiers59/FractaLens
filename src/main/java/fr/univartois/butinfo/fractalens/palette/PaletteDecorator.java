package fr.univartois.butinfo.fractalens.palette;

/**
 * Abstract palette decorator: wraps an existing palette in order to
 * modify its colors.
 */
public abstract class PaletteDecorator implements IPalette {

    /**
     * The decorated palette.
     */
    protected final IPalette wrapped;

    /**
     * Creates a palette decorator.
     *
     * @param wrapped The palette to decorate.
     */
    public PaletteDecorator(IPalette wrapped) {
        this.wrapped = wrapped;
    }
}