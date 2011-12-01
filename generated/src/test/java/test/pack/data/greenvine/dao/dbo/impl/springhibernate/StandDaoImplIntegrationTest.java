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

import test.pack.data.greenvine.entity.dbo.Stand;
import test.pack.data.greenvine.entity.dbo.StandTestUtils;

@HibernateSessionFactory("hibernate.cfg.xml")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class StandDaoImplIntegrationTest {
    
    @TestedObject
    private StandDaoImpl standDao = null;

    @SuppressWarnings("unused")
    @HibernateSessionFactory
    @InjectInto(property = "sessionFactory")
    private SessionFactory sessionFactory;
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/dbo/StandBeforeCreateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/dbo/StandAfterCreateDataSet.xml")
    public void testCreateStand() throws Exception {
    
        // Create new entity
        Stand create = new Stand();
                    
        // Set identity
        create.setStandId(Integer.valueOf(1));                
                 
        // Populate simple properties
        create.setDescription("s");

        // Create in database                    
        standDao.createStand(create);
    
    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/dbo/StandBeforeUpdateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/dbo/StandAfterUpdateDataSet.xml")
    public void testUpdateStand() throws Exception {
        
        // Load entity and modify
        Stand update = standDao.loadStand(StandTestUtils.getDefaultIdentity());
        update.setDescription("t");

        // Update entity
        standDao.updateStand(update);
                    
    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/dbo/StandBeforeDeleteDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/dbo/StandAfterDeleteDataSet.xml")
    public void testRemoveStand() throws Exception {

        // Delete by id
        standDao.removeStand(StandTestUtils.getDefaultIdentity());

    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/dbo/StandFindAllDataSet.xml")
    public void testFindAllStands() throws Exception {
    
        List<Stand> results = standDao.findAllStands();
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
        
    }


    @Test
    @DataSet("/test/pack/data/greenvine/entity/dbo/StandFindDataSet.xml")
    public void testLoadStandByIdentity() throws Exception {

        Stand result = standDao.loadStand(StandTestUtils.getDefaultIdentity());
        Assert.assertNotNull(result);
        Assert.assertEquals("s", result.getDescription());

    }

}