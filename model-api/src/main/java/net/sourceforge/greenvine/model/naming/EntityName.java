package net.sourceforge.greenvine.model.naming;


public interface EntityName extends CompoundName<NameSegment> {

    public CamelCaseNameSegment getObjectName();
    
    public LowerCaseNameSegment getNamespace();
    
}