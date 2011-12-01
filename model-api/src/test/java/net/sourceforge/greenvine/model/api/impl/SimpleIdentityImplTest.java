package net.sourceforge.greenvine.model.api.impl;

import java.util.SortedSet;
import java.util.TreeSet;

import junit.framework.Assert;
import net.sourceforge.greenvine.model.api.ColumnType;
import net.sourceforge.greenvine.model.api.ColumnValueGenerationStrategy;
import net.sourceforge.greenvine.model.api.Field;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.PropertyType;
import net.sourceforge.greenvine.model.api.PropertyValueGenerationStrategy;
import net.sourceforge.greenvine.model.api.SimpleIdentity;
import net.sourceforge.greenvine.model.api.impl.CatalogImpl;
import net.sourceforge.greenvine.model.api.impl.ColumnImpl;
import net.sourceforge.greenvine.model.api.impl.DatabaseImpl;
import net.sourceforge.greenvine.model.api.impl.EntityImpl;
import net.sourceforge.greenvine.model.api.impl.ModelImpl;
import net.sourceforge.greenvine.model.api.impl.PrimaryKeyImpl;
import net.sourceforge.greenvine.model.api.impl.SimpleIdentityImpl;
import net.sourceforge.greenvine.model.api.impl.TableImpl;
import net.sourceforge.greenvine.model.naming.testutils.NamingConventionFactory;

import org.junit.Before;
import org.junit.Test;

public class SimpleIdentityImplTest {
    
    private EntityImpl entity;
    private CatalogImpl catalog;
    private DatabaseImpl database;
    private TableImpl table;
    private ColumnImpl pkCol;
    
    @Before
    public void setUp() throws ModelException {
        
        // Set up test clases
        ModelImpl model = new ModelImpl("test");
        this.database = new DatabaseImpl("TEST_DB", NamingConventionFactory.getTestNamingConvention());
        this.catalog = new CatalogImpl("testModel", model, database);
        this.table = new TableImpl("TABLE", database);
        this.pkCol = table.createColumn("TEST_ID", ColumnType.INTEGER, true, ColumnValueGenerationStrategy.ASSIGNED);
        SortedSet<CharSequence> cols = new TreeSet<CharSequence>();
        cols.add(pkCol.getName());
        this.table.createPrimaryKey("PK_TABLE", cols);
        this.entity = new EntityImpl(null, "test", table.getName(), catalog);
        
    }
    
    @Test
    public void testCreateValid() throws ModelException {
        
        SimpleIdentityImpl simpleProperty = new SimpleIdentityImpl("testId", PropertyType.INTEGER, PropertyValueGenerationStrategy.ASSIGNED, pkCol.getName(), entity);
        Assert.assertEquals("testId", simpleProperty.getName().toString());
        Assert.assertEquals(PropertyType.INTEGER, simpleProperty.getPropertyType());
        Assert.assertEquals(true, simpleProperty.getNotNull());
        Assert.assertNotNull(simpleProperty.getColumn());
        Assert.assertEquals("TEST_ID", simpleProperty.getColumn().getName().toString());
        Assert.assertEquals(ColumnType.INTEGER, simpleProperty.getColumn().getColumnType());
        
        // Test created primary key
        PrimaryKeyImpl primaryKey = table.getPrimaryKey();
        Assert.assertNotNull(primaryKey);
        Assert.assertEquals("PK_TABLE", primaryKey.getName().toString());
        Assert.assertEquals(1, primaryKey.getColumnCount());
    }
    
    @Test
    public void testEntityFindField() throws ModelException {
        SimpleIdentity simpleProperty = new SimpleIdentityImpl("testId", PropertyType.INTEGER, PropertyValueGenerationStrategy.ASSIGNED, pkCol.getName(), entity);
        Field field = entity.getFieldByName("testId");
        Assert.assertNotNull(field);
        Assert.assertTrue(field instanceof SimpleIdentity);
        SimpleIdentity found = (SimpleIdentity)field;
        Assert.assertEquals(simpleProperty, found);
        
    }
    
    @Test(expected = ModelException.class)
    public void testCreateNullName() throws ModelException {
        new SimpleIdentityImpl(null, PropertyType.INTEGER, PropertyValueGenerationStrategy.ASSIGNED, pkCol.getName(), entity);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateZeroLength() throws ModelException {
        new SimpleIdentityImpl("", PropertyType.INTEGER, PropertyValueGenerationStrategy.ASSIGNED, pkCol.getName(), entity);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateNullType() throws ModelException {
        new SimpleIdentityImpl("test", null, PropertyValueGenerationStrategy.ASSIGNED, pkCol.getName(), entity);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateNullEntity() throws ModelException {
        new SimpleIdentityImpl("test", PropertyType.INTEGER, PropertyValueGenerationStrategy.ASSIGNED, pkCol.getName(), null);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateNullKeyGenerationStrategy() throws ModelException {
        new SimpleIdentityImpl("test", PropertyType.INTEGER, null, pkCol.getName(), entity);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateDuplicate() throws ModelException {
        new SimpleIdentityImpl("test", PropertyType.INTEGER, PropertyValueGenerationStrategy.ASSIGNED, pkCol.getName(), entity);
        new SimpleIdentityImpl("test", PropertyType.INTEGER, PropertyValueGenerationStrategy.ASSIGNED, pkCol.getName(), entity);
    }
    
}
