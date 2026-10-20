package fr.univartois.butinfo.fractalens.palette;

/**
 * Décorateur abstrait de palette : enveloppe une palette existante
 * afin d'en modifier les couleurs.
 */
public abstract class PaletteDecorator implements IPalette {

    /**
     * La palette décorée.
     */
    protected final IPalette wrapped;

    /**
     * Crée un décorateur de palette.
     *
     * @param wrapped La palette à décorer.
     */
    public PaletteDecorator(IPalette wrapped) {
        this.wrapped = wrapped;
    }
}