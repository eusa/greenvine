package net.sourceforge.greenvine.model.api.impl;

import junit.framework.Assert;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.impl.CatalogImpl;
import net.sourceforge.greenvine.model.api.impl.DatabaseImpl;
import net.sourceforge.greenvine.model.api.impl.ModelImpl;
import net.sourceforge.greenvine.model.naming.testutils.NamingConventionFactory;

import org.junit.Before;
import org.junit.Test;


public class CatalogImplTest {

    private ModelImpl model;
    private DatabaseImpl database;
    
    @Before
    public void setUp() throws ModelException {
        this.model = new ModelImpl("test");
        this.database = new DatabaseImpl("TEST_DB", NamingConventionFactory.getTestNamingConvention());
    }

    @Test
    public void testCreateValid() throws ModelException {
        CatalogImpl catalog = new CatalogImpl("test", model, database);
        Assert.assertEquals("test", catalog.getName().toString());
    }
    
    @Test(expected = ModelException.class)
    public void testCreateNullName() throws ModelException {
        new CatalogImpl(null, model, database);
    }

    @Test(expected = ModelException.class)
    public void testCreateEmptyName() throws ModelException {
        new CatalogImpl("", model, database);
    }
    
    @Test(expected = ModelException.class)
    public void testCreateNullModel() throws ModelException {
        new CatalogImpl("blah", null, database);
    }
    
}
