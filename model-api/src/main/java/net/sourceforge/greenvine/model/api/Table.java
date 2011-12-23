package net.sourceforge.greenvine.model.api;

import java.util.Collection;
import java.util.List;

public interface Table extends ColumnCollection, DatabaseObject {

    public abstract Database getDatabase();

    public abstract PrimaryKey getPrimaryKey();
    
    public abstract UniqueKey getNaturalKey();

    public abstract Collection<? extends UniqueKey> getUniqueKeys();

    public abstract Collection<? extends ForeignKey> getImportedForeignKeys();

    public abstract Collection<? extends ForeignKey> getExportedForeignKeys();
    
    public abstract int getImportedForeignKeyCount();

    public abstract int getExportedForeignKeyCount();

    public abstract int getUniqueKeyCount();

    public abstract UniqueKey getUniqueKeyByName(CharSequence uniqueKeyName)
            throws ModelException;

    public abstract ForeignKey getForeignKeyByName(CharSequence foreignKeyName)
            throws ModelException;

    public abstract boolean hasPrimaryKey();

    public abstract boolean hasUniqueKeys();

    public abstract boolean hasImportedForeignKeys();

    public abstract boolean hasExportedForeignKeys();

    public abstract UniqueKey getUniqueKey(int ordinal);

    public abstract ForeignKey getImportedForeignKey(int ordinal);

    public abstract ForeignKey getExportedForeignKey(int ordinal);

    public abstract boolean isAssociationTable();

    public abstract Collection<? extends Table> getReferencedTables();

    /**
     * Returns the tables in the database with the most
     * dependent table last
     * @return
     */
    public abstract List<? extends Table> getDependentTablesInDependencyOrder();

    public abstract boolean isView();

}