package net.sourceforge.greenvine.model.api.impl;

import java.util.SortedSet;
import java.util.TreeSet;

import junit.framework.Assert;
import net.sourceforge.greenvine.model.api.ColumnType;
import net.sourceforge.greenvine.model.api.ColumnValueGenerationStrategy;
import net.sourceforge.greenvine.model.api.Field;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.PropertyType;
import net.sourceforge.greenvine.model.api.SimpleNaturalIdentity;
import net.sourceforge.greenvine.model.api.impl.CatalogImpl;
import net.sourceforge.greenvine.model.api.impl.ColumnImpl;
import net.sourceforge.greenvine.model.api.impl.DatabaseImpl;
import net.sourceforge.greenvine.model.api.impl.EntityImpl;
import net.sourceforge.greenvine.model.api.impl.ModelImpl;
import net.sourceforge.greenvine.model.api.impl.PrimaryKeyImpl;
import net.sourceforge.greenvine.model.api.impl.SimpleNaturalIdentityImpl;
import net.sourceforge.greenvine.model.api.impl.TableImpl;
import net.sourceforge.greenvine.model.api.impl.UniqueKeyImpl;
import net.sourceforge.greenvine.model.naming.testutils.NamingConventionFactory;

import org.junit.Before;
import org.junit.Test;

public class SimpleNaturalIdentityImplTest {
    
    private EntityImpl entity;
    private CatalogImpl catalog;
    private DatabaseImpl database;
    private TableImpl table;
    private ColumnImpl pkCol;
    private ColumnImpl ukCol;
    private ColumnImpl dataCol;
    private UniqueKeyImpl uniqueKey;
    
    @Before
    public void setUp() throws ModelException {
        
        // Set up test classes
        ModelImpl model = new ModelImpl("test");
        this.database = new DatabaseImpl("TEST_DB", NamingConventionFactory.getTestNamingConvention());
        this.catalog = new CatalogImpl("testModel", model, database);
        this.table = new TableImpl("TABLE", database);
        this.pkCol = table.createColumn("TEST_ID", ColumnType.INTEGER, true, ColumnValueGenerationStrategy.ASSIGNED);
        this.ukCol = table.createColumn("TEST_NATURAL_ID", ColumnType.INTEGER, true, ColumnValueGenerationStrategy.ASSIGNED);
        this.dataCol = table.createColumn("TEST_DATA", ColumnType.INTEGER, true, ColumnValueGenerationStrategy.ASSIGNED);
        SortedSet<CharSequence> cols = new TreeSet<CharSequence>();
        cols.add(pkCol.getName());
        this.table.createPrimaryKey("PK_TABLE", cols);
        SortedSet<CharSequence> ukCols = new TreeSet<CharSequence>();
        ukCols.add(ukCol.getName());
        this.uniqueKey = this.table.createUniqueKey("UK_TABLE", ukCols);
        
        this.entity = new EntityImpl(null, "test", table.getName(), catalog);
        
    }
    
    @Test
    public void testCreateValid() throws ModelException {
        
        SimpleNaturalIdentityImpl simpleProperty = new SimpleNaturalIdentityImpl("testId", PropertyType.INTEGER, ukCol.getName(), uniqueKey, entity);
        Assert.assertEquals("testId", simpleProperty.getName().toString());
        Assert.assertEquals(PropertyType.INTEGER, simpleProperty.getPropertyType());
        Assert.assertEquals(true, simpleProperty.getNotNull());
        Assert.assertNotNull(simpleProperty.getColumn());
        Assert.assertEquals("TEST_NATURAL_ID", simpleProperty.getColumn().getName().toString());
        Assert.assertEquals(ColumnType.INTEGER, simpleProperty.getColumn().getColumnType());
        
        // Test created primary key
        PrimaryKeyImpl primaryKey = table.getPrimaryKey();
        Assert.assertNotNull(primaryKey);
        Assert.assertEquals("PK_TABLE", primaryKey.getName().toString());
        Assert.assertEquals(1, primaryKey.getColumnCount());
    }
    
    @Test
    public void testEntityFindField() throws ModelException {
        SimpleNaturalIdentity simpleProperty = new SimpleNaturalIdentityImpl("testId", PropertyType.INTEGER, ukCol.getName(), uniqueKey, entity);
        Field field = entity.getFieldByName("testId");
        Assert.assertNotNull(field);
        Assert.assertTrue(field instanceof SimpleNaturalIdentity);
        SimpleNaturalIdentity found = (SimpleNaturalIdentity)field;
        Assert.assertEquals(simpleProperty, found);
        
    }
    
    @Test(expected = ModelException.class)
    public void testCreateNullName() throws ModelException {
        new SimpleNaturalIdentityImpl(null, PropertyType.INTEGER, ukCol.getName(), uniqueKey, entity);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateZeroLength() throws ModelException {
        new SimpleNaturalIdentityImpl("", PropertyType.INTEGER, ukCol.getName(), uniqueKey, entity);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateNullType() throws ModelException {
        new SimpleNaturalIdentityImpl("test", null, ukCol.getName(), uniqueKey, entity);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateNullEntity() throws ModelException {
        new SimpleNaturalIdentityImpl("test", PropertyType.INTEGER, ukCol.getName(), uniqueKey, null);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateNullUniqueKey() throws ModelException {
        new SimpleNaturalIdentityImpl("test", PropertyType.INTEGER, ukCol.getName(), null, entity);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateNonUniqueCol() throws ModelException {
        new SimpleNaturalIdentityImpl("test", PropertyType.INTEGER, dataCol.getName(), uniqueKey, entity);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateDuplicate() throws ModelException {
        new SimpleNaturalIdentityImpl("test", PropertyType.INTEGER, ukCol.getName(), uniqueKey, entity);
        new SimpleNaturalIdentityImpl("test", PropertyType.INTEGER, ukCol.getName(), uniqueKey, entity);
    }
    
}
