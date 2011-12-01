package net.sourceforge.greenvine.model.naming.impl;

import junit.framework.Assert;
import net.sourceforge.greenvine.model.api.ModelException;

import org.junit.Test;

public class CamelCaseNameSegmentTest {
    
    @Test
    public void testCreateValid() throws ModelException {
        CamelCaseNameSegmentImpl test = new CamelCaseNameSegmentImpl("test");
        Assert.assertEquals("test", test.toString());
    }
    
    @Test
    public void testEquality() throws ModelException {
        CamelCaseNameSegmentImpl test1 = new CamelCaseNameSegmentImpl("test");
        CamelCaseNameSegmentImpl test2 = new CamelCaseNameSegmentImpl("test");
        Assert.assertEquals(test1, test2);
        Assert.assertEquals(test2, test1);
    }

}
