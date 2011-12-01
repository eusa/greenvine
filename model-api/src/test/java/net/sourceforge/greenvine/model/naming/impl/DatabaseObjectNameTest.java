package net.sourceforge.greenvine.model.naming.impl;

import junit.framework.Assert;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.naming.DatabaseObjectName;

import org.junit.Test;

public class DatabaseObjectNameTest {
    
    @Test
    public void testCreateValid() throws ModelException {
        DatabaseObjectNameImpl test = new DatabaseObjectNameImpl("schema", "object");
        Assert.assertEquals("schema.object", test.toString());
        Assert.assertEquals("object", test.getObjectName().toString());
        Assert.assertEquals("schema", test.getSchemaName().toString());
    }
    
    @Test
    public void testEquality() throws ModelException {
        DatabaseObjectName test1 = new DatabaseObjectNameImpl("schema", "object");
        DatabaseObjectName test2 = new DatabaseObjectNameImpl("schema", "object");
        Assert.assertEquals(test1, test2);
        Assert.assertEquals(test2, test1);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateNullObjectName() throws ModelException {
        new DatabaseObjectNameImpl("schema", null);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateEmptyObjectName() throws ModelException {
        new DatabaseObjectNameImpl("schema", "");
    }
    
    @Test(expected = ModelException.class)
    public void testCreateInvalidObjectName() throws ModelException {
        new DatabaseObjectNameImpl("schema", "test.invalid");
    }
    
    @Test(expected = ModelException.class)
    public void testCreateEmptySchemaName() throws ModelException {
        new DatabaseObjectNameImpl("", "test");
    }
    
    @Test
    public void testCreateNullSchemaName() throws ModelException {
        new DatabaseObjectNameImpl(null, "test");
    }
    
    @Test
    public void testParseValid() throws ModelException {
        DatabaseObjectNameImpl test = DatabaseObjectNameImpl.parse("schema.object");
        Assert.assertEquals("schema.object", test.toString());
        Assert.assertEquals("object", test.getObjectName().toString());
        Assert.assertEquals("schema", test.getSchemaName().toString());
        
        DatabaseObjectNameImpl test2 = DatabaseObjectNameImpl.parse("object");
        Assert.assertEquals("object", test2.toString());
        Assert.assertEquals("object", test2.getObjectName().toString());
        Assert.assertNull(test2.getSchemaName());
    }
    
}
