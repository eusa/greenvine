package net.sourceforge.greenvine.model.api;

public interface MutableFieldCollection extends FieldCollection {
    
    abstract public <T extends Field> void addField(T field) throws ModelException;

}
