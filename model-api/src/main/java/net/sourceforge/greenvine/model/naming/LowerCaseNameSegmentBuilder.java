package net.sourceforge.greenvine.model.naming;

import net.sourceforge.greenvine.model.api.ModelException;

public interface LowerCaseNameSegmentBuilder extends NameSegmentBuilder {


    /**
     * Add plural form to supplied
     * word. No doubt this method
     * will not be 100% reliable
     * due to exceptions like "fish"
     * @return
     * @throws ModelException
     */
    public abstract LowerCaseNameSegmentBuilder pluralise()
            throws ModelException;

    /**
     * Remove plural form to supplied
     * word. Likely to be less reliable
     * than pluralise() due to the
     * larger number of exceptions
     * and words that look like plurals
     * but are in fact singular like
     * "rabies"
     * @return
     */
    public abstract LowerCaseNameSegmentBuilder depluralise();

}