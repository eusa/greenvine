package test.pack.data.greenvine.dao.test.impl.springhibernate;

import java.util.List;
import java.math.BigDecimal;

import org.junit.Assert;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.inject.annotation.InjectInto;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.orm.hibernate.annotation.HibernateSessionFactory;

import test.pack.data.greenvine.entity.test.Activity;
import test.pack.data.greenvine.entity.test.ActivityTestUtils;
import test.pack.data.greenvine.entity.test.Timesheet;
import test.pack.data.greenvine.entity.test.TimesheetTestUtils;

@HibernateSessionFactory("hibernate.cfg.xml")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class ActivityDaoImplIntegrationTest {
    
    @TestedObject
    private ActivityDaoImpl activityDao = null;

    @HibernateSessionFactory
    @InjectInto(property = "sessionFactory")
    private SessionFactory sessionFactory;
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/ActivityBeforeCreateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/ActivityAfterCreateDataSet.xml")
    public void testCreateActivity() throws Exception {
    
        // Create new entity
        Activity create = new Activity();
                    
        // Set identity
        create.setActivityId(Integer.valueOf(1));                
                 
        // Populate simple properties
        create.setDescription("s");
        create.setHours(new BigDecimal("100.1"));

        // Populate dependencies
        Session session = sessionFactory.getCurrentSession();
        
        Timesheet timesheet = (Timesheet)session.load(Timesheet.class, TimesheetTestUtils.getDefaultIdentity());
        create.setTimesheet(timesheet);

        // Create in database                    
        activityDao.createActivity(create);
    
    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/ActivityBeforeUpdateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/ActivityAfterUpdateDataSet.xml")
    public void testUpdateActivity() throws Exception {
        
        // Load entity and modify
        Activity update = activityDao.loadActivity(ActivityTestUtils.getDefaultIdentity());
        update.setDescription("t");
        update.setHours(new BigDecimal("200.2"));

        // Update entity
        activityDao.updateActivity(update);
                    
    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/ActivityBeforeDeleteDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/ActivityAfterDeleteDataSet.xml")
    public void testRemoveActivity() throws Exception {

        // Delete by id
        activityDao.removeActivity(ActivityTestUtils.getDefaultIdentity());

    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/ActivityFindAllDataSet.xml")
    public void testFindAllActivitys() throws Exception {
    
        List<Activity> results = activityDao.findAllActivitys();
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
        
    }


    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/ActivityFindDataSet.xml")
    public void testLoadActivityByIdentity() throws Exception {

        Activity result = activityDao.loadActivity(ActivityTestUtils.getDefaultIdentity());
        Assert.assertNotNull(result);
        Assert.assertEquals("s", result.getDescription());
        Assert.assertEquals(new BigDecimal("100.1"), result.getHours());

    }

}