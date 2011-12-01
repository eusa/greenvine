package net.sourceforge.greenvine.model.naming;

public interface RdbmsNameSegmentBuilder extends NameSegmentBuilder {
    
    public RdbmsNameSegmentBuilder removeSuffixPrefixIncludingSeparators();

    public RdbmsNameSegmentBuilder surroundWithSuffixAndPrefix();

    public RdbmsNameSegmentBuilder toCase();

    public RdbmsNameSegmentBuilder appendSeparator();

    public RdbmsNameSegmentBuilder insertSeparatorAtCapitals();

}
