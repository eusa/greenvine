package net.sourceforge.greenvine.model.naming.impl;

import junit.framework.Assert;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.naming.RdbmsNamingConventions;
import net.sourceforge.greenvine.model.naming.testutils.NamingConventionFactory;

import org.junit.Before;
import org.junit.Test;

public class DatabaseNameBuilderTest {
    
    private RdbmsNamingConventions conventions;
    
    @Before
    public void setUp() {
        this.conventions = NamingConventionFactory.getTestNamingConvention();
    }

    @Test
    public void testRemoveSuffixAndPrefixIncludingSeparator() throws ModelException {
        RdbmsNameSegmentBuilderImpl builder = new RdbmsNameSegmentBuilderImpl("TBL_USER_THING", conventions.getTable());
        builder.removeSuffixPrefixIncludingSeparators();
        Assert.assertEquals("USER_THING", builder.toString());
    }
    
    @Test
    public void testSurroundWithSuffixAndPrefix() throws ModelException {
        RdbmsNameSegmentBuilderImpl ukName = new RdbmsNameSegmentBuilderImpl("USER_THING", conventions.getUniqueKey());
        ukName.surroundWithSuffixAndPrefix();
        Assert.assertEquals("UK_USER_THING_UNIQUE", ukName.toString());
    }

}
