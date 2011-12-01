package net.sourceforge.greenvine.model.naming;

import java.util.List;

public interface CompoundName<T extends NameSegment> extends Name {
    
    public List<T> getSegments();
    
    public Character getSeparatorChar();

}
