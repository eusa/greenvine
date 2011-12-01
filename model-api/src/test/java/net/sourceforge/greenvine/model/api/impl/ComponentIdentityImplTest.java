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
import net.sourceforge.greenvine.model.api.impl.CatalogImpl;
import net.sourceforge.greenvine.model.api.impl.ColumnImpl;
import net.sourceforge.greenvine.model.api.impl.ComponentIdentityImpl;
import net.sourceforge.greenvine.model.api.impl.DatabaseImpl;
import net.sourceforge.greenvine.model.api.impl.EntityImpl;
import net.sourceforge.greenvine.model.api.impl.IdentityFieldImpl;
import net.sourceforge.greenvine.model.api.impl.ModelImpl;
import net.sourceforge.greenvine.model.api.impl.PrimaryKeyImpl;
import net.sourceforge.greenvine.model.api.impl.TableImpl;
import net.sourceforge.greenvine.model.naming.impl.DatabaseObjectNameImpl;
import net.sourceforge.greenvine.model.naming.testutils.NamingConventionFactory;

import org.junit.Before;
import org.junit.Test;

public class ComponentIdentityImplTest {
    
    private EntityImpl entity;
    private CatalogImpl catalog;
    private TableImpl table;
    private ColumnImpl pkCol1;
    private ColumnImpl pkCol2;
    private DatabaseImpl database;
    
    @Before
    public void setUp() throws ModelException {
        
        // Set up model objects
        ModelImpl model = new ModelImpl("test");
        this.database = new DatabaseImpl("TEST_DB", NamingConventionFactory.getTestNamingConvention());
        this.catalog = new CatalogImpl("test", model, database);
        this.table = new TableImpl(new DatabaseObjectNameImpl(null, "TABLE"), database);
        this.pkCol1 = table.createColumn("PROP1", ColumnType.INTEGER, true, ColumnValueGenerationStrategy.ASSIGNED);
        this.pkCol2 = table.createColumn("PROP2", ColumnType.DATE, true, ColumnValueGenerationStrategy.ASSIGNED);
        SortedSet<CharSequence> cols = new TreeSet<CharSequence>();
        cols.add("PROP1");
        cols.add("PROP2");
        this.table.createPrimaryKey("PK_TABLE", cols );
        this.entity = new EntityImpl(null, "test", table.getName(), catalog);
        
    }
    
    @Test
    public void testCreateValid() throws ModelException {
        // Create complex id
        ComponentIdentityImpl complexIdentity = new ComponentIdentityImpl("testId", entity);
        complexIdentity.createSimpleProperty("prop1", PropertyType.INTEGER, PropertyValueGenerationStrategy.ASSIGNED, pkCol1.getName());
        complexIdentity.createSimpleProperty("prop2", PropertyType.DATE, PropertyValueGenerationStrategy.ASSIGNED, pkCol2.getName());
        
        // Test identity
        Assert.assertEquals("testId", complexIdentity.getName().toString());
        Assert.assertEquals(2, complexIdentity.getFieldCount());
        Assert.assertEquals(2, complexIdentity.getSimplePropertyCount());
        IdentityFieldImpl prop1 = complexIdentity.getSimpleProperty("prop1");
        Assert.assertNotNull(prop1);
        Assert.assertEquals("prop1", prop1.getName().toString());
        Assert.assertEquals(PropertyType.INTEGER, prop1.getPropertyType());
        Assert.assertEquals(true, prop1.getNotNull());
        Assert.assertNotNull(prop1.getColumn());
        Assert.assertEquals("PROP1", prop1.getColumn().getName().toString());
        Assert.assertEquals(ColumnType.INTEGER, prop1.getColumn().getColumnType());
        IdentityFieldImpl prop2 = complexIdentity.getSimpleProperty("prop2");
        Assert.assertNotNull(prop2);
        Assert.assertEquals("prop2", prop2.getName().toString());
        Assert.assertEquals(PropertyType.DATE, prop2.getPropertyType());
        Assert.assertEquals(true, prop2.getNotNull());
        Assert.assertNotNull(prop2.getColumn());
        Assert.assertEquals("PROP2", prop2.getColumn().getName().toString());
        Assert.assertEquals(ColumnType.DATE, prop2.getColumn().getColumnType());
        
        // Test created primary key
        PrimaryKeyImpl primaryKey = table.getPrimaryKey();
        Assert.assertNotNull(primaryKey);
        Assert.assertEquals("PK_TABLE", primaryKey.getName().toString());
        Assert.assertEquals(2, primaryKey.getColumnCount());
    }
    
    @Test
    public void testEntityFindField() throws ModelException {
        // Create complex id
        ComponentIdentityImpl complexIdentity = new ComponentIdentityImpl("testId", entity);
        complexIdentity.createSimpleProperty("prop1", PropertyType.INTEGER, PropertyValueGenerationStrategy.ASSIGNED, pkCol1.getName());
        complexIdentity.createSimpleProperty("prop2", PropertyType.DATE, PropertyValueGenerationStrategy.ASSIGNED, pkCol2.getName());
        
        Field field = entity.getFieldByName("testId");
        Assert.assertNotNull(field);
        Assert.assertTrue(field instanceof ComponentIdentityImpl);
        ComponentIdentityImpl found = (ComponentIdentityImpl)field;
        Assert.assertEquals(complexIdentity, found);
        
    }
    
    @Test(expected = ModelException.class)
    public void testCreateNullName() throws ModelException {
        String name = null;
        new ComponentIdentityImpl(name, entity);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateZeroLength() throws ModelException {
        new ComponentIdentityImpl("", entity);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateNullEntity() throws ModelException {
        new ComponentIdentityImpl("test", null);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateFieldNullName() throws ModelException {
        // Create complex id
        ComponentIdentityImpl complexIdentity = new ComponentIdentityImpl("testId", entity);
        complexIdentity.createSimpleProperty(null, PropertyType.INTEGER, PropertyValueGenerationStrategy.ASSIGNED, pkCol1.getName());
        complexIdentity.createSimpleProperty("prop2", PropertyType.DATE, PropertyValueGenerationStrategy.ASSIGNED, pkCol2.getName());
       
    }
    
    @Test(expected = ModelException.class)
    public void testCreateFieldZeroLengthName() throws ModelException {
        // Create complex id
        ComponentIdentityImpl complexIdentity = new ComponentIdentityImpl("testId", entity);
        complexIdentity.createSimpleProperty("", PropertyType.INTEGER, PropertyValueGenerationStrategy.ASSIGNED, pkCol1.getName());
        complexIdentity.createSimpleProperty("prop2", PropertyType.DATE, PropertyValueGenerationStrategy.ASSIGNED, pkCol2.getName());
       
    }
    
    @Test(expected = ModelException.class)
    public void testCreateFieldNullType() throws ModelException {
        // Create complex id
        ComponentIdentityImpl complexIdentity = new ComponentIdentityImpl("testId", entity);
        complexIdentity.createSimpleProperty("prop1", null, PropertyValueGenerationStrategy.ASSIGNED, pkCol1.getName());
        complexIdentity.createSimpleProperty("prop2", PropertyType.DATE, PropertyValueGenerationStrategy.ASSIGNED, pkCol2.getName());
       
    }
    
    @Test(expected = ModelException.class)
    public void testCreateDuplicate() throws ModelException {
        new ComponentIdentityImpl("testId", entity);
        new ComponentIdentityImpl("testId", entity);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateDuplicateFieldName() throws ModelException {
        // Create complex id
        ComponentIdentityImpl complexIdentity = new ComponentIdentityImpl("testId", entity);
        complexIdentity.createSimpleProperty("prop1", PropertyType.INTEGER, PropertyValueGenerationStrategy.ASSIGNED, pkCol1.getName());
        complexIdentity.createSimpleProperty("prop1", PropertyType.DATE, PropertyValueGenerationStrategy.ASSIGNED, pkCol2.getName());
       
    }
    
    @Test(expected = ModelException.class)
    public void testCreateDuplicateFieldColumn() throws ModelException {
        // Create complex id
        ComponentIdentityImpl complexIdentity = new ComponentIdentityImpl("testId", entity);
        complexIdentity.createSimpleProperty("prop1", PropertyType.INTEGER, PropertyValueGenerationStrategy.ASSIGNED, pkCol1.getName());
        complexIdentity.createSimpleProperty("prop2", PropertyType.DATE, PropertyValueGenerationStrategy.ASSIGNED, pkCol1.getName());
       
    }
    

}
