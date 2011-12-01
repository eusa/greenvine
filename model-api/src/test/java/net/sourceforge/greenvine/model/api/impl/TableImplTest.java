package net.sourceforge.greenvine.model.api.impl;

import java.util.SortedSet;
import java.util.TreeSet;

import junit.framework.Assert;

import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.ColumnType;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.PrimaryKey;
import net.sourceforge.greenvine.model.api.UniqueKey;
import net.sourceforge.greenvine.model.api.impl.DatabaseImpl;
import net.sourceforge.greenvine.model.api.impl.TableImpl;

import org.junit.Before;
import org.junit.Test;

public class TableImplTest {
    
    private DatabaseImpl database;
    
    @Before
    public void setUp() throws ModelException {
        
        // Create database
        this.database = new DatabaseImpl("testdb");
        
    }

    @Test
    public void testCreateValid() throws ModelException {
        
        // Set up table, primary and unique keys
        TableImpl tbl_test = new TableImpl("tbl_test", database);
        Column tbl_test_pk1 = tbl_test.createColumn("tbl_test_pk1", ColumnType.INTEGER, true, 8, 0);
        Column tbl_test_pk2 = tbl_test.createColumn("tbl_test_pk2", ColumnType.INTEGER, true, 8, 0);
        Column tbl_test_not_unique = tbl_test.createColumn("tbl_test_not_unique", ColumnType.INTEGER, true, 8, 0);
        Column tbl_test_unique = tbl_test.createColumn("tbl_test_unique", ColumnType.INTEGER, true, 8, 0);
        SortedSet<String> pkCols1 = new TreeSet<String>();
        pkCols1.add("tbl_test_pk1");
        pkCols1.add("tbl_test_pk2");
        PrimaryKey pk = tbl_test.createPrimaryKey("tbl_test_pk", pkCols1);
        SortedSet<String> ukCols1 = new TreeSet<String>();
        ukCols1.add("tbl_test_unique");
        UniqueKey uk = tbl_test.createUniqueKey("tbl_test_uk", ukCols1);
        
        //Test table
        Assert.assertEquals("tbl_test", tbl_test.getName().toString());
        Assert.assertEquals(database, tbl_test.getDatabase());
        
        // Test columns
        Assert.assertEquals(4, tbl_test.getColumnCount());
        Assert.assertEquals(tbl_test_pk1, tbl_test.getColumnByName("tbl_test_pk1"));
        Assert.assertEquals(tbl_test_pk2, tbl_test.getColumnByName("tbl_test_pk2"));
        Assert.assertEquals(tbl_test_not_unique, tbl_test.getColumnByName("tbl_test_not_unique"));
        Assert.assertEquals(tbl_test_unique, tbl_test.getColumnByName("tbl_test_unique"));
        
        // Test keys
        Assert.assertNotNull(tbl_test.getPrimaryKey());
        Assert.assertEquals(pk, tbl_test.getPrimaryKey());
        Assert.assertNotNull(tbl_test.getUniqueKeys());
        Assert.assertEquals(1, tbl_test.getUniqueKeyCount());
        Assert.assertEquals(uk, tbl_test.getUniqueKeyByName("tbl_test_uk"));
        
    }
    
    @Test(expected = ModelException.class) 
    public void testCreateNullName() throws ModelException {
        new TableImpl(null, database);
    }

    @Test(expected = ModelException.class) 
    public void testCreateInvalidName() throws ModelException {
        new TableImpl("", database);
    }
    
    @Test(expected = ModelException.class) 
    public void testCreateNullDatabase() throws ModelException {
        new TableImpl("tbl_test", null);
    }
    
}
