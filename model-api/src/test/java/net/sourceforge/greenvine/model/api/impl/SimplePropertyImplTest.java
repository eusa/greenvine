package net.sourceforge.greenvine.model.api.impl;

import junit.framework.Assert;
import net.sourceforge.greenvine.model.api.ColumnType;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.PropertyType;
import net.sourceforge.greenvine.model.api.impl.CatalogImpl;
import net.sourceforge.greenvine.model.api.impl.ColumnImpl;
import net.sourceforge.greenvine.model.api.impl.DatabaseImpl;
import net.sourceforge.greenvine.model.api.impl.EntityImpl;
import net.sourceforge.greenvine.model.api.impl.ModelImpl;
import net.sourceforge.greenvine.model.api.impl.SimplePropertyImpl;
import net.sourceforge.greenvine.model.api.impl.TableImpl;
import net.sourceforge.greenvine.model.naming.testutils.NamingConventionFactory;

import org.junit.Before;
import org.junit.Test;

public class SimplePropertyImplTest {
    private DatabaseImpl database;
    private EntityImpl entity;
    private CatalogImpl catalog;
    private TableImpl table;
    private ColumnImpl column;
    private TableImpl otherTable;
    private ColumnImpl otherColumn;
    
    @Before
    public void setUp() throws ModelException {
        
        // Test items
        ModelImpl model = new ModelImpl("test");
        this.database = new DatabaseImpl("TEST_DB", NamingConventionFactory.getTestNamingConvention());
        this.catalog = new CatalogImpl("testModel", model, database);
        this.table = new TableImpl("Table", database);
        this.entity = new EntityImpl(null, "test", table.getName(), catalog);
        this.otherTable = new TableImpl("OtherTable", database);
        this.column = table.createColumn("TEST_NAME", ColumnType.INTEGER, false, 4, 0);
        this.otherColumn = otherTable.createColumn("TEST_NAME_OTHER", ColumnType.INTEGER, false, 4, 0);
    }
    
    @Test
    public void testCreateValid() throws ModelException {
        SimplePropertyImpl simpleProperty = new SimplePropertyImpl("testName", PropertyType.INTEGER, true, column.getName(), entity);
        Assert.assertEquals("testName", simpleProperty.getName().toString());
        Assert.assertEquals(PropertyType.INTEGER, simpleProperty.getPropertyType());
        Assert.assertEquals(true, simpleProperty.getNotNull());
        Assert.assertNotNull(simpleProperty.getColumn());
        Assert.assertEquals("TEST_NAME", simpleProperty.getColumn().getName().toString());
        Assert.assertEquals(ColumnType.INTEGER, simpleProperty.getColumn().getColumnType());
        
        
    }
    
    @Test(expected = ModelException.class)
    public void testCreateNullName() throws ModelException {
        new SimplePropertyImpl(null, PropertyType.INTEGER, true, column.getName(), entity);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateZeroLength() throws ModelException {
        new SimplePropertyImpl("", PropertyType.INTEGER, true, column.getName(), entity);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateNullType() throws ModelException {
        new SimplePropertyImpl("test", null, true, column.getName(), entity);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateNullEntity() throws ModelException {
        new SimplePropertyImpl("test", PropertyType.INTEGER, true, column.getName(), null);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateDuplicate() throws ModelException {
        new SimplePropertyImpl("test", PropertyType.INTEGER, true, column.getName(), entity);
        new SimplePropertyImpl("test", PropertyType.INTEGER, true, column.getName(), entity);
    }
    
    @Test
    public void testCreateWithColumn() throws ModelException {
        new SimplePropertyImpl("test", PropertyType.INTEGER, true, column.getName(), entity);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateWithColumnFromWrongTable() throws ModelException {
        new SimplePropertyImpl("test", PropertyType.INTEGER, true, otherColumn.getName(), entity);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateWithNullColumn() throws ModelException {
        new SimplePropertyImpl("test", PropertyType.INTEGER, true, null, entity);
    }
    
}
