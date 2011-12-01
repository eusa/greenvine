package net.sourceforge.greenvine.model.naming.impl;

import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.naming.RdbmsNameSegmentBuilder;
import net.sourceforge.greenvine.model.naming.RdbmsNamingConvention;

public abstract class AbstractRdbmsNameSegmentBuilder implements RdbmsNameSegmentBuilder {

    protected final StringBuilder builder;
    protected final RdbmsNamingConvention convention;
    protected final RdbmsNameBuilderHelper helper;

    public AbstractRdbmsNameSegmentBuilder(CharSequence chars, RdbmsNamingConvention convention) throws ModelException {
        validateName(chars);
        validateConvention(convention);
        this.builder = new StringBuilder(chars);
        this.convention = convention;
        this.helper = new RdbmsNameBuilderHelper(convention);
    }
    
    
    private void validateName(CharSequence chars) throws ModelException {
        if (chars == null || chars.length() == 0) {
            throw new ModelException("Cannot create a DatabaseNameBuilder with a null or empty name.");
        }
    }
    
    private void validateConvention(RdbmsNamingConvention convention) throws ModelException {
        if (convention == null) {
            throw new ModelException("Cannot create a DatabaseNameBuilder with a null NamingConvention.");
        }
    }
    
    public String toString() {
        return this.builder.toString();
    }
    
    public char charAt(int index) {
        return builder.charAt(index);
    }

    public int length() {
        return builder.length();
    }

    public CharSequence subSequence(int start, int end) {
        return builder.subSequence(start, end);
    }
    
    protected void replaceStringBuilderContents(CharSequence replacement) {
        builder.delete(0, builder.length());
        builder.append(replacement);
    }
    
}
