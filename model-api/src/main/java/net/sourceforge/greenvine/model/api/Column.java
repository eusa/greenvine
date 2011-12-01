package net.sourceforge.greenvine.model.api;

import java.util.List;
import java.util.SortedSet;

import net.sourceforge.greenvine.model.naming.ColumnName;

public interface Column extends RdbmsNamedObject {

    public abstract ColumnName getName();
    
    public abstract Table getDataContainer();

    public abstract ColumnType getColumnType();

    public abstract boolean getNotNull();

    public abstract int getScale();

    public abstract int getPrecision();

    public abstract ColumnValueGenerationStrategy getColumnValueGenerationStrategy();

    public abstract boolean compatibleWith(Column column);

    public abstract boolean referencesAnotherColumn();

    public abstract SortedSet<? extends Column> getDirectlyReferencingColumns();

    public abstract List<? extends Column> getAllReferencingColumns();

    public abstract boolean partOfPrimaryKey();

    /**
     * Returns the specification of this column 
     * as a String in the form (scale,precision)
     * @return
     */
    public abstract String getSpecification();

}