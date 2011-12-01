package net.sourceforge.greenvine.model.api;


public interface ColumnConstraint {

    public abstract ForeignKey getForeignKey();

    public abstract Column getReferencedColumn();

    public abstract Column getReferencingColumn();

}