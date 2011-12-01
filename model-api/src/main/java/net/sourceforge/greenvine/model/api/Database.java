package net.sourceforge.greenvine.model.api;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import net.sourceforge.greenvine.model.naming.NameContainer;
import net.sourceforge.greenvine.model.naming.RdbmsNameSegment;
import net.sourceforge.greenvine.model.naming.RdbmsNamingConventions;

public interface Database extends NameContainer, RdbmsNamedObject {

    public abstract Collection<? extends Table> getTables();

    public abstract Collection<? extends PrimaryKey> getPrimaryKeys();

    public abstract Collection<? extends UniqueKey> getUniqueKeys();

    public abstract Collection<? extends ForeignKey> getForeignKeys();

    public abstract Table getTableByName(CharSequence tableName)
            throws ModelException;

    public abstract PrimaryKey getPrimaryKeyByName(CharSequence pkName)
            throws ModelException;

    public abstract UniqueKey getUniqueKeyByName(CharSequence uniqueName)
            throws ModelException;

    public abstract ForeignKey getForeignKeyByName(
            CharSequence foreignKeyName) throws ModelException;

    public abstract DatabaseObject getDatabaseObjectByName(
            CharSequence objectName) throws ModelException;

    public abstract int getTableCount();

    public abstract int getForeignKeyCount();

    public abstract RdbmsNamingConventions getNamingConventions();

    /**
     * Returns the tables in the database with the most
     * dependent table last
     * @return
     */
    public abstract List<? extends Table> getTablesInDependencyOrder();

    public abstract SortedSet<? extends RdbmsNameSegment> getSchemaNames();

    public abstract Set<? extends ForeignKey> getForeignKeys(
            CharSequence referencingTable, CharSequence referencedTable)
            throws ModelException;

    public abstract List<? extends Table> getAssociationTables();

}