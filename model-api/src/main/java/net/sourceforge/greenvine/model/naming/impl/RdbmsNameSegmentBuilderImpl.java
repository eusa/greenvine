package net.sourceforge.greenvine.model.naming.impl;

import java.io.IOException;

import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.naming.RdbmsNamingConvention;

public class RdbmsNameSegmentBuilderImpl extends AbstractRdbmsNameSegmentBuilder {
    
    public RdbmsNameSegmentBuilderImpl(CharSequence chars,
            RdbmsNamingConvention convention) throws ModelException {
        super(chars, convention);
    }

    public ColumnNameImpl toColumnName() throws ModelException {
        return new ColumnNameImpl(this.builder);
    }

    public RdbmsNameSegmentBuilderImpl append(CharSequence seq) {
        this.builder.append(seq);
        return this;
    }

    public RdbmsNameSegmentBuilderImpl append(char character) throws IOException {
        this.builder.append(character);
        return this;
    }

    public RdbmsNameSegmentBuilderImpl append(CharSequence seq, int begin, int end)
            throws IOException {
        this.builder.append(seq, begin, end);
        return this;
    }
    
    public RdbmsNameSegmentBuilderImpl appendSeparator() {
        if (convention.hasSeparator()) {
            this.builder.append(convention.getSeparator());
        }
        return this;
    }
    
    public RdbmsNameSegmentBuilderImpl removeSuffixPrefixIncludingSeparators() {
        CharSequence processed = helper.removeSuffixPrefixIncludingSeparators(builder);
        replaceStringBuilderContents(processed);
        return this;
    }

    public RdbmsNameSegmentBuilderImpl surroundWithSuffixAndPrefix() {
        CharSequence processed = helper.surroundWithSuffixAndPrefix(this.builder);
        replaceStringBuilderContents(processed);
        return this;
    }

    public RdbmsNameSegmentBuilderImpl insertSeparatorAtCapitals() {
        CharSequence processed = helper.insertSeparatorAtCapitals(this.builder);
        replaceStringBuilderContents(processed);
        return this;
    }

    public RdbmsNameSegmentBuilderImpl toCase() {
        CharSequence processed = helper.toCase(this.builder);
        replaceStringBuilderContents(processed);
        return this;
    }

    public RdbmsNameSegmentBuilderImpl prependPrefix() {
        CharSequence processed = helper.prependPrefix(this.builder);
        replaceStringBuilderContents(processed);
        return this;
    }

}
