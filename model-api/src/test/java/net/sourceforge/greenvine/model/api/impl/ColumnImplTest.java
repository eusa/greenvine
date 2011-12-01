package net.sourceforge.greenvine.model.api.impl;

import junit.framework.Assert;
import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.ColumnType;
import net.sourceforge.greenvine.model.api.ModelException;

import org.junit.Before;
import org.junit.Test;

public class ColumnImplTest {
    
    private DatabaseImpl database;
    private TableImpl table;
    
    @Before
    public void setUp() throws ModelException {
        database = new DatabaseImpl("test_db");
        table = database.createTable("test_tbl");
    }
    
    @Test
    public void testCreateValid() throws ModelException {
        ColumnImpl column = table.createColumn("test_id", ColumnType.INTEGER, true, 4, 0);
        Assert.assertEquals(table, column.getDataContainer());
        Assert.assertEquals("test_id", column.getName().toString());
        Assert.assertEquals(ColumnType.INTEGER, column.getColumnType());
        Assert.assertEquals(true, column.getNotNull());
        Assert.assertEquals(4, column.getScale());
        Assert.assertEquals(0, column.getPrecision());
    }

    @Test(expected = ModelException.class)
    public void testCreateNullName() throws ModelException {
        table.createColumn(null, ColumnType.INTEGER, true, 4, 0);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateEmptyName() throws ModelException {
        table.createColumn("", ColumnType.INTEGER, true, 4, 0);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateNullType() throws ModelException {
        table.createColumn("test_col", null, true, 4, 0);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateDuplicate() throws ModelException {
        table.createColumn("test_id", ColumnType.INTEGER, true, 4, 0);
        table.createColumn("test_id", ColumnType.INTEGER, true, 4, 0);
    }
    
    @Test
    public void testCompatibility() throws ModelException {
        Column column1 = table.createColumn("test_col1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl column2 = table.createColumn("test_col2", ColumnType.INTEGER, true, 4, 0);
        Assert.assertTrue(column1.compatibleWith(column2));
        ColumnImpl column3 = table.createColumn("test_col3", ColumnType.INTEGER, true, 4, 1);
        Assert.assertFalse(column1.compatibleWith(column3));
        ColumnImpl column4 = table.createColumn("test_col4", ColumnType.INTEGER, true, 5, 0);
        Assert.assertFalse(column1.compatibleWith(column4));
        ColumnImpl column5 = table.createColumn("test_col5", ColumnType.REAL, true, 4, 0);
        Assert.assertFalse(column1.compatibleWith(column5));
        ColumnImpl column6 = table.createColumn("test_col6", ColumnType.INTEGER, false, 4, 0);
        Assert.assertTrue(column1.compatibleWith(column6));
    }

}
