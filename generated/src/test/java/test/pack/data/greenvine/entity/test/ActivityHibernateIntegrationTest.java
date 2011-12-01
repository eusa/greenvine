package test.pack.data.greenvine.entity.test;

import java.util.List;
import java.math.BigDecimal;

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

import test.pack.data.greenvine.entity.test.Activity;
import test.pack.data.greenvine.entity.test.ActivityTestUtils;
import test.pack.data.greenvine.entity.test.Timesheet;
import test.pack.data.greenvine.entity.test.TimesheetTestUtils;

@HibernateSessionFactory("hibernate.cfg.xml")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class ActivityHibernateIntegrationTest {
    
    @HibernateSessionFactory
    private SessionFactory factory;
   
    @Test
    @DataSet("ActivityBeforeCreateDataSet.xml")
    @ExpectedDataSet("ActivityAfterCreateDataSet.xml")
    public void testCreateActivity() throws Exception {
    
        // Create new entity
        Activity create = new Activity();
                    
        // Set identity
        create.setActivityId(Integer.valueOf(1));
  
        // populate simple properties
        create.setDescription("s");
        create.setHours(new BigDecimal("100.1"));
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
                
        // populate dependencies
        Timesheet timesheet = (Timesheet)session.get(Timesheet.class, TimesheetTestUtils.getDefaultIdentity());
        create.setTimesheet(timesheet);

        // create in database        
        session.persist(create); 

    }
    
    @Test
    @DataSet("ActivityBeforeUpdateDataSet.xml")
    @ExpectedDataSet("ActivityAfterUpdateDataSet.xml")
    public void testUpdateActivity() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Load and modify
        Activity result = (Activity)session.get(Activity.class, ActivityTestUtils.getDefaultIdentity());
        result.setDescription("t");
        result.setHours(new BigDecimal("200.2"));
        // update
        session.save(result);

    }
    
    @Test
    @DataSet("ActivityBeforeDeleteDataSet.xml")
    @ExpectedDataSet("ActivityAfterDeleteDataSet.xml")
    public void testRemoveActivity() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // delete
        Activity result = (Activity)session.get(Activity.class, ActivityTestUtils.getDefaultIdentity());
        session.delete(result);

    }
    
    @Test
    @DataSet("ActivityFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllActivitys() throws Exception {

        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Create query
        Query query = session.createQuery("from test.pack.data.greenvine.entity.test.Activity");
        
        // Get results 
        List<Activity> results = query.list();
        
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }


	@Test
	@DataSet("ActivityFindDataSet.xml")
    public void testFindActivityByIdentity() throws Exception {
    
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Get object from Hibernate
        Activity result  = (Activity)session.get(Activity.class, ActivityTestUtils.getDefaultIdentity());
        
        // Test properties
        Assert.assertNotNull(result);
        Assert.assertEquals("s", result.getDescription());
        Assert.assertEquals(new BigDecimal("100.1"), result.getHours());

    }
    
}