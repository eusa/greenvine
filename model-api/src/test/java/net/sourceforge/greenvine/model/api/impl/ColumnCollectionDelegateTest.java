package net.sourceforge.greenvine.model.api.impl;

import junit.framework.Assert;
import net.sourceforge.greenvine.model.api.ColumnType;
import net.sourceforge.greenvine.model.api.ModelException;

import org.junit.Before;
import org.junit.Test;

public class ColumnCollectionDelegateTest {
    
    private DatabaseImpl database;
    private TableImpl table;
    private ColumnImpl column1;
    private ColumnImpl column2;
    private ColumnImpl column3;
    
    @Before
    public void setUp() throws ModelException {
        database = new DatabaseImpl("test_db");
        table = database.createTable("test_tbl");
        column1 = table.createColumn("test_id", ColumnType.INTEGER, true, 4, 0);
        column2 = table.createColumn("test_data1", ColumnType.VARCHAR, true, 200, 0);
        column3 = table.createColumn("test_data2", ColumnType.DATE, true, 8, 0);
    }
    
    @Test
    public void testCreateValid() throws ModelException {
        ColumnCollectionDelegate columnCollection = new ColumnCollectionDelegate(table);
        columnCollection.addColumn(column1);
        columnCollection.addColumn(column2);
        columnCollection.addColumn(column3);
        Assert.assertEquals(3, columnCollection.getColumnCount());
        Assert.assertTrue(columnCollection.contains(column1));
        Assert.assertTrue(columnCollection.contains(column2));
        Assert.assertTrue(columnCollection.contains(column3));
        Assert.assertEquals(column1, columnCollection.getColumnByName("test_id"));
        Assert.assertEquals(column2, columnCollection.getColumnByName("test_data1"));
        Assert.assertEquals(column3, columnCollection.getColumnByName("test_data2"));
        
    }

    @Test(expected = ModelException.class)
    public void testCreateDuplicateColumn() throws ModelException {
        ColumnCollectionDelegate columnCollection = new ColumnCollectionDelegate(table);
        columnCollection.addColumn(column1);
        columnCollection.addColumn(column1);
    }
    
    @Test
    public void testColumnsEqual() throws ModelException {
        ColumnCollectionDelegate columnCollection1 = new ColumnCollectionDelegate(table);
        columnCollection1.addColumn(column1);
        columnCollection1.addColumn(column2);
        columnCollection1.addColumn(column3);
        ColumnCollectionDelegate columnCollection2 = new ColumnCollectionDelegate(table);
        columnCollection2.addColumn(column3);
        columnCollection2.addColumn(column1);
        columnCollection2.addColumn(column2);
        Assert.assertTrue(columnCollection1.columnsEqual(columnCollection2));
    }
   
    
    @Test
    public void testEquals() throws ModelException {
        ColumnCollectionDelegate columnCollection1 = new ColumnCollectionDelegate(table);
        columnCollection1.addColumn(column1);
        columnCollection1.addColumn(column2);
        columnCollection1.addColumn(column3);
        ColumnCollectionDelegate columnCollection2 = new ColumnCollectionDelegate(table);
        columnCollection2.addColumn(column3);
        columnCollection2.addColumn(column1);
        columnCollection2.addColumn(column2);
        Assert.assertEquals(columnCollection1, columnCollection2);
    }
   

}
