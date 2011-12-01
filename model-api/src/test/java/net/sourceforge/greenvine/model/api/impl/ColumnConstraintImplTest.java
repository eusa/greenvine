package net.sourceforge.greenvine.model.api.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import net.sourceforge.greenvine.model.api.ColumnType;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.impl.ColumnConstraintImpl;
import net.sourceforge.greenvine.model.api.impl.ColumnImpl;
import net.sourceforge.greenvine.model.api.impl.DatabaseImpl;
import net.sourceforge.greenvine.model.api.impl.ForeignKeyImpl;
import net.sourceforge.greenvine.model.api.impl.TableImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ColumnConstraintImplTest {
    
    private DatabaseImpl database;
    private TableImpl table1;
    private TableImpl table2;
    private ColumnImpl referencing1;
    private ColumnImpl referencing2;
    private ColumnImpl referenced;
    private ForeignKeyImpl foreignKey;
    
    @Before
    public void setUp() throws ModelException {
        
        // Create database
        this.database = new DatabaseImpl("testdb");
        
        // Create table #1
        this.table1 = new TableImpl("tbl_test1", database);
        referenced = this.table1.createColumn("table1_id", ColumnType.INTEGER, true, 8, 0);
        //this.table1.createColumn("table1_data", ColumnType.REAL, true, 15, 10);
        SortedSet<String> pkCols1 = new TreeSet<String>();
        pkCols1.add("table1_id");
        this.table1.createPrimaryKey("table1_pk", pkCols1);
        
        // Create table #2
        this.table2 = new TableImpl("tbl_test2", database);
        this.table2.createColumn("table2_id", ColumnType.INTEGER, true, 8, 0);
        referencing1 = this.table2.createColumn("test_fk1", ColumnType.INTEGER, true, 8, 0);
        referencing2 = this.table2.createColumn("test_fk2", ColumnType.INTEGER, true, 8, 0);
        SortedSet<String> pkCols2 = new TreeSet<String>();
        pkCols2.add("table2_id");
        this.table2.createPrimaryKey("table2_pk", pkCols2);
        
        // Create foreign key
        Map<String, String> constraints = new HashMap<String, String>();
        constraints.put("test_fk1", "table1_id");
        foreignKey = new ForeignKeyImpl("fk_test", "tbl_test2", "tbl_test1", constraints, database);
        
    }
    
    @Test
    public void testCreateValid() throws ModelException {
        new ColumnConstraintImpl(foreignKey, referencing2, referenced); 
        Assert.assertEquals(2, foreignKey.getColumnConstraintCount());
        Assert.assertFalse(foreignKey.getColumnConstraint(0).equals(foreignKey.getColumnConstraint(1)));
    }
    
    @Test(expected = ModelException.class)
    public void testCreateNullForeignKey() throws ModelException {
        new ColumnConstraintImpl(null, referencing2, referenced);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateNullReferencingColumn() throws ModelException {
        new ColumnConstraintImpl(foreignKey, null, referenced);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateNullReferencedColumn() throws ModelException {
        new ColumnConstraintImpl(foreignKey, referencing2, null);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateDuplicateConstraint() throws ModelException {
        new ColumnConstraintImpl(foreignKey, referencing1, referenced);
    }
}
