package net.sourceforge.greenvine.model.naming.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import junit.framework.Assert;
import net.sourceforge.greenvine.model.api.ColumnType;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.impl.CatalogImpl;
import net.sourceforge.greenvine.model.api.impl.ColumnImpl;
import net.sourceforge.greenvine.model.api.impl.DatabaseImpl;
import net.sourceforge.greenvine.model.api.impl.EntityImpl;
import net.sourceforge.greenvine.model.api.impl.ForeignKeyImpl;
import net.sourceforge.greenvine.model.api.impl.ModelImpl;
import net.sourceforge.greenvine.model.api.impl.TableImpl;
import net.sourceforge.greenvine.model.naming.RdbmsNamingConventions;
import net.sourceforge.greenvine.model.naming.testutils.NamingConventionFactory;

import org.junit.Before;
import org.junit.Test;

public class NameExtractorHelperTest {

    private RdbmsNamingConventions namingConventions;
    
	private ModelNameExtractorHelper helper;

    private DatabaseImpl database;

    private CatalogImpl catalog;
	
	@Before
	public void setUp() throws Exception {
		
		// Create extractor
		helper = new ModelNameExtractorHelper();
		
		// Set up NameExtractorConfig
		namingConventions = NamingConventionFactory.getTestNamingConvention();
		
		// Create database
		database = new DatabaseImpl("TEST_DB", namingConventions);
		
		// Create model
		ModelImpl model = new ModelImpl("testModel");
		
		// Create catalog
		catalog = model.createCatalog("testCat", database);
		
	}

	
	@Test
	public void testExtractTableName() throws Exception {
		EntityTableNameExtractor extractor = new EntityTableNameExtractor(namingConventions);
		DatabaseObjectNameImpl original = new DatabaseObjectNameImpl("DBO", "TBL_THIS_IS_THE_ORIGINAL");
		String expected = "dbo.thisIsTheOriginal";
		TableImpl table = database.createTable(original);
		EntityNameImpl processed = extractor.extractName(catalog, table);
		Assert.assertEquals(expected, processed.toString());
		
	}
	
	@Test
	public void testExtractSimpleIdentityName() throws Exception {
		SimpleIdentityNameExtractor extractor = new SimpleIdentityNameExtractor(namingConventions);
		ColumnNameImpl original = new ColumnNameImpl("PK_THIS_IS_THE_ORIGINAL");
		String expected = "thisIsTheOriginal";
		TableImpl table = database.createTable(new DatabaseObjectNameImpl("DBO", "TABLE"));
		ColumnImpl item = table.createColumn(original, ColumnType.INTEGER, false, 4, 0);
		EntityImpl entity = catalog.createEntity("test", "entity", table.getName());
		FieldNameImpl processed = extractor.extractName(entity, item);
		Assert.assertEquals(expected, processed.toString());
		
	}
	
	@Test
	public void testExtractForeignColumnName() throws Exception {
		ChildNameExtractor extractor = new ChildNameExtractor(namingConventions);
		ColumnNameImpl original = new ColumnNameImpl("FK_THIS_IS_THE_ORIGINAL");
		String expected = "thisIsTheOriginal";
		TableImpl table1 = database.createTable("TABLE1");
        ColumnImpl item1 = table1.createColumn(original, ColumnType.INTEGER, true, 4, 0);
        SortedSet<ColumnNameImpl> pkCols1 = new TreeSet<ColumnNameImpl>();
        pkCols1.add(item1.getName());
        table1.createPrimaryKey(new DatabaseObjectNameImpl("DBO", "pk_test"), pkCols1);
        TableImpl table2 = database.createTable("TABLE2");
        ColumnImpl item2 = table2.createColumn(original, ColumnType.INTEGER, true, 4, 0);
        ColumnImpl item3 = table2.createColumn("other_fk", ColumnType.INTEGER, true, 4, 0);
        Map<ColumnNameImpl,ColumnNameImpl> columnConstraints = new HashMap<ColumnNameImpl, ColumnNameImpl>();
        columnConstraints.put(item2.getName(), item1.getName());
        ForeignKeyImpl key = database.createForeignKey(original, table2.getName(), table1.getName(), columnConstraints);
        EntityImpl inverse = catalog.createEntity("test", "entity", table2.getName());
        // NB this to force the extractor to use fallback name strategy
        Map<ColumnNameImpl,ColumnNameImpl> columnConstraints2 = new HashMap<ColumnNameImpl, ColumnNameImpl>();
        columnConstraints2.put(item3.getName(), item1.getName());
        database.createForeignKey("OTHER_FK", table2.getName(), table1.getName(), columnConstraints2);
        FieldNameImpl processed = extractor.extractName(inverse, key);
		Assert.assertEquals(expected, processed.toString());
		
	}
	
	@Test
	public void testExtractDataColumnName() throws Exception {
		SimplePropertyNameExtractor extractor = new SimplePropertyNameExtractor(namingConventions);
		String original = "THIS_IS_THE_ORIGINAL";
		String expected = "thisIsTheOriginal";
		TableImpl table = database.createTable("TABLE");
        ColumnImpl item = table.createColumn(original, ColumnType.INTEGER, false, 4, 0);
        EntityImpl entity = catalog.createEntity("test", "entity", table.getName());
		FieldNameImpl processed = extractor.extractName(entity, item);
		Assert.assertEquals(expected, processed.toString());
	}
	
	@Test
    public void testIsCamelCase() throws ModelException {
        String camel = "thisIsTheOriginal";
        Assert.assertTrue(helper.isCamelCase(camel));
        String pascal = "ThisIsTheOriginal";
        Assert.assertFalse(helper.isCamelCase(pascal));
        String space = "this Is The Original";
        Assert.assertFalse(helper.isCamelCase(space));
        String lower = "thisistheoriginal";
        Assert.assertTrue(helper.isCamelCase(lower)); // Lower case is a valid camel case too.
        String upper = "THISISTHEORIGINAL";
        Assert.assertFalse(helper.isCamelCase(upper));
    }
	
	@Test
    public void testIsPascalCase() throws ModelException {
        String pascal = "ThisIsTheOriginal";
        Assert.assertTrue(helper.isPascalCase(pascal));
        String camel = "thisIsTheOriginal";
        Assert.assertFalse(helper.isPascalCase(camel));
        String space = "this Is The Original";
        Assert.assertFalse(helper.isPascalCase(space));
        String lower = "thisistheoriginal";
        Assert.assertFalse(helper.isPascalCase(lower));
        String upper = "THISISTHEORIGINAL";
        Assert.assertFalse(helper.isPascalCase(upper));
    }
	
	@Test
    public void testIsLowerCase() throws ModelException {
        String pascal = "ThisIsTheOriginal";
        Assert.assertFalse(helper.isLowerCase(pascal));
        String camel = "thisIsTheOriginal";
        Assert.assertFalse(helper.isLowerCase(camel));
        String space = "this Is The Original";
        Assert.assertFalse(helper.isLowerCase(space));
        String lower = "thisistheoriginal";
        Assert.assertTrue(helper.isLowerCase(lower));
        String upper = "THISISTHEORIGINAL";
        Assert.assertFalse(helper.isLowerCase(upper));
    }
	
	@Test
    public void testIsUpperCase() throws ModelException {
        String pascal = "ThisIsTheOriginal";
        Assert.assertFalse(helper.isUpperCase(pascal));
        String camel = "thisIsTheOriginal";
        Assert.assertFalse(helper.isUpperCase(camel));
        String space = "this Is The Original";
        Assert.assertFalse(helper.isUpperCase(space));
        String lower = "thisistheoriginal";
        Assert.assertFalse(helper.isUpperCase(lower));
        String upper = "THISISTHEORIGINAL";
        Assert.assertTrue(helper.isUpperCase(upper));
    }
	
	
}
