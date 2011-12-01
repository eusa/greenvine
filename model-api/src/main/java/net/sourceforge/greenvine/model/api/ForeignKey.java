package net.sourceforge.greenvine.model.api;

import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import net.sourceforge.greenvine.model.naming.ColumnName;
import net.sourceforge.greenvine.model.naming.DatabaseObjectName;

public interface ForeignKey extends DatabaseObject {
    
    public abstract DatabaseObjectName getName();

    public abstract boolean referencesUniqueKey();

    public abstract boolean referencesPrimaryKey();

    public abstract SortedSet<ColumnName> getReferencingColumnNames();

    public abstract SortedSet<ColumnName> getReferencedColumnNames();

    public abstract Map<ColumnName, ColumnName> getColumnConstraintNames();

    public abstract Table getReferencingTable();

    public abstract Table getReferencedTable();

    public abstract Set<? extends ColumnConstraint> getColumnConstraints();

    public abstract int getColumnConstraintCount();

    public abstract ColumnConstraint getColumnConstraint(int index);

    public abstract ColumnConstraint getColumnConstraintByReferencingColumnName(
            ColumnName referencingColumnName) throws ModelException;

    public abstract ColumnConstraint getColumnConstraintByReferencedColumnName(
            ColumnName referencedColumnName) throws ModelException;

    public abstract boolean hasConstraint(CharSequence referencingColumn,
            CharSequence referencedColumn) throws ModelException;

    public abstract boolean hasReferencingColumn(Column column);

    public abstract boolean hasReferencedColumn(Column column);

    /**
     * Returns true if the referencing
     * columns in a {@link ForeignKey}
     * are also constrained by 
     * a {@link UniqueKey}. In other
     * words every column in the
     * foreign key is contained
     * in a corresponding unique key
     * @param constraint
     * @param foreignTable
     * @return
     */
    public abstract boolean areReferencingColumnsUniqueKey();

    /**
     * True if the {@link ForeignKey}
     * referencing {@Column}s are also
     * the {@link PrimaryKey} of the 
     * referencing {@link Table}.
     * This means that the foreign columns
     * defined in the {@link ColumnConstraint}
     * of the {@link ForeignKey} must have
     * the same names as the columns referenced
     * by the {@link PrimaryKey}
     * @return
     */
    public abstract boolean areReferencingColumnsPrimaryKey();

    public abstract SortedSet<? extends Column> getReferencingColumns();

    public abstract SortedSet<? extends Column> getReferencedColumns();

    /**
     * Are all of the referencing
     * columns in this key able
     * to be set to null
     * @return
     */
    public abstract boolean areReferencingColumnsNullable();

    public abstract boolean isSelfReferencing();

    /**
     * Return the cardinality of the relationship
     * modeled by this foreign key.
     * It will be one-to-one if the referencing
     * columns are unique or primary. Otherwise,
     * it is one-to-many
     * @return
     */
    public abstract Cardinality getCardinality();

    /**
     * This method returns true if another
     * foreign key exists in the database
     * that links the same tables with the same
     * cardinality. This would be true
     * if a table had two (or more) foreign keys 
     * to another table where at least one of 
     * these keys would result in the same 
     * cardinality of relationship (e.g. 
     * one-to-one or one-to-many). This is
     * determined by the uniqueness of the
     * referencing columns in the key 
     * @return
     * @throws ModelException
     */
    public abstract boolean otherSimilarRelationsExist() throws ModelException;

}