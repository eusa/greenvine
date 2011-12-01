package test.pack.data.greenvine.entity.dbo;

import java.util.List;

import org.junit.Assert;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.orm.hibernate.annotation.HibernateSessionFactory;

import test.pack.data.greenvine.entity.dbo.Stand;
import test.pack.data.greenvine.entity.dbo.StandTestUtils;

@HibernateSessionFactory("hibernate.cfg.xml")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class StandHibernateIntegrationTest {
    
    @HibernateSessionFactory
    private SessionFactory factory;
   
    @Test
    @DataSet("StandBeforeCreateDataSet.xml")
    @ExpectedDataSet("StandAfterCreateDataSet.xml")
    public void testCreateStand() throws Exception {
    
        // Create new entity
        Stand create = new Stand();
                    
        // Set identity
        create.setStandId(Integer.valueOf(1));
  
        // populate simple properties
        create.setDescription("s");
        // Get Hibernate Session
        Session session = factory.getCurrentSession();

        // create in database        
        session.persist(create); 

    }
    
    @Test
    @DataSet("StandBeforeUpdateDataSet.xml")
    @ExpectedDataSet("StandAfterUpdateDataSet.xml")
    public void testUpdateStand() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Load and modify
        Stand result = (Stand)session.get(Stand.class, StandTestUtils.getDefaultIdentity());
        result.setDescription("t");
        // update
        session.save(result);

    }
    
    @Test
    @DataSet("StandBeforeDeleteDataSet.xml")
    @ExpectedDataSet("StandAfterDeleteDataSet.xml")
    public void testRemoveStand() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // delete
        Stand result = (Stand)session.get(Stand.class, StandTestUtils.getDefaultIdentity());
        session.delete(result);

    }
    
    @Test
    @DataSet("StandFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllStands() throws Exception {

        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Create query
        Query query = session.createQuery("from test.pack.data.greenvine.entity.dbo.Stand");
        
        // Get results 
        List<Stand> results = query.list();
        
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }


	@Test
	@DataSet("StandFindDataSet.xml")
    public void testFindStandByIdentity() throws Exception {
    
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Get object from Hibernate
        Stand result  = (Stand)session.get(Stand.class, StandTestUtils.getDefaultIdentity());
        
        // Test properties
        Assert.assertNotNull(result);
        Assert.assertEquals("s", result.getDescription());

    }
    
}