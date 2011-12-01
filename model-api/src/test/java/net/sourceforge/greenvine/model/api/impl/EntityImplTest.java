package net.sourceforge.greenvine.model.api.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import junit.framework.Assert;
import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.ColumnType;
import net.sourceforge.greenvine.model.api.ColumnValueGenerationStrategy;
import net.sourceforge.greenvine.model.api.Entity;
import net.sourceforge.greenvine.model.api.ForeignKey;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.PropertyType;
import net.sourceforge.greenvine.model.api.PropertyValueGenerationStrategy;
import net.sourceforge.greenvine.model.naming.ColumnName;
import net.sourceforge.greenvine.model.naming.FieldName;
import net.sourceforge.greenvine.model.naming.testutils.NamingConventionFactory;

import org.junit.Before;
import org.junit.Test;

public class EntityImplTest {
    
    private CatalogImpl catalog;
    private TableImpl table;
    private DatabaseImpl database;
    
    @Before
    public void setUp() throws ModelException {
        
        // Test items
        ModelImpl model = new ModelImpl("test");
        this.database = new DatabaseImpl("TEST_DB", NamingConventionFactory.getTestNamingConvention());
        this.catalog = new CatalogImpl("testModel", model, this.database);
        this.table = new TableImpl("TABLE", database);
    }
    
    @Test
    public void testCreateValidWithTable() throws ModelException {
        EntityImpl entity = new EntityImpl(null, "test", table.getName(), catalog);
        Assert.assertEquals("test", entity.getName().toString());
    }
    
    @Test 
    public void testCreateValidWithoutTable() throws ModelException {
        EntityImpl entity = new EntityImpl(null, "test", table.getName(), catalog);
        Assert.assertEquals("test", entity.getName().toString());
    }
    
    @Test(expected = ModelException.class)
    public void testCreateNullName() throws ModelException {
        new EntityImpl(null, null, table.getName(), catalog);
    }

    @Test(expected = ModelException.class)
    public void testCreateEmptyName() throws ModelException {
        new EntityImpl(null, "", table.getName(), catalog);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateNullCatalog() throws ModelException {
        new EntityImpl(null, "test", table.getName(), null);
    }
    
    @Test
    public void testFindDependencies() throws ModelException {
        
        // Create tables
        TableImpl parentTable = new TableImpl("PARENT", database);
        TableImpl child1Table = new TableImpl("CHILD1", database);
        TableImpl child2Table = new TableImpl("CHILD2", database);
        TableImpl child3Table = new TableImpl("CHILD3", database);
        
        // Create primary keys
        Column parentPkCol = parentTable.createColumn("PARENT_ID", ColumnType.CHARACTER, true, ColumnValueGenerationStrategy.ASSIGNED);
        SortedSet<CharSequence> parentPkCols = new TreeSet<CharSequence>();
        parentPkCols.add(parentPkCol.getName());
        parentTable.createPrimaryKey("PK_PARENT", parentPkCols);
        Column child1PkCol = child1Table.createColumn("CHILD1_ID", ColumnType.CHARACTER, true, ColumnValueGenerationStrategy.ASSIGNED);
        Column child1FkCol = child1Table.createColumn("FK_PARENT_ID", ColumnType.CHARACTER, true, ColumnValueGenerationStrategy.ASSIGNED);
        SortedSet<CharSequence> child1PkCols = new TreeSet<CharSequence>();
        child1PkCols.add(child1PkCol.getName());
        child1Table.createPrimaryKey("PK_CHILD1", child1PkCols);
        Column child2PkCol = child2Table.createColumn("CHILD2_ID", ColumnType.CHARACTER, true, ColumnValueGenerationStrategy.ASSIGNED);
        Column child2FkCol = child2Table.createColumn("FK_PARENT_ID", ColumnType.CHARACTER, true, ColumnValueGenerationStrategy.ASSIGNED);
        SortedSet<CharSequence> child2PkCols = new TreeSet<CharSequence>();
        child2PkCols.add(child2PkCol.getName());
        child2Table.createPrimaryKey("PK_CHILD2", child2PkCols);
        Column child3PkCol = child3Table.createColumn("CHILD3_ID", ColumnType.CHARACTER, true, ColumnValueGenerationStrategy.ASSIGNED);
        SortedSet<CharSequence> child3PkCols = new TreeSet<CharSequence>();
        child3PkCols.add(child3PkCol.getName());
        child3Table.createPrimaryKey("PK_CHILD3", child3PkCols);
        
        // Create unique key
        SortedSet<CharSequence> child2UkCols = new TreeSet<CharSequence>();
        child2UkCols.add(child2FkCol.getName());
        child2Table.createUniqueKey("UK_CHILD2", child2UkCols);
        
        // Create foreign keys
        Map<ColumnName, ColumnName> m2oCols = new HashMap<ColumnName, ColumnName>();
        m2oCols.put(child1FkCol.getName(), parentPkCol.getName());
        ForeignKey m2oFk = database.createForeignKey("m2oFk", child1Table.getName(), parentTable.getName(), m2oCols);
        Map<ColumnName, ColumnName> o2oCols = new HashMap<ColumnName, ColumnName>();
        o2oCols.put(child2FkCol.getName(), parentPkCol.getName());
        ForeignKey o2oFk = database.createForeignKey("o2oFk", child2Table.getName(), parentTable.getName(), o2oCols);
        Map<ColumnName, ColumnName> co2oCols = new HashMap<ColumnName, ColumnName>();
        co2oCols.put(child3PkCol.getName(), parentPkCol.getName());
        ForeignKey co2oFk = database.createForeignKey("co2oFk", child3Table.getName(), parentTable.getName(), co2oCols);
        
        // Create entities
        EntityImpl parent = new EntityImpl(null, "parent", parentTable.getName(), catalog);
        EntityImpl child1 = new EntityImpl(null, "child1", child1Table.getName(), catalog);
        EntityImpl child2 = new EntityImpl(null, "child2", child2Table.getName(), catalog);
        EntityImpl child3 = new EntityImpl(null, "child3", child3Table.getName(), catalog);
        
        // Create identities
        parent.createSimpleIdentity("parentId", PropertyType.CHARACTER, PropertyValueGenerationStrategy.ASSIGNED, parentPkCol.getName());
        child1.createSimpleIdentity("child1Id", PropertyType.CHARACTER, PropertyValueGenerationStrategy.ASSIGNED, child1PkCol.getName());
        child2.createSimpleIdentity("child2Id", PropertyType.CHARACTER, PropertyValueGenerationStrategy.ASSIGNED, child2PkCol.getName());
        
        // Create one-to-many
        catalog.createBiDirectionalManyToOneRelationship("parentField1", "childField", m2oFk.getName());
         
        // Create one-to-one
        catalog.createBiDirectionalOneToOneRelationship("parentField2", "childField", o2oFk.getName());
        
        // Create one-to-one constrained
        catalog.createBiDirectionalConstrainedOneToOneRelationship("parentField3", "childField", co2oFk.getName());
        
        // Test child 1 dependencies
        Map<FieldName, Entity> dependencies1 = child1.findDependencies();
        Assert.assertNotNull(dependencies1);
        Assert.assertEquals(1, dependencies1.size());
        
        // Test child 2 dependencies
        Map<FieldName, Entity> dependencies2 = child2.findDependencies();
        Assert.assertNotNull(dependencies2);
        Assert.assertEquals(1, dependencies2.size());
        
        // Test child 3 dependencies
        Map<FieldName, Entity> dependencies3 = child3.findDependencies();
        Assert.assertNotNull(dependencies3);
        Assert.assertEquals(1, dependencies3.size());
        
        Assert.assertEquals(dependencies1, dependencies2);
        Assert.assertEquals(dependencies1, dependencies3);
        
    }
    
    @Test
    public void testFindUniqueDependencies() throws ModelException {
        
        // Create tables
        TableImpl parentTable = new TableImpl("PARENT", database);
        TableImpl child1Table = new TableImpl("CHILD1", database);
        TableImpl child2Table = new TableImpl("CHILD2", database);
        TableImpl child3Table = new TableImpl("CHILD3", database);
        
     // Create primary keys
        Column parentPkCol = parentTable.createColumn("PARENT_ID", ColumnType.CHARACTER, true, ColumnValueGenerationStrategy.ASSIGNED);
        SortedSet<CharSequence> parentPkCols = new TreeSet<CharSequence>();
        parentPkCols.add(parentPkCol.getName());
        parentTable.createPrimaryKey("PK_PARENT", parentPkCols);
        Column child1PkCol = child1Table.createColumn("CHILD1_ID", ColumnType.CHARACTER, true, ColumnValueGenerationStrategy.ASSIGNED);
        Column child1FkCol = child1Table.createColumn("FK_PARENT_ID", ColumnType.CHARACTER, true, ColumnValueGenerationStrategy.ASSIGNED);
        SortedSet<CharSequence> child1PkCols = new TreeSet<CharSequence>();
        child1PkCols.add(child1PkCol.getName());
        child1Table.createPrimaryKey("PK_CHILD1", child1PkCols);
        Column child2PkCol = child2Table.createColumn("CHILD2_ID", ColumnType.CHARACTER, true, ColumnValueGenerationStrategy.ASSIGNED);
        Column child2FkCol = child2Table.createColumn("FK_PARENT_ID", ColumnType.CHARACTER, true, ColumnValueGenerationStrategy.ASSIGNED);
        SortedSet<CharSequence> child2PkCols = new TreeSet<CharSequence>();
        child2PkCols.add(child2PkCol.getName());
        child2Table.createPrimaryKey("PK_CHILD2", child2PkCols);
        Column child3PkCol = child3Table.createColumn("CHILD3_ID", ColumnType.CHARACTER, true, ColumnValueGenerationStrategy.ASSIGNED);
        SortedSet<CharSequence> child3PkCols = new TreeSet<CharSequence>();
        child3PkCols.add(child3PkCol.getName());
        child3Table.createPrimaryKey("PK_CHILD3", child3PkCols);
        
        // Create unique key
        SortedSet<CharSequence> child2UkCols = new TreeSet<CharSequence>();
        child2UkCols.add(child2FkCol.getName());
        child2Table.createUniqueKey("UK_CHILD2", child2UkCols);
        
        // Create foreign keys
        Map<ColumnName, ColumnName> m2oCols = new HashMap<ColumnName, ColumnName>();
        m2oCols.put(child1FkCol.getName(), parentPkCol.getName());
        ForeignKey m2oFk = database.createForeignKey("m2oFk", child1Table.getName(), parentTable.getName(), m2oCols);
        Map<ColumnName, ColumnName> o2oCols = new HashMap<ColumnName, ColumnName>();
        o2oCols.put(child2FkCol.getName(), parentPkCol.getName());
        ForeignKey o2oFk = database.createForeignKey("o2oFk", child2Table.getName(), parentTable.getName(), o2oCols);
        Map<ColumnName, ColumnName> co2oCols = new HashMap<ColumnName, ColumnName>();
        co2oCols.put(child3PkCol.getName(), parentPkCol.getName());
        ForeignKey co2oFk = database.createForeignKey("co2oFk", child3Table.getName(), parentTable.getName(), co2oCols);
        
        // Create entities
        EntityImpl parent = new EntityImpl(null, "parent", parentTable.getName(), catalog);
        EntityImpl child1 = new EntityImpl(null, "child1", child1Table.getName(), catalog);
        EntityImpl child2 = new EntityImpl(null, "child2", child2Table.getName(), catalog);
        EntityImpl child3 = new EntityImpl(null, "child3", child3Table.getName(), catalog);
        
        // Create identities
        parent.createSimpleIdentity("parentId", PropertyType.CHARACTER, PropertyValueGenerationStrategy.ASSIGNED, parentPkCol.getName());
        child1.createSimpleIdentity("child1Id", PropertyType.CHARACTER, PropertyValueGenerationStrategy.ASSIGNED, child1PkCol.getName());
        child2.createSimpleIdentity("child2Id", PropertyType.CHARACTER, PropertyValueGenerationStrategy.ASSIGNED, child2PkCol.getName());
        
        // Create one-to-many
        catalog.createBiDirectionalManyToOneRelationship("parentField1", "childField", m2oFk.getName());
         
        // Create one-to-one
        catalog.createBiDirectionalOneToOneRelationship("parentField2", "childField", o2oFk.getName());
        
        // Create one-to-one constrained
        catalog.createBiDirectionalConstrainedOneToOneRelationship("parentField3", "childField", co2oFk.getName());
        
        // Test child 1 dependencies
        Set<Entity> dependencies1 = child1.getUniqueDependentEntities();
        Assert.assertNotNull(dependencies1);
        Assert.assertEquals(1, dependencies1.size());
        
        // Test child 2 dependencies
        Set<Entity> dependencies2 = child2.getUniqueDependentEntities();
        Assert.assertNotNull(dependencies2);
        Assert.assertEquals(1, dependencies2.size());
        
        // Test child 3 dependencies
        Set<Entity> dependencies3 = child3.getUniqueDependentEntities();
        Assert.assertNotNull(dependencies3);
        Assert.assertEquals(1, dependencies3.size());
        
        Assert.assertEquals(dependencies1, dependencies2);
        Assert.assertEquals(dependencies1, dependencies3);
    }

}
