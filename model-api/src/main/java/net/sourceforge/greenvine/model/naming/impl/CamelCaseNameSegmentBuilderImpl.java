package net.sourceforge.greenvine.model.naming.impl;

import java.io.IOException;

import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.naming.CamelCaseNameSegmentBuilder;

/**
 * @author patrick
 *
 */
public class CamelCaseNameSegmentBuilderImpl implements CamelCaseNameSegmentBuilder {
    
    private final StringBuilder builder;

    public CamelCaseNameSegmentBuilderImpl(CharSequence chars) {
        this.builder = new StringBuilder(chars);
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
    
    public CamelCaseNameSegmentBuilderImpl append(CharSequence seq) {
        this.builder.append(seq);
        return this;
    }

    public CamelCaseNameSegmentBuilderImpl append(char character) throws IOException {
        this.builder.append(character);
        return this;
    }

    public CamelCaseNameSegmentBuilderImpl append(CharSequence seq, int begin, int end)
            throws IOException {
        this.builder.append(seq, begin, end);
        return this;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.naming.ModelNameSegmentBuilder#appendCamelCase(java.lang.CharSequence)
     */
    public CamelCaseNameSegmentBuilder appendCamelCase(CharSequence segment) {
        this.builder.append(ModelNameExtractorHelper.firstToUpperCase(segment.toString()));
        return this;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.naming.ModelNameSegmentBuilder#pluralise()
     */
    public CamelCaseNameSegmentBuilderImpl pluralise() throws ModelException {
        String word = builder.toString();
        int end = word.length()-1;
        if (word.endsWith("y")) {
            builder.deleteCharAt(end).append("ies");
        } else if (word.endsWith("x")) {
            builder.append("es");
        } else if (word.endsWith("s")) {
            builder.append("es");
        } else if (word.endsWith("sh")) {
            builder.append("es");
        } else if (word.endsWith("ch")) {
            builder.append("es");
        } else {
            builder.append("s");
        }
        return this;
    }
    
    /**
     * Attempt to remove plural-form
     * from a word. I expect that there
     * are a large number of edge cases
     * this method will fail with. Need
     * to improve over time!
     */
    public CamelCaseNameSegmentBuilder depluralise() {
        String word = builder.toString();
        int length = word.length();
        int end = word.length()-1;
        if (word.endsWith("ies")) {
            builder.delete(length - 3, length).append("y");
        } else if (word.endsWith("xes")) {
            builder.delete(length - 2, length);
        } else if (word.endsWith("ches")) {
            builder.delete(length - 2, length);
        } else if (word.endsWith("shes")) {
            builder.delete(length - 2, length);
        } else if (word.endsWith("sses") ) {
            builder.delete(length - 2, length);
        } else if (word.endsWith("ses") ) {
            builder.delete(length - 2, length);
        } else if (word.endsWith("s") && !word.endsWith("ss") && !word.matches("(.*)([au]s)")) {
            builder.deleteCharAt(end);
        }
        return this;
    }
    
    public String toString() {
        return this.builder.toString();
    }

}
