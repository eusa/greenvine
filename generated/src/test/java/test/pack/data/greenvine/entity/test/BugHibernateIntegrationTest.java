package test.pack.data.greenvine.entity.test;

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

import test.pack.data.greenvine.entity.test.Bug;
import test.pack.data.greenvine.entity.test.BugTestUtils;
import test.pack.data.greenvine.entity.test.User;
import test.pack.data.greenvine.entity.test.UserTestUtils;

@HibernateSessionFactory("hibernate.cfg.xml")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class BugHibernateIntegrationTest {
    
    @HibernateSessionFactory
    private SessionFactory factory;
   
    @Test
    @DataSet("BugBeforeCreateDataSet.xml")
    @ExpectedDataSet("BugAfterCreateDataSet.xml")
    public void testCreateBug() throws Exception {
    
        // Create new entity
        Bug create = new Bug();
                    
        // Set identity
        create.setBugId(Integer.valueOf(1));
  
        // populate simple properties
        create.setDescription("s");
        create.setOpen(Boolean.TRUE);
        create.setTitle("s");
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
                
        // populate dependencies
        User reporter = (User)session.get(User.class, UserTestUtils.getDefaultIdentity());
        create.setReporter(reporter);
        User owner = (User)session.get(User.class, UserTestUtils.getDefaultIdentity());
        create.setOwner(owner);

        // create in database        
        session.persist(create); 

    }
    
    @Test
    @DataSet("BugBeforeUpdateDataSet.xml")
    @ExpectedDataSet("BugAfterUpdateDataSet.xml")
    public void testUpdateBug() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Load and modify
        Bug result = (Bug)session.get(Bug.class, BugTestUtils.getDefaultIdentity());
        result.setDescription("t");
        result.setOpen(Boolean.FALSE);
        result.setTitle("t");
        // update
        session.save(result);

    }
    
    @Test
    @DataSet("BugBeforeDeleteDataSet.xml")
    @ExpectedDataSet("BugAfterDeleteDataSet.xml")
    public void testRemoveBug() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // delete
        Bug result = (Bug)session.get(Bug.class, BugTestUtils.getDefaultIdentity());
        session.delete(result);

    }
    
    @Test
    @DataSet("BugFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllBugs() throws Exception {

        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Create query
        Query query = session.createQuery("from test.pack.data.greenvine.entity.test.Bug");
        
        // Get results 
        List<Bug> results = query.list();
        
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }


	@Test
	@DataSet("BugFindDataSet.xml")
    public void testFindBugByIdentity() throws Exception {
    
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Get object from Hibernate
        Bug result  = (Bug)session.get(Bug.class, BugTestUtils.getDefaultIdentity());
        
        // Test properties
        Assert.assertNotNull(result);
        Assert.assertEquals("s", result.getDescription());
        Assert.assertEquals(Boolean.TRUE, result.getOpen());
        Assert.assertEquals("s", result.getTitle());

    }
    
}