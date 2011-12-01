package test.pack.data.greenvine.dao.dbo.impl.springhibernate;

import java.util.List;

import org.junit.Assert;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.inject.annotation.InjectInto;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.orm.hibernate.annotation.HibernateSessionFactory;

import test.pack.data.greenvine.entity.dbo.Desk;
import test.pack.data.greenvine.entity.dbo.DeskTestUtils;

@HibernateSessionFactory("hibernate.cfg.xml")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class DeskDaoImplIntegrationTest {
    
    @TestedObject
    private DeskDaoImpl deskDao = null;

    @SuppressWarnings("unused")
    @HibernateSessionFactory
    @InjectInto(property = "sessionFactory")
    private SessionFactory sessionFactory;
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/dbo/DeskBeforeCreateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/dbo/DeskAfterCreateDataSet.xml")
    public void testCreateDesk() throws Exception {
    
        // Create new entity
        Desk create = new Desk();
                    
        // Set identity
        create.setDeskId(Integer.valueOf(1));                
                 
        // Populate simple properties
        create.setCode("s");

        // Create in database                    
        deskDao.createDesk(create);
    
    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/dbo/DeskBeforeUpdateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/dbo/DeskAfterUpdateDataSet.xml")
    public void testUpdateDesk() throws Exception {
        
        // Load entity and modify
        Desk update = deskDao.loadDesk(DeskTestUtils.getDefaultIdentity());
        update.setCode("t");

        // Update entity
        deskDao.updateDesk(update);
                    
    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/dbo/DeskBeforeDeleteDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/dbo/DeskAfterDeleteDataSet.xml")
    public void testRemoveDesk() throws Exception {

        // Delete by id
        deskDao.removeDesk(DeskTestUtils.getDefaultIdentity());

    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/dbo/DeskFindAllDataSet.xml")
    public void testFindAllDesks() throws Exception {
    
        List<Desk> results = deskDao.findAllDesks();
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
        
    }


    @Test
    @DataSet("/test/pack/data/greenvine/entity/dbo/DeskFindDataSet.xml")
    public void testLoadDeskByIdentity() throws Exception {

        Desk result = deskDao.loadDesk(DeskTestUtils.getDefaultIdentity());
        Assert.assertNotNull(result);
        Assert.assertEquals("s", result.getCode());

    }

}