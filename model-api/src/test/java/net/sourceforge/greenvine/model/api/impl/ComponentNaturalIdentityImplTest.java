package net.sourceforge.greenvine.model.api.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import junit.framework.Assert;
import net.sourceforge.greenvine.model.api.ColumnType;
import net.sourceforge.greenvine.model.api.ManyToOneAggregationField;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.PropertyType;
import net.sourceforge.greenvine.model.api.PropertyValueGenerationStrategy;
import net.sourceforge.greenvine.model.api.SimpleProperty;
import net.sourceforge.greenvine.model.naming.testutils.NamingConventionFactory;

import org.junit.Before;
import org.junit.Test;

public class ComponentNaturalIdentityImplTest {
    
    private CatalogImpl catalog;
    private EntityImpl manyToOneOwnerEntity;
    private EntityImpl inverseEntity;
    private DatabaseImpl database;
    private TableImpl manyToOneParent;
    private TableImpl child;
    private ForeignKeyImpl manyToOneForeignKey;
    private ColumnImpl manyToOneParentFk1;
    private ColumnImpl manyToOneParentFk2;
    private ColumnImpl childData;
    
    @Before
    public void setUp() throws ModelException {
        
        // Set up model and database
        ModelImpl model = new ModelImpl("test");
        this.database = new DatabaseImpl("TEST_DB", NamingConventionFactory.getTestNamingConvention());
        this.catalog = new CatalogImpl("testModel", model, database);
        
        
        // Many-to-one parent table
        manyToOneParent = database.createTable("MANY_TO_ONE_PARENT");
        ColumnImpl manyToOneParentPk1 = manyToOneParent.createColumn("MANY_TO_ONE_PARENT_ID_1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl manyToOneParentPk2 = manyToOneParent.createColumn("MANY_TO_ONE_PARENT_ID_2", ColumnType.DATE, true, 4, 0);
        ColumnImpl manyToOneParentData = manyToOneParent.createColumn("MANY_TO_ONE_PARENT_DATA", ColumnType.VARCHAR, false, 50, 0);
        SortedSet<CharSequence> manyToOneParentPkCols = new TreeSet<CharSequence>();
        manyToOneParentPkCols.add(manyToOneParentPk1.getName());
        manyToOneParentPkCols.add(manyToOneParentPk2.getName());
        manyToOneParent.createPrimaryKey("MANY_TO_ONE_PARENT_PK", manyToOneParentPkCols);
        
        // Child table
        child = database.createTable("CHILD");
        ColumnImpl childPk1 = child.createColumn("CHILD_ID_1", ColumnType.INTEGER, true, 4, 0);
        ColumnImpl childPk2 = child.createColumn("CHILD_ID_2", ColumnType.DATE, true, 4, 0);
        child.createColumn("ONE_TO_ONE_PARENT_ID_1_FK", ColumnType.INTEGER, true, 4, 0);
        child.createColumn("ONE_TO_ONE_PARENT_ID_2_FK", ColumnType.DATE, true, 4, 0);
        manyToOneParentFk1 = child.createColumn("MANY_TO_ONE_PARENT_ID_1_FK", ColumnType.INTEGER, true, 4, 0);
        manyToOneParentFk2 = child.createColumn("MANY_TO_ONE_PARENT_ID_2_FK", ColumnType.DATE, true, 4, 0);
        childData = child.createColumn("CHILD_DATA", ColumnType.VARCHAR, false, 50, 0);
        SortedSet<CharSequence> oneToOneChildPkCols = new TreeSet<CharSequence>();
        oneToOneChildPkCols.add(childPk1.getName());
        oneToOneChildPkCols.add(childPk2.getName());
        child.createPrimaryKey("CHILD_PK", oneToOneChildPkCols);
        
        // Foreign keys
        Map<CharSequence, CharSequence> manyToOneForeignKeyCols = new HashMap<CharSequence, CharSequence>();
        manyToOneForeignKeyCols.put(manyToOneParentFk1.getName(), manyToOneParentPk1.getName());
        manyToOneForeignKeyCols.put(manyToOneParentFk2.getName(), manyToOneParentPk2.getName());
        manyToOneForeignKey = database.createForeignKey("MANY_TO_ONE_FK", "CHILD", "MANY_TO_ONE_PARENT", manyToOneForeignKeyCols);
         
        // Entities
        manyToOneOwnerEntity = catalog.createEntity(null, "manyToOneOwnerEntity", manyToOneParent.getName());
        ComponentIdentityImpl parentId = manyToOneOwnerEntity.createComponentIdentity("manyToOneOwnerEntityIdentity");
        parentId.createSimpleProperty("manyToOneOwnerId1", manyToOneParentPk1.getColumnType().getPropertyType(), PropertyValueGenerationStrategy.ASSIGNED, manyToOneParentPk1.getName());
        parentId.createSimpleProperty("manyToOneOwnerId2", manyToOneParentPk2.getColumnType().getPropertyType(), PropertyValueGenerationStrategy.ASSIGNED, manyToOneParentPk2.getName());
        manyToOneOwnerEntity.createSimpleProperty("data", PropertyType.STRING, false, manyToOneParentData.getName());
        
        inverseEntity = catalog.createEntity(null, "inverseEntity", child.getName());
        ComponentIdentityImpl childId = inverseEntity.createComponentIdentity("oneToOneInverseEntityIdentity");
        childId.createSimpleProperty("oneToOneInverseId1", childPk1.getColumnType().getPropertyType(), PropertyValueGenerationStrategy.ASSIGNED, childPk1.getName());
        childId.createSimpleProperty("oneToOneInverseId2", childPk2.getColumnType().getPropertyType(), PropertyValueGenerationStrategy.ASSIGNED, childPk2.getName());
        inverseEntity.createSimpleProperty("data", PropertyType.STRING, false, childData.getName());
        
    }
    
    @Test
    public void testCreateValidSimpleProperty() throws ModelException {
        
        // Create unique keys on one-to-one child table
        SortedSet<CharSequence> ukColsNat = new TreeSet<CharSequence>();
        ukColsNat.add(childData.getName());
        UniqueKeyImpl uk = child.createUniqueKey("CHILD_UK_NATURAL", ukColsNat);
        
        // Create NaturalIdentity
        ComponentNaturalIdentityImpl naturalIdentity = new ComponentNaturalIdentityImpl("inverseNaturalId", uk, inverseEntity);
        
        // Create simple property
        SimpleProperty simple = naturalIdentity.createSimpleProperty("data", PropertyType.CHARACTER, childData.getName());
        
        // Test
        Assert.assertEquals("inverseNaturalId", naturalIdentity.getName().toString());
        Assert.assertEquals(1, naturalIdentity.getFieldCount());
        
        // Check  field
        SimpleProperty simpleProperty = naturalIdentity.getSimpleProperty("data");
        Assert.assertNotNull(simpleProperty);
        Assert.assertEquals(simpleProperty, simple);

    }
    
    
    
    @Test
    public void testCreateValidManyToOne() throws ModelException {
        
        // Create unique keys on one-to-one child table
        SortedSet<CharSequence> ukColsNat = new TreeSet<CharSequence>();
        ukColsNat.add(manyToOneParentFk1.getName());
        ukColsNat.add(manyToOneParentFk2.getName());
        ukColsNat.add(childData.getName());
        UniqueKeyImpl uk = child.createUniqueKey("CHILD_UK_NATURAL", ukColsNat);
        
        // Create NaturalIdentity
        ComponentNaturalIdentityImpl naturalIdentity = new ComponentNaturalIdentityImpl("inverseNaturalId", uk, inverseEntity);
        SimpleProperty simple = naturalIdentity.createSimpleProperty("data", PropertyType.CHARACTER, childData.getName());
        ManyToOneAggregationField many = naturalIdentity.createManyToOneRelationship("oneToMany", "manyToOne", manyToOneForeignKey.getName());
        
        // Test
        Assert.assertEquals("inverseNaturalId", naturalIdentity.getName().toString());
        Assert.assertEquals(2, naturalIdentity.getFieldCount());
        
        // Check many-to-one field
        ManyToOneAggregationField manyToOne = naturalIdentity.getManyToOne("manyToOne");
        Assert.assertNotNull(manyToOne);
        Assert.assertEquals(manyToOne, many);
        SimpleProperty simpleProperty = naturalIdentity.getSimpleProperty("data");
        Assert.assertNotNull(simpleProperty);
        Assert.assertEquals(simpleProperty, simple);
    }
    
    @Test(expected = ModelException.class)
    public void testCreatePartialManyToOne() throws ModelException {
        
        // Create unique key that has only part of the foreign key
        SortedSet<CharSequence> ukColsNat = new TreeSet<CharSequence>();
        ukColsNat.add(manyToOneParentFk2.getName());
        ukColsNat.add(childData.getName());
        UniqueKeyImpl uk = child.createUniqueKey("CHILD_UK_NATURAL", ukColsNat);
        
        // Create NaturalIdentity
        ComponentNaturalIdentityImpl naturalIdentity = new ComponentNaturalIdentityImpl("inverseNaturalId", uk, inverseEntity);
        naturalIdentity.createSimpleProperty("data", PropertyType.CHARACTER, childData.getName());
        naturalIdentity.createManyToOneRelationship("oneToMany", "manyToOne", manyToOneForeignKey.getName());
        
    }

}
