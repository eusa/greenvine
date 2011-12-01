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

import test.pack.data.greenvine.entity.dbo.Desk;
import test.pack.data.greenvine.entity.dbo.DeskTestUtils;

@HibernateSessionFactory("hibernate.cfg.xml")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class DeskHibernateIntegrationTest {
    
    @HibernateSessionFactory
    private SessionFactory factory;
   
    @Test
    @DataSet("DeskBeforeCreateDataSet.xml")
    @ExpectedDataSet("DeskAfterCreateDataSet.xml")
    public void testCreateDesk() throws Exception {
    
        // Create new entity
        Desk create = new Desk();
                    
        // Set identity
        create.setDeskId(Integer.valueOf(1));
  
        // populate simple properties
        create.setCode("s");
        // Get Hibernate Session
        Session session = factory.getCurrentSession();

        // create in database        
        session.persist(create); 

    }
    
    @Test
    @DataSet("DeskBeforeUpdateDataSet.xml")
    @ExpectedDataSet("DeskAfterUpdateDataSet.xml")
    public void testUpdateDesk() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Load and modify
        Desk result = (Desk)session.get(Desk.class, DeskTestUtils.getDefaultIdentity());
        result.setCode("t");
        // update
        session.save(result);

    }
    
    @Test
    @DataSet("DeskBeforeDeleteDataSet.xml")
    @ExpectedDataSet("DeskAfterDeleteDataSet.xml")
    public void testRemoveDesk() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // delete
        Desk result = (Desk)session.get(Desk.class, DeskTestUtils.getDefaultIdentity());
        session.delete(result);

    }
    
    @Test
    @DataSet("DeskFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllDesks() throws Exception {

        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Create query
        Query query = session.createQuery("from test.pack.data.greenvine.entity.dbo.Desk");
        
        // Get results 
        List<Desk> results = query.list();
        
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }


	@Test
	@DataSet("DeskFindDataSet.xml")
    public void testFindDeskByIdentity() throws Exception {
    
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Get object from Hibernate
        Desk result  = (Desk)session.get(Desk.class, DeskTestUtils.getDefaultIdentity());
        
        // Test properties
        Assert.assertNotNull(result);
        Assert.assertEquals("s", result.getCode());

    }
    
}