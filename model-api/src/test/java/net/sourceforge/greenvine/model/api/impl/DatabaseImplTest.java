package net.sourceforge.greenvine.model.api.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import junit.framework.Assert;
import net.sourceforge.greenvine.model.api.ColumnType;
import net.sourceforge.greenvine.model.api.ForeignKey;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.PrimaryKey;
import net.sourceforge.greenvine.model.api.Table;
import net.sourceforge.greenvine.model.api.UniqueKey;

import org.junit.Test;

public class DatabaseImplTest {
    
    @Test
    public void testCreateValid() throws ModelException {
        DatabaseImpl database = new DatabaseImpl("TestDB");
        Assert.assertEquals("TestDB", database.getName().toString());
    }
    
    @Test(expected = ModelException.class)
    public void testCreateNullName() throws ModelException {
        new DatabaseImpl(null);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateEmptyName() throws ModelException {
        new DatabaseImpl("");
    }
    
    @Test
    public void testCreateTable() throws ModelException {
        DatabaseImpl database = new DatabaseImpl("TestDB");
        TableImpl table = database.createTable("Test_Table");
        Assert.assertEquals("Test_Table", table.getName().toString());
        Assert.assertEquals(database, table.getDatabase());
        Assert.assertEquals(1, database.getTableCount());
    }
    
    @Test(expected = ModelException.class)
    public void testFindTable() throws ModelException {
        DatabaseImpl database = new DatabaseImpl("TestDB");
        Table table = database.createTable("Test_Table");
        Table found = database.getTableByName("Test_Table");
        Assert.assertEquals(table, found);
        database.getTableByName("No_Way");
    }
    
    @Test(expected = ModelException.class)
    public void testCreateDuplicateTable() throws ModelException {
        DatabaseImpl database = new DatabaseImpl("TestDB");
        database.createTable("Test_Table");
        database.createTable("Test_Table");
    }

    
    @Test
    public void testFindPrimaryKey() throws ModelException {
        DatabaseImpl database = new DatabaseImpl("TestDB");
        TableImpl table = database.createTable("Test_Table");
        ColumnImpl pk = table.createColumn("pk", ColumnType.INTEGER, true, 4, 0);
        SortedSet<CharSequence> pkCols = new TreeSet<CharSequence>();
        pkCols.add(pk.getName());
        PrimaryKeyImpl key = table.createPrimaryKey("test_pk", pkCols);
        PrimaryKey found = database.getPrimaryKeyByName(key.getName());
        Assert.assertEquals(key, found);
    }
    
    @Test(expected = ModelException.class)
    public void testFindPrimaryKeyInvalid() throws ModelException {
        DatabaseImpl database = new DatabaseImpl("TestDB");
        database.getPrimaryKeyByName("no_chance");
    }
    
    @Test
    public void testFindUniqueKey() throws ModelException {
        DatabaseImpl database = new DatabaseImpl("TestDB");
        TableImpl table = database.createTable("Test_Table");
        ColumnImpl uk = table.createColumn("pk", ColumnType.INTEGER, true, 4, 0);
        SortedSet<CharSequence> ukCols = new TreeSet<CharSequence>();
        ukCols.add(uk.getName());
        UniqueKeyImpl key = table.createUniqueKey("test_pk", ukCols);
        UniqueKey found = database.getUniqueKeyByName(key.getName());
        Assert.assertEquals(key, found);
    }
    
    @Test(expected = ModelException.class)
    public void testFindUniqueKeyInvalid() throws ModelException {
        DatabaseImpl database = new DatabaseImpl("TestDB");
        database.getUniqueKeyByName("no_chance");
    }
    
    @Test
    public void testCreateForeignKey() throws ModelException {
        DatabaseImpl database = new DatabaseImpl("TestDB");
        TableImpl referenced = database.createTable("Referenced_Table");
        TableImpl referencing = database.createTable("Referencing_Table");
        ColumnImpl pk = referenced.createColumn("pk", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl fk = referencing.createColumn("fk", ColumnType.INTEGER, true, 4, 0);
        SortedSet<CharSequence> pkCols = new TreeSet<CharSequence>();
        pkCols.add(pk.getName());
        referenced.createPrimaryKey("test_pk", pkCols);
        Map<CharSequence, CharSequence> columnConstraints = new HashMap<CharSequence, CharSequence>();
        columnConstraints.put(fk.getName(), pk.getName());
        ForeignKey created = database.createForeignKey("fk_name", referencing.getName(), referenced.getName(), columnConstraints);
        ForeignKey found = database.getForeignKeyByName("fk_name");
        Assert.assertEquals(created, found);
    }
    
    @Test(expected = ModelException.class)
    public void testFindForeignKeyInvalid() throws ModelException {
        DatabaseImpl database = new DatabaseImpl("TestDB");
        database.getUniqueKeyByName("no_chance");
    }
}