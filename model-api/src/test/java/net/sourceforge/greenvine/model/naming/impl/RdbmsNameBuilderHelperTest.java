package net.sourceforge.greenvine.model.naming.impl;

import junit.framework.Assert;
import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.ColumnType;
import net.sourceforge.greenvine.model.api.ColumnValueGenerationStrategy;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.impl.DatabaseImpl;
import net.sourceforge.greenvine.model.api.impl.TableImpl;
import net.sourceforge.greenvine.model.naming.RdbmsNamingConventions;
import net.sourceforge.greenvine.model.naming.testutils.NamingConventionFactory;

import org.junit.Before;
import org.junit.Test;

public class RdbmsNameBuilderHelperTest {
    
    private RdbmsNamingConventions convention;
    private DatabaseImpl database;
    
    @Before
    public void setUp() throws Exception {
        // Create naming convention
        convention = NamingConventionFactory.getTestNamingConvention();
        
        // Create database
        database = new DatabaseImpl("TEST_DB", convention);
    }
    
    @Test
    public void testProcessItemName() throws Exception {
        RdbmsNameBuilderHelper helper = new RdbmsNameBuilderHelper(convention.getTable());
        String original = "THIS_IS_THE_ORIGINAL";
        String expected = "thisIsTheOriginal";
        TableImpl table = database.createTable(original);
        CharSequence processed = helper.extractModelName(table.getName().getObjectName());
        Assert.assertEquals(expected, processed.toString());
    }
    
    @Test
    public void testProcessItemNameLowerCase() throws Exception {
        RdbmsNameBuilderHelper helper = new RdbmsNameBuilderHelper(convention.getTable());
        String original = "this_is_the_original";
        String expected = "thisIsTheOriginal";
        TableImpl table = database.createTable(original);
        CharSequence processed = helper.extractModelName(table.getName().getObjectName());
        Assert.assertEquals(expected, processed.toString());
    }
    
    @Test
    public void testProcessItemNamePascalCase() throws Exception {
        RdbmsNameBuilderHelper helper = new RdbmsNameBuilderHelper(convention.getTable());
        String original = "ThisIsTheOriginal";
        String expected = "thisIsTheOriginal";
        TableImpl table = database.createTable(original);
        CharSequence processed = helper.extractModelName(table.getName().getObjectName());
        Assert.assertEquals(expected, processed.toString());
    }
    
    @Test
    public void testProcessItemNamePascalCaseSeparators() throws Exception {
        RdbmsNameBuilderHelper helper = new RdbmsNameBuilderHelper(convention.getTable());
        String original = "This_Is_The_Original";
        String expected = "thisIsTheOriginal";
        TableImpl table = database.createTable(original);
        CharSequence processed = helper.extractModelName(table.getName().getObjectName());
        Assert.assertEquals(expected, processed.toString());
    }
    
    @Test
    public void testProcessReferenceName() throws Exception {
        RdbmsNameBuilderHelper helper = new RdbmsNameBuilderHelper(convention.getForeignColumn());
        ColumnNameImpl original = new ColumnNameImpl("FK_THIS_IS_THE_ORIGINAL_ID");
        String expected = "thisIsTheOriginal";
        TableImpl table = database.createTable("TBL");
        Column column = table.createColumn(original, ColumnType.INTEGER, true, ColumnValueGenerationStrategy.ASSIGNED);
        CharSequence processed = helper.extractModelName(column);
        Assert.assertEquals(expected, processed.toString());
    }
    
    @Test
    public void testRemovePrefixIncludingSeparator() throws ModelException {
        RdbmsNameBuilderHelper helper = new RdbmsNameBuilderHelper(convention.getPrimaryKey());
        StringBuilder target = new StringBuilder("pK_test");
        Assert.assertEquals("test", helper.removePrefixIncludingSeparator(target).toString());
    }
    
    @Test
    public void testRemoveSuffixIncludingSeparator() throws ModelException {
        RdbmsNameBuilderHelper helper = new RdbmsNameBuilderHelper(convention.getUniqueKey());
        StringBuilder target = new StringBuilder("test_uniQue");
        Assert.assertEquals("test", helper.removeSuffixIncludingSeparator(target).toString());
    }

    @Test
    public void testInsertSeparatorsAtCapitals() throws ModelException {
        RdbmsNameBuilderHelper helper = new RdbmsNameBuilderHelper(convention.getUniqueKey());
        StringBuilder original = new StringBuilder("thisIsTheOriginal");
        String expected = "this_Is_The_Original";
        CharSequence processed = helper.insertSeparatorAtCapitals(original);
        Assert.assertEquals(expected, processed.toString());
    }
    
    @Test
    public void testStripNonAlphanumerics() throws ModelException {
        RdbmsNameBuilderHelper helper = new RdbmsNameBuilderHelper(convention.getUniqueKey());
        StringBuilder original = new StringBuilder("th$i%s_I£s_T*he_O  ri^ginal");
        String expected = "this_Is_The_Original";
        CharSequence processed = helper.stripAllNonAlphanumeric(original);
        Assert.assertEquals(expected, processed.toString());
    }
    
    @Test
    public void testConvertUnseparatedStringToPascalCase() throws ModelException {
        RdbmsNameBuilderHelper helper = new RdbmsNameBuilderHelper(convention.getUniqueKey());
        Assert.assertEquals("HtmlParser", helper.convertToPascalCaseUnseparated(new StringBuilder("HTMLParser")).toString());
        Assert.assertEquals("DCloudThing", helper.convertToPascalCaseUnseparated(new StringBuilder("DCloudThing")).toString());
        Assert.assertEquals("PcThing", helper.convertToPascalCaseUnseparated(new StringBuilder("PCThing")).toString());
        Assert.assertEquals("HtmlToXmlConverter", helper.convertToPascalCaseUnseparated(new StringBuilder("HTMLToXMLConverter")).toString());
        Assert.assertEquals("Wsdl2Java", helper.convertToPascalCaseUnseparated(new StringBuilder("WSDL2Java")).toString());
        Assert.assertEquals("Wsdl2Java", helper.convertToPascalCaseUnseparated(new StringBuilder("WSDL2JAVA")).toString());
        Assert.assertEquals("Catch22Reader", helper.convertToPascalCaseUnseparated(new StringBuilder("CATCH22READER")).toString());
        Assert.assertEquals("Allincaps", helper.convertToPascalCaseUnseparated(new StringBuilder("ALLINCAPS")).toString());
        Assert.assertEquals("Allinlowercase", helper.convertToPascalCaseUnseparated(new StringBuilder("allinlowercase")).toString());
        Assert.assertEquals("ATrickyOne", helper.convertToPascalCaseUnseparated(new StringBuilder("aTrickyONE")).toString());
    }
}
