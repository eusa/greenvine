package net.sourceforge.greenvine.model.api.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import junit.framework.Assert;
import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.ColumnType;
import net.sourceforge.greenvine.model.api.ColumnValueGenerationStrategy;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.impl.DatabaseImpl;
import net.sourceforge.greenvine.model.api.impl.ForeignKeyImpl;
import net.sourceforge.greenvine.model.api.impl.PrimaryKeyImpl;
import net.sourceforge.greenvine.model.api.impl.TableImpl;
import net.sourceforge.greenvine.model.api.impl.UniqueKeyImpl;
import net.sourceforge.greenvine.model.naming.impl.ColumnNameImpl;

import org.junit.Before;
import org.junit.Test;

public class PrimaryKeyImplTest {
    
    private DatabaseImpl database;
    private TableImpl table;
    
    @Before
    public void setUp() throws ModelException {
        this.database = new DatabaseImpl("testdb");
        this.table = new TableImpl("tbl_test", database);
        this.table.createColumn("test_id1", ColumnType.INTEGER, true, 8, 0);
        this.table.createColumn("test_id2", ColumnType.DATE, true, 8, 0);
        this.table.createColumn("test_data", ColumnType.REAL, true, 15, 10);
        this.table.createColumn("test_nullable", ColumnType.INTEGER, false, 8, 0);
    }

    @Test
    public void testCreateValid() throws ModelException {
        SortedSet<CharSequence> cols = new TreeSet<CharSequence>();
        cols.add(new ColumnNameImpl("test_id1"));
        cols.add(new ColumnNameImpl("test_id2"));
        PrimaryKeyImpl primaryKey = new PrimaryKeyImpl("pk_test", cols, table);
        Assert.assertEquals("pk_test", primaryKey.getName().toString());
        Assert.assertEquals(2, primaryKey.getColumnCount());
        Assert.assertEquals(cols, primaryKey.getColumnNames());
    }
    
    @Test(expected = ModelException.class)
    public void testCreateInvalidColumn() throws ModelException {
        SortedSet<CharSequence> cols = new TreeSet<CharSequence>();
        cols.add("test_id1");
        cols.add("test_xx");
        new PrimaryKeyImpl("pk_test", cols, table);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateNullName() throws ModelException {
        SortedSet<CharSequence> cols = new TreeSet<CharSequence>();
        cols.add("test_id1");
        cols.add("test_id2");
        CharSequence name = null;
        new PrimaryKeyImpl(name, cols, table);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateNullTable() throws ModelException {
        SortedSet<CharSequence> cols = new TreeSet<CharSequence>();
        cols.add("test_id1");
        cols.add("test_id2");
        new PrimaryKeyImpl("pk_test", cols, null);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateNullColumns() throws ModelException {
        new PrimaryKeyImpl("pk_test", null, table);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateNoColumns() throws ModelException {
        SortedSet<CharSequence> cols = new TreeSet<CharSequence>();
        new PrimaryKeyImpl("pk_test", cols, table);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateUniqueKeyWithSameColumnsAsPrimaryKey() throws ModelException {
        SortedSet<CharSequence> cols = new TreeSet<CharSequence>();
        cols.add("test_id1");
        cols.add("test_id2");
        new PrimaryKeyImpl("pk_test", cols, table);
        new UniqueKeyImpl("uk_test", cols, table);
    }
    
    @Test(expected = ModelException.class)
    public void testCreatePrimaryKeyWithSameColumnsAsUniqueKey() throws ModelException {
        SortedSet<CharSequence> cols = new TreeSet<CharSequence>();
        cols.add("test_id1");
        cols.add("test_id2");
        new UniqueKeyImpl("uk_test", cols, table);
        new PrimaryKeyImpl("pk_test", cols, table);
    }

    @Test(expected = ModelException.class)
    public void testCreateWithNullableColumn() throws ModelException {
        SortedSet<CharSequence> cols = new TreeSet<CharSequence>();
        cols.add("test_id1");
        cols.add("test_nullable");
        new PrimaryKeyImpl("pk_test", cols, table);
    }
    
    @Test
    public void testContainsColumn() throws ModelException {
        SortedSet<CharSequence> cols = new TreeSet<CharSequence>();
        cols.add("test_id1");
        cols.add("test_id2");
        PrimaryKeyImpl pk = new PrimaryKeyImpl("pk_test", cols, table);
        Column id1 = table.getColumnByName("test_id1");
        Assert.assertTrue(pk.contains(id1));
        Assert.assertTrue(id1.partOfPrimaryKey());
    }
    
    @Test
    public void testArePrimaryColumnsReferencing() throws ModelException {
        
        // Create table #1 primary key
        SortedSet<CharSequence> cols = new TreeSet<CharSequence>();
        cols.add("test_id1");
        cols.add("test_id2");
        new PrimaryKeyImpl("pk_test", cols, table);
        
        // Create table #3 with primary key as foreign key
        TableImpl table3 = new TableImpl("tbl_test3", database);
        table3.createColumn("table3_fk_pk1", ColumnType.INTEGER, true, 8, 0);
        table3.createColumn("table3_fk_pk2", ColumnType.DATE, true, 8, 0);
        table3.createColumn("table3_data", ColumnType.REAL, true, 8, 10);
        SortedSet<CharSequence> pkCols = new TreeSet<CharSequence>();
        pkCols.add("table3_fk_pk1");
        pkCols.add("table3_fk_pk2");
        table3.createPrimaryKey("table3_pk", pkCols);
        Map<CharSequence, CharSequence> constraints = new HashMap<CharSequence, CharSequence>();
        constraints.put("table3_fk_pk1", "test_id1");
        constraints.put("table3_fk_pk2", "test_id2");
        new ForeignKeyImpl("fk_test", "tbl_test3", "tbl_test", constraints, database);
        Assert.assertTrue(table3.getPrimaryKey().isForeignKey());
    }
    
    @Test
    public void testGetForeignKeys() throws ModelException {
        
        // Create table #1 primary key
        SortedSet<CharSequence> cols = new TreeSet<CharSequence>();
        cols.add("test_id1");
        cols.add("test_id2");
        new PrimaryKeyImpl("pk_test", cols, table);
        
        // Create table #3 with primary key as foreign key
        TableImpl table3 = new TableImpl("tbl_test3", database);
        table3.createColumn("table3_pk1", ColumnType.INTEGER, true, 8, 0);
        table3.createColumn("table3_pk2", ColumnType.DATE, true, 8, 0);
        table3.createColumn("table3_pk3", ColumnType.CHARACTER, true, ColumnValueGenerationStrategy.ASSIGNED);
        table3.createColumn("table3_data", ColumnType.REAL, true, 8, 10);
        SortedSet<CharSequence> pkCols = new TreeSet<CharSequence>();
        pkCols.add("table3_pk1");
        pkCols.add("table3_pk2");
        pkCols.add("table3_pk3");
        table3.createPrimaryKey("table3_pk", pkCols);
        Map<CharSequence, CharSequence> constraints = new HashMap<CharSequence, CharSequence>();
        constraints.put("table3_pk1", "test_id1");
        constraints.put("table3_pk2", "test_id2");
        new ForeignKeyImpl("fk_test", "tbl_test3", "tbl_test", constraints, database);
        Assert.assertNotNull(table3.getPrimaryKey().getForeignKeys());
        Assert.assertEquals(1, table3.getPrimaryKey().getForeignKeys().size());
    }
    
}
