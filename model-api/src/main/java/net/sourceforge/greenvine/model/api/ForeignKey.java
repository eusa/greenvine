package net.sourceforge.greenvine.model.api;

import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import net.sourceforge.greenvine.model.naming.ColumnName;
import net.sourceforge.greenvine.model.naming.DatabaseObjectName;

public interface ForeignKey extends DatabaseObject {
    
    public DatabaseObjectName getName();

    public boolean referencesUniqueKey();

    public boolean referencesPrimaryKey();

    public SortedSet<ColumnName> getReferencingColumnNames();

    public SortedSet<ColumnName> getReferencedColumnNames();

    public Map<ColumnName, ColumnName> getColumnConstraintNames();

    public Table getReferencingTable();

    public Table getReferencedTable();

    public Set<? extends ColumnConstraint> getColumnConstraints();

    public int getColumnConstraintCount();

    public ColumnConstraint getColumnConstraint(int index);

    public ColumnConstraint getColumnConstraintByReferencingColumnName(
            ColumnName referencingColumnName) throws ModelException;

    public ColumnConstraint getColumnConstraintByReferencedColumnName(
            ColumnName referencedColumnName) throws ModelException;

    public boolean hasConstraint(CharSequence referencingColumn,
            CharSequence referencedColumn) throws ModelException;

    public boolean hasReferencingColumn(Column column);

    public boolean hasReferencedColumn(Column column);

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
    public boolean areReferencingColumnsUniqueKey();

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
    public boolean areReferencingColumnsPrimaryKey();

    public SortedSet<? extends Column> getReferencingColumns();

    public SortedSet<? extends Column> getReferencedColumns();

    /**
     * Are all of the referencing
     * columns in this key able
     * to be set to null
     * @return
     */
    public boolean areReferencingColumnsNullable();

    public boolean isSelfReferencing();

    /**
     * Return the cardinality of the relationship
     * modeled by this foreign key.
     * It will be one-to-one if the referencing
     * columns are unique or primary. Otherwise,
     * it is one-to-many
     * @return
     */
    public Cardinality getCardinality();

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
    public boolean otherSimilarRelationsExist() throws ModelException;

	public UniqueKey getReferencingUniqueKey();

}