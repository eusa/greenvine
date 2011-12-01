package net.sourceforge.greenvine.model.api.impl;

import net.sourceforge.greenvine.model.api.ColumnConstraint;
import net.sourceforge.greenvine.model.api.ForeignKey;
import net.sourceforge.greenvine.model.api.ModelException;

public class ColumnConstraintImpl implements Comparable<ColumnConstraintImpl>, ColumnConstraint {
   
    private final ForeignKeyImpl foreignKey;
    private final ColumnImpl referencedColumn;
    private final ColumnImpl referencingColumn;

    public ColumnConstraintImpl(ForeignKeyImpl foreignKey,
            ColumnImpl referencingColumn, ColumnImpl referencedColumn) throws ModelException {
        super();
        
        // Validate parameters
        if (foreignKey == null) {
            throw new ModelException(String.format("Column constraint cannot be created with a null foreign key."));
        } 
        if (referencedColumn == null) {
            throw new ModelException(String.format("Column constraint cannot be created with a null referenced column."));
        }
        if (referencingColumn == null) {
            throw new ModelException(String.format("Column constraint cannot be created with a null referencing column."));
        }
        
        // Initialise fields
        this.foreignKey = foreignKey;
        this.referencedColumn = referencedColumn;
        this.referencingColumn = referencingColumn;
        
        // Add the referencing column to the referenced column
        referencedColumn.addReferencingColumn(referencingColumn);
        
        // Add to the foreign key
        this.foreignKey.addColumnConstraint(this);
        
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.ColumnConstraint#getForeignKey()
     */
    public ForeignKey getForeignKey() {
        return foreignKey;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.ColumnConstraint#getReferencedColumn()
     */
    public ColumnImpl getReferencedColumn() {
        return referencedColumn;
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.ColumnConstraint#getReferencingColumn()
     */
    public ColumnImpl getReferencingColumn() {
        return referencingColumn;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime
                * result
                + ((referencedColumn == null) ? 0 : referencedColumn.hashCode());
        result = prime
                * result
                + ((referencingColumn == null) ? 0 : referencingColumn
                        .hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ColumnConstraintImpl other = (ColumnConstraintImpl) obj;
        if (referencedColumn == null) {
            if (other.referencedColumn != null)
                return false;
        } else if (!referencedColumn.equals(other.referencedColumn))
            return false;
        if (referencingColumn == null) {
            if (other.referencingColumn != null)
                return false;
        } else if (!referencingColumn.equals(other.referencingColumn))
            return false;
        return true;
    }

    public int compareTo(ColumnConstraintImpl o) {
        return this.referencingColumn.compareTo(o.getReferencingColumn());
    }
    
    @Override
    public String toString() {
        return "ColumnConstraint [foreignKey=" + foreignKey
                + ", referencedColumn=" + referencedColumn
                + ", referencingColumn=" + referencingColumn + "]";
    }
    
}
