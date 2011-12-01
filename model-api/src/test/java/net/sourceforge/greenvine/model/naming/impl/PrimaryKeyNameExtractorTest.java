package net.sourceforge.greenvine.model.naming.impl;

import java.util.SortedSet;
import java.util.TreeSet;

import junit.framework.Assert;
import net.sourceforge.greenvine.model.api.ColumnType;
import net.sourceforge.greenvine.model.api.ColumnValueGenerationStrategy;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.impl.ColumnImpl;
import net.sourceforge.greenvine.model.api.impl.DatabaseImpl;
import net.sourceforge.greenvine.model.api.impl.TableImpl;
import net.sourceforge.greenvine.model.naming.RdbmsNamingConventions;
import net.sourceforge.greenvine.model.naming.testutils.NamingConventionFactory;

import org.junit.Before;
import org.junit.Test;

public class PrimaryKeyNameExtractorTest {
    
    private RdbmsNamingConventions conventions;

    private DatabaseImpl database;
    
    @Before
    public void setUp() throws Exception {
        
        // Set up NameExtractorConfig
        conventions = NamingConventionFactory.getTestNamingConvention();
        
        // Create database
        database = new DatabaseImpl("test_db", conventions);
        
    }
    
    @Test
    public void testGePrimaryKeyName() throws ModelException {
        PrimaryKeyNameExtractor helper = new PrimaryKeyNameExtractor();
        TableImpl table = database.createTable("TBL_TEST_NAME");
        ColumnImpl pkCol1 = table.createColumn("UK_COL_1", ColumnType.VARCHAR, true, ColumnValueGenerationStrategy.ASSIGNED);
        SortedSet<ColumnNameImpl> pkCols = new TreeSet<ColumnNameImpl>();
        pkCols.add(pkCol1.getName());
        DatabaseObjectNameImpl pkName = helper.extractName(database, table);
        Assert.assertEquals("PK_TEST_NAME", pkName.toString());
    }

}
