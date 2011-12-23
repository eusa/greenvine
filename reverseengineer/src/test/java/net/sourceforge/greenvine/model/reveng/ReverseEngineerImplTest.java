package net.sourceforge.greenvine.model.reveng;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import junit.framework.Assert;
import net.sourceforge.greenvine.model.api.AggregationRelation;
import net.sourceforge.greenvine.model.api.AssociationRelation;
import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.ColumnType;
import net.sourceforge.greenvine.model.api.Entity;
import net.sourceforge.greenvine.model.api.ForeignKey;
import net.sourceforge.greenvine.model.api.Identity;
import net.sourceforge.greenvine.model.api.ManyToManyAssociationField;
import net.sourceforge.greenvine.model.api.ManyToOneAggregationField;
import net.sourceforge.greenvine.model.api.ManyToOneAssociationField;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.OneToManyAssociationField;
import net.sourceforge.greenvine.model.api.OneToManyField;
import net.sourceforge.greenvine.model.api.OneToOneAssociationField;
import net.sourceforge.greenvine.model.api.OneToOneChildField;
import net.sourceforge.greenvine.model.api.OneToOneChildIdentity;
import net.sourceforge.greenvine.model.api.OneToOneChildNaturalIdentity;
import net.sourceforge.greenvine.model.api.OneToOneParentField;
import net.sourceforge.greenvine.model.api.PropertyType;
import net.sourceforge.greenvine.model.api.SimpleIdentity;
import net.sourceforge.greenvine.model.api.SimpleNaturalIdentity;
import net.sourceforge.greenvine.model.api.impl.CatalogImpl;
import net.sourceforge.greenvine.model.api.impl.ColumnImpl;
import net.sourceforge.greenvine.model.api.impl.ComponentIdentityImpl;
import net.sourceforge.greenvine.model.api.impl.ComponentNaturalIdentityImpl;
import net.sourceforge.greenvine.model.api.impl.DatabaseImpl;
import net.sourceforge.greenvine.model.api.impl.EntityImpl;
import net.sourceforge.greenvine.model.api.impl.IdentityFieldImpl;
import net.sourceforge.greenvine.model.api.impl.ModelImpl;
import net.sourceforge.greenvine.model.api.impl.OneToOneChildIdentityImpl;
import net.sourceforge.greenvine.model.api.impl.OneToOneChildNaturalIdentityImpl;
import net.sourceforge.greenvine.model.api.impl.SimpleIdentityImpl;
import net.sourceforge.greenvine.model.api.impl.SimpleNaturalIdentityImpl;
import net.sourceforge.greenvine.model.api.impl.SimplePropertyImpl;
import net.sourceforge.greenvine.model.api.impl.TableImpl;
import net.sourceforge.greenvine.model.naming.testutils.NamingConventionFactory;
import net.sourceforge.greenvine.reveng.impl.RevengConfig;
import net.sourceforge.greenvine.reveng.impl.ReverseEngineerImpl;

import org.junit.Before;
import org.junit.Test;

public class ReverseEngineerImplTest {
    
    private ReverseEngineerImpl reveng;
    
    @Before
    public void setUp() {
        
        // Set up RevengConfig
        RevengConfig revengConfig = new RevengConfig();
        revengConfig.setModelName("model");
        revengConfig.setCreateNaturalIdentities(true);
        
        // Create ReverseEngineer
        reveng = new ReverseEngineerImpl(revengConfig);
        
    }
    
    @Test
    public void testCreateSimpleIdentity() throws ModelException {
        DatabaseImpl database = createDatabase();
        TableImpl table = database.createTable("TBL_TABLE");
        ColumnImpl pk = table.createColumn("TEST_ID", ColumnType.INTEGER, true, 4, 0);
        Column data = table.createColumn("DATA", ColumnType.VARCHAR, false, 50, 0);
        SortedSet<CharSequence> pkCols = new TreeSet<CharSequence>();
        pkCols.add(pk.getName().toString());
        table.createPrimaryKey("TEST_PK", pkCols);
        
        // Reverse engineer
        ModelImpl model = reveng.reverseEngineer(database);
        
        
        // Test
        Assert.assertNotNull(model.getCatalogs());
        Assert.assertEquals(1, model.getCatalogCount());
        CatalogImpl catalog = model.getCatalog(0);
        
        Assert.assertNotNull(catalog);
        Assert.assertEquals("catalog", catalog.getName().toString());
        Assert.assertEquals(1, catalog.getEntityCount());
        EntityImpl entity = catalog.getEntityByName("table");
        Assert.assertNotNull(entity);
        Assert.assertEquals("table", entity.getName().toString());
        Assert.assertEquals(2, entity.getFieldCount());
        Assert.assertEquals(table, entity.getTable());
        Identity identity = entity.getIdentity();
        Assert.assertNotNull(identity);
        Assert.assertTrue(identity instanceof SimpleIdentity);
        SimpleIdentityImpl simpleIdentity = (SimpleIdentityImpl)identity;
        Assert.assertEquals("testId", simpleIdentity.getName().toString());
        Assert.assertEquals(PropertyType.INTEGER, simpleIdentity.getPropertyType());
        Assert.assertEquals(pk, simpleIdentity.getColumn());
        Assert.assertEquals(1, entity.getSimplePropertyCount());
        SimplePropertyImpl simpleProperty = entity.getSimpleProperty("data");
        Assert.assertNotNull(simpleProperty);
        Assert.assertEquals("data", simpleProperty.getName().toString());
        Assert.assertEquals(PropertyType.STRING, simpleProperty.getPropertyType());
        Assert.assertEquals(data, simpleProperty.getColumn());
    }
    
    @Test
    public void testCreateComponentIdentityWithSimpleProperties() throws ModelException {
        
        DatabaseImpl database = createDatabase();
        TableImpl table = database.createTable("TABLE");
        ColumnImpl pk1 = table.createColumn("TEST_ID_1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl pk2 = table.createColumn("TEST_ID_2", ColumnType.DATE, true, 4, 0);
        Column data = table.createColumn("DATA", ColumnType.VARCHAR, false, 50, 0);
        SortedSet<CharSequence> pkCols = new TreeSet<CharSequence>();
        pkCols.add(pk1.getName().toString());
        pkCols.add(pk2.getName().toString());
        table.createPrimaryKey("TEST_PK", pkCols);
        
        // Reverse engineer
        ModelImpl model = reveng.reverseEngineer(database);
        
        // Test
        Assert.assertNotNull(model.getCatalogs());
        Assert.assertEquals(1, model.getCatalogCount());
        CatalogImpl catalog = model.getCatalog(0);
        Assert.assertNotNull(catalog);
        Assert.assertEquals("catalog", catalog.getName().toString());
        Assert.assertEquals(1, catalog.getEntityCount());
        EntityImpl entity = catalog.getEntityByName("table");
        Assert.assertNotNull(entity);
        Assert.assertEquals("table", entity.getName().toString());
        Assert.assertEquals(2, entity.getFieldCount());
        Assert.assertEquals(table, entity.getTable());
        Identity identity = entity.getIdentity();
        Assert.assertNotNull(identity);
        Assert.assertTrue(identity instanceof ComponentIdentityImpl);
        ComponentIdentityImpl complexIdentity = (ComponentIdentityImpl)identity;
        Assert.assertEquals("tableIdentity", complexIdentity.getName().toString());
        IdentityFieldImpl id1 = complexIdentity.getSimpleProperty("testId1");
        Assert.assertEquals(PropertyType.INTEGER, id1.getPropertyType());
        Assert.assertEquals(pk1, id1.getColumn());
        IdentityFieldImpl id2 = complexIdentity.getSimpleProperty("testId2");
        Assert.assertEquals(PropertyType.DATE, id2.getPropertyType());
        Assert.assertEquals(pk2, id2.getColumn());
        
        Assert.assertEquals(1, entity.getSimplePropertyCount());
        SimplePropertyImpl simpleProperty = entity.getSimpleProperty("data");
        Assert.assertNotNull(simpleProperty);
        Assert.assertEquals("data", simpleProperty.getName().toString());
        Assert.assertEquals(PropertyType.STRING, simpleProperty.getPropertyType());
        Assert.assertEquals(data, simpleProperty.getColumn());
    }
    
    @Test
    public void testCreateComponentIdentityWithManyToOne() throws ModelException {
        
        DatabaseImpl database = createDatabase();
        
        // Parent table
        TableImpl table = database.createTable("PARENT");
        ColumnImpl parentPk1 = table.createColumn("TEST_ID_1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl parentPk2 = table.createColumn("TEST_ID_2", ColumnType.DATE, true, 4, 0);
        Column data = table.createColumn("DATA", ColumnType.VARCHAR, false, 50, 0);
        SortedSet<CharSequence> pkCols = new TreeSet<CharSequence>();
        pkCols.add(parentPk1.getName().toString());
        pkCols.add(parentPk2.getName().toString());
        table.createPrimaryKey("PARENT_PK", pkCols);
        
        // Child table
        TableImpl child = database.createTable("CHILD");
        ColumnImpl childPk1 = child.createColumn("CHILD_ID_1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl childPk2 = child.createColumn("CHILD_ID_2", ColumnType.DATE, true, 4, 0);
        ColumnImpl childPk3 = child.createColumn("CHILD_ID_3", ColumnType.INTEGER, true, 4, 0);
        Column childData = child.createColumn("DATA", ColumnType.VARCHAR, false, 50, 0);
        SortedSet<CharSequence> childPkCols = new TreeSet<CharSequence>();
        childPkCols.add(childPk1.getName().toString());
        childPkCols.add(childPk2.getName().toString());
        childPkCols.add(childPk3.getName().toString());
        child.createPrimaryKey("CHILD_PK", childPkCols);
        
        // Foreign keys
        Map<CharSequence, CharSequence> parentConstraints = new HashMap<CharSequence, CharSequence>();
        parentConstraints.put(childPk1.getName().toString(), parentPk1.getName().toString());
        parentConstraints.put(childPk2.getName().toString(), parentPk2.getName().toString());
        database.createForeignKey("PARENT_FK", "CHILD", "PARENT", parentConstraints);
        
        // Reverse engineer
        ModelImpl model = reveng.reverseEngineer(database);
        
        // Test catalog
        Assert.assertNotNull(model.getCatalogs());
        Assert.assertEquals(1, model.getCatalogCount());
        CatalogImpl catalog = model.getCatalog(0);
        Assert.assertNotNull(catalog);
        Assert.assertEquals("catalog", catalog.getName().toString());
        Assert.assertEquals(2, catalog.getEntityCount());
        
        // Test parent
        EntityImpl parentEntity = catalog.getEntityByName("parent");
        Assert.assertNotNull(parentEntity);
        Assert.assertEquals("parent", parentEntity.getName().toString());
        Assert.assertEquals(3, parentEntity.getFieldCount());
        Assert.assertEquals(table, parentEntity.getTable());
        Identity identity = parentEntity.getIdentity();
        Assert.assertNotNull(identity);
        Assert.assertTrue(identity instanceof ComponentIdentityImpl);
        ComponentIdentityImpl complexIdentity = (ComponentIdentityImpl)identity;
        Assert.assertEquals("parentIdentity", complexIdentity.getName().toString());
        IdentityFieldImpl id1 = complexIdentity.getSimpleProperty("testId1");
        Assert.assertEquals(PropertyType.INTEGER, id1.getPropertyType());
        Assert.assertEquals(parentPk1, id1.getColumn());
        IdentityFieldImpl id2 = complexIdentity.getSimpleProperty("testId2");
        Assert.assertEquals(PropertyType.DATE, id2.getPropertyType());
        Assert.assertEquals(parentPk2, id2.getColumn());
        Assert.assertEquals(1, parentEntity.getSimplePropertyCount());
        SimplePropertyImpl simpleProperty = parentEntity.getSimpleProperty("data");
        Assert.assertNotNull(simpleProperty);
        Assert.assertEquals("data", simpleProperty.getName().toString());
        Assert.assertEquals(PropertyType.STRING, simpleProperty.getPropertyType());
        Assert.assertEquals(data, simpleProperty.getColumn());
        
        // Child entity
        EntityImpl childEntity = catalog.getEntityByName("child");
        Assert.assertNotNull(childEntity);
        Assert.assertEquals("child", childEntity.getName().toString());
        Assert.assertEquals(3, childEntity.getFieldCount());
        Assert.assertEquals(child, childEntity.getTable());
        Identity childIdentity = childEntity.getIdentity();
        Assert.assertNotNull(childIdentity);
        Assert.assertTrue(childIdentity instanceof ComponentIdentityImpl);
        ComponentIdentityImpl childComplexIdentity = (ComponentIdentityImpl)childIdentity;
        Assert.assertEquals("childIdentity", childComplexIdentity.getName().toString());
        Assert.assertEquals(0, childComplexIdentity.getManyToOneCount());
        Assert.assertEquals(3, childComplexIdentity.getSimplePropertyCount());
        IdentityFieldImpl childId1 = childComplexIdentity.getSimpleProperty("childId1");
        Assert.assertEquals(PropertyType.INTEGER, childId1.getPropertyType());
        Assert.assertEquals(childPk1, childId1.getColumn());
        IdentityFieldImpl childId2 = childComplexIdentity.getSimpleProperty("childId2");
        Assert.assertEquals(PropertyType.DATE, childId2.getPropertyType());
        Assert.assertEquals(childPk2, childId2.getColumn());
        IdentityFieldImpl childId3 = childComplexIdentity.getSimpleProperty("childId3");
        Assert.assertEquals(PropertyType.INTEGER, childId3.getPropertyType());
        Assert.assertEquals(childPk3, childId3.getColumn());
        
        // Check many-to-one was added to entity
        Assert.assertEquals(1, childEntity.getManyToOneCount());
        ManyToOneAggregationField parentFk = childEntity.getManyToOne("parent");
        Assert.assertEquals("parent", parentFk.getName().toString());
        
        // Check simple property
        Assert.assertEquals(1, childEntity.getSimplePropertyCount());
        SimplePropertyImpl childSimpleProperty = childEntity.getSimpleProperty("data");
        Assert.assertNotNull(childSimpleProperty);
        Assert.assertEquals("data", childSimpleProperty.getName().toString());
        Assert.assertEquals(PropertyType.STRING, childSimpleProperty.getPropertyType());
        Assert.assertEquals(childData, childSimpleProperty.getColumn());
    }

    @Test
    public void testCreateSimpleIdentityView() throws ModelException {
        DatabaseImpl database = createDatabase();
        TableImpl table = database.createTable("TABLE", true);
        Column pk = table.createColumn("A_COLUMN", ColumnType.INTEGER, true, 4, 0);
        Column data = table.createColumn("B_COLUMN", ColumnType.VARCHAR, false, 50, 0);
        
        // Reverse engineer
        ModelImpl model = reveng.reverseEngineer(database);
        
        // Test
        Assert.assertNotNull(model.getCatalogs());
        Assert.assertEquals(1, model.getCatalogCount());
        CatalogImpl catalog = model.getCatalog(0);
        Assert.assertNotNull(catalog);
        Assert.assertEquals("catalog", catalog.getName().toString());
        Assert.assertEquals(1, catalog.getEntityCount());
        EntityImpl entity = catalog.getEntityByName("table");
        Assert.assertNotNull(entity);
        Assert.assertEquals("table", entity.getName().toString());
        Assert.assertEquals(2, entity.getFieldCount());
        Assert.assertEquals(table, entity.getTable());
        Identity identity = entity.getIdentity();
        Assert.assertNotNull(identity);
        Assert.assertTrue(identity instanceof SimpleIdentity);
        SimpleIdentityImpl simpleIdentity = (SimpleIdentityImpl)identity;
        Assert.assertEquals("aColumn", simpleIdentity.getName().toString());
        Assert.assertEquals(PropertyType.INTEGER, simpleIdentity.getPropertyType());
        Assert.assertEquals(pk, simpleIdentity.getColumn());
        Assert.assertEquals(1, entity.getSimplePropertyCount());
        SimplePropertyImpl simpleProperty = entity.getSimpleProperty("bColumn");
        Assert.assertNotNull(simpleProperty);
        Assert.assertEquals("bColumn", simpleProperty.getName().toString());
        Assert.assertEquals(PropertyType.STRING, simpleProperty.getPropertyType());
        Assert.assertEquals(data, simpleProperty.getColumn());
    }
    
    @Test
    public void testCreateManyToManyComplex() throws ModelException {
        
        DatabaseImpl database = createDatabase();
        
        // Parent table
        TableImpl parent = database.createTable("PARENT");
        ColumnImpl parentPk1 = parent.createColumn("PARENT_ID_1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl parentPk2 = parent.createColumn("PARENT_ID_2", ColumnType.DATE, true, 4, 0);
        parent.createColumn("DATA", ColumnType.VARCHAR, false, 50, 0);
        SortedSet<CharSequence> parentPkCols = new TreeSet<CharSequence>();
        parentPkCols.add(parentPk1.getName().toString());
        parentPkCols.add(parentPk2.getName().toString());
        parent.createPrimaryKey("PARENT_PK", parentPkCols);
        
        // Child table
        TableImpl child = database.createTable("CHILD");
        ColumnImpl childPk1 = child.createColumn("CHILD_ID_1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl childPk2 = child.createColumn("CHILD_ID_2", ColumnType.DATE, true, 4, 0);
        child.createColumn("DATA", ColumnType.VARCHAR, false, 50, 0);
        SortedSet<CharSequence> childPkCols = new TreeSet<CharSequence>();
        childPkCols.add(childPk1.getName().toString());
        childPkCols.add(childPk2.getName().toString());
        child.createPrimaryKey("CHILD_PK", childPkCols);
        
        // Join table
        TableImpl join = database.createTable("JOIN");
        ColumnImpl joinPk1 = join.createColumn("PARENT_ID_1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl joinPk2 = join.createColumn("PARENT_ID_2", ColumnType.DATE, true, 4, 0);
        ColumnImpl joinPk3 = join.createColumn("CHILD_ID_1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl joinPk4 = join.createColumn("CHILD_ID_2", ColumnType.DATE, true, 4, 0);
        SortedSet<CharSequence> joinPkCols = new TreeSet<CharSequence>();
        joinPkCols.add(joinPk1.getName().toString());
        joinPkCols.add(joinPk2.getName().toString());
        joinPkCols.add(joinPk3.getName().toString());
        joinPkCols.add(joinPk4.getName().toString());
        join.createPrimaryKey("JOIN_PK", joinPkCols);
        
        // Foreign keys
        // NOTE: foreign keys are processed in alphabetical order 
        Map<CharSequence, CharSequence> childConstraints = new HashMap<CharSequence, CharSequence>();
        childConstraints.put(joinPk3.getName().toString(), childPk1.getName().toString());
        childConstraints.put(joinPk4.getName().toString(), childPk2.getName().toString());
        ForeignKey inverseForeignKey = database.createForeignKey("b_fk", "JOIN", "CHILD", childConstraints);
        Map<CharSequence, CharSequence> parentConstraints = new HashMap<CharSequence, CharSequence>();
        parentConstraints.put(joinPk1.getName().toString(), parentPk1.getName().toString());
        parentConstraints.put(joinPk2.getName().toString(), parentPk2.getName().toString());
        ForeignKey ownerForeignKey = database.createForeignKey("a_fk", "JOIN", "PARENT", parentConstraints);
        
        
        // Reverse engineer
        ModelImpl model = reveng.reverseEngineer(database);
        
        // Test
        Assert.assertNotNull(model.getCatalogs());
        Assert.assertEquals(1, model.getCatalogCount());
        CatalogImpl catalog = model.getCatalog(0);
        Assert.assertNotNull(catalog);
        Assert.assertEquals("catalog", catalog.getName().toString());
        Assert.assertEquals(2, catalog.getEntityCount());
        Entity ownerEntity = catalog.getEntityByName("parent");
        Assert.assertNotNull(ownerEntity);
        Entity inverseEntity = catalog.getEntityByName("child");
        Assert.assertNotNull(inverseEntity);
        ManyToManyAssociationField parentField = ownerEntity.getManyToMany("childs");
        Assert.assertNotNull(parentField);
        Assert.assertEquals("childs", parentField.getName().toString());
        ManyToManyAssociationField childField = inverseEntity.getManyToMany("parents");
        Assert.assertNotNull(childField);
        Assert.assertEquals("parents", childField.getName().toString());
        AssociationRelation relation = parentField.getRelation();
        Assert.assertNotNull(relation);
        Assert.assertEquals(ownerForeignKey, relation.getOwnerForeignKey());
        Assert.assertEquals(inverseForeignKey, relation.getInverseForeignKey());
        Assert.assertEquals(ownerEntity, relation.getParentEntity());
        Assert.assertEquals(inverseEntity, relation.getChildEntity());
        
    }
    
    @Test
    public void testCreateManyToOneAssociationComplex() throws ModelException {
        
        // Set up database
        DatabaseImpl database = createDatabase();
        
        // Parent table
        TableImpl parent = database.createTable("PARENT");
        ColumnImpl parentPk1 = parent.createColumn("PARENT_ID_1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl parentPk2 = parent.createColumn("PARENT_ID_2", ColumnType.DATE, true, 4, 0);
        parent.createColumn("DATA", ColumnType.VARCHAR, false, 50, 0);
        SortedSet<CharSequence> parentPkCols = new TreeSet<CharSequence>();
        parentPkCols.add(parentPk1.getName().toString());
        parentPkCols.add(parentPk2.getName().toString());
        parent.createPrimaryKey("PARENT_PK", parentPkCols);
        
        // Child table
        TableImpl child = database.createTable("CHILD");
        ColumnImpl childPk1 = child.createColumn("CHILD_ID_1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl childPk2 = child.createColumn("CHILD_ID_2", ColumnType.DATE, true, 4, 0);
        child.createColumn("DATA", ColumnType.VARCHAR, false, 50, 0);
        SortedSet<CharSequence> childPkCols = new TreeSet<CharSequence>();
        childPkCols.add(childPk1.getName().toString());
        childPkCols.add(childPk2.getName().toString());
        child.createPrimaryKey("CHILD_PK", childPkCols);
        
        // Join table
        TableImpl join = database.createTable("JOIN");
        ColumnImpl joinPk1 = join.createColumn("PARENT_ID_1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl joinPk2 = join.createColumn("PARENT_ID_2", ColumnType.DATE, true, 4, 0);
        ColumnImpl joinPk3 = join.createColumn("CHILD_ID_1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl joinPk4 = join.createColumn("CHILD_ID_2", ColumnType.DATE, true, 4, 0);
        SortedSet<CharSequence> joinPkCols = new TreeSet<CharSequence>();
        joinPkCols.add(joinPk1.getName().toString());
        joinPkCols.add(joinPk2.getName().toString());
        joinPkCols.add(joinPk3.getName().toString());
        joinPkCols.add(joinPk4.getName().toString());
        join.createPrimaryKey("JOIN_PK", joinPkCols);
        
        // Create unique key on join table
        SortedSet<CharSequence> ukCols = new TreeSet<CharSequence>();
        ukCols.add(joinPk3.getName().toString());
        ukCols.add(joinPk4.getName().toString());
        join.createUniqueKey("JOIN_UK", ukCols);
        
        // Foreign keys
        Map<CharSequence, CharSequence> parentConstraints = new HashMap<CharSequence, CharSequence>();
        parentConstraints.put(joinPk1.getName().toString(), parentPk1.getName().toString());
        parentConstraints.put(joinPk2.getName().toString(), parentPk2.getName().toString());
        ForeignKey ownerForeignKey = database.createForeignKey("parent_fk", "JOIN", "PARENT", parentConstraints);
        Map<CharSequence, CharSequence> childConstraints = new HashMap<CharSequence, CharSequence>();
        childConstraints.put(joinPk3.getName().toString(), childPk1.getName().toString());
        childConstraints.put(joinPk4.getName().toString(), childPk2.getName().toString());
        ForeignKey inverseForeignKey = database.createForeignKey("child_fk", "JOIN", "CHILD", childConstraints);
        
        
        // Reverse engineer
        ModelImpl model = reveng.reverseEngineer(database);
        
        // Test
        Assert.assertNotNull(model.getCatalogs());
        Assert.assertEquals(1, model.getCatalogCount());
        CatalogImpl catalog = model.getCatalog(0);
        Assert.assertNotNull(catalog);
        Assert.assertEquals("catalog", catalog.getName().toString());
        Assert.assertEquals(2, catalog.getEntityCount());
        Entity ownerEntity = catalog.getEntityByName("parent");
        Assert.assertNotNull(ownerEntity);
        Entity inverseEntity = catalog.getEntityByName("child");
        Assert.assertNotNull(inverseEntity);
        OneToManyAssociationField parentField = ownerEntity.getOneToManyAssociation("parents");
        Assert.assertNotNull(parentField);
        Assert.assertEquals("parents", parentField.getName().toString());
        ManyToOneAssociationField childField = inverseEntity.getManyToOneAssociation("child");
        Assert.assertNotNull(childField);
        Assert.assertEquals("child", childField.getName().toString());
        AssociationRelation relation = parentField.getRelation();
        Assert.assertNotNull(relation);
        Assert.assertEquals(ownerForeignKey, relation.getOwnerForeignKey());
        Assert.assertEquals(inverseForeignKey, relation.getInverseForeignKey());
        Assert.assertEquals(ownerEntity, relation.getParentEntity());
        Assert.assertEquals(inverseEntity, relation.getChildEntity());
        
    }
    
    @Test
    public void testCreateReverseManyToOneAssociationComplex() throws ModelException {
        
        // Set up database
        DatabaseImpl database = createDatabase();
        
        // Parent table
        TableImpl parent = database.createTable("PARENT");
        ColumnImpl parentPk1 = parent.createColumn("PARENT_ID_1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl parentPk2 = parent.createColumn("PARENT_ID_2", ColumnType.DATE, true, 4, 0);
        parent.createColumn("DATA", ColumnType.VARCHAR, false, 50, 0);
        SortedSet<CharSequence> parentPkCols = new TreeSet<CharSequence>();
        parentPkCols.add(parentPk1.getName().toString());
        parentPkCols.add(parentPk2.getName().toString());
        parent.createPrimaryKey("PARENT_PK", parentPkCols);
        
        // Child table
        TableImpl child = database.createTable("CHILD");
        ColumnImpl childPk1 = child.createColumn("CHILD_ID_1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl childPk2 = child.createColumn("CHILD_ID_2", ColumnType.DATE, true, 4, 0);
        child.createColumn("DATA", ColumnType.VARCHAR, false, 50, 0);
        SortedSet<CharSequence> childPkCols = new TreeSet<CharSequence>();
        childPkCols.add(childPk1.getName().toString());
        childPkCols.add(childPk2.getName().toString());
        child.createPrimaryKey("CHILD_PK", childPkCols);
        
        // Join table
        TableImpl join = database.createTable("JOIN");
        ColumnImpl joinPk1 = join.createColumn("PARENT_ID_1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl joinPk2 = join.createColumn("PARENT_ID_2", ColumnType.DATE, true, 4, 0);
        ColumnImpl joinPk3 = join.createColumn("CHILD_ID_1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl joinPk4 = join.createColumn("CHILD_ID_2", ColumnType.DATE, true, 4, 0);
        SortedSet<CharSequence> joinPkCols = new TreeSet<CharSequence>();
        joinPkCols.add(joinPk1.getName().toString());
        joinPkCols.add(joinPk2.getName().toString());
        joinPkCols.add(joinPk3.getName().toString());
        joinPkCols.add(joinPk4.getName().toString());
        join.createPrimaryKey("JOIN_PK", joinPkCols);
        
        // Create unique key on join table
        SortedSet<CharSequence> ukCols = new TreeSet<CharSequence>();
        ukCols.add(joinPk1.getName().toString()); 
        ukCols.add(joinPk2.getName().toString());
        join.createUniqueKey("JOIN_UK", ukCols);
        
        // Foreign keys
        Map<CharSequence, CharSequence> parentConstraints = new HashMap<CharSequence, CharSequence>();
        parentConstraints.put(joinPk1.getName().toString(), parentPk1.getName().toString());
        parentConstraints.put(joinPk2.getName().toString(), parentPk2.getName().toString());
        ForeignKey ownerForeignKey = database.createForeignKey("parent_fk", "JOIN", "PARENT", parentConstraints);
        Map<CharSequence, CharSequence> childConstraints = new HashMap<CharSequence, CharSequence>();
        childConstraints.put(joinPk3.getName().toString(), childPk1.getName().toString());
        childConstraints.put(joinPk4.getName().toString(), childPk2.getName().toString());
        ForeignKey inverseForeignKey = database.createForeignKey("child_fk", "JOIN", "CHILD", childConstraints);
        
        // Reverse engineer
        ModelImpl model = reveng.reverseEngineer(database);
        
        // Test
        Assert.assertNotNull(model.getCatalogs());
        Assert.assertEquals(1, model.getCatalogCount());
        CatalogImpl catalog = model.getCatalog(0);
        Assert.assertNotNull(catalog);
        Assert.assertEquals("catalog", catalog.getName().toString());
        Assert.assertEquals(2, catalog.getEntityCount());
        Entity ownerEntity = catalog.getEntityByName("child"); // These are reversed from above
        Assert.assertNotNull(ownerEntity);
        Entity inverseEntity = catalog.getEntityByName("parent");
        Assert.assertNotNull(inverseEntity);
        OneToManyAssociationField parentField = ownerEntity.getOneToManyAssociation("parents");
        Assert.assertNotNull(parentField);
        Assert.assertEquals("parents", parentField.getName().toString());
        ManyToOneAssociationField childField = inverseEntity.getManyToOneAssociation("child");
        Assert.assertNotNull(childField);
        Assert.assertEquals("child", childField.getName().toString());
        AssociationRelation relation = parentField.getRelation();
        Assert.assertNotNull(relation);
        Assert.assertEquals(inverseForeignKey, relation.getOwnerForeignKey());
        Assert.assertEquals(ownerForeignKey, relation.getInverseForeignKey());
        Assert.assertEquals(ownerEntity, relation.getParentEntity());
        Assert.assertEquals(inverseEntity, relation.getChildEntity());
        
    }
    
    @Test
    public void testCreateOneToOneAssociationComplex() throws ModelException {
        
        DatabaseImpl database = createDatabase();
        
        // Parent table
        TableImpl parent = database.createTable("PARENT");
        ColumnImpl parentPk1 = parent.createColumn("PARENT_ID_1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl parentPk2 = parent.createColumn("PARENT_ID_2", ColumnType.DATE, true, 4, 0);
        parent.createColumn("DATA", ColumnType.VARCHAR, false, 50, 0);
        SortedSet<CharSequence> parentPkCols = new TreeSet<CharSequence>();
        parentPkCols.add(parentPk1.getName().toString());
        parentPkCols.add(parentPk2.getName().toString());
        parent.createPrimaryKey("PARENT_PK", parentPkCols);
        
        // Child table
        TableImpl child = database.createTable("CHILD");
        ColumnImpl childPk1 = child.createColumn("CHILD_ID_1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl childPk2 = child.createColumn("CHILD_ID_2", ColumnType.DATE, true, 4, 0);
        child.createColumn("DATA", ColumnType.VARCHAR, false, 50, 0);
        SortedSet<CharSequence> childPkCols = new TreeSet<CharSequence>();
        childPkCols.add(childPk1.getName().toString());
        childPkCols.add(childPk2.getName().toString());
        child.createPrimaryKey("CHILD_PK", childPkCols);
        
        // Join table
        TableImpl join = database.createTable("JOIN");
        ColumnImpl joinPk1 = join.createColumn("PARENT_ID_1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl joinPk2 = join.createColumn("PARENT_ID_2", ColumnType.DATE, true, 4, 0);
        ColumnImpl joinPk3 = join.createColumn("CHILD_ID_1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl joinPk4 = join.createColumn("CHILD_ID_2", ColumnType.DATE, true, 4, 0);
        SortedSet<CharSequence> joinPkCols = new TreeSet<CharSequence>();
        joinPkCols.add(joinPk1.getName().toString());
        joinPkCols.add(joinPk2.getName().toString());
        joinPkCols.add(joinPk3.getName().toString());
        joinPkCols.add(joinPk4.getName().toString());
        join.createPrimaryKey("JOIN_PK", joinPkCols);
        
        // Create unique keys on join table
        SortedSet<CharSequence> ukCols1 = new TreeSet<CharSequence>();
        ukCols1.add(joinPk1.getName().toString());
        ukCols1.add(joinPk2.getName().toString());
        join.createUniqueKey("JOIN_UK1", ukCols1);
        SortedSet<CharSequence> ukCols2 = new TreeSet<CharSequence>();
        ukCols2.add(joinPk3.getName().toString());
        ukCols2.add(joinPk4.getName().toString());
        join.createUniqueKey("JOIN_UK2", ukCols2);
        
        // Foreign keys - NOTE processed in alphabetical order
        Map<CharSequence, CharSequence> parentConstraints = new HashMap<CharSequence, CharSequence>();
        parentConstraints.put(joinPk1.getName().toString(), parentPk1.getName().toString());
        parentConstraints.put(joinPk2.getName().toString(), parentPk2.getName().toString());
        ForeignKey ownerForeignKey = database.createForeignKey("a_fk", "JOIN", "PARENT", parentConstraints);
        Map<CharSequence, CharSequence> childConstraints = new HashMap<CharSequence, CharSequence>();
        childConstraints.put(joinPk3.getName().toString(), childPk1.getName().toString());
        childConstraints.put(joinPk4.getName().toString(), childPk2.getName().toString());
        ForeignKey inverseForeignKey = database.createForeignKey("b_fk", "JOIN", "CHILD", childConstraints);
        
        
        // Reverse engineer
        ModelImpl model = reveng.reverseEngineer(database);
        
        
        // Test
        Assert.assertNotNull(model.getCatalogs());
        Assert.assertEquals(1, model.getCatalogCount());
        CatalogImpl catalog = model.getCatalog(0);
        Assert.assertNotNull(catalog);
        Assert.assertEquals("catalog", catalog.getName().toString());
        Assert.assertEquals(2, catalog.getEntityCount());
        Entity ownerEntity = catalog.getEntityByName("parent");
        Assert.assertNotNull(ownerEntity);
        Entity inverseEntity = catalog.getEntityByName("child");
        Assert.assertNotNull(inverseEntity);
        OneToOneAssociationField parentField = ownerEntity.getOneToOneAssociation("child");
        Assert.assertNotNull(parentField);
        Assert.assertEquals("child", parentField.getName().toString());
        OneToOneAssociationField childField = inverseEntity.getOneToOneAssociation("parent");
        Assert.assertNotNull(childField);
        Assert.assertEquals("parent", childField.getName().toString());
        AssociationRelation relation = parentField.getRelation();
        Assert.assertNotNull(relation);
        Assert.assertEquals(ownerForeignKey, relation.getOwnerForeignKey());
        Assert.assertEquals(inverseForeignKey, relation.getInverseForeignKey());
        Assert.assertEquals(ownerEntity, relation.getParentEntity());
        Assert.assertEquals(inverseEntity, relation.getChildEntity());
        
    }
    
    @Test
    public void testCreateOneToOneComplex() throws ModelException {
        
        DatabaseImpl database = createDatabase();
        
        // Parent table
        TableImpl parent = database.createTable("PARENT");
        ColumnImpl parentPk1 = parent.createColumn("PARENT_ID_1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl parentPk2 = parent.createColumn("PARENT_ID_2", ColumnType.DATE, true, 4, 0);
        parent.createColumn("DATA", ColumnType.VARCHAR, false, 50, 0);
        SortedSet<CharSequence> parentPkCols = new TreeSet<CharSequence>();
        parentPkCols.add(parentPk1.getName().toString());
        parentPkCols.add(parentPk2.getName().toString());
        parent.createPrimaryKey("PARENT_PK", parentPkCols);
        
        // Child table
        TableImpl child = database.createTable("CHILD");
        ColumnImpl childPk1 = child.createColumn("CHILD_ID_1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl childPk2 = child.createColumn("CHILD_ID_2", ColumnType.DATE, true, 4, 0);
        ColumnImpl childFk1 = child.createColumn("PARENT_FK_1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl childFk2 = child.createColumn("PARENT_FK_2", ColumnType.DATE, true, 4, 0);
        ColumnImpl childUk1 = child.createColumn("CHILD_UK_1", ColumnType.INTEGER, true, 4, 0);
        child.createColumn("DATA", ColumnType.VARCHAR, false, 50, 0);
        SortedSet<CharSequence> childPkCols = new TreeSet<CharSequence>();
        childPkCols.add(childPk1.getName().toString());
        childPkCols.add(childPk2.getName().toString());
        child.createPrimaryKey("CHILD_PK", childPkCols);
        
        // Create unique keys on child table
        // Need two - one to set up a NaturalIdentity
        // and the second for the OneToOne
        SortedSet<CharSequence> ukCols1 = new TreeSet<CharSequence>();
        ukCols1.add(childUk1.getName().toString());
        child.createUniqueKey("CHILD_UK1", ukCols1);
        SortedSet<CharSequence> ukCols2 = new TreeSet<CharSequence>();
        ukCols2.add(childFk1.getName().toString());
        ukCols2.add(childFk2.getName().toString());
        child.createUniqueKey("CHILD_UK2", ukCols2);
        
        // Foreign keys
        Map<CharSequence, CharSequence> parentConstraints = new HashMap<CharSequence, CharSequence>();
        parentConstraints.put(childFk1.getName().toString(), parentPk1.getName().toString());
        parentConstraints.put(childFk2.getName().toString(), parentPk2.getName().toString());
        ForeignKey foreignKey = database.createForeignKey("parent_fk", "CHILD", "PARENT", parentConstraints);
        
        
        // Reverse engineer
        ModelImpl model = reveng.reverseEngineer(database);
        
        
        // Test
        Assert.assertNotNull(model.getCatalogs());
        Assert.assertEquals(1, model.getCatalogCount());
        CatalogImpl catalog = model.getCatalog(0);
        Assert.assertNotNull(catalog);
        Assert.assertEquals("catalog", catalog.getName().toString());
        Assert.assertEquals(2, catalog.getEntityCount());
        Entity ownerEntity = catalog.getEntityByName("parent");
        Assert.assertNotNull(ownerEntity);
        Entity inverseEntity = catalog.getEntityByName("child");
        Assert.assertNotNull(inverseEntity);
        OneToOneParentField parentField = ownerEntity.getOneToOneParent("child");
        Assert.assertNotNull(parentField);
        Assert.assertEquals("child", parentField.getName().toString());
        OneToOneChildField childField = inverseEntity.getOneToOneChild("parent");
        Assert.assertNotNull(childField);
        Assert.assertEquals("parent", childField.getName().toString());
        AggregationRelation relation = parentField.getRelation();
        Assert.assertNotNull(relation);
        Assert.assertEquals(foreignKey, relation.getForeignKey());
        Assert.assertEquals(ownerEntity, relation.getParentEntity());
        Assert.assertEquals(inverseEntity, relation.getChildEntity());
        
    }
    
    @Test
    public void testCreateManyToOneComplex() throws ModelException {
        
        DatabaseImpl database = createDatabase();
        
        // Parent table
        TableImpl parent = database.createTable("PARENT");
        ColumnImpl parentPk1 = parent.createColumn("PARENT_ID_1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl parentPk2 = parent.createColumn("PARENT_ID_2", ColumnType.DATE, true, 4, 0);
        parent.createColumn("DATA", ColumnType.VARCHAR, false, 50, 0);
        SortedSet<CharSequence> parentPkCols = new TreeSet<CharSequence>();
        parentPkCols.add(parentPk1.getName().toString());
        parentPkCols.add(parentPk2.getName().toString());
        parent.createPrimaryKey("PARENT_PK", parentPkCols);
        
        // Child table
        TableImpl child = database.createTable("CHILD");
        ColumnImpl childPk1 = child.createColumn("CHILD_ID_1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl childPk2 = child.createColumn("CHILD_ID_2", ColumnType.DATE, true, 4, 0);
        ColumnImpl childFk1 = child.createColumn("PARENT_FK_1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl childFk2 = child.createColumn("PARENT_FK_2", ColumnType.DATE, true, 4, 0);
        child.createColumn("DATA", ColumnType.VARCHAR, false, 50, 0);
        SortedSet<CharSequence> childPkCols = new TreeSet<CharSequence>();
        childPkCols.add(childPk1.getName().toString());
        childPkCols.add(childPk2.getName().toString());
        child.createPrimaryKey("CHILD_PK", childPkCols);
        
        // Foreign keys
        Map<CharSequence, CharSequence> parentConstraints = new HashMap<CharSequence, CharSequence>();
        parentConstraints.put(childFk1.getName().toString(), parentPk1.getName().toString());
        parentConstraints.put(childFk2.getName().toString(), parentPk2.getName().toString());
        ForeignKey foreignKey = database.createForeignKey("parent_fk", "CHILD", "PARENT", parentConstraints);
        
        // Reverse engineer
        ModelImpl model = reveng.reverseEngineer(database);
        
        
        // Test
        Assert.assertNotNull(model.getCatalogs());
        Assert.assertEquals(1, model.getCatalogCount());
        CatalogImpl catalog = model.getCatalog(0);
        Assert.assertNotNull(catalog);
        Assert.assertEquals("catalog", catalog.getName().toString());
        Assert.assertEquals(2, catalog.getEntityCount());
        Entity ownerEntity = catalog.getEntityByName("parent");
        Assert.assertNotNull(ownerEntity);
        EntityImpl inverseEntity = catalog.getEntityByName("child");
        Assert.assertNotNull(inverseEntity);
        OneToManyField parentField = ownerEntity.getOneToMany("childs");
        Assert.assertNotNull(parentField);
        Assert.assertEquals("childs", parentField.getName().toString());
        ManyToOneAggregationField childField = inverseEntity.getManyToOne("parent");
        Assert.assertNotNull(childField);
        Assert.assertEquals("parent", childField.getName().toString());
        AggregationRelation relation = parentField.getRelation();
        Assert.assertNotNull(relation);
        Assert.assertEquals(foreignKey, relation.getForeignKey());
        Assert.assertEquals(ownerEntity, relation.getParentEntity());
        Assert.assertEquals(inverseEntity, relation.getChildEntity());
        
    }
    
    @Test
    public void testCreateConstrainedOneToOneIdentityComplex() throws ModelException {
        
        DatabaseImpl database = createDatabase();
        
        // Parent table
        TableImpl parent = database.createTable("PARENT");
        ColumnImpl parentPk1 = parent.createColumn("PARENT_ID_1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl parentPk2 = parent.createColumn("PARENT_ID_2", ColumnType.DATE, true, 4, 0);
        parent.createColumn("DATA", ColumnType.VARCHAR, false, 50, 0);
        SortedSet<CharSequence> parentPkCols = new TreeSet<CharSequence>();
        parentPkCols.add(parentPk1.getName().toString());
        parentPkCols.add(parentPk2.getName().toString());
        parent.createPrimaryKey("PARENT_PK", parentPkCols);
        
        // Child table
        TableImpl child = database.createTable("CHILD");
        ColumnImpl childPk1 = child.createColumn("CHILD_ID_1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl childPk2 = child.createColumn("CHILD_ID_2", ColumnType.DATE, true, 4, 0);
        child.createColumn("DATA", ColumnType.VARCHAR, false, 50, 0);
        SortedSet<CharSequence> childPkCols = new TreeSet<CharSequence>();
        childPkCols.add(childPk1.getName().toString());
        childPkCols.add(childPk2.getName().toString());
        child.createPrimaryKey("CHILD_PK", childPkCols);
        
        // Foreign keys
        Map<CharSequence, CharSequence> parentConstraints = new HashMap<CharSequence, CharSequence>();
        parentConstraints.put(childPk1.getName().toString(), parentPk1.getName().toString());
        parentConstraints.put(childPk2.getName().toString(), parentPk2.getName().toString());
        ForeignKey foreignKey = database.createForeignKey("parent_fk", "CHILD", "PARENT", parentConstraints);
        
        // Reverse engineer
        ModelImpl model = reveng.reverseEngineer(database);
        
        
        // Test
        Assert.assertNotNull(model.getCatalogs());
        Assert.assertEquals(1, model.getCatalogCount());
        CatalogImpl catalog = model.getCatalog(0);
        Assert.assertNotNull(catalog);
        Assert.assertEquals("catalog", catalog.getName().toString());
        Assert.assertEquals(2, catalog.getEntityCount());
        Entity ownerEntity = catalog.getEntityByName("parent");
        Assert.assertNotNull(ownerEntity);
        Entity inverseEntity = catalog.getEntityByName("child");
        Assert.assertNotNull(inverseEntity);
        OneToOneParentField parentField = ownerEntity.getOneToOneParent("child");
        Assert.assertNotNull(parentField);
        Assert.assertEquals("child", parentField.getName().toString());
        Assert.assertTrue(inverseEntity.getIdentity() instanceof OneToOneChildIdentity);
        OneToOneChildIdentity childField = (OneToOneChildIdentityImpl)inverseEntity.getIdentity();
        Assert.assertNotNull(childField);
        Assert.assertEquals("parent", childField.getName().toString());
        AggregationRelation relation = parentField.getRelation();
        Assert.assertNotNull(relation);
        Assert.assertEquals(foreignKey, relation.getForeignKey());
        Assert.assertEquals(ownerEntity, relation.getParentEntity());
        Assert.assertEquals(inverseEntity, relation.getChildEntity());
        
    }
    
    @Test
    public void testCreateSimpleNaturalIdentity() throws ModelException {
        DatabaseImpl database = createDatabase();
        TableImpl table = database.createTable("TBL_TABLE");
        ColumnImpl pk = table.createColumn("TEST_ID", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl uk = table.createColumn("TEST_UK", ColumnType.INTEGER, true, 4, 0);
        table.createColumn("DATA", ColumnType.VARCHAR, false, 50, 0);
        SortedSet<CharSequence> pkCols = new TreeSet<CharSequence>();
        pkCols.add(pk.getName().toString());
        table.createPrimaryKey("TEST_PK", pkCols);
        SortedSet<CharSequence> ukCols = new TreeSet<CharSequence>();
        ukCols.add(uk.getName().toString());
        table.createUniqueKey("TEST_UK", ukCols);
        
        // Reverse engineer
        ModelImpl model = reveng.reverseEngineer(database);
        
        
        // Test
        Assert.assertNotNull(model.getCatalogs());
        Assert.assertEquals(1, model.getCatalogCount());
        CatalogImpl catalog = model.getCatalog(0);
        
        Assert.assertNotNull(catalog);
        Assert.assertEquals("catalog", catalog.getName().toString());
        Assert.assertEquals(1, catalog.getEntityCount());
        Entity entity = catalog.getEntityByName("table");
        Assert.assertNotNull(entity);
        Assert.assertNotNull(entity.getNaturalIdentity());
        Assert.assertTrue(entity.getNaturalIdentity() instanceof SimpleNaturalIdentity);
        SimpleNaturalIdentityImpl simple = (SimpleNaturalIdentityImpl)entity.getNaturalIdentity();
        Assert.assertEquals("testUk", simple.getName().toString());
    }
    
    @Test
    public void testCreateNaturalOneToOneIdentity() throws ModelException {
        
        DatabaseImpl database = createDatabase();
        
        // Parent table
        TableImpl parent = database.createTable("PARENT");
        ColumnImpl parentPk1 = parent.createColumn("PARENT_ID_1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl parentPk2 = parent.createColumn("PARENT_ID_2", ColumnType.DATE, true, 4, 0);
        parent.createColumn("DATA", ColumnType.VARCHAR, false, 50, 0);
        SortedSet<CharSequence> parentPkCols = new TreeSet<CharSequence>();
        parentPkCols.add(parentPk1.getName().toString());
        parentPkCols.add(parentPk2.getName().toString());
        parent.createPrimaryKey("PARENT_PK", parentPkCols);
        
        // Child table
        TableImpl child = database.createTable("CHILD");
        ColumnImpl childPk1 = child.createColumn("CHILD_ID_1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl childUk1 = child.createColumn("CHILD_UK_1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl childUk2 = child.createColumn("CHILD_UK_2", ColumnType.DATE, true, 4, 0);
        child.createColumn("DATA", ColumnType.VARCHAR, false, 50, 0);
        SortedSet<CharSequence> childPkCols = new TreeSet<CharSequence>();
        childPkCols.add(childPk1.getName().toString());
        child.createPrimaryKey("CHILD_PK", childPkCols);
        SortedSet<CharSequence> childUkCols = new TreeSet<CharSequence>();
        childUkCols.add(childUk1.getName().toString());
        childUkCols.add(childUk2.getName().toString());
        child.createUniqueKey("CHILD_UKK", childUkCols);
        
        // Foreign keys
        Map<CharSequence, CharSequence> parentConstraints = new HashMap<CharSequence, CharSequence>();
        parentConstraints.put(childUk1.getName().toString(), parentPk1.getName().toString());
        parentConstraints.put(childUk2.getName().toString(), parentPk2.getName().toString());
        ForeignKey foreignKey = database.createForeignKey("PARENT_FK", "CHILD", "PARENT", parentConstraints);
        
        // Reverse engineer
        ModelImpl model = reveng.reverseEngineer(database);
        
        
        // Test
        Assert.assertNotNull(model.getCatalogs());
        Assert.assertEquals(1, model.getCatalogCount());
        CatalogImpl catalog = model.getCatalog(0);
        Assert.assertNotNull(catalog);
        Assert.assertEquals("catalog", catalog.getName().toString());
        Assert.assertEquals(2, catalog.getEntityCount());
        Entity ownerEntity = catalog.getEntityByName("parent");
        Assert.assertNotNull(ownerEntity);
        Entity inverseEntity = catalog.getEntityByName("child");
        Assert.assertNotNull(inverseEntity);
        OneToOneParentField parentField = ownerEntity.getOneToOneParent("child");
        Assert.assertNotNull(parentField);
        Assert.assertEquals("child", parentField.getName().toString());
        Assert.assertTrue(inverseEntity.getIdentity() instanceof SimpleIdentity);
        Assert.assertNotNull(inverseEntity.getNaturalIdentity());
        Assert.assertTrue(inverseEntity.getNaturalIdentity() instanceof OneToOneChildNaturalIdentity);
        OneToOneChildNaturalIdentityImpl childField = (OneToOneChildNaturalIdentityImpl)inverseEntity.getNaturalIdentity();
        Assert.assertNotNull(childField);
        Assert.assertEquals("parent", childField.getName().toString());
        AggregationRelation relation = parentField.getRelation();
        Assert.assertNotNull(relation);
        Assert.assertEquals(foreignKey, relation.getForeignKey());
        Assert.assertEquals(ownerEntity, relation.getParentEntity());
        Assert.assertEquals(inverseEntity, relation.getChildEntity());
        
    }
    
    @Test
    public void testCreateComponentNaturalIdentityWithSimpleProperties() throws ModelException {
        DatabaseImpl database = createDatabase();
        TableImpl table = database.createTable("TBL_TABLE");
        ColumnImpl pk = table.createColumn("TEST_ID", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl uk1 = table.createColumn("TEST_UK_1", ColumnType.VARCHAR, true, 4, 0);
        ColumnImpl uk2 = table.createColumn("TEST_UK_2", ColumnType.CHARACTER, true, 4, 0);
        table.createColumn("DATA", ColumnType.VARCHAR, false, 50, 0);
        SortedSet<CharSequence> pkCols = new TreeSet<CharSequence>();
        pkCols.add(pk.getName().toString());
        table.createPrimaryKey("TEST_PK", pkCols);
        SortedSet<CharSequence> ukCols = new TreeSet<CharSequence>();
        ukCols.add(uk1.getName().toString());
        ukCols.add(uk2.getName().toString());
        table.createUniqueKey("TEST_UK", ukCols);
        
        // Reverse engineer
        ModelImpl model = reveng.reverseEngineer(database);
        
        
        // Test
        Assert.assertNotNull(model.getCatalogs());
        Assert.assertEquals(1, model.getCatalogCount());
        CatalogImpl catalog = model.getCatalog(0);
        
        Assert.assertNotNull(catalog);
        Assert.assertEquals("catalog", catalog.getName().toString());
        Assert.assertEquals(1, catalog.getEntityCount());
        Entity entity = catalog.getEntityByName("table");
        Assert.assertNotNull(entity);
        Assert.assertNotNull(entity.getNaturalIdentity());
        Assert.assertTrue(entity.getNaturalIdentity() instanceof ComponentNaturalIdentityImpl);
        ComponentNaturalIdentityImpl component = (ComponentNaturalIdentityImpl)entity.getNaturalIdentity();
        Assert.assertEquals("tableNaturalIdentity", component.getName().toString());
        Assert.assertEquals(2, component.getFieldCount());
        Assert.assertEquals(2, component.getSimplePropertyCount());
        Assert.assertNotNull(component.getSimpleProperty("testUk1"));
        Assert.assertEquals(uk1, component.getSimpleProperty("testUk1").getColumn());
        Assert.assertNotNull(component.getSimpleProperty("testUk2"));
        Assert.assertEquals(uk2, component.getSimpleProperty("testUk2").getColumn());
        
    }
    
    @Test
    public void testCreateComponentNaturalIdentityWithManyToOnes() throws ModelException {
        
        DatabaseImpl database = createDatabase();
        
        // Parent table
        TableImpl parent = database.createTable("PARENT");
        ColumnImpl parentPk1 = parent.createColumn("PARENT_ID_1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl parentPk2 = parent.createColumn("PARENT_ID_2", ColumnType.DATE, true, 4, 0);
        parent.createColumn("DATA", ColumnType.VARCHAR, false, 50, 0);
        SortedSet<CharSequence> parentPkCols = new TreeSet<CharSequence>();
        parentPkCols.add(parentPk1.getName().toString());
        parentPkCols.add(parentPk2.getName().toString());
        parent.createPrimaryKey("PARENT_PK", parentPkCols);
        
        // Child table
        TableImpl child = database.createTable("CHILD");
        ColumnImpl childPk1 = child.createColumn("CHILD_ID_1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl childUk1 = child.createColumn("CHILD_UK_1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl childUk2 = child.createColumn("CHILD_UK_2", ColumnType.DATE, true, 4, 0);
        child.createColumn("DATA", ColumnType.VARCHAR, false, 50, 0);
        SortedSet<CharSequence> childPkCols = new TreeSet<CharSequence>();
        childPkCols.add(childPk1.getName().toString());
        child.createPrimaryKey("CHILD_PK", childPkCols);
        SortedSet<CharSequence> childUkCols = new TreeSet<CharSequence>();
        childUkCols.add(childUk1.getName().toString());
        childUkCols.add(childUk2.getName().toString());
        child.createUniqueKey("CHILD_UKK", childUkCols);
        
        // Foreign keys
        Map<CharSequence, CharSequence> parentConstraints = new HashMap<CharSequence, CharSequence>();
        parentConstraints.put(childUk1.getName().toString(), parentPk1.getName().toString());
        parentConstraints.put(childUk2.getName().toString(), parentPk2.getName().toString());
        ForeignKey foreignKey = database.createForeignKey("PARENT_FK", "CHILD", "PARENT", parentConstraints);
        
        // Reverse engineer
        ModelImpl model = reveng.reverseEngineer(database);
        
        
        // Test
        Assert.assertNotNull(model.getCatalogs());
        Assert.assertEquals(1, model.getCatalogCount());
        CatalogImpl catalog = model.getCatalog(0);
        Assert.assertNotNull(catalog);
        Assert.assertEquals("catalog", catalog.getName().toString());
        Assert.assertEquals(2, catalog.getEntityCount());
        Entity ownerEntity = catalog.getEntityByName("parent");
        Assert.assertNotNull(ownerEntity);
        Entity inverseEntity = catalog.getEntityByName("child");
        Assert.assertNotNull(inverseEntity);
        OneToOneParentField parentField = ownerEntity.getOneToOneParent("child");
        Assert.assertNotNull(parentField);
        Assert.assertEquals("child", parentField.getName().toString());
        Assert.assertTrue(inverseEntity.getIdentity() instanceof SimpleIdentity);
        Assert.assertNotNull(inverseEntity.getNaturalIdentity());
        Assert.assertTrue(inverseEntity.getNaturalIdentity() instanceof OneToOneChildNaturalIdentity);
        OneToOneChildNaturalIdentityImpl childField = (OneToOneChildNaturalIdentityImpl)inverseEntity.getNaturalIdentity();
        Assert.assertNotNull(childField);
        Assert.assertEquals("parent", childField.getName().toString());
        AggregationRelation relation = parentField.getRelation();
        Assert.assertNotNull(relation);
        Assert.assertEquals(foreignKey, relation.getForeignKey());
        Assert.assertEquals(ownerEntity, relation.getParentEntity());
        Assert.assertEquals(inverseEntity, relation.getChildEntity());
        
    }

    private DatabaseImpl createDatabase() throws ModelException {
        DatabaseImpl database = new DatabaseImpl("CATALOG", NamingConventionFactory.getTestNamingConvention());
        return database;
    }


}
