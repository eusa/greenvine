package net.sourceforge.greenvine.model.api.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import net.sourceforge.greenvine.model.api.ColumnType;
import net.sourceforge.greenvine.model.api.ColumnValueGenerationStrategy;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.PropertyType;
import net.sourceforge.greenvine.model.api.PropertyValueGenerationStrategy;
import net.sourceforge.greenvine.model.naming.impl.DatabaseObjectNameImpl;
import net.sourceforge.greenvine.model.naming.testutils.NamingConventionFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ComponentIdentityImplBuilderTest {
    
    private EntityImpl entity;
    private CatalogImpl catalog;
    private TableImpl table;
    private ColumnImpl pkCol1;
    private ColumnImpl pkCol2;
    private DatabaseImpl database;
    private TableImpl daddy;
    private ColumnImpl daddyPk;
    private ForeignKeyImpl fk;
    
    @Before
    public void setUp() throws ModelException {
        
        // Set up test objects
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
        
        // Create related table
        daddy = database.createTable("DADDY");
        daddyPk = daddy.createColumn("DADDY_ID", ColumnType.INTEGER, true, ColumnValueGenerationStrategy.ASSIGNED);
        final SortedSet<CharSequence> daddyPkCols = new TreeSet<CharSequence>();
        daddyPkCols.add(daddyPk.getName());
        daddy.createPrimaryKey("PK_DADDY", daddyPkCols);
        
        // Create foreign key
        final Map<CharSequence, CharSequence> fkCols = new HashMap<CharSequence, CharSequence>();
        fkCols.put(pkCol1.getName(), daddyPk.getName());
        fk = database.createForeignKey("FK_TABLE_DADDY", table.getName(), daddy.getName(), fkCols);
        
        // Create related entity
        final EntityImpl dad = catalog.createEntity("test", "daddy", daddy.getName());
        dad.createSimpleIdentity("daddyId", PropertyType.INTEGER, PropertyValueGenerationStrategy.ASSIGNED, daddyPk.getName());
        
    }
    
    @Test
    public void testCreate() throws ModelException {
        
        final ComponentIdentityImplBuilder builder = new ComponentIdentityImplBuilder();
        builder.setName("testIdentity");
        builder.setEntity(entity);
        builder.addSimpleProperty("prop1", pkCol1.getName(), PropertyType.INTEGER, PropertyValueGenerationStrategy.ASSIGNED);
        builder.addSimpleProperty("prop2", pkCol2.getName(), PropertyType.DATE, PropertyValueGenerationStrategy.ASSIGNED);
        final ComponentIdentityImpl identity = builder.build();
        Assert.assertEquals(builder.getName().toString(), identity.getName().toString());
        
    }
    
    @Test(expected = ModelException.class)
    public void testCreatePartial() throws ModelException {
        
        final ComponentIdentityImplBuilder builder = new ComponentIdentityImplBuilder();
        builder.setName("testIdentity");
        builder.setEntity(entity);
        builder.addSimpleProperty("prop1", pkCol1.getName(), PropertyType.INTEGER, PropertyValueGenerationStrategy.ASSIGNED);
        final ComponentIdentityImpl identity = builder.build();
        Assert.assertEquals(builder.getName().toString(), identity.getName().toString());
        
    }
    
    @Test
    public void testCreateManyToOne() throws ModelException {
        
        final ComponentIdentityImplBuilder builder = new ComponentIdentityImplBuilder();
        builder.setName("testIdentity");
        builder.setEntity(entity);
        builder.addManyToOne("tables", "daddy", fk.getName());
        builder.addSimpleProperty("prop2", pkCol2.getName(), PropertyType.DATE, PropertyValueGenerationStrategy.ASSIGNED);
        final ComponentIdentityImpl identity = builder.build();
        Assert.assertEquals(builder.getName().toString(), identity.getName().toString());
        
    }
    
    @Test(expected = ModelException.class)
    public void testCreateWithDuplicateManyToOne() throws ModelException {
        
        final ComponentIdentityImplBuilder builder = new ComponentIdentityImplBuilder();
        builder.setName("testIdentity");
        builder.setEntity(entity);
        builder.addManyToOne("tables", "daddy", fk.getName());
        builder.addSimpleProperty("prop1", pkCol1.getName(), PropertyType.INTEGER, PropertyValueGenerationStrategy.ASSIGNED);
        builder.addSimpleProperty("prop2", pkCol2.getName(), PropertyType.DATE, PropertyValueGenerationStrategy.ASSIGNED);
       final ComponentIdentityImpl identity = builder.build();
        Assert.assertEquals(builder.getName().toString(), identity.getName().toString());
        
    }
    
    @Test(expected = ModelException.class)
    public void testCreateWithDuplicateSimpleProperty() throws ModelException {
        
        final ComponentIdentityImplBuilder builder = new ComponentIdentityImplBuilder();
        builder.setName("testIdentity");
        builder.setEntity(entity);
        builder.addSimpleProperty("dupe", pkCol1.getName(), PropertyType.INTEGER, PropertyValueGenerationStrategy.ASSIGNED);
        builder.addSimpleProperty("prop1", pkCol1.getName(), PropertyType.INTEGER, PropertyValueGenerationStrategy.ASSIGNED);
        builder.addSimpleProperty("prop2", pkCol2.getName(), PropertyType.DATE, PropertyValueGenerationStrategy.ASSIGNED);
        final ComponentIdentityImpl identity = builder.build();
        Assert.assertEquals(builder.getName().toString(), identity.getName().toString());
        
    }


}
