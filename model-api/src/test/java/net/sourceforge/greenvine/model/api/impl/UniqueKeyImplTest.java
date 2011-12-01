package net.sourceforge.greenvine.model.api.impl;

import java.util.SortedSet;
import java.util.TreeSet;

import junit.framework.Assert;
import net.sourceforge.greenvine.model.api.ColumnType;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.naming.impl.ColumnNameImpl;

import org.junit.Before;
import org.junit.Test;

public class UniqueKeyImplTest {
    
    private DatabaseImpl database;
    private TableImpl table;
    
    @Before
    public void setUp() throws ModelException {
        this.database = new DatabaseImpl("testdb");
        this.table = new TableImpl("tbl_test", database);
        this.table.createColumn("test_id", ColumnType.INTEGER, true, 8, 0);
        this.table.createColumn("test_uk1", ColumnType.VARCHAR, true, 50, 0);
        this.table.createColumn("test_uk2", ColumnType.VARCHAR, true, 50, 0);
        this.table.createColumn("test_data", ColumnType.REAL, true, 15, 10);
    }

    @Test
    public void testCreateValid() throws ModelException {
        SortedSet<CharSequence> cols = new TreeSet<CharSequence>();
        cols.add("test_uk1");
        cols.add("test_uk2");
        UniqueKeyImpl uniqueKey = new UniqueKeyImpl("uk_test", cols, table);
        Assert.assertEquals("uk_test", uniqueKey.getName().toString());
        Assert.assertEquals(2, uniqueKey.getColumnCount());
        Assert.assertEquals(convertToColumnName(cols), uniqueKey.getColumnNames());
    }
    
    @Test(expected = ModelException.class)
    public void testCreateInvalidColumn() throws ModelException {
        SortedSet<CharSequence> cols = new TreeSet<CharSequence>();
        cols.add("test_uk1");
        cols.add("test_xx");
        new UniqueKeyImpl("uk_test", cols, table);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateNullName() throws ModelException {
        SortedSet<CharSequence> cols = new TreeSet<CharSequence>();
        cols.add("test_uk1");
        cols.add("test_uk2");
        new UniqueKeyImpl(null, cols, table);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateNullTable() throws ModelException {
        SortedSet<CharSequence> cols = new TreeSet<CharSequence>();
        cols.add("test_uk1");
        cols.add("test_uk2");
        new UniqueKeyImpl("uk_test", cols, null);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateNullColumns() throws ModelException {
        new UniqueKeyImpl("uk_test", null, table);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateNoColumns() throws ModelException {
        SortedSet<CharSequence> cols = new TreeSet<CharSequence>();
        new UniqueKeyImpl("uk_test", cols, table);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateUniqueKeyWithSameColumnsAsAnotherUniqueKey() throws ModelException {
        SortedSet<CharSequence> cols = new TreeSet<CharSequence>();
        cols.add("test_uk1");
        cols.add("test_uk2");
        new UniqueKeyImpl("uk_test1", cols, table);
        new UniqueKeyImpl("uk_test2", cols, table);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateUniqueKeyWithSameNameAsAnotherUniqueKey() throws ModelException {
        SortedSet<CharSequence> cols1 = new TreeSet<CharSequence>();
        cols1.add("test_uk1");
        cols1.add("test_uk2");
        SortedSet<CharSequence> cols2 = new TreeSet<CharSequence>();
        cols2.add("test_data");
        new UniqueKeyImpl("uk_test", cols1, table);
        new UniqueKeyImpl("uk_test", cols2, table);
    }
    
    public static SortedSet<ColumnNameImpl> convertToColumnName(SortedSet<? extends CharSequence> names) throws ModelException {
        SortedSet<ColumnNameImpl> dbNames = new TreeSet<ColumnNameImpl>();
        if (names != null) {
            for (CharSequence name : names) {
                dbNames.add(new ColumnNameImpl(name));
            }
        } 
        return dbNames;
    }
    
}
