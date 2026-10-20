package fr.univartois.butinfo.fractalens.palette;

public abstract class PaletteDecorator implements IPalette {

    protected final IPalette wrapped;

    public PaletteDecorator(IPalette wrapped) {
        this.wrapped = wrapped;
    }
}