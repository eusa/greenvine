package net.sourceforge.greenvine.model.api.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import junit.framework.Assert;
import net.sourceforge.greenvine.model.api.ColumnType;
import net.sourceforge.greenvine.model.api.ManyToManyAssociationField;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.PropertyType;
import net.sourceforge.greenvine.model.api.PropertyValueGenerationStrategy;
import net.sourceforge.greenvine.model.naming.testutils.NamingConventionFactory;

import org.junit.Before;
import org.junit.Test;

public class ManyToManyRelationshipImplTest {
    
    private CatalogImpl catalog;
    private EntityImpl ownerEntity;
    private EntityImpl inverseEntity;
    private DatabaseImpl database;
    private TableImpl parent;
    private TableImpl join;
    private TableImpl child;
    private ForeignKeyImpl ownerForeignKey;
    private ForeignKeyImpl inverseForeignKey;
    
    @Before
    public void setUp() throws ModelException {
        
        // Set up model and database
        ModelImpl model = new ModelImpl("test");
        this.database = new DatabaseImpl("TEST_DB", NamingConventionFactory.getTestNamingConvention());
        this.catalog = new CatalogImpl("testModel", model, database);
        
        
        // Parent table
        parent = database.createTable("PARENT");
        ColumnImpl parentPk1 = parent.createColumn("PARENT_ID_1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl parentPk2 = parent.createColumn("PARENT_ID_2", ColumnType.DATE, true, 4, 0);
        ColumnImpl parentData = parent.createColumn("DATA", ColumnType.VARCHAR, false, 50, 0);
        SortedSet<CharSequence> parentPkCols = new TreeSet<CharSequence>();
        parentPkCols.add(parentPk1.getName());
        parentPkCols.add(parentPk2.getName());
        parent.createPrimaryKey("PARENT_PK", parentPkCols);
        
        // Child table
        child = database.createTable("CHILD");
        ColumnImpl childPk1 = child.createColumn("CHILD_ID_1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl childPk2 = child.createColumn("CHILD_ID_2", ColumnType.DATE, true, 4, 0);
        ColumnImpl childData = child.createColumn("DATA", ColumnType.VARCHAR, false, 50, 0);
        SortedSet<CharSequence> childPkCols = new TreeSet<CharSequence>();
        childPkCols.add(childPk1.getName());
        childPkCols.add(childPk2.getName());
        child.createPrimaryKey("CHILD_PK", childPkCols);
        
        // Join table
        join = database.createTable("JOIN");
        ColumnImpl joinPk1 = join.createColumn("PARENT_ID_1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl joinPk2 = join.createColumn("PARENT_ID_2", ColumnType.DATE, true, 4, 0);
        ColumnImpl joinPk3 = join.createColumn("CHILD_ID_1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl joinPk4 = join.createColumn("CHILD_ID_2", ColumnType.DATE, true, 4, 0);
        SortedSet<CharSequence> joinPkCols = new TreeSet<CharSequence>();
        joinPkCols.add(joinPk1.getName());
        joinPkCols.add(joinPk2.getName());
        joinPkCols.add(joinPk3.getName());
        joinPkCols.add(joinPk4.getName());
        join.createPrimaryKey("JOIN_PK", joinPkCols);
        
        // Foreign keys
        Map<CharSequence, CharSequence> parentConstraints = new HashMap<CharSequence, CharSequence>();
        parentConstraints.put(joinPk1.getName(), parentPk1.getName());
        parentConstraints.put(joinPk2.getName(), parentPk2.getName());
        ownerForeignKey = database.createForeignKey("parent_fk", "JOIN", "PARENT", parentConstraints);
        Map<CharSequence, CharSequence> childConstraints = new HashMap<CharSequence, CharSequence>();
        childConstraints.put(joinPk3.getName(), childPk1.getName());
        childConstraints.put(joinPk4.getName(), childPk2.getName());
        inverseForeignKey = database.createForeignKey("child_fk", "JOIN", "CHILD", childConstraints);
        
        // Entities
        ownerEntity = catalog.createEntity(null, "ownerEntity", parent.getName());
        ComponentIdentityImpl parentId = ownerEntity.createComponentIdentity("manyToOneOwnerEntityIdentity");
        parentId.createSimpleProperty("ownerIdField1", parentPk1.getColumnType().getPropertyType(), PropertyValueGenerationStrategy.ASSIGNED, parentPk1.getName());
        parentId.createSimpleProperty("ownerIdField2", parentPk2.getColumnType().getPropertyType(), PropertyValueGenerationStrategy.ASSIGNED, parentPk2.getName());
        ownerEntity.createSimpleProperty("data", PropertyType.STRING, false, parentData.getName());
        inverseEntity = catalog.createEntity(null, "inverseEntity", child.getName());
        ComponentIdentityImpl childId = inverseEntity.createComponentIdentity("oneToOneInverseEntityIdentity");
        childId.createSimpleProperty("oneToOneInverseId1", childPk1.getColumnType().getPropertyType(), PropertyValueGenerationStrategy.ASSIGNED, childPk1.getName());
        childId.createSimpleProperty("oneToOneInverseId2", childPk2.getColumnType().getPropertyType(), PropertyValueGenerationStrategy.ASSIGNED, childPk2.getName());
        inverseEntity.createSimpleProperty("data", PropertyType.STRING, false, childData.getName());
        
    }
    
    @Test
    public void testCreateValidBiDirectional() throws ModelException {
        ManyToManyRelationImpl relation = new ManyToManyRelationImpl("ownerField", ownerForeignKey.getName(), "inverseField", inverseForeignKey.getName(), catalog);
        
        // Test relation
        Assert.assertEquals(ownerEntity, relation.getParentEntity());
        Assert.assertEquals(inverseEntity, relation.getChildEntity());
        Assert.assertEquals(ownerForeignKey, relation.getOwnerForeignKey());
        Assert.assertEquals(inverseForeignKey, relation.getInverseForeignKey());
        
        // Test owner field
        Assert.assertNotNull(relation.getParentField());
        Assert.assertEquals("ownerField", relation.getParentField().getName().toString());
        Assert.assertEquals(ownerEntity, relation.getParentField().getFieldCollection());
        ManyToManyAssociationField ownerField = ownerEntity.getManyToMany("ownerField");
        Assert.assertEquals(ownerField, relation.getParentField());
        
        // Test inverse field
        Assert.assertNotNull(relation.getChildField());
        Assert.assertEquals("inverseField", relation.getChildField().getName().toString());
        Assert.assertEquals(inverseEntity, relation.getChildField().getFieldCollection());
        ManyToManyAssociationField inverseField = inverseEntity.getManyToMany("inverseField");
        Assert.assertEquals(inverseField, relation.getChildField());

    }
    
    @Test
    public void testCreateValidUniDirectional() throws ModelException {
        ManyToManyRelationImpl relation = new ManyToManyRelationImpl("ownerField", ownerForeignKey.getName(), inverseForeignKey.getName(), catalog);
        
        // Test relation
        Assert.assertEquals(ownerEntity, relation.getParentEntity());
        Assert.assertEquals(inverseEntity, relation.getChildEntity());
        Assert.assertEquals(ownerForeignKey, relation.getOwnerForeignKey());
        Assert.assertEquals(inverseForeignKey, relation.getInverseForeignKey());
        
        // Test owner field
        Assert.assertNotNull(relation.getParentField());
        Assert.assertEquals("ownerField", relation.getParentField().getName().toString());
        Assert.assertEquals(ownerEntity, relation.getParentField().getFieldCollection());
        ManyToManyAssociationField ownerField = ownerEntity.getManyToMany("ownerField");
        Assert.assertEquals(ownerField, relation.getParentField());
        
        // Test inverse field
        Assert.assertNull(relation.getChildField());

    }
    
}
