package net.sourceforge.greenvine.dbextractor.testutils;

import net.sourceforge.greenvine.model.api.ColumnCollection;
import net.sourceforge.greenvine.model.api.Database;
import net.sourceforge.greenvine.model.api.ForeignKey;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.PrimaryKey;
import net.sourceforge.greenvine.model.api.Table;
import net.sourceforge.greenvine.model.api.UniqueKey;

import org.junit.Assert;

public class DatabaseAssert {
    
    private final Database database;
    
    public DatabaseAssert(final Database database) {
        this.database = database;
    }
    
    public void assertTableExists(final CharSequence table) {
        try {
            database.getTableByName(table);
        } catch (ModelException modelEx) {
            throw new AssertionError(modelEx.getMessage());
        }
    }
    
    private void assertColumnExists(final ColumnCollection columns, final CharSequence column) {
        try {
            columns.getColumnByName(column);
        } catch (ModelException modelEx) {
            throw new AssertionError(modelEx.getMessage());
        }
    }
    
    public void assertColumnExists(final CharSequence table, final CharSequence column) {
        try {
            final Table tbl = database.getTableByName(table);
            assertColumnExists(tbl, column);
        } catch (ModelException modelEx) {
            throw new AssertionError(modelEx.getMessage());
        }
    }
    
    public void assertTableExists(final CharSequence table, final CharSequence[] columns) {
        try {
            final Table tbl = database.getTableByName(table);
            Assert.assertEquals(tbl.getColumnCount(), columns.length);
            for (CharSequence column : columns) {
                assertColumnExists(tbl, column);
            }
        } catch (ModelException modelEx) {
            throw new AssertionError(modelEx.getMessage());
        }
    }
    
    public void assertForeignKeyExists(final CharSequence foreignKey, final CharSequence referencingTable, final CharSequence referencedTable) {
        try {
            final ForeignKey fKey = database.getForeignKeyByName(foreignKey);
            Assert.assertEquals(referencingTable, fKey.getReferencingTable().getName().toString());
            Assert.assertEquals(referencedTable, fKey.getReferencedTable().getName().toString());
        } catch (ModelException modelEx) {
            throw new AssertionError(modelEx.getMessage());
        }
    }
    
    public void assertPrimaryKeyExists(final CharSequence primaryKey, final CharSequence[] columns) {
        try {
            final PrimaryKey pKey = database.getPrimaryKeyByName(primaryKey);
            Assert.assertEquals(pKey.getColumnCount(), columns.length);
            for (CharSequence column : columns) {
                assertColumnExists(pKey, column);
            }
        } catch (ModelException modelEx) {
            throw new AssertionError(modelEx.getMessage());
        }
    }
    
    public void assertUniqueKeyExists(final CharSequence uniqueKey, final CharSequence[] columns) {
        try {
            final UniqueKey uKey = database.getUniqueKeyByName(uniqueKey);
            Assert.assertEquals(uKey.getColumnCount(), columns.length);
            for (CharSequence column : columns) {
                assertColumnExists(uKey, column);
            }
        } catch (ModelException modelEx) {
            throw new AssertionError(modelEx.getMessage());
        }
    }

}
