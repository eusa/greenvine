package net.sourceforge.greenvine.model.naming.impl;

import junit.framework.Assert;
import net.sourceforge.greenvine.model.api.ModelException;

import org.junit.Test;

public class CamelCaseNameSegmentTest {
    
    @Test
    public void testCreateValid() throws ModelException {
        CamelCaseNameSegmentImpl test = new CamelCaseNameSegmentImpl("test");
        Assert.assertEquals("test", test.toString());
        CamelCaseNameSegmentImpl test2 = new CamelCaseNameSegmentImpl("testTest");
        Assert.assertEquals("testTest", test2.toString());
    }
    
    @Test(expected = ModelException.class)
    public void testCreateInvalid() throws ModelException {
        new CamelCaseNameSegmentImpl("TEST");
    }
    
    @Test(expected = ModelException.class)
    public void testCreateReserved() throws ModelException {
        new CamelCaseNameSegmentImpl("public");
    }
    
    @Test
    public void testEquality() throws ModelException {
        CamelCaseNameSegmentImpl test1 = new CamelCaseNameSegmentImpl("test");
        CamelCaseNameSegmentImpl test2 = new CamelCaseNameSegmentImpl("test");
        Assert.assertEquals(test1, test2);
        Assert.assertEquals(test2, test1);
    }

}
