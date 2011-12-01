package test.pack.data.greenvine.entity.test;

import java.util.List;
import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.junit.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.orm.jpa.annotation.JpaEntityManagerFactory;

import test.pack.data.greenvine.entity.test.Activity;
import test.pack.data.greenvine.entity.test.ActivityTestUtils;
import test.pack.data.greenvine.entity.test.Timesheet;
import test.pack.data.greenvine.entity.test.TimesheetTestUtils;

@JpaEntityManagerFactory(persistenceUnit = "greenvine")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class ActivityJpaIntegrationTest {
    
    @PersistenceContext
    EntityManager entityManager;

    @Test
    @DataSet("ActivityBeforeCreateDataSet.xml")
    @ExpectedDataSet("ActivityAfterCreateDataSet.xml")
    public void testCreateActivity() throws Exception {
    
        // Create new entity
        Activity create = new Activity();
                    
        // Set identity
        create.setActivityId(Integer.valueOf(1));

        // Populate simple properties
        create.setDescription("s");
        create.setHours(new BigDecimal("100.1"));
                
        // Populate dependencies
        Timesheet timesheet = (Timesheet)entityManager.getReference(Timesheet.class, TimesheetTestUtils.getDefaultIdentity());
        create.setTimesheet(timesheet);

        // Create in database                    
        entityManager.persist(create); 

    }
    
    @Test
    @DataSet("ActivityBeforeUpdateDataSet.xml")
    @ExpectedDataSet("ActivityAfterUpdateDataSet.xml")
    public void testUpdateActivity() throws Exception {
                    
        // Load entity and modify
        Activity result = (Activity)entityManager.find(Activity.class, ActivityTestUtils.getDefaultIdentity());

        // Set simple properties
        result.setDescription("t");
        result.setHours(new BigDecimal("200.2"));

        // Update
        entityManager.merge(result);

    }
    
    @Test
    @DataSet("ActivityBeforeDeleteDataSet.xml")
    @ExpectedDataSet("ActivityAfterDeleteDataSet.xml")
    public void testRemoveActivity() throws Exception {
                    
        // Delete
        Activity result = (Activity)entityManager.find(Activity.class, ActivityTestUtils.getDefaultIdentity());
        entityManager.remove(result);

    }
    
    @Test
    @DataSet("ActivityFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllActivity() throws Exception {
                     
        // Create query
        Query query = entityManager.createQuery("from test.Activity");
                    
        // Get results 
        List<Activity> results = query.getResultList();
                    
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }
    
	@Test
	@DataSet("ActivityFindDataSet.xml")
    public void testFindActivityByIdentity() throws Exception {
                    
        // Get object 
        Activity result  = (Activity)entityManager.find(Activity.class, ActivityTestUtils.getDefaultIdentity());
                    
        // Test result
        Assert.assertNotNull(result);        

        // Test properties        
        Assert.assertEquals("s", result.getDescription());
        Assert.assertEquals(new BigDecimal("100.1"), result.getHours());

    }
    
}