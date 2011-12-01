package net.sourceforge.greenvine.model.api.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import junit.framework.Assert;
import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.ColumnType;
import net.sourceforge.greenvine.model.api.ForeignKey;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.naming.impl.ColumnNameImpl;

import org.junit.Before;
import org.junit.Test;

public class ForeignKeyImplTest {
    
    private DatabaseImpl database;
    private TableImpl table1;
    private TableImpl table2;
    
    @Before
    public void setUp() throws ModelException {
        
        // Create database
        this.database = new DatabaseImpl("testdb");
        
        // Create table #1
        this.table1 = new TableImpl("tbl_test1", database);
        this.table1.createColumn("table1_pk1", ColumnType.INTEGER, true, 8, 0);
        this.table1.createColumn("table1_pk2", ColumnType.INTEGER, true, 8, 0);
        this.table1.createColumn("table1_not_compat", ColumnType.DATE, true, 8, 10);
        this.table1.createColumn("table1_not_unique", ColumnType.INTEGER, true, 8, 0);
        this.table1.createColumn("table1_unique", ColumnType.INTEGER, true, 8, 0);
        SortedSet<String> pkCols1 = new TreeSet<String>();
        pkCols1.add("table1_pk1");
        pkCols1.add("table1_pk2");
        this.table1.createPrimaryKey("table1_pk", pkCols1);
        SortedSet<String> ukCols1 = new TreeSet<String>();
        ukCols1.add("table1_unique");
        this.table1.createUniqueKey("table1_uk", ukCols1);
        
        // Create table #2
        this.table2 = new TableImpl("tbl_test2", database);
        this.table2.createColumn("table2_pk", ColumnType.INTEGER, true, 8, 0);
        this.table2.createColumn("table2_fk1", ColumnType.INTEGER, true, 8, 0);
        this.table2.createColumn("table2_fk2", ColumnType.INTEGER, true, 8, 0);
        this.table2.createColumn("table2_fk_null1", ColumnType.INTEGER, false, 8, 0);
        this.table2.createColumn("table2_fk_null2", ColumnType.INTEGER, false, 8, 0);
        this.table2.createColumn("table2_data", ColumnType.REAL, true, 15, 10);
        SortedSet<String> pkCols2 = new TreeSet<String>();
        pkCols2.add("table2_pk");
        this.table2.createPrimaryKey("table2_pk", pkCols2);
    }

    @Test
    public void testCreateValidPrimaryKey() throws ModelException {
        Map<String, String> constraints = new HashMap<String, String>();
        constraints.put("table2_fk1", "table1_pk1");
        constraints.put("table2_fk2", "table1_pk2");
        ForeignKeyImpl foreignKey = new ForeignKeyImpl("fk_test", table2.getName(), table1.getName(), constraints, database);
        Assert.assertEquals("fk_test", foreignKey.getName().toString());
        Assert.assertEquals(table2, foreignKey.getReferencingTable());
        Assert.assertEquals(table1, foreignKey.getReferencedTable());
        Assert.assertEquals(2, foreignKey.getColumnConstraintCount());
        Assert.assertEquals(convertForeignColumnMappingToDatabaseName(constraints), foreignKey.getColumnConstraintNames());
    }
    
    @Test
    public void testCreateValidUniqueKey() throws ModelException {
        Map<String, String> constraints = new HashMap<String, String>();
        constraints.put("table2_fk1", "table1_unique");
        ForeignKeyImpl foreignKey = new ForeignKeyImpl("fk_test", "tbl_test2", "tbl_test1", constraints, database);
        Assert.assertEquals("fk_test", foreignKey.getName().toString());
        Assert.assertEquals(table2, foreignKey.getReferencingTable());
        Assert.assertEquals(table1, foreignKey.getReferencedTable());
        Assert.assertEquals(1, foreignKey.getColumnConstraintCount());
        Assert.assertTrue(foreignKey.hasConstraint("table2_fk1", "table1_unique"));
    }
    
    @Test(expected = ModelException.class)
    public void testCreateInvalidReferencingTable() throws ModelException {
        Map<String, String> constraints = new HashMap<String, String>();
        constraints.put("table2_fk1", "table1_pk1");
        constraints.put("table2_fk2", "table1_pk2");
        new ForeignKeyImpl("fk_test", "tbl_testxxx", "tbl_test1", constraints, database);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateInvalidReferencedTable() throws ModelException {
        Map<String, String> constraints = new HashMap<String, String>();
        constraints.put("table2_fk1", "table1_pk1");
        constraints.put("table2_fk2", "table1_pk2");
        new ForeignKeyImpl("fk_test", "tbl_test2", "tbl_testxxx", constraints, database);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateInvalidReferencingColumn() throws ModelException {
        Map<String, String> constraints = new HashMap<String, String>();
        constraints.put("table2_fk1", "table1_pk1");
        constraints.put("table2_xxx", "table1_pk2");
        new ForeignKeyImpl("fk_test", "tbl_test2", "tbl_test1", constraints, database);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateInvalidReferencedColumn() throws ModelException {
        Map<String, String> constraints = new HashMap<String, String>();
        constraints.put("table2_fk1", "table1_pk1");
        constraints.put("table2_fk2", "table1_xxx");
        new ForeignKeyImpl("fk_test", "tbl_test2", "tbl_test1", constraints, database);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateDuplicateName() throws ModelException {
        Map<String, String> constraints = new HashMap<String, String>();
        constraints.put("table2_fk1", "table1_pk1");
        constraints.put("table2_fk2", "table1_pk2");
        new ForeignKeyImpl("fk_test", "tbl_test2", "tbl_test1", constraints, database);
        new ForeignKeyImpl("fk_test", "tbl_test2", "tbl_test1", constraints, database);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateDuplicateColumnsInDifferentKeys() throws ModelException {
        Map<String, String> constraints = new HashMap<String, String>();
        constraints.put("table2_fk1", "table1_pk1");
        constraints.put("table2_fk2", "table1_pk2");
        new ForeignKeyImpl("fk_test1", "tbl_test2", "tbl_test1", constraints, database);
        new ForeignKeyImpl("fk_test2", "tbl_test2", "tbl_test1", constraints, database);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateIncompatibleColumns() throws ModelException {
        Map<String, String> constraints = new HashMap<String, String>();
        constraints.put("table2_fk1", "table1_not_compat");
        new ForeignKeyImpl("fk_test1", "tbl_test2", "tbl_test1", constraints, database);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateNullColumns() throws ModelException {
        new ForeignKeyImpl("fk_test1", "tbl_test2", "tbl_test1", null, database);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateZeroLengthColumns() throws ModelException {
        Map<String, String> constraints = new HashMap<String, String>();
        new ForeignKeyImpl("fk_test1", "tbl_test2", "tbl_test1", constraints, database);
    }
    
    @Test(expected = ModelException.class)
    public void testNotReferencingPrimaryOrUniqueKey() throws ModelException {
        Map<String, String> constraints = new HashMap<String, String>();
        constraints.put("table2_fk1", "table1_not_unique");
        new ForeignKeyImpl("fk_test1", "tbl_test2", "tbl_test1", constraints, database);
    }
    
    @Test
    public void testReferencingColumnNullable() throws ModelException {
        Map<String, String> constraints = new HashMap<String, String>();
        constraints.put("table2_fk_null1", "table1_pk1");
        constraints.put("table2_fk_null2", "table1_pk2");
        new ForeignKeyImpl("fk_test1", "tbl_test2", "tbl_test1", constraints, database);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateDuplicateReferencedColumn() throws ModelException {
        // Note because it is not possible to have a duplicate
        // key in a Map, it is therefore not necessary to 
        // check for duplicate referencing columns (as they are the key)
        Map<String, String> constraints = new HashMap<String, String>();
        constraints.put("table2_fk1", "table1_pk1");
        constraints.put("table2_fk2", "table1_pk1");
        new ForeignKeyImpl("fk_test", "tbl_test2", "tbl_test1", constraints, database);
    }
    
    @Test
    public void testHasReferencingColumn() throws ModelException {
        Map<String, String> constraints = new HashMap<String, String>();
        constraints.put("table2_fk1", "table1_pk1");
        constraints.put("table2_fk2", "table1_pk2");
        ForeignKey foreignKey = new ForeignKeyImpl("fk_test", "tbl_test2", "tbl_test1", constraints, database);
        Column ref = table2.getColumnByName("table2_fk1");
        Column nonRef = table1.getColumn(0);
        Assert.assertTrue(foreignKey.hasReferencingColumn(ref));
        Assert.assertFalse(foreignKey.hasReferencingColumn(nonRef));
        Assert.assertFalse(foreignKey.hasReferencingColumn(null));
    }
    
    @Test
    public void testHasReferencedColumn() throws ModelException {
        Map<String, String> constraints = new HashMap<String, String>();
        constraints.put("table2_fk1", "table1_pk1");
        constraints.put("table2_fk2", "table1_pk2");
        ForeignKey foreignKey = new ForeignKeyImpl("fk_test", "tbl_test2", "tbl_test1", constraints, database);
        Column ref = table1.getColumnByName("table1_pk1");
        Column nonRef = table2.getColumn(0);
        Assert.assertTrue(foreignKey.hasReferencedColumn(ref));
        Assert.assertFalse(foreignKey.hasReferencedColumn(nonRef));
        Assert.assertFalse(foreignKey.hasReferencedColumn(null));
    }
    
    @Test
    public void testDoesColumnReferenceAnotherColumn() throws ModelException {
        Map<String, String> constraints = new HashMap<String, String>();
        constraints.put("table2_fk1", "table1_pk1");
        constraints.put("table2_fk2", "table1_pk2");
        new ForeignKeyImpl("fk_test", "tbl_test2", "tbl_test1", constraints, database);
        Column ref = table2.getColumnByName("table2_fk1");
        Column nonRef = table1.getColumn(0);
        Assert.assertTrue(ref.referencesAnotherColumn());
        Assert.assertFalse(nonRef.referencesAnotherColumn());
    }
    
    @Test
    public void testAreReferencingColumnsPrimary() throws ModelException {
        
        // Create table with primary key as foreign key
        TableImpl table3 = new TableImpl("tbl_test3", database);
        table3.createColumn("table3_fk_pk1", ColumnType.INTEGER, true, 8, 0);
        table3.createColumn("table3_fk_pk2", ColumnType.INTEGER, true, 8, 0);
        table3.createColumn("table3_data", ColumnType.DATE, true, 8, 10);
        SortedSet<String> pkCols = new TreeSet<String>();
        pkCols.add("table3_fk_pk1");
        pkCols.add("table3_fk_pk2");
        table3.createPrimaryKey("table3_pk", pkCols);
        Map<String, String> constraints = new HashMap<String, String>();
        constraints.put("table3_fk_pk1", "table1_pk1");
        constraints.put("table3_fk_pk2", "table1_pk2");
        ForeignKey fk = new ForeignKeyImpl("fk_test", "tbl_test3", "tbl_test1", constraints, database);
        Assert.assertTrue(fk.areReferencingColumnsPrimaryKey());
    }
    
    @Test
    public void testAreReferencingColumnsUnique() throws ModelException {
        
        // Create table with unique key as foreign key
        TableImpl table3 = new TableImpl("tbl_test3", database);
        table3.createColumn("table3_pk", ColumnType.INTEGER, true, 4, 0);
        table3.createColumn("table3_fk_1", ColumnType.INTEGER, true, 8, 0);
        table3.createColumn("table3_fk_2", ColumnType.INTEGER, true, 8, 0);
        table3.createColumn("table3_data", ColumnType.DATE, true, 8, 10);
        SortedSet<String> pkCols = new TreeSet<String>();
        pkCols.add("table3_pk");
        table3.createPrimaryKey("table3_pk", pkCols);
        SortedSet<String> ukCols = new TreeSet<String>();
        ukCols.add("table3_fk_1");
        ukCols.add("table3_fk_2");
        table3.createUniqueKey("table3_uk", ukCols);
        Map<String, String> constraints = new HashMap<String, String>();
        constraints.put("table3_fk_1", "table1_pk1");
        constraints.put("table3_fk_2", "table1_pk2");
        ForeignKey fk = new ForeignKeyImpl("fk_test", "tbl_test3", "tbl_test1", constraints, database);
        Assert.assertTrue(fk.areReferencingColumnsUniqueKey());
    }
    
    @Test
    public void testInferValid() throws ModelException {
        ForeignKeyImpl foreignKey = new ForeignKeyImpl("fk_test", table2.getName(), table1.getName(), database);
        Assert.assertEquals("fk_test", foreignKey.getName().toString());
        
        // Test name and tables
        Assert.assertEquals(table2, foreignKey.getReferencingTable());
        Assert.assertEquals(table1, foreignKey.getReferencedTable());
        Assert.assertEquals(2, foreignKey.getColumnConstraintCount());
        
        // Test constraints
        Map<String, String> constraints = new HashMap<String, String>();
        constraints.put("TBL_TEST1TABLE1_PK1", "table1_pk1");
        constraints.put("TBL_TEST1TABLE1_PK2", "table1_pk2");
        Assert.assertEquals(convertForeignColumnMappingToDatabaseName(constraints), foreignKey.getColumnConstraintNames());
    }
    
    public static Map<ColumnNameImpl, ColumnNameImpl> convertForeignColumnMappingToDatabaseName(Map<? extends CharSequence, ? extends CharSequence> names) throws ModelException {
        Map<ColumnNameImpl, ColumnNameImpl> dbNames = new TreeMap<ColumnNameImpl, ColumnNameImpl>();
        for (CharSequence name : names.keySet()) {
            dbNames.put(new ColumnNameImpl(name), new ColumnNameImpl(names.get(name)));
        }
        return dbNames;
    }
    
}
